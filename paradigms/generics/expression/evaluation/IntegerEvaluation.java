package expression.evaluation;

public class IntegerEvaluation implements Evaluation<Integer> {

    @Override
    public Integer andT(Integer t1, Integer t2) {
        return t1 & t2;
    }

    @Override
    public Integer orT(Integer t1, Integer t2) {
        return t1 | t2;
    }

    @Override
    public Integer xorT(Integer t1, Integer t2) {
        return t1 ^ t2;
    }

    @Override
    public Integer addT(Integer t1, Integer t2) {
        return Math.addExact(t1, t2);
    }

    @Override
    public Integer constT(int i) {
        return i;
    }

    @Override
    public Integer divT(Integer t1, Integer t2) {
        return t1 / t2;
    }

    @Override
    public Integer mulT(Integer t1, Integer t2) {
        return Math.multiplyExact(t1, t2);
    }

    @Override
    public Integer subT(Integer t1, Integer t2) {
        return Math.subtractExact(t1, t2);
    }

    @Override
    public Integer invT(Integer integer) {
        if (integer == Integer.MIN_VALUE) {
            throw new ArithmeticException();
        }
        return -integer;
    }
}
