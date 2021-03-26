package queue;

public class Main {

    public static void main(String[] args) {

        /*

        Model:
            [a1, a2, ... , an]
            n - size of queue

        I:
            n >= 0 && forall i = 1..n a[i] != null

        Immut:
            n == n' && forall i = 1..n: a[i] == a'[i]

        Pred: x != null
        Post: n == n' + 1 && a[n] == x && forall i = 1..n': a[i] == a'[i]
        enqueue(x)

        Pred: n > 0
        Post: n == n' - 1 && forall i = 1..n: a[i] == a'[i + 1] && R == a'[1]
        dequeue()

        Pred: n > 0
        Post: Immut && R == a[1]
        element()

        Pred: true
        Post: Immut && R == n
        size()

        Pred: true
        Post: Immut && R == n
        isEmpty()

        Pred: true
        Post: n = 0 && forall i = 1..n': a[i] == null
        clear()

        Pred: true
        Post: R == mas[n] && forall i = 1..n: a[i] == mas[i] && Immut
        toArray()
        */

    }
}
