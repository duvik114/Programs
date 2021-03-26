package expression.parser;

import expression.*;
import expression.evaluation.Evaluation;

public abstract class UnOperator<T extends Number> implements GeneralExpression<T> {
    protected final GeneralExpression<T> expression;
    private int hashCode = 0;
    private final int operationID;

    protected Evaluation<T> evaluation;

    protected UnOperator(GeneralExpression<T> exp, Evaluation<T> evaluation, int operationID) {
        this.expression = exp;
        this.operationID = operationID;
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object expression) {
        if  (expression instanceof UnOperator<?>) {
            UnOperator<?> unOperator = (UnOperator<?>) expression;
            return  operationID == unOperator.getOperationID()
                    && this.expression.equals(unOperator.getExpression());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = 31 * (expression == null ? 0 : expression.hashCode()) * operationID;
        }
        return hashCode;
    }

    @Override
    public T evaluate(T valueX, T valueY, T valueZ) {
        return makeEvaluate(expression.evaluate(valueX, valueY, valueZ));
    }

    @Override
    public T evaluate(T valueX) {
        return makeEvaluate(expression.evaluate(valueX));
    }

    public int getOperationID() {
        return operationID;
    }

    protected abstract T makeEvaluate(T evaluate);

    public GeneralExpression<T> getExpression() {
        return expression;
    }
}
