package expression.evaluation;

import java.math.BigInteger;

public class BigIntegerEvaluation implements Evaluation<BigInteger> {

    @Override
    public BigInteger andT(BigInteger t1, BigInteger t2) throws NumberFormatException {
        return BigInteger.valueOf(t1.intValue() & t2.intValue());
    }

    @Override
    public BigInteger orT(BigInteger t1, BigInteger t2) throws NumberFormatException {
        return BigInteger.valueOf(t1.intValue() | t2.intValue());
    }

    @Override
    public BigInteger xorT(BigInteger t1, BigInteger t2) throws NumberFormatException {
        return BigInteger.valueOf(t1.intValue() ^ t2.intValue());
    }

    @Override
    public BigInteger addT(BigInteger t1, BigInteger t2) throws NumberFormatException {
        return t1.add(t2);
    }

    @Override
    public BigInteger constT(int i) {
        return BigInteger.valueOf(i);
    }

    @Override
    public BigInteger divT(BigInteger t1, BigInteger t2) throws NumberFormatException {
        return t1.divide(t2);
    }

    @Override
    public BigInteger mulT(BigInteger t1, BigInteger t2) throws NumberFormatException {
        return t1.multiply(t2);
    }

    @Override
    public BigInteger subT(BigInteger t1, BigInteger t2) throws NumberFormatException {
        return t1.subtract(t2);
    }

    @Override
    public BigInteger invT(BigInteger bigInteger) throws NumberFormatException {
        return bigInteger.multiply(BigInteger.valueOf(-1));
    }
}
