package expression.generic;

public class Variable<T extends Number> implements GeneralExpression<T> {
    private final String variable;
    private int hashCode = 0;

    //private Evaluation<T> evaluation;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public boolean equals(Object expression) {
        if ((expression != null)&&(expression.getClass() == Variable.class)) {
            Variable<?> var = (Variable<?>) expression;
            return variable.equals(var.getVariable());
        }
        return false;
    }

    @Override
    public String toString() {
        return variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public T evaluate(T valueX, T valueY, T valueZ) {
        return switch (variable) {
            case "x" -> valueX;
            case "y" -> valueY;
            case "z" -> valueZ;
            default -> null;
        };
    }

    @Override
    public T evaluate(T x) {
        return x;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = 31 * (variable == null ? 0 : variable.hashCode());
        }
        return hashCode;
    }
}
