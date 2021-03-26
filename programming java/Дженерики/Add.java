package expression;

import expression.evaluation.Evaluation;

public class Add<T extends Number> extends BinOperator<T> {

    public Add(GeneralExpression<T> exp1, GeneralExpression<T> exp2, Evaluation<T> evaluation) {
        super(exp1, exp2, evaluation, 1);
    }

    @Override
    protected T makeEvaluate(T evaluate1, T evaluate2) {
        return evaluation.addT(evaluate1, evaluate2);
        //return evaluate1 + evaluate2;
    }

    @Override
    protected String makeString() {
        return exp1.toString() +  " + " + exp2.toString();
    }
}
