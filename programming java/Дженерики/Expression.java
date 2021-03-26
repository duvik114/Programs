package expression;

public interface Expression<T extends Number> extends ToMiniString {
    T evaluate(T valueX);
}