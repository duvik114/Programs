package queue;

public class ArrayQueueADTMyTest {

    public static void fill(ArrayQueueADT arrayQueueADT) {
        int i = 1;
        while (ArrayQueueADT.size(arrayQueueADT) < 2048) {
            if (i % 4 == 0) {
                //ArrayQueueModule.enqueue(-4);
            } else if (i % 3 == 0) {
                //ArrayQueueModule.enqueue(-3);
            } else if (i % 2 == 0) {
                //ArrayQueueModule.enqueue(-2);
            } else {
                //System.out.println(ArrayQueueModule.size());
                ArrayQueueADT.enqueue(arrayQueueADT, i);
            }
            i++;
        }
    }

    public static void dump(ArrayQueueADT arrayQueueADT) {
        while (!ArrayQueueADT.isEmpty(arrayQueueADT)) {
            System.out.println("Number '" + ArrayQueueADT.dequeue(arrayQueueADT) + "' - is not divisible by 2, 3 and 4!");
        }
        ArrayQueueADT.clear(arrayQueueADT);
    }

    public static void main(String[] args) {
        ArrayQueueADT arrayQueueADT = new ArrayQueueADT();
        fill(arrayQueueADT);
        dump(arrayQueueADT);
    }

}
