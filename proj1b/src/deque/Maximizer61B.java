package deque;
import java.util.Comparator;
import java.util.Iterator;

public class Maximizer61B {
    /**
     * Returns the maximum element from the given iterable of comparables.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @return          the maximum element
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        T max = null;
        if (iterator.hasNext()) {
            max = iterator.next();
        } else {
            return null;
        }

        while (iterator.hasNext()) {
            T curr = iterator.next();
            if (max.compareTo(curr) < 0 || max == null) {
                max = curr;
            }
        }
        return max;
    }

    /**
     * Returns the maximum element from the given iterable according to the specified comparator.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @param comp      the Comparator to compare elements
     * @return          the maximum element according to the comparator
     */
    public static <T> T max(Iterable<T> iterable, Comparator<T> comp) {
        Iterator<T> iterator = iterable.iterator();
        T max = null;
        if (iterator.hasNext()) {
            max = iterator.next();
        } else {
            return null;
        }
        while (iterator.hasNext()) {
            T curr = iterator.next();
            if (comp.compare(max, curr) < 0 || max == null) {
                max = curr;
            }
        }
        return max;
    }
}
