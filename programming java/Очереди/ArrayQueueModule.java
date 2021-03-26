package queue;

public class ArrayQueueModule {

    /*

        Model:
            [a1, a2, ... , an]
            n - size of queue

        I:
            n >= 0 && forall i = 1..n a[i] != null

        Immut:
            n == n' && forall i = 1..n: a[i] == a'[i]

    */

    private static Object[] mas = new Object[4];
    private static int size, n = 4;
    private static int head, tail;

    /*
        Pred: x != null
        Post: n == n' + 1 && a[n] == x && forall i = 1..n': a[i] == a'[i]
    */
    public static void enqueue(Object x) {
        assert x != null;

        if (size == n) {
            addPlace();
        }
        mas[tail] = x;
        tail++;
        size++;
        if (tail == n) {
            tail = 0;
        }
    }

    /*
        Pred: n > 0
        Post: Immut && R == a[1]
    */
    public static Object element() {
        assert size > 0;

        return mas[head];
    }

    /*
        Pred: n > 0
        Post: n == n' - 1 && forall i = 1..n: a[i] == a'[i + 1] && R == a'[1]
    */
    public static Object dequeue() {
        assert size > 0;

        Object res = mas[head];
        head++;
        size--;
        if (head == n) {
            head = 0;
        }
        return res;
    }

    /*
        Pred: true
        Post: Immut && R == n
    */
    public static int size() {
        return size;
    }

    /*
        Pred: true
        Post: Immut && R == n
    */
    public static boolean isEmpty() {
        return size == 0;
    }

    /*
        Pred: true
        Post: n = 0
    */
    public static void clear() {
        mas = new Object[4];
        size = 0;
        head = 0;
        tail = 0;
        n = 4;
    }

    /*
    Pred: true
    Post: R == mas[n] && forall i = 1..n: a[i] == mas[i] && Immut
     */
    public static Object[] toArray() {
        return getQueueMassive(size);
    }

    private static Object[] getQueueMassive(int masSize) {
        Object[] sas = new Object[masSize];
        int sizeOfCopy = Math.min(size, n - head);
        System.arraycopy(mas, head, sas, 0, sizeOfCopy);
        int startOfCopy = (head + sizeOfCopy) % n;
        System.arraycopy(mas, startOfCopy, sas, sizeOfCopy, tail - startOfCopy);
        return sas;
    }

    private static void addPlace() {
        mas = getQueueMassive(n * 2);
        tail = size;
        head = 0;
        n *= 2;
    }
}
