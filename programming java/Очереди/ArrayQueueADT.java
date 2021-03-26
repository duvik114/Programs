
package queue;

public class ArrayQueueADT {

    /*

        Model:
            [a1, a2, ... , an]
            n - size of queue

        I:
            n >= 0 && forall i = 1..n a[i] != null

        Immut:
            n == n' && forall i = 1..n: a[i] == a'[i]

    */

    private  Object[] mas = new Object[4];
    private  int size, n = 4;
    private  int head, tail;

    /*
        Pred: x != null && arrayQueueADT != null
        Post: n == n' + 1 && a[n] == x && forall i = 1..n': a[i] == a'[i]
    */
    public static void enqueue(ArrayQueueADT arrayQueueADT, Object x) {
        assert x != null;
        assert arrayQueueADT != null;

        if (arrayQueueADT.size == arrayQueueADT.n) {
            addPlace(arrayQueueADT);
        }
        arrayQueueADT.mas[arrayQueueADT.tail] = x;
        arrayQueueADT.tail++;
        arrayQueueADT.size++;
        if (arrayQueueADT.tail == arrayQueueADT.n) {
            arrayQueueADT.tail = 0;
        }
    }

    /*
        Pred: n > 0 && arrayQueueADT != null
        Post: Immut && R == a[1]
    */
    public static Object element(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT != null;
        assert arrayQueueADT.size > 0;

        return arrayQueueADT.mas[arrayQueueADT.head];
    }

    /*
        Pred: n > 0 &&arrayQueueADT != null
        Post: n == n' - 1 && forall i = 1..n: a[i] == a'[i + 1] && R == a'[1]
    */
    public static Object dequeue(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT != null;
        assert arrayQueueADT.size > 0;

        Object res = arrayQueueADT.mas[arrayQueueADT.head];
        arrayQueueADT.head++;
        arrayQueueADT.size--;
        if (arrayQueueADT.head == arrayQueueADT.n) {
            arrayQueueADT.head = 0;
        }
        return res;
    }

    /*
        Pred: arrayQueueADT != null
        Post: Immut && R == n
    */
    public static int size(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT != null;

        return arrayQueueADT.size;
    }

    /*
        Pred: arrayQueueADT != null
        Post: Immut && R == n
    */
    public static boolean isEmpty(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT != null;

        return arrayQueueADT.size == 0;
    }

    /*
        Pred: arrayQueueADT != null
        Post: n = 0
    */
    public static void clear(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT != null;

        arrayQueueADT.mas = new Object[4];
        arrayQueueADT.size = 0;
        arrayQueueADT.head = 0;
        arrayQueueADT.tail = 0;
        arrayQueueADT.n = 4;
    }

    /*
    Pred: arrayQueueADT != null
    Post: R == mas[n] && forall i = 1..n: a[i] == mas[i] && Immut
     */
    public static Object[] toArray(ArrayQueueADT arrayQueueADT) {
        assert arrayQueueADT != null;

        return getQueueMassive(arrayQueueADT, arrayQueueADT.size);
    }

    private static Object[] getQueueMassive(ArrayQueueADT arrayQueueADT, int masSize) {
        Object[] sas = new Object[masSize];
        int sizeOfCopy = Math.min(arrayQueueADT.size, arrayQueueADT.n - arrayQueueADT.head);
        System.arraycopy(arrayQueueADT.mas, arrayQueueADT.head, sas, 0, sizeOfCopy);
        int startOfCopy = (arrayQueueADT.head + sizeOfCopy) % arrayQueueADT.n;
        System.arraycopy(arrayQueueADT.mas, startOfCopy, sas, sizeOfCopy, arrayQueueADT.tail - startOfCopy);
        return sas;
    }

    private static void addPlace(ArrayQueueADT arrayQueueADT) {
        arrayQueueADT.mas = getQueueMassive(arrayQueueADT, arrayQueueADT.n * 2);
        arrayQueueADT.tail = arrayQueueADT.size;
        arrayQueueADT.head = 0;
        arrayQueueADT.n *= 2;
    }
}
