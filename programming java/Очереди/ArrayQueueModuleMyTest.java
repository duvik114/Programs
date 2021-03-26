package queue;

public class ArrayQueueModuleMyTest {

    public static void fill() {
        int i = 1;
        while (ArrayQueueModule.size() < 1024) {
            if (i % 4 == 0) {
                //ArrayQueueModule.enqueue(-4);
            } else if (i % 3 == 0) {
                //ArrayQueueModule.enqueue(-3);
            } else if (i % 2 == 0) {
                //ArrayQueueModule.enqueue(-2);
            } else {
                //System.out.println(ArrayQueueModule.size());
                ArrayQueueModule.enqueue(i);
            }
            i++;
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println("Number '" + ArrayQueueModule.dequeue() + "' - is not divisible by 2, 3 and 4!");
        }
        ArrayQueueModule.clear();
    }

    public static void main(String[] args) {
        fill();
        dump();
    }

}
