module HW3.Parser (
    parse
) where

import Control.Monad ()
import Control.Monad.Combinators.Expr ( makeExprParser, Operator(InfixR, InfixL, InfixN) )
import qualified Data.Text as Text
import Data.Void (Void)
import Data.String ( IsString(fromString) )
import Data.Char ( isAlphaNum, isSpace, chr, isAlpha, isHexDigit )
import Numeric ( readHex )
import Data.Function ( (&) )
import HW3.Base
import qualified Text.Megaparsec as Megaparsec
import Text.Megaparsec
    ( (<|>),
      satisfy,
      between,
      choice,
      many,
      manyTill,
      sepBy,
      sepBy1,
      sepEndBy,
      Parsec,
      MonadParsec(notFollowedBy, eof, try),
      ParseErrorBundle )
import Text.Megaparsec.Char ( char, string )
import Text.Megaparsec.Char.Lexer (scientific, charLiteral)
import Data.List (intercalate)

parse :: String -> Either (ParseErrorBundle String Void) HiExpr
parse = Megaparsec.parse (pExpr <* eof) ""

type Parser = Parsec Void String

pExpr :: Parser HiExpr
pExpr = makeExprParser pTerm operatorTable

pTerm :: Parser HiExpr
pTerm = do
    _ <- mSpace
    f    <- choice 
        [ parens pExpr
        , HiExprValue <$> pValue
        , pList                
        , pDict
        ]
    args <- many $ choice
        [ try $ (mSpace *>) $ fmap (flip HiExprApply) $ parens commaItems
        , fmap (flip HiExprApply) $ char '.' 
            *> ((:[]) . HiExprValue . HiValueString . Text.pack . intercalate "-" 
                <$> (((:) <$> satisfy isAlpha <*> many (satisfy isAlphaNum)) `sepBy1` char '-'))
        , HiExprRun <$ char '!'
        ]
    _ <- mSpace
    pure $ foldl (&) f args

pList :: Parser HiExpr
pList = fmap (HiExprApply (HiExprValue $ HiValueFunction HiFunList)) $ between (char '[') (char ']') $ mSpace *> commaItems

pDict :: Parser HiExpr
pDict = fmap HiExprDict $ between (char '{') (char '}') $ pKV `sepBy` char ','
    where
        pKV = do
            e1 <- pExpr
            _ <- char ':'
            e2 <- pExpr
            pure (e1, e2)

commaItems :: Parser [HiExpr]
commaItems = pExpr `sepBy` char ','

operatorTable :: [[Operator Parser HiExpr]]
operatorTable =
    [   [ binary InfixL "/"  HiFunDiv
        , binary InfixL "*"  HiFunMul
        ]
        ,
        [ binary InfixL "-"  HiFunSub
        , binary InfixL "+"  HiFunAdd
        ]
        ,
        [ binary InfixN "<=" HiFunNotGreaterThan
        , binary InfixN ">=" HiFunNotLessThan
        , binary InfixN "==" HiFunEquals
        , binary InfixN "/=" HiFunNotEquals
        , binary InfixN "<"  HiFunLessThan
        , binary InfixN ">"  HiFunGreaterThan 
        ]
        ,
        [ binary InfixR "&&" HiFunAnd 
        ]
        ,
        [ binary InfixR "||" HiFunOr 
        ]
    ]

binary :: (Parser (HiExpr -> HiExpr -> HiExpr) -> Operator Parser HiExpr) -> String -> HiFun -> Operator Parser HiExpr
binary assoc name fun = 
    let p = (try $ (string name) <* notFollowedBy (string "="))
    in assoc ((\a b -> HiExprApply (HiExprValue $ HiValueFunction fun) [a, b]) <$ p)

pValue :: Parser HiValue
pValue = choice
    [ parens pValue
    , pNum
    , pFun
    , pBool
    , pNull
    , pString
    , pBytes
    , pAction
    ]

pAction :: Parser HiValue
pAction = fmap HiValueAction $ (HiActionCwd <$ string "cwd") <|> (HiActionNow <$ string "now")
pBytes :: Parser HiValue
pBytes = fmap HiValueBytes $ between (string "[#" <> mSpace) (mSpace <> string "#]") 
    $ fromString <$> pHex `sepEndBy` (char ' ')
  where
    pHex = do
        d1 <- satisfy isHexDigit
        d2 <- satisfy isHexDigit
        return $ chr $ fst $ head $ readHex [d1, d2]

pString :: Parser HiValue
pString = fmap (HiValueString . Text.pack) $ char '"' >> manyTill charLiteral (char '"')

pFun :: Parser HiValue
pFun = HiValueFunction <$> choice 
    [ HiFunDiv <$ string "div"
    , HiFunMul <$ string "mul"
    , HiFunSub <$ string "sub"
    , HiFunAdd <$ string "add"
    , HiFunNotLessThan    <$ string "not-less-than"
    , HiFunNotGreaterThan <$ string "not-greater-than"
    , HiFunNotEquals      <$ string "not-equals"
    , HiFunIf             <$ string "if"
    , HiFunNot            <$ string "not"
    , HiFunAnd            <$ string "and"
    , HiFunOr             <$ string "or"
    , HiFunLessThan       <$ string "less-than"
    , HiFunGreaterThan    <$ string "greater-than"
    , HiFunEquals         <$ string "equals"
    , HiFunLength        <$ string "length"
    , HiFunToUpper       <$ string "to-upper" 
    , HiFunToLower       <$ string "to-lower" 
    , HiFunReverse       <$ string "reverse" 
    , HiFunTrim          <$ string "trim"
    , HiFunList          <$ string "list"
    , HiFunRange         <$ string "range" 
    , HiFunFold          <$ string "fold"
    , HiFunPackBytes     <$ string "pack-bytes"
    , HiFunUnpackBytes   <$ string "unpack-bytes"  
    , HiFunZip           <$ string "zip"
    , HiFunUnzip         <$ string "unzip" 
    , HiFunEncodeUtf8    <$ string "encode-utf8"   
    , HiFunDecodeUtf8    <$ string "decode-utf8"  
    , HiFunSerialise     <$ string "serialise" 
    , HiFunDeserialise   <$ string "deserialise"
    , HiFunRead          <$ string "read"
    , HiFunWrite         <$ string "write"
    , HiFunMkDir         <$ string "mkdir"
    , HiFunChDir         <$ string "cd"
    , HiFunParseTime     <$ string "parse-time"
    , HiFunRand          <$ string "rand"
    , HiFunEcho          <$ string "echo"
    , HiFunCount         <$ string "count"
    , HiFunKeys          <$ string "keys"
    , HiFunValues        <$ string "values"
    , HiFunInvert        <$ string "invert"
    ]

pBool :: Parser HiValue
pBool = fmap HiValueBool $ 
        False <$ string "false" 
    <|> True  <$ string "true"

pNum :: Parser HiValue
pNum = fmap (HiValueNumber . toRational) $ 
    scientific
    <|> (char '-' >> negate <$> (mSpace *> scientific))

pNull :: Parser HiValue
pNull = HiValueNull <$ string "null"

parens :: Parser a -> Parser a
parens p = between (char '(') (mSpace *> char ')') $ mSpace *> p

mSpace :: Parser String
mSpace = many $ satisfy isSpace
