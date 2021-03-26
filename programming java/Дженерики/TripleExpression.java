package expression;

public interface TripleExpression<T extends Number> extends ToMiniString {
    T evaluate(T valueX, T valueY, T valueZ);
}
