package expression.parser;

import expression.evaluation.Evaluation;
import expression.GeneralExpression;

public class Inverse<T extends Number> extends UnOperator<T> implements GeneralExpression<T> {

    public Inverse(GeneralExpression<T> expression,Evaluation<T> evaluation) {
        super(expression, evaluation, 1);
    }

    @Override
    protected T makeEvaluate(T evaluate) {
        return evaluation.invT(evaluate);
        //return -evaluate;
    }

    @Override
    public String toString() {
        return "(-(" + expression.toString() + "))";
    }

}
