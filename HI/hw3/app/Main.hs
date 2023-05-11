{-# LANGUAGE BlockArguments #-}

module Main (
  main
) where

import System.Console.Haskeline
import HW3.Parser (parse)
import HW3.Evaluator
import HW3.Pretty
import HW3.Action
import qualified Data.Set as S
import Control.Monad.Cont (liftIO)

main :: IO ()
main = runInputT defaultSettings loop
    where 
        loop :: InputT IO ()
        loop = do
            minput <- getInputLine "hi> "
            case minput of
                Just ":q" -> return ()
                Just input -> do
                    case (parse input) of
                        Right val -> do
                            evaluated <- liftIO $ runHIO (eval val) (S.fromList [])
                            case evaluated of
                                Right v -> outputStrLn $ show (prettyValue v)
                                Left e -> outputStrLn (show e)
                        Left err -> outputStrLn (show err)
                    loop
                Nothing -> return ()
