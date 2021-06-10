package expression.generic;

import expression.evaluation.Evaluation;

public class Const<T extends Number> implements GeneralExpression<T> {
    private final int constant;

    private final Evaluation<T> evaluation;

    public Const(int constant, Evaluation<T> evaluation) {
        this.constant = constant;
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object expression) {
        if ((expression != null)&&(expression.getClass() == Const.class)) {
            Const<?> c = (Const<?>) expression;
            return constant == c.getConstant();
        }
        return false;
    }

    @Override
    public String toString() {
        //return  constant.toString();
        return Integer.toString(constant);
    }

    public int getConstant() {
        return constant;
    }

    @Override
    public T evaluate(T valueX, T valueY, T valueZ) {
        return evaluation.constT(constant);
        //return constant;
    }

    @Override
    public T evaluate(T valueX) {
        return evaluation.constT(constant);
        //return constant;
    }

    @Override
    public int hashCode() {
        //return 31 * constant.hashCode();
        return 31 * Integer.hashCode(constant);
    }
}
