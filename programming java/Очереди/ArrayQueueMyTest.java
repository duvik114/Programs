package queue;

public class ArrayQueueMyTest {

    public static void fill(ArrayQueue arrayQueue) {
        int i = 1;
        while (arrayQueue.size() < 3704) { // 11111
            if (i % 4 == 0) {
                //ArrayQueueModule.enqueue(-4);
            } else if (i % 3 == 0) {
                //ArrayQueueModule.enqueue(-3);
            } else if (i % 2 == 0) {
                //ArrayQueueModule.enqueue(-2);
            } else {
                //System.out.println(ArrayQueueModule.size());
                arrayQueue.enqueue(i);
            }
            i++;
        }
    }

    public static void dump(ArrayQueue arrayQueue) {
        while (!arrayQueue.isEmpty()) {
            System.out.println("Number '" + arrayQueue.dequeue() + "' - is not divisible by 2, 3 and 4!");
        }
        arrayQueue.clear();
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue();
        fill(arrayQueue);
        dump(arrayQueue);
    }

}
