package expression.parser;

import expression.*;
import expression.evaluation.Evaluation;

public class ExpressionParser<T extends Number> extends BaseParser implements Parser {

    protected Evaluation<T> evaluation;

    public ExpressionParser(Evaluation<T> evaluation) {
        super(new StringSource(""));
        this.evaluation = evaluation;
    }

    @Override
    public GeneralExpression<T> parse(String expression) {
        setSource(new StringSource(expression + " "));
        nextVal();
        return bitOr();
    }

    private GeneralExpression<T> bitOr() {
        GeneralExpression<T> exp1 = bitXor();
        return makeExpression(exp1, '|', '|', "bitXor");
    }

    private GeneralExpression<T> bitXor() {
        GeneralExpression<T> exp1 = bitAnd();
        return makeExpression(exp1, '^', '^', "bitAnd");
    }

    private GeneralExpression<T> bitAnd() {
        GeneralExpression<T> exp1 = addSub();
        return makeExpression(exp1, '&', '&', "addSub");
    }

    private GeneralExpression<T> addSub() {
        GeneralExpression<T> exp1 = mulDiv();
        return makeExpression(exp1, '+', '-', "mulDiv");
    }

    private GeneralExpression<T> mulDiv() {
        GeneralExpression<T> exp1 = getVal();
        return makeExpression(exp1, '*', '/', "getVal");
    }

    private GeneralExpression<T> getVal() {
        GeneralExpression<T> exp = null;
        boolean minus = false;
        while (!eof()) {
            if (ch == '(') {
                nextVal();
                exp = bitOr();
                break;
            } else if (ch == '-') {
                minus = !minus;
                nextVal();
            } else if (between('0', '9')) {
                int num = toInt(minus);
                skipWhitespace();
                exp = new Const<T>(num, evaluation);
                return exp;
            } else {
                exp = new Variable<>(String.valueOf(ch));
                break;
            }
        }
        nextVal();
        if (minus) {
            exp = new Inverse<>(exp, evaluation);
        }
        if (exp == null) {
            throw error("error in function getVal");
        }
        return exp;
    }

    private GeneralExpression<T> makeExpression(GeneralExpression<T> exp1, char znak1, char znak2, String operation) {
        while (!eof() && ch != ')') {
            char znak = ch;
            if (znak != znak1 && znak != znak2) {
                return exp1;
            }
            nextVal();
            GeneralExpression<T> exp2 = makeOperation(operation);
            if (znak == znak1) {
                exp1 = makeThisOperation(exp1, exp2, znak1);
            } else {
                exp1 = makeThisOperation(exp1, exp2, znak2);
            }
            if (ch == ')') {
                return exp1;
            }
        }
        return exp1;
    }

    private GeneralExpression<T> makeOperation(String operation) {
        switch (operation) {
            case "bitXor":
                return bitXor();
            case "bitAnd":
                return bitAnd();
            case "addSub":
                return addSub();
            case "mulDiv":
                return mulDiv();
            case "getVal":
                return getVal();
            default: {
                throw error("Unsupported operation " + operation + " in makeOperation");
            }
        }
    }

    private GeneralExpression<T> makeThisOperation(GeneralExpression<T> exp1, GeneralExpression<T> exp2, char znak) {
        switch (znak) {
            case '|':
                return new Or<>(exp1, exp2, evaluation);
            case '^':
                return new Xor<>(exp1, exp2, evaluation);
            case '&':
                return new And<>(exp1, exp2, evaluation);
            case '+':
                return new Add<>(exp1, exp2, evaluation);
            case '-':
                return new Subtract<>(exp1, exp2, evaluation);
            case '*':
                return new Multiply<>(exp1, exp2, evaluation);
            case '/':
                return new Divide<>(exp1, exp2, evaluation);
            default: {
                throw error("Operation : " + znak + " is unsupported");
            }
        }
    }

    private int toInt(boolean minus) {
        StringBuilder sb = new StringBuilder();
        if (minus) {
            sb.append("-");
        }
        while (!eof() && between('0', '9')) {
            sb.append(ch);
            nextChar();
        }
        try {
            //return (T) evaluation.castToT(Integer.parseInt(sb.toString()));
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            throw error("Invalid number to parse : " + sb.toString());
        }
    }

    private void skipWhitespace() {
        while (test(' ') || test('\r') || test('\n') || test('\t')) {
            //skip
            //nextChar();
        }
    }

    private void nextVal() {
        nextChar();
        skipWhitespace();
    }
}
