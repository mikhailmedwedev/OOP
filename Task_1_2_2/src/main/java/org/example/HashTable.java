package org.example;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class HashTable<K, V> implements Iterable<HashTable.Node<K, V>> {
    Node<K, V>[] buckets;
    private int size;
    private int modCount;
    private static final double LOAD_FACTOR = 0.75;

    public HashTable(int capacity) {
        this.buckets = new Node[capacity];
        this.size = 0;
    }

    public HashTable() {
        this(16);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(buckets, null);
        size = 0;
        modCount++;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];
        size = 0;

        for (Node<K, V> bucket : oldBuckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    public void put(K key, V value) {
        if ((double) (size + 1) / buckets.length > LOAD_FACTOR) {
            resize();
        }

        int index = (key == null) ? 0 : Math.abs(key.hashCode()) % buckets.length;

        if (buckets[index] == null) {
            buckets[index] = new Node<>(key, value, null);
            size++;
            modCount++;
        } else {
            Node<K, V> current = buckets[index];
            while (current != null) {
                boolean keysEqual = (key == null) ? (current.key == null) : key.equals(current.key);

                if (keysEqual) {
                    current.value = value;
                    return;
                }

                if (current.next == null) {
                    current.next = new Node<>(key, value, null);
                    size++;
                    modCount++;
                    return;
                }

                current = current.next;
            }
        }
    }

    public V get(K key) {
        int index = (key == null) ? 0 : Math.abs(key.hashCode()) % buckets.length;

        if (buckets[index] == null) {
            return null;
        } else {
            Node<K, V> current = buckets[index];
            while (current != null) {
                boolean keysEqual = (key == null) ? (current.key == null) : key.equals(current.key);

                if (keysEqual) {
                    return current.value;
                }

                current = current.next;
            }

            return null;
        }
    }

    public boolean containsKey(K key) {
        int index = (key == null) ? 0 : Math.abs(key.hashCode()) % buckets.length;

        if (buckets[index] == null) {
            return false;
        } else {
            Node<K, V> current = buckets[index];
            while (current != null) {
                boolean keysEqual = (key == null) ? (current.key == null) : key.equals(current.key);

                if (keysEqual) {
                    return true;
                }

                current = current.next;
            }

            return false;
        }
    }

    public void remove(K key) {
        int index = (key == null) ? 0 : Math.abs(key.hashCode()) % buckets.length;

        if (buckets[index] == null) {
            return;
        } else {
            Node<K, V> current = buckets[index];
            Node<K, V> prev = null;
            while (current != null) {
                boolean keysEqual = (key == null) ? (current.key == null) : key.equals(current.key);

                if (keysEqual) {
                    if (prev == null) {
                        buckets[index] = current.next;
                    } else {
                        prev.next = current.next;
                    }

                    size--;
                    modCount++;
                    return;
                }

                prev = current;
                current = current.next;
            }
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;

        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.key).append("=").append(current.value);
                first = false;
                current = current.next;
            }
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (!(o instanceof HashTable)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        HashTable<Object, Object> other = (HashTable<Object, Object>) o;
        if (this.size != other.size) {
            return false;
        }

        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                if (!other.containsKey(current.key) ||
                        !Objects.equals(current.value, other.get(current.key))) {
                    return false;
                }

                current = current.next;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Node<K, V> bucket : buckets) {
            Node<K, V> current = bucket;
            while (current != null) {
                int keyHash = Objects.hashCode(current.key);
                int valueHash = Objects.hashCode(current.value);
                result += (keyHash ^ valueHash);

                current = current.next;
            }
        }

        return result;
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new HashIterator();
    }

    private class HashIterator implements Iterator<Node<K, V>> {
        private Node<K, V> next;
        private Node<K, V> current;
        private int bucketIndex;
        private int expectedModCount;

        public HashIterator() {
            expectedModCount = modCount;
            bucketIndex = 0;
            current = null;
            next = null;

            if (size > 0 && buckets != null) {
                while (bucketIndex < buckets.length && (next = buckets[bucketIndex++]) == null) {

                }
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Node<K, V> next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

            if (next == null) {
                throw new NoSuchElementException();
            }

            current = next;
            next = current.next;

            if (next == null && bucketIndex < buckets.length) {
                while (bucketIndex < buckets.length && (next = buckets[bucketIndex++]) == null) {

                }
            }

            return current;
        }
    }

    public static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
