import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    public class Node {
        T item;
        Node next, prev;

        public Node() {
            this.item = null;
            this.next = null;
            this.prev = null;
        }

        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node nodeToAdd = new Node(x, sentinel.next, sentinel);
        sentinel.next.prev = nodeToAdd;
        sentinel.next = nodeToAdd;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node nodeToAdd = new Node(x, sentinel, sentinel.prev);
        sentinel.prev.next = nodeToAdd;
        sentinel.prev = nodeToAdd;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node p = sentinel.next;
        while (p.item != null) {
            returnList.add(p.item);
            p = p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        Node toRemove = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        this.size--;
        return toRemove.item;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        Node toRemove = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        this.size--;
        return toRemove.item;
    }

    @Override
    public T get(int index) {
        if (index > this.size - 1 || index < 0) {
            return null;
        }
        int indexCheck = 0;
        Node p = sentinel.next;
        while (indexCheck != index) {
            p = p.next;
            indexCheck++;
        }
        return p.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index > this.size - 1 || index < 0) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    public T getRecursiveHelper(Node n, int index) {
        if (index == 0) {
            return n.item;
        } else {
            return getRecursiveHelper(n.next, index - 1);
        }
    }
}
