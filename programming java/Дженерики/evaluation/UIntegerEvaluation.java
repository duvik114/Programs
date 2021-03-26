package expression.evaluation;

public class UIntegerEvaluation extends IntegerEvaluation {

    @Override
    public Integer addT(Integer t1, Integer t2) {
        return t1 + t2;
    }

    @Override
    public Integer mulT(Integer t1, Integer t2) {
        return t1 * t2;
    }

    @Override
    public Integer subT(Integer t1, Integer t2) {
        return t1 - t2;
    }

    @Override
    public Integer invT(Integer integer) {
        return -integer;
    }
}
