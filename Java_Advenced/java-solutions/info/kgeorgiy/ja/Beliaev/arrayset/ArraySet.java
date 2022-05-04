package info.kgeorgiy.ja.Beliaev.arrayset;

import java.util.*;

public class ArraySet<T> extends AbstractSet<T> implements SortedSet<T> {
    private final List<T> array;
    private final Comparator<? super T> comparator;

    public ArraySet() {
        array = Collections.emptyList();
        comparator = null;
    }

    public ArraySet(Collection<? extends T> collection) {
        this(collection, null);
    }

    public ArraySet(Collection<? extends T> collection, Comparator<? super T> comparator) {
        TreeSet<T> treeSet = new TreeSet<>(comparator);
        treeSet.addAll(collection);
        array = new ArrayList<>(treeSet);
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

    private int getIndex(T element, boolean moreOrLess) { // true is need more
        int index = Collections.binarySearch(array, element, comparator);
        if (index < 0) {
            index = (index + 1) * -1;
        }
        index -= moreOrLess ? 0 : 1;
        return index;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SortedSet<T> subSet(T fromElement, T toElement) {
        if ((comparator == null
                ? ((Comparator<T>) Comparator.naturalOrder()).compare(fromElement, toElement)
                : comparator.compare(fromElement, toElement)) > 0) {
            throw new IllegalArgumentException("Index of fromElement > index of toElement");
        }
        int indexFrom = getIndex(fromElement, true), indexTo = getIndex(toElement, false);
        return new ArraySet<>(array.subList(indexFrom, indexTo + 1), comparator);
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        int indexFrom = 0, indexTo = getIndex(toElement, false);
        return new ArraySet<>(array.subList(indexFrom, indexTo + 1), comparator);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        int indexFrom = getIndex(fromElement, true), indexTo = array.size();
        return new ArraySet<>(array.subList(indexFrom, indexTo), comparator);
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArraySet is empty");
        }
        return array.get(0);
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArraySet is empty");
        }
        return array.get(array.size() - 1);
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.size() == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        return Collections.binarySearch(array, (T) o, comparator) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableList(array).iterator();
    }
}
