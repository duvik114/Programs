package expression.parser;

import expression.BinOperator;
import expression.evaluation.Evaluation;
import expression.GeneralExpression;

public class Or<T extends Number> extends BinOperator<T> {

    private Evaluation<T> evaluation;

    public Or(GeneralExpression<T> exp1, GeneralExpression<T> exp2, Evaluation<T> evaluation) {
        super(exp1, exp2, evaluation, 7);
    }

    @Override
    protected T makeEvaluate(T evaluate1, T evaluate2) {
        return evaluation.orT(evaluate1, evaluate2);
        //return evaluate1 | evaluate2;
    }

    @Override
    protected String makeString() {
        return "|";
    }
}