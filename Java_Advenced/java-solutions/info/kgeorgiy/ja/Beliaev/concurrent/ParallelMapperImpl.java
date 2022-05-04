package info.kgeorgiy.ja.Beliaev.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class implementing {@link ParallelMapper}
 *
 * @author Nikita Beliaev
 */

public class ParallelMapperImpl implements ParallelMapper {

    private final List<Thread> threadsList;
    private final LinkedList<Runnable> runnableQueue;

    /**
     * Constructor which is filling <code>threadsList</code> with correct {@link Thread} objects
     *
     * @param threads number of threads
     */
    public ParallelMapperImpl(int threads) {
        this.runnableQueue = new LinkedList<>();

        this.threadsList = Stream
                .generate(() -> new Thread(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        Runnable runnable;
                        synchronized (runnableQueue) {
                            while (runnableQueue.isEmpty()) {
                                try {
                                    runnableQueue.wait();
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }
                            runnable = runnableQueue.pollFirst();
                        }
                        runnable.run();
                    }
                }))
                .limit(threads)
                .collect(Collectors.toList());

        threadsList.forEach(Thread::start);
    }

    /**
     * Maps function {@code f} over specified {@code args}.
     * Mapping for each element performs in parallel.
     *
     * @param f    {@link Function} to map
     * @param args {@link List} objects to map
     * @throws InterruptedException if calling thread was interrupted
     */
    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> f, List<? extends T> args) throws InterruptedException {
        final int[] calculated = {0};

        List<R> result = new ArrayList<>(Collections.nCopies(args.size(), null));

        for (int i = 0; i < args.size(); i++) {
            synchronized (runnableQueue) {
                int finalI = i;
                runnableQueue.add(() -> {
                    result.set(finalI, f.apply(args.get(finalI)));
                    synchronized (calculated) {
                        if (calculated[0]++ >= args.size() - 1) {
                            calculated.notify();
                        }
                    }
                });
                runnableQueue.notify();
            }
        }

        synchronized (calculated) {
            if (calculated[0] < args.size()) {
                calculated.wait();
            }
        }

        return result;
    }

    /**
     * Stops all threads. All unfinished mappings leave in undefined state.
     */
    @Override
    public void close() {
        threadsList.forEach(Thread::interrupt);
        for (Thread t : threadsList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Error during joining thread: " + e.getMessage());
            }
        }
    }
}
