package queue;

public abstract class AbstractQueue {

    /*

        Model:
            [a1, a2, ... , an]
            n - size of queue

        I:
            n >= 0 && forall i = 1..n a[i] != null

        Immut:
            n == n' && forall i = 1..n: a[i] == a'[i]

    */

    protected int size = 0;

    /*
        Pred: x != null
        Post: n == n' + 1 && a[n] == x && forall i = 1..n': a[i] == a'[i]
    */
    public void enqueue(Object x) {
        assert x != null;
        enqueueImpl(x);
        size++;
    }

    /*
        Pred: n > 0
        Post: n == n' - 1 && forall i = 1..n: a[i] == a'[i + 1] && R == a'[1]
    */
    public Object dequeue() {
        assert size > 0;
        size--;
        return dequeueImpl();
    }

    /*
        Pred: n > 0
        Post: Immut && R == a[1]
    */
    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    /*
        Pred: true
        Post: Immut && R == n
    */
    public int size() {
        return size;
    }

    /*
        Pred: true
        Post: Immut && R == n
    */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
        Pred: true
        Post: n = 0 && forall i = 1..n': a[i] == null
    */
    public void clear() {
        clearImpl();
        size = 0;
    }

    /*
        Pred: true
        Post: R == mas[n] && forall i = 1..n: a[i] == mas[i] && Immut
    */
    public Object[] toArray() {
        return getQueueMassive(size);
    }

    protected abstract void enqueueImpl(Object x);

    protected abstract Object dequeueImpl();

    protected abstract Object elementImpl();

    protected abstract void clearImpl();

    protected abstract Object[] getQueueMassive(int masSize);

}
