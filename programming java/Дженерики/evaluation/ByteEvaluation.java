package expression.evaluation;

public class ByteEvaluation implements Evaluation<Byte> {
    @Override
    public Byte andT(Byte t1, Byte t2) {
        return (byte) (t1 & t2);
    }

    @Override
    public Byte orT(Byte t1, Byte t2) {
        return (byte) (t1 | t2);
    }

    @Override
    public Byte xorT(Byte t1, Byte t2) {
        return (byte) (t1 ^ t2);
    }

    @Override
    public Byte addT(Byte t1, Byte t2) {
        return (byte) (t1 + t2);
    }

    @Override
    public Byte constT(int i) {
        return (byte) (i);
    }

    @Override
    public Byte divT(Byte t1, Byte t2) {
        return (byte) (t1 / t2);
    }

    @Override
    public Byte mulT(Byte t1, Byte t2) {
        return (byte) (t1 * t2);
    }

    @Override
    public Byte subT(Byte t1, Byte t2) {
        return (byte) (t1 - t2);
    }

    @Override
    public Byte invT(Byte aByte) {
        return (byte) (-aByte);
    }
}
