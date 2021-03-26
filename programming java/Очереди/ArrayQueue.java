package queue;

public class ArrayQueue extends AbstractQueue implements Queue {

    /*

        Model:
            [a1, a2, ... , an]
            n - size of queue

    */

    private Object[] mas = new Object[4];
    private int n = 4, head, tail;

    public void enqueueImpl(Object x) {
        if (size == n) {
            addPlace();
        }
        mas[tail] = x;
        tail++;
        if (tail == n) {
            tail = 0;
        }
    }

    public Object elementImpl() {
        return mas[head];
    }

    public Object dequeueImpl() {
        Object res = mas[head];
        head++;
        if (head == n) {
            head = 0;
        }
        return res;
    }

    public void clearImpl() {
        mas = new Object[4];
        head = 0;
        tail = 0;
        n = 4;
    }

    protected Object[] getQueueMassive(int masSize) {
        Object[] sas = new Object[masSize];
        int sizeOfCopy = Math.min(size, n - head);
        System.arraycopy(mas, head, sas, 0, sizeOfCopy);
        int startOfCopy = (head + sizeOfCopy) % n;
        System.arraycopy(mas, startOfCopy, sas, sizeOfCopy, tail - startOfCopy);
        return sas;
    }

    private void addPlace() {
        mas = getQueueMassive(n * 2);
        tail = size;
        head = 0;
        n *= 2;
    }
}
