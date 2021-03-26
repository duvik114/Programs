package expression;

import expression.evaluation.Evaluation;

public abstract class BinOperator<T extends Number> implements GeneralExpression<T> {
    protected final GeneralExpression<T> exp1;
    protected final GeneralExpression<T> exp2;
    private int hashCode = 0;
    private final int operationID;

    protected Evaluation<T> evaluation;

    protected BinOperator(GeneralExpression<T> exp1, GeneralExpression<T> exp2, Evaluation<T> evaluation, int operationID) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operationID = operationID;
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object expression) {
        if  (expression instanceof BinOperator<?>) {
            BinOperator<?> binOperator = (BinOperator<?>) expression;
            return  operationID == binOperator.getOperationID()
                    && exp1.equals(binOperator.getExp1())
                    && exp2.equals(binOperator.getExp2());
        }
        return false;
    }

    @Override
    public int hashCode() {//
        int result = 1;
        result = 31 * result + (exp1 == null ? 0 : exp1.hashCode());
        result = 31 * result + (exp2 == null ? 0 : exp2.hashCode());
        if (hashCode == 0) {
            hashCode = 31 * result * operationID;
        }
        return hashCode;
    }

    @Override
    public T evaluate(T valueX, T valueY, T valueZ) {
        return makeEvaluate(exp1.evaluate(valueX, valueY, valueZ), exp2.evaluate(valueX, valueY, valueZ));
    }

    @Override
    public T evaluate(T valueX) {
        return makeEvaluate(exp1.evaluate(valueX), exp2.evaluate(valueX));
    }

    @Override
    public String toString() {
        return "(" + makeString() + ")";
    }

    protected abstract String makeString();

    protected abstract T makeEvaluate(T evaluate, T evaluate1);

    public GeneralExpression<T> getExp1() {
        return exp1;
    }

    public int getOperationID() {
        return operationID;
    }

    public GeneralExpression<T> getExp2() {
        return exp2;
    }
}
