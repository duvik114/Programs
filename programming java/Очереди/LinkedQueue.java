package queue;

public class LinkedQueue extends AbstractQueue implements Queue {

    /*

        Model:
            [a1, a2, ... , an]
            n - size of queue

    */

    private Node head = null, tail = null;

    private static class Node {

        private Node next = null;
        private final Object value;

        public Node(final Object value) {
            this.value = value;
        }

    }

    public void enqueueImpl(Object value) {
        if (tail == null) {
            tail = new Node(value);
            head = tail;
        } else {
            tail.next = new Node(value);
            tail = tail.next;
        }
    }

    public Object dequeueImpl() {
        Object res = head.value;
        if (head == tail) {
            tail = null;
        }
        head = head.next;
        return res;
    }

    public Object elementImpl() {
        return head.value;
    }

    public void clearImpl() {
        head = null;
        tail = null;
        size = 0;
    }

    protected Object[] getQueueMassive(int masSize) { //???? protected
        Object[] sas = new Object[masSize];
        Node iNode = head;
        for(int i = 0; i < masSize; i++) {
            sas[i] = iNode.value;
            iNode = iNode.next;
        }
        return sas;
    }

}
