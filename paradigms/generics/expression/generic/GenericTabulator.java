package expression.generic;

import expression.evaluation.*;
import expression.generic.parser.ExpressionParser;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];

        switch (mode) {
            case "i" -> {
                IntegerEvaluation integerEvaluation = new IntegerEvaluation();
                ExpressionParser<Integer> expressionParser = new ExpressionParser<>(integerEvaluation);
                GeneralExpression<Integer> tripleExpression = expressionParser.parse(expression);
                for (int i = 0; i <= x2 - x1; i++) {
                    for (int j = 0; j <= y2 - y1; j++) {
                        for (int k = 0; k <= z2 - z1; k++) {
                            try {
                                int value = tripleExpression.evaluate(x1 + i, y1 + j, z1 + k);
                                res[i][j][k] = value;
                            } catch (ArithmeticException e) {
                                res[i][j][k] = null;
                            }
                        }
                    }
                }
            }
            case "u" -> {
                UIntegerEvaluation uintegerEvaluation = new UIntegerEvaluation();
                ExpressionParser<Integer> expressionParser = new ExpressionParser<>(uintegerEvaluation);
                GeneralExpression<Integer> tripleExpression = expressionParser.parse(expression);
                for (int i = 0; i <= x2 - x1; i++) {
                    for (int j = 0; j <= y2 - y1; j++) {
                        for (int k = 0; k <= z2 - z1; k++) {
                            try {
                                int value = tripleExpression.evaluate(x1 + i, y1 + j, z1 + k);
                                res[i][j][k] = value;
                            } catch (ArithmeticException e) {
                                res[i][j][k] = null;
                            }
                        }
                    }
                }
            }
            case "f" -> {
                FloatEvaluation floatEvaluation = new FloatEvaluation();
                ExpressionParser<Float> expressionParser = new ExpressionParser<>(floatEvaluation);
                GeneralExpression<Float> tripleExpression = expressionParser.parse(expression);
                for (float i = 0; i <= x2 - x1; i++) {
                    for (float j = 0; j <= y2 - y1; j++) {
                        for (float k = 0; k <= z2 - z1; k++) {
                            try {
                                float value = tripleExpression.evaluate((float) x1 + i, (float) y1 + j, (float) z1 + k);
                                res[(int) i][(int) j][(int) k] = value;
                            } catch (ArithmeticException e) {
                                res[(int) i][(int) j][(int) k] = null;
                            }
                        }
                    }
                }
            }
            case "b" -> {
                ByteEvaluation byteEvaluation = new ByteEvaluation();
                ExpressionParser<Byte> expressionParser = new ExpressionParser<>(byteEvaluation);
                GeneralExpression<Byte> tripleExpression = expressionParser.parse(expression);
                for (byte i = 0; i <= x2 - x1; i++) {
                    for (byte j = 0; j <= y2 - y1; j++) {
                        for (byte k = 0; k <= z2 - z1; k++) {
                            try {
                                byte value = tripleExpression.evaluate((byte) (x1 + i), (byte) (y1 + j), (byte) (z1 + k));
                                res[i][j][k] = value;
                            } catch (ArithmeticException e) {
                                res[i][j][k] = null;
                            }
                        }
                    }
                }
            }
            case "d" -> {
                DoubleEvaluation doubleEvaluation = new DoubleEvaluation();
                ExpressionParser<Double> expressionParser = new ExpressionParser<>(doubleEvaluation);
                GeneralExpression<Double> tripleExpression = expressionParser.parse(expression);
                for (double i = 0; i <= x2 - x1; i++) {
                    for (double j = 0; j <= y2 - y1; j++) {
                        for (double k = 0; k <= z2 - z1; k++) {
                            double value = tripleExpression.evaluate(x1 + i, y1 + j, z1 + k);
                            res[(int) i][(int) j][(int) k] = value;
                        }
                    }
                }
            }
            case "bi" -> {
                BigIntegerEvaluation bigIntegerEvaluation = new BigIntegerEvaluation();
                ExpressionParser<BigInteger> expressionParserB = new ExpressionParser<>(bigIntegerEvaluation);
                GeneralExpression<BigInteger> tripleExpressionB = expressionParserB.parse(expression);
                for (int i = 0; i <= x2 - x1; i++) {
                    BigInteger valX = BigInteger.valueOf(x1 + i);
                    for (int j = 0; j <= y2 - y1; j++) {
                        BigInteger valY = BigInteger.valueOf(y1 + j);
                        for (int k = 0; k <= z2 - z1; k++) {
                            BigInteger valZ = BigInteger.valueOf(z1 + k);
                            try {
                                BigInteger value = tripleExpressionB.evaluate(valX, valY, valZ);
                                res[i][j][k] = value;
                            } catch (ArithmeticException e) {
                                res[i][j][k] = null;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
}
