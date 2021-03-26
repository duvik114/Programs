package expression.evaluation;

public class FloatEvaluation implements Evaluation<Float> {
    @Override
    public Float andT(Float t1, Float t2) {
        return (float) ( t1.intValue() & t2.intValue() );
    }

    @Override
    public Float orT(Float t1, Float t2) {
        return (float) ( t1.intValue() | t2.intValue() );
    }

    @Override
    public Float xorT(Float t1, Float t2) {
        return (float) ( t1.intValue() ^ t2.intValue() );
    }

    @Override
    public Float addT(Float t1, Float t2) {
        return t1 + t2;
    }

    @Override
    public Float constT(int i) {
        return (float) i;
    }

    @Override
    public Float divT(Float t1, Float t2) {
        return t1 / t2;
    }

    @Override
    public Float mulT(Float t1, Float t2) {
        return t1 * t2;
    }

    @Override
    public Float subT(Float t1, Float t2) {
        return t1 - t2;
    }

    @Override
    public Float invT(Float aFloat) {
        return -aFloat;
    }
}
