package expression.evaluation;

public interface Evaluation<T extends Number> {
    T andT(T t1, T t2);
    T orT(T t1, T t2);
    T xorT(T t1, T t2);
    T addT(T t1, T t2);
    T constT(int i);
    T divT(T t1, T t2);
    T mulT(T t1, T t2);
    T subT(T t1, T t2);
    T invT(T t);
}
