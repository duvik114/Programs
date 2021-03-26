package queue;

public interface Queue {

        /*

        Model:
            [a1, a2, ... , an]
            n - size of queue

        I:
            n >= 0 && forall i = 1..n a[i] != null

        Immut:
            n == n' && forall i = 1..n: a[i] == a'[i]

    */

        /*
        Pred: x != null
        Post: n == n' + 1 && a[n] == x && forall i = 1..n': a[i] == a'[i]
        */
        void enqueue(Object x);

        /*
        Pred: n > 0
        Post: n == n' - 1 && forall i = 1..n: a[i] == a'[i + 1] && R == a'[1]
        */
        Object dequeue();

        /*
        Pred: n > 0
        Post: Immut && R == a[1]
        */
        Object element();

        /*
        Pred: true
        Post: Immut && R == n
        */
        int size();

        /*
        Pred: true
        Post: Immut && R == n
        */
        boolean isEmpty();

        /*
        Pred: true
        Post: n = 0 && forall i = 1..n': a[i] == null
        */
        void clear();

        /*
        Pred: true
        Post: R == mas[n] && forall i = 1..n: a[i] == mas[i] && Immut
        */
        Object[] toArray();

}
