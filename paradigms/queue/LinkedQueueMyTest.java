package queue;

public class LinkedQueueMyTest {
    public static void main(String[] args) {
        LinkedQueue linkedQueue = new LinkedQueue();
        linkedQueue.enqueue(4);
        System.out.println(linkedQueue.size());
        System.out.println(linkedQueue.isEmpty());
        linkedQueue.dequeue();
        System.out.println(linkedQueue.size());
        for (int i = 0; i < 44; i++) {
            linkedQueue.enqueue(i + 5);
            System.out.print(linkedQueue.element() + ", ");
            linkedQueue.dequeue();
        }
    }
}
