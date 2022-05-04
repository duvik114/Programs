package info.kgeorgiy.ja.Beliaev.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Class who implements {@link ScalarIP}
 *
 * @author Nikita Beliaev
 */

public class IterativeParallelism implements ScalarIP {

    private final ParallelMapper parallelMapper;

    public IterativeParallelism() {
        this.parallelMapper = null;
    }

    public IterativeParallelism(ParallelMapper parallelMapper) {
        this.parallelMapper = parallelMapper;
    }

    private <T> List<List<? extends T>> divideIntoLists(int threadsCount, List<? extends T> values) {
        int mod = values.size() % threadsCount;
        int count = values.size() / threadsCount;
        List<List<? extends T>> list = new ArrayList<>(threadsCount);

        for (int i = 0; i < values.size();) {
            int endPos = i + count;
            if (mod > 0) {
                endPos += 1;
                mod--;
            }
            list.add(values.subList(i, endPos));
            i = endPos;
        }
        return list;
    }

    private <T, R> R divideIntoThreads(int threadsCount, List<? extends T> values,
                                       Function<List<? extends T>, R> listFunction,
                                       Function<List<R>, R> resFunction) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        threadsCount = Math.min(threadsCount, values.size());
        List<List<? extends T>> dividedLists = divideIntoLists(threadsCount, values);

        if (parallelMapper != null) {
            return resFunction.apply(parallelMapper.map(listFunction, dividedLists));
        }

        List<R> results = new ArrayList<>(Collections.nCopies(dividedLists.size(), null));

        IntStream.range(0, dividedLists.size()).forEach(i -> {
            threads.add(new Thread(() -> results.set(i, listFunction.apply(dividedLists.get(i)))));
            threads.get(threads.size() - 1).start();
        });

        for (Thread t : threads) {
            try {
                t.join();
            } catch (IllegalArgumentException e) {
                System.err.println("Error during joining thread: " + e.getMessage());
            }
        }

        return resFunction.apply(results);
    }

    /**
     * Returns maximum value.
     *
     * @param threads    number or concurrent threads.
     * @param values     values to get maximum of.
     * @param comparator value comparator.
     * @param <T>        value type.
     * @return maximum of given values
     * @throws InterruptedException             if executing thread was interrupted.
     * @throws java.util.NoSuchElementException if no values are given.
     */

    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return minimum(threads, values, comparator.reversed());
    }

    /**
     * Returns minimum value.
     *
     * @param threads    number or concurrent threads.
     * @param values     values to get minimum of.
     * @param comparator value comparator.
     * @param <T>        value type.
     * @return minimum of given values
     * @throws InterruptedException             if executing thread was interrupted.
     * @throws java.util.NoSuchElementException if no values are given.
     */

    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return divideIntoThreads(threads, values, (l -> l.stream().min(comparator).orElse(null)),
                (l -> l.stream().min(comparator).orElse(null)));
    }

    /**
     * Returns whether all values satisfies predicate.
     *
     * @param threads   number or concurrent threads.
     * @param values    values to test.
     * @param predicate test predicate.
     * @param <T>       value type.
     * @return whether all values satisfies predicate or {@code true}, if no values are given
     * @throws InterruptedException if executing thread was interrupted.
     */

    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return divideIntoThreads(threads, values, (l -> l.stream().allMatch(predicate)),
                (l -> l.stream().allMatch(aBoolean -> aBoolean)));
    }

    /**
     * Returns whether any of values satisfies predicate.
     *
     * @param threads   number or concurrent threads.
     * @param values    values to test.
     * @param predicate test predicate.
     * @param <T>       value type.
     * @return whether any value satisfies predicate or {@code false}, if no values are given
     * @throws InterruptedException if executing thread was interrupted.
     */

    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return !all(threads, values, predicate.negate());
    }
}
