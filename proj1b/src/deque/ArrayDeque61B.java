package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private T[] items;
    private int size;
    private int frontIndex;
    private int backIndex;

    public ArrayDeque61B() {
        size = 0;
        frontIndex = 0;
        backIndex = 0;
        items = (T[]) new Object[8];
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        frontIndex = Math.floorMod(frontIndex - 1, items.length);
        items[frontIndex] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[backIndex] = x;
        backIndex = Math.floorMod(backIndex + 1, items.length);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }

        T toRemove = items[frontIndex];
        items[frontIndex] = null;
        frontIndex = Math.floorMod(frontIndex + 1, items.length);
        size--;
        if (size > 0 && size < items.length / 2 && items.length > 8) {
            resize(items.length / 2);
        }
        return toRemove;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        backIndex = Math.floorMod(backIndex - 1, items.length);
        T toRemove = items[backIndex];
        items[backIndex] = null;
        size--;
        if (size > 0 && size < items.length / 2 && items.length > 8) {
            resize(items.length / 2);
        }
        return toRemove;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[Math.floorMod(frontIndex +  index, items.length)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public void resize(int newLength) {
        T[] newArray = (T[]) new Object[newLength];
        for (int i = 0; i < size; i++) {
            newArray[i] = items[Math.floorMod(frontIndex + i, items.length)];
        }
        items = newArray;
        frontIndex = 0;
        backIndex = size;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        public ArrayDequeIterator() {
            pos = 0;
        }

        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T nextItem = get(pos);
            pos += 1;
            return nextItem;
        }
    }

    public boolean equals(Object toCompare) {
        if (!(toCompare instanceof Deque61B)) {
            return false;
        }

        Deque61B<T> toCompareDeque = (Deque61B) toCompare;
        if (this.size != toCompareDeque.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.get(i) == null || toCompareDeque.get(i) == null) {
                if (this.get(i) != toCompareDeque.get(i)) {
                    return false;
                }
            } else if (!(toCompareDeque.get(i).equals(this.get(i)))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String output = "[";
        for (int i = 0; i < size - 1; i++) {
            output += (get(i) + ", ");
        }

        if (size > 0) {
            output += get(size - 1);
        }

        output += "]";
        return output;
    }
}
