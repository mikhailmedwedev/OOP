package org.example;

public class HashTable<K, V> {
    Node<K, V>[] buckets;
    private int size;
    private int modCount;

    public HashTable(int capacity) {
        this.buckets = new Node[capacity];
        this.size = 0;
    }

    public HashTable() {
        this(16);
    }

    public void put(K key, V value) {
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

    public static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
