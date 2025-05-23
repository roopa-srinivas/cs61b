import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V>{

    Node root;
    int size;

    private class Node {
        K key;
        V value;
        Node left, right;

        private Node() {
            key = null;
            value = null;
            left = null;
            right = null;
        }

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        root = put(root, key, value);
    }

    public Node put(Node root, K key, V value) {
        if (root == null) {
            size++;
            return new Node(key, value);
        }
        int comp = key.compareTo(root.key);
        if (comp > 0) {
            root.right = put(root.right, key, value);
        } else if (comp < 0) {
            root.left = put(root.left, key, value);
        } else {
            root.value = value;
        }
        return root;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        Node nodeToGet = getValue(root, key);
        if (nodeToGet == null) {
            return null;
        }
        return nodeToGet.value;
    }

    public Node getValue(Node node, K key) {
        if (node == null) {
            return null;
        }
        int comp = key.compareTo(node.key);
        if (comp > 0) {
            return getValue(node.right, key);
        } else if (comp < 0) {
            return getValue(node.left, key);
        } else {
            return node;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    public boolean containsKeyHelper(Node node, K key) {
        if (node == null) {
            return false;
        }
        int comp = key.compareTo(node.key);
        if (comp == 0) {
            return true;
        } else if (comp > 0) {
            return containsKeyHelper(node.right, key);
        } else if (comp < 0) {
            return containsKeyHelper(node.left, key);
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.println(node.key + " -> " + node.value);
            printInOrder(node.right);
        }
    }
}
