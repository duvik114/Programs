package expression.generic.parser;

import expression.generic.BinOperator;
import expression.evaluation.Evaluation;
import expression.generic.GeneralExpression;

public class And<T extends Number> extends BinOperator<T> {

    public And(GeneralExpression<T> exp1, GeneralExpression<T> exp2, Evaluation<T> evaluation) {
        super(exp1, exp2, evaluation, 5);
    }

    @Override
    protected T makeEvaluate(T evaluate1, T evaluate2) {
        return evaluation.andT(evaluate1, evaluate2);
        //return evaluate1 & evaluate2;
    }

    @Override
    protected String makeString() {
        return "&";
    }
}
