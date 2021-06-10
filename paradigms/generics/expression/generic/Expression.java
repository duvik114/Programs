package expression.generic;

public interface Expression<T extends Number> extends ToMiniString {
    T evaluate(T valueX);
}