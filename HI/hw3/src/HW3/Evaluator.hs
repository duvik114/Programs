{-# LANGUAGE FlexibleInstances, LambdaCase, PatternSynonyms, OverloadedLists #-}

module HW3.Evaluator ( 
    eval
) where

import Data.Semigroup ( Semigroup(stimes) )
import Data.Char ( ord )
import Data.Foldable ( Foldable(toList) )
import qualified Data.ByteString as ByteString
import qualified Data.ByteString.Char8 as C8ByteString
import qualified Data.ByteString.Lazy as LByteString
import HW3.Base
import Control.Monad.Except
    ( when,
      MonadTrans(lift),
      ExceptT,
      foldM,
      runExceptT,
      MonadError(throwError) )
import qualified Codec.Compression.Zlib as Zlib
import qualified Codec.Serialise as Serialise
import qualified Data.Text as Text
import qualified Data.Text.Encoding as Text
import qualified Data.Sequence as Sequence
import Text.Read (readMaybe)
import qualified Data.Time as Time
import qualified Data.Map as Map
import qualified Data.List as List
import Data.Tuple (swap)
import Data.Ratio ( denominator, numerator )
import Data.Traversable (for)

type EvalT m a = ExceptT HiError m a

pattern FApp f args = HiExprApply (FExp f) args
pattern SApp s args = HiExprApply (SExp s) args
pattern LApp l args = HiExprApply (LExp l) args
pattern BApp b args = HiExprApply (BExp b) args
pattern DApp d args = HiExprApply (DExp d) args

pattern FExp f = HiExprValue (FVal f)
pattern BExp b = HiExprValue (BVal b)
pattern NExp n = HiExprValue (NVal n)
pattern SExp s = HiExprValue (SVal s)
pattern LExp l = HiExprValue (LVal l)
pattern DExp d = HiExprValue (DVal d)

pattern FVal f = HiValueFunction f
pattern NVal n = HiValueNumber n
pattern SVal s = HiValueString s
pattern BVal b = HiValueBytes b
pattern LVal l = HiValueList l
pattern DVal d = HiValueDict d

eval :: HiMonad m => HiExpr -> m (Either HiError HiValue)
eval = runExceptT . shortCircuit

shortCircuit :: HiMonad m => HiExpr -> EvalT m HiValue
shortCircuit = \case
    HiExprValue val -> pure val
    HiExprDict d -> fmap (HiValueDict . Map.fromList) $ for d $ \(k,v) -> do
        ke <- shortCircuit k
        ve <- shortCircuit v
        pure (ke, ve)
    SApp s args -> evalContainer s args
    LApp l args -> evalContainer l args
    BApp b args -> evalContainer b args
    DApp d args -> traverse shortCircuit args >>= \case
        [x] -> pure $ maybe HiValueNull id $ Map.lookup x d
        _   -> throwError HiErrorInvalidArgument 
    HiExprApply e@(HiExprDict _) args -> shortCircuit e >>= shortCircuit . (`HiExprApply` args) . HiExprValue
    HiExprRun expr -> shortCircuit expr >>= \case
        HiValueAction a -> lift $ runAction a 
        _               -> throwError HiErrorInvalidArgument
    FApp HiFunAnd       [l, r] -> do
        le <- shortCircuit l
        case le of
            HiValueBool False -> pure $ HiValueBool False
            HiValueNull       -> pure $ HiValueNull
            _                 -> shortCircuit r
    FApp HiFunOr       [l, r] -> do
        le <- shortCircuit l
        case le of
            HiValueBool False -> shortCircuit r
            HiValueNull       -> shortCircuit r
            _                 -> pure le
    FApp HiFunIf [expr, l, r] -> shortCircuit expr >>= \case
        HiValueBool True  -> shortCircuit l
        HiValueBool False -> shortCircuit r
        HiValueNull       -> shortCircuit r
        _                 -> throwError HiErrorInvalidArgument
    FApp f args -> traverse shortCircuit args >>= curry noShortCircuit f
    HiExprApply expr@(HiExprApply _ _) args2 -> do 
        f <- shortCircuit expr
        shortCircuit $ HiExprApply (HiExprValue f) args2
    HiExprApply _ _ -> throwError HiErrorInvalidFunction

noShortCircuit :: HiMonad m => (HiFun, [HiValue]) -> EvalT m HiValue
noShortCircuit = \case
    (HiFunDiv, a@[_ ,_]) -> case a of
        [NVal _, NVal 0] -> throwError HiErrorDivideByZero
        [NVal l, NVal r] -> pure $ HiValueNumber $ l / r
        [SVal l, SVal r] -> pure $ HiValueString $ l <> Text.pack "/" <> r
        _                -> throwError HiErrorInvalidArgument
    (HiFunMul, a@[_,_]) -> case a of
        [NVal l, NVal r] -> pure $ HiValueNumber $ l * r
        [SVal s, NVal n] -> mulContainer s n
        [LVal l, NVal n] -> mulContainer l n
        [BVal b, NVal n] -> mulContainer b n
        _                -> throwError HiErrorInvalidArgument
    (HiFunSub, a@[_,_]) -> case a of
        [NVal l       , NVal r       ] -> pure $ HiValueNumber $ l - r
        [HiValueTime l, HiValueTime r] -> pure $ HiValueNumber $ toRational $ Time.diffUTCTime l r
        _                              -> throwError HiErrorInvalidArgument
    (HiFunAdd, a@[_,_]) -> case a of
        [NVal l        , NVal r         ] -> pure $ HiValueNumber $ l + r
        [SVal l        , SVal r         ] -> pure $ HiValueString $ l <> r
        [HiValueList  l, HiValueList r  ] -> pure $ HiValueList   $ l <> r
        [HiValueBytes l, HiValueBytes r ] -> pure $ HiValueBytes  $ l <> r
        [HiValueTime  t, HiValueNumber n] -> pure $ HiValueTime   $ Time.addUTCTime (realToFrac n) t
        _                                 -> throwError HiErrorInvalidArgument
    (HiFunNot           , [a   ]) -> case a of
        HiValueBool b -> pure $ HiValueBool $ not b
        _             -> throwError HiErrorInvalidArgument
    (HiFunEquals        , [l, r]) -> pure $ HiValueBool $ l == r
    (HiFunNotEquals     , [l, r]) -> pure $ HiValueBool $ l /= r
    (HiFunLessThan      , [l, r]) -> pure $ HiValueBool $ l <  r
    (HiFunGreaterThan   , [l, r]) -> pure $ HiValueBool $ l >  r
    (HiFunNotLessThan   , [l, r]) -> pure $ HiValueBool $ l >= r
    (HiFunNotGreaterThan, [l, r]) -> pure $ HiValueBool $ l <= r
    (HiFunLength,  [v]) -> case v of
        HiValueString str -> pure $ HiValueNumber $ fromIntegral $ Text.length     str
        HiValueList   lst -> pure $ HiValueNumber $ fromIntegral $ Sequence.length lst
        HiValueBytes  bs  -> pure $ HiValueNumber $ fromIntegral $ ByteString.length bs
        _                 -> throwError HiErrorInvalidArgument
    (HiFunToUpper, [v]) -> case v of
        HiValueString str -> pure $ HiValueString $ Text.toUpper str
        _                 -> throwError HiErrorInvalidArgument
    (HiFunToLower, [v]) -> case v of
        HiValueString str -> pure $ HiValueString $ Text.toLower str
        _                 -> throwError HiErrorInvalidArgument
    (HiFunReverse, [v]) -> case v of
        HiValueString str -> pure $ HiValueString $ Text.reverse     str
        HiValueList   lst -> pure $ HiValueList   $ Sequence.reverse lst
        HiValueBytes  bs  -> pure $ HiValueBytes  $ ByteString.reverse bs
        _                 -> throwError HiErrorInvalidArgument
    (HiFunTrim,    [v]) -> case v of
        HiValueString str -> pure $ HiValueString $ Text.strip str
        _                 -> throwError HiErrorInvalidArgument
    (HiFunList,     vs) -> pure $ HiValueList $ Sequence.fromList vs
    (HiFunRange, a@[_,_]) -> case a of
        [NVal l, NVal r] -> pure . HiValueList . Sequence.fromList $ map HiValueNumber [l .. r]
        _                -> throwError HiErrorInvalidArgument
    (HiFunFold, arr) -> case arr of
        [_, LVal []                 ] -> pure HiValueNull
        [_, LVal [v]                ] -> pure v
        [f, LVal (v Sequence.:<| vs)] -> foldM (\a b -> shortCircuit $ HiExprApply (HiExprValue f) (map HiExprValue [a, b])) v vs
        _                             -> throwError HiErrorInvalidArgument
    (HiFunPackBytes, [a]) -> case a of
        LVal l -> do
            let validateHex (HiValueNumber r) = do
                    n <- readHiInt r
                    if n >= 0 && n < 256 then pure n else throwError HiErrorInvalidArgument 
                validateHex _ = throwError HiErrorInvalidArgument 
            ints <- traverse validateHex l
            pure $ HiValueBytes $ ByteString.pack $ fromIntegral <$> toList ints
        _ -> throwError HiErrorInvalidArgument 
    (HiFunUnpackBytes, [a]) -> case a of
        BVal b -> pure $ HiValueList $ Sequence.fromList $ cToHiList b
        _      -> throwError HiErrorInvalidArgument
    (HiFunEncodeUtf8, [a]) -> case a of
        SVal s -> pure $ HiValueBytes $ Text.encodeUtf8 s
        _      -> throwError HiErrorInvalidArgument
    (HiFunDecodeUtf8, [a]) -> case a of
        BVal b -> pure $ case Text.decodeUtf8' b of
            Right s -> HiValueString s
            _       -> HiValueNull
        _  -> throwError HiErrorInvalidArgument
    (HiFunZip, [a]) -> case a of
        BVal b -> pure $ HiValueBytes $ compress b
        _      -> throwError HiErrorInvalidArgument
    (HiFunUnzip, [a]) -> case a of
        BVal b -> pure $ HiValueBytes $ LByteString.toStrict $ Zlib.decompress $ LByteString.fromStrict b
        _      -> throwError HiErrorInvalidArgument
    (HiFunSerialise, [a]) -> pure $ HiValueBytes $ LByteString.toStrict $ Serialise.serialise a
    (HiFunDeserialise, [a]) -> case a of
        BVal b -> pure $ case Serialise.deserialiseOrFail $ LByteString.fromStrict b of
            Left _  -> HiValueNull
            Right v -> v
        _ -> throwError HiErrorInvalidArgument
    (HiFunRead, [a]) -> case a of
        SVal fp -> pure $ HiValueAction $ HiActionRead (Text.unpack fp)
        _       -> throwError HiErrorInvalidArgument
    (HiFunWrite, [ll, rr]) -> case (ll, rr) of
        (SVal l, BVal r) -> pure $ HiValueAction $ HiActionWrite (Text.unpack l) r
        (SVal l, SVal r) -> pure $ HiValueAction $ HiActionWrite (Text.unpack l) $ C8ByteString.pack $ Text.unpack r
        _                -> throwError HiErrorInvalidArgument
    (HiFunMkDir, [a]) -> case a of
        SVal fp -> pure $ HiValueAction $ HiActionMkDir (Text.unpack fp)
        _       -> throwError HiErrorInvalidArgument
    (HiFunChDir, [a]) -> case a of
        SVal fp -> pure $ HiValueAction $ HiActionChDir (Text.unpack fp)
        _       -> throwError HiErrorInvalidArgument
    (HiFunParseTime, [a]) -> case a of
        SVal s -> pure $ maybe HiValueNull HiValueTime (readMaybe $ Text.unpack s)
        _      -> throwError HiErrorInvalidArgument 
    (HiFunRand, [ll, rr]) -> case (ll, rr) of
        (NVal l, NVal r) -> do
            li <- readHiInt l
            ri <- readHiInt r
            when (max ri li > fromIntegral (maxBound :: Int) 
               || min ri li < fromIntegral (minBound :: Int)
               ) $ throwError HiErrorInvalidArgument
            pure $ HiValueAction $ HiActionRand (fromInteger li) (fromInteger ri)
        _ -> throwError HiErrorInvalidArgument 
    (HiFunEcho, [a]) -> case a of
        HiValueString s -> pure $ HiValueAction $ HiActionEcho s
        _               -> throwError HiErrorInvalidArgument
    (HiFunCount, [a]) -> case a of
        SVal t -> countContainer t
        LVal l -> countContainer l
        BVal b -> countContainer b
        _      -> throwError HiErrorInvalidArgument 
    (HiFunKeys, [a]) -> case a of
        DVal d -> pure $ HiValueList $ Sequence.fromList $ Map.keys d
        _      -> throwError HiErrorInvalidArgument 
    (HiFunValues, [a]) -> case a of
        DVal d -> pure $ HiValueList $ Sequence.fromList $ Map.elems d
        _      -> throwError HiErrorInvalidArgument 
    (HiFunInvert, [a]) -> case a of
        DVal d -> pure $ HiValueDict $ Map.map (HiValueList . Sequence.fromList) $ invertDict d
        _      -> throwError HiErrorInvalidArgument 
    (_, _)             -> throwError HiErrorArityMismatch

class Semigroup c => HiContainer c where
    cLength :: c -> Int
    cPack   :: c -> HiValue
    cIndex  :: c -> Int -> HiValue 
    cTake   :: Int -> c -> c
    cDrop   :: Int -> c -> c
    cToHiList :: c -> [HiValue]

instance HiContainer Text.Text where
    cLength  = Text.length
    cPack    = HiValueString
    cIndex c = cPack . Text.singleton . Text.index c
    cTake    = Text.take 
    cDrop    = Text.drop
    cToHiList = map (HiValueString . Text.singleton) . Text.unpack

instance HiContainer (Sequence.Seq HiValue) where
    cLength = Sequence.length
    cPack   = HiValueList 
    cIndex  = Sequence.index
    cTake   = Sequence.take
    cDrop   = Sequence.drop
    cToHiList = toList

instance HiContainer C8ByteString.ByteString where
    cLength = C8ByteString.length
    cPack   = HiValueBytes 
    cIndex i = HiValueNumber . fromIntegral . ord . C8ByteString.index i
    cTake   = C8ByteString.take
    cDrop   = C8ByteString.drop
    cToHiList = map (HiValueNumber . fromIntegral . ord) . C8ByteString.unpack

evalContainer :: (HiMonad m, HiContainer c) => c -> [HiExpr] -> EvalT m HiValue
evalContainer c args = traverse shortCircuit args >>= \case
    [NVal n] -> do
        i <- readHiInt n
        pure $ if i >= cLength c || i < 0
               then HiValueNull
               else cIndex c i
    [HiValueNull, HiValueNull] -> pure $ cPack c
    [HiValueNull, NVal n] -> do
        i <- readHiInt n
        let t = if i < 0 then i `mod` cLength c else i
        pure $ cPack $ cTake t c
    [NVal n, HiValueNull] -> do
        i <- readHiInt n
        pure . cPack $ cDrop i c
    [NVal fromI, NVal toI] -> do 
        from <- readHiInt fromI
        to   <- readHiInt toI
        let to' = if to < 0 then to `mod` cLength c else to
        pure $ cPack $ cTake (to' - from) $ cDrop from c
    _      -> throwError HiErrorInvalidArgument

mulContainer :: (HiMonad m, HiContainer c) => c -> Rational -> EvalT m HiValue
mulContainer c r =  case (ratToInt r) of
    Just i -> if i <= 0 
              then throwError HiErrorInvalidArgument 
              else pure $ cPack $ stimes i c
    _      -> throwError HiErrorInvalidArgument 

countContainer :: (HiMonad m, HiContainer c) => c -> EvalT m HiValue
countContainer = pure 
              . HiValueDict 
              . Map.fromList 
              . map (\xs -> (head xs, HiValueNumber $ fromIntegral $ length xs))
              . List.group 
              . List.sort 
              . cToHiList

invertDict :: (Show a, Show b, Eq b, Ord b, Ord a) => Map.Map a b -> Map.Map b [a]
invertDict d = Map.fromList $ map (fmap reverse) $ foldr f [] $ List.sort $ map swap $ Map.assocs d
    where f   (v',k') []            = [(v',[k'])]
          f   (v',k') ((v,ks):rest) = if v == v' then (v, k':ks):rest else (v',[k']):(v,ks):rest

compress :: C8ByteString.ByteString -> C8ByteString.ByteString
compress = LByteString.toStrict 
         . Zlib.compressWith Zlib.defaultCompressParams {Zlib.compressLevel = Zlib.bestCompression} 
         . LByteString.fromStrict

readHiInt :: (Integral i, HiMonad m) => Rational -> EvalT m i
readHiInt = maybe (throwError HiErrorInvalidArgument) pure . ratToInt

ratToInt :: Integral i => Rational -> Maybe i
ratToInt rat = case divMod (numerator rat) (denominator rat) of
    (num,0) -> Just $ fromIntegral num
    _       -> Nothing
