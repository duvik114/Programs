package expression.parser;

import expression.BinOperator;
import expression.evaluation.Evaluation;
import expression.GeneralExpression;

public class Xor<T extends Number> extends BinOperator<T> {

    private Evaluation<T> evaluation;

    public Xor(GeneralExpression<T> exp1, GeneralExpression<T> exp2, Evaluation<T> evaluation) {
        super(exp1, exp2, evaluation, 6);
    }

    @Override
    protected T makeEvaluate(T evaluate1, T evaluate2) {
        return evaluation.xorT(evaluate1, evaluate2);
        //return evaluate1 ^ evaluate2;
    }

    @Override
    protected String makeString() {
        return "^";
    }
}