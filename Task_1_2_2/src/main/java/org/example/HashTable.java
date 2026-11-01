package org.example;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Реализация хэш-таблицы на основе метода цепочек.
 *
 * @param <K> тип ключей
 * @param <V> тип сопоставляемых значений
 */
public class HashTable<K, V> implements Iterable<HashTable.Node<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] buckets;
    private int size;
    private int modCount;

    /**
     * Конструктор хэш-таблицы с заданной начальной емкостью.
     *
     * @param capacity начальная емкость
     */
    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        this.buckets = new Node[capacity];
        this.size = 0;
    }

    /**
     * Конструктор хэш-таблицы по умолчанию.
     */
    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Возвращает количество элементов в хэш-таблице.
     *
     * @return количество элементов
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли хэш-таблица.
     *
     * @return true, если таблица не содержит элементов, иначе - false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Полностью очищает хэш-таблицу.
     */
    public void clear() {
        Arrays.fill(buckets, null);
        size = 0;
        modCount++;
    }

    /**
     * Вспомогательный метод для безопасного вычисления индекса бакета.
     *
     * @param key ключ
     * @return индекс в массиве бакетов
     */
    private int getIndex(K key) {
        if (key == null) {
            return 0;
        }
        return (key.hashCode() & 0x7FFFFFFF) % buckets.length;
    }

    /**
     * Проверяет ключи на равенство с учетом возможных null-значений.
     *
     * @param k1 первый ключ
     * @param k2 второй ключ
     * @return true, если ключи равны, иначе - false
     */
    private boolean keysEqual(K k1, K k2) {
        return (k1 == null) ? (k2 == null) : k1.equals(k2);
    }

    /**
     * Увеличивает емкость массива бакетов и перехеширует существующие элементы.
     */
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

    /**
     * Добавляет или обновляет пару ключ-значение в хэш-таблице.
     *
     * @param key ключ
     * @param value значение
     */
    public void put(K key, V value) {
        if ((double) (size + 1) / buckets.length > LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(key);

        if (buckets[index] == null) {
            buckets[index] = new Node<>(key, value, null);
            size++;
            modCount++;
        } else {
            Node<K, V> current = buckets[index];
            while (current != null) {
                if (keysEqual(current.key, key)) {
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

    /**
     * Обновляет значение по существующему ключу.
     *
     * @param key   ключ, значение которого требуется обновить
     * @param value новое значение
     * @return старое значение или null, если ключ не найден
     */
    public V update(K key, V value) {
        int index = getIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (keysEqual(current.key, key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }

            current = current.next;
        }

        return null;
    }

    /**
     * Возвращает значение по указанному ключу.
     *
     * @param key ключ
     * @return значение или null, если ключ не найден
     */
    public V get(K key) {
        int index = getIndex(key);

        if (buckets[index] == null) {
            return null;
        } else {
            Node<K, V> current = buckets[index];
            while (current != null) {
                if (keysEqual(current.key, key)) {
                    return current.value;
                }

                current = current.next;
            }

            return null;
        }
    }

    /**
     * Проверяет наличие указанного ключа в хэш-таблице.
     *
     * @param key ключ для проверки
     * @return true, если ключ присутствует в таблице, иначе - false
     */
    public boolean containsKey(K key) {
        int index = getIndex(key);

        if (buckets[index] == null) {
            return false;
        } else {
            Node<K, V> current = buckets[index];
            while (current != null) {
                if (keysEqual(current.key, key)) {
                    return true;
                }

                current = current.next;
            }

            return false;
        }
    }

    /**
     * Удаляет элемент по заданному ключу.
     *
     * @param key ключ удаляемого элемента
     */
    public void remove(K key) {
        int index = getIndex(key);

        if (buckets[index] == null) {
            return;
        } else {
            Node<K, V> current = buckets[index];
            Node<K, V> prev = null;
            while (current != null) {
                if (keysEqual(current.key, key)) {
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

    /**
     * Возвращает строковое представление хэш-таблицы.
     *
     * @return строка, содержащая все пары ключ-значение
     */
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

    /**
     * Сравнивает указанный объект с данной хэш-таблицей на равенство.
     *
     * @param o объект для сравнения
     * @return true, если объекты равны, иначе - false
     */
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

    /**
     * Возвращает хэш-код для данной хэш-таблицы.
     *
     * @return хэш-код
     */
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

    /**
     * Возвращает итератор для обхода элементов хэш-таблицы.
     *
     * @return итератор узлов
     */
    @Override
    public Iterator<Node<K, V>> iterator() {
        return new HashIterator();
    }

    /**
     * Итератор для последовательного обхода всех элементов хэш-таблицы.
     */
    private class HashIterator implements Iterator<Node<K, V>> {
        private Node<K, V> next;
        private Node<K, V> current;
        private int bucketIndex;
        private int expectedModCount;

        /**
         * Создает новый итератор.
         */
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

        /**
         * Проверяет наличие следующего элемента в итераторе.
         *
         * @return true, если есть следующий элемент, иначе - false
         */
        @Override
        public boolean hasNext() {
            return next != null;
        }

        /**
         * Возвращает следующий узел в итераторе.
         *
         * @return следующий узел
         */
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

    /**
     * Узел хэш-таблицы, содержащий ключ, значение и ссылку на следующий узел в цепочке.
     *
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    public static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        /**
         * Конструктор для узла.
         * @param key ключ
         * @param value значение
         * @param next ссылка на следующий узел
         */
        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Возвращает ключ узла.
         *
         * @return ключ
         */
        public K getKey() {
            return key;
        }

        /**
         * Возвращает значение узла.
         *
         * @return значение
         */
        public V getValue() {
            return value;
        }
    }
}
