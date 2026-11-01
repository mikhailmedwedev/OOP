package org.example;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для класса HashTable.
 */
class HashTableTest {

    private HashTable<String, Integer> table;

    @BeforeEach
    void setUp() {
        table = new HashTable<>();
    }

    @Test
    void putAndGet() {
        table.put("One", 1);
        table.put("Two", 2);

        assertEquals(1, table.get("One"));
        assertEquals(2, table.get("Two"));
        assertNull(table.get("NonExistent"));
        assertEquals(2, table.size());
        assertFalse(table.isEmpty());
    }

    @Test
    void updateValue() {
        table.put("Key", 10);
        assertEquals(10, table.get("Key"));

        Integer oldValue = table.update("Key", 20);
        assertEquals(10, oldValue);
        assertEquals(20, table.get("Key"));
        assertEquals(1, table.size());

        assertNull(table.update("NonExistent", 50));
    }

    @Test
    void nullKeyAndValue() {
        table.put(null, 100);
        table.put("ValueNull", null);

        assertEquals(100, table.get(null));
        assertNull(table.get("ValueNull"));
        assertTrue(table.containsKey(null));
        assertTrue(table.containsKey("ValueNull"));
        assertEquals(2, table.size());

        table.remove(null);
        assertNull(table.get(null));
        assertEquals(1, table.size());
    }

    @Test
    void containsKey() {
        table.put("Apple", 5);

        assertTrue(table.containsKey("Apple"));
        assertFalse(table.containsKey("Banana"));
    }

    @Test
    void remove() {
        table.put("A", 1);
        table.put("B", 2);

        assertEquals(2, table.size());

        table.remove("A");
        assertNull(table.get("A"));
        assertFalse(table.containsKey("A"));
        assertEquals(1, table.size());

        table.remove("NonExistent");
        assertEquals(1, table.size());
    }

    @Test
    void clear() {
        table.put("A", 1);
        table.put("B", 2);

        assertFalse(table.isEmpty());

        table.clear();
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        assertNull(table.get("A"));
    }

    @Test
    void resize() {
        HashTable<Integer, Integer> smallTable = new HashTable<>(2);

        for (int i = 0; i < 10; i++) {
            smallTable.put(i, i * 10);
        }

        assertEquals(10, smallTable.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(i * 10, smallTable.get(i));
        }
    }

    @Test
    void iterator() {
        table.put("A", 1);
        table.put("B", 2);
        table.put("C", 3);

        int count = 0;
        for (HashTable.Node<String, Integer> node : table) {
            assertNotNull(node.getKey());
            assertNotNull(node.getValue());
            count++;
        }

        assertEquals(3, count);
    }

    @Test
    void failFastIterator() {
        table.put("A", 1);
        table.put("B", 2);

        Iterator<HashTable.Node<String, Integer>> iterator = table.iterator();
        assertTrue(iterator.hasNext());

        table.put("C", 3);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void equalsAndHashCode() {
        HashTable<String, Integer> t1 = new HashTable<>();
        HashTable<String, Integer> t2 = new HashTable<>();

        t1.put("A", 1);
        t2.put("A", 1);

        assertEquals(t1, t1);
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());

        t2.put("B", 2);
        assertNotEquals(t1, t2);
        assertNotEquals(t1, null);
        assertNotEquals(t1, "SomeString");

        HashTable<String, Integer> t3 = new HashTable<>();
        t3.put("A", 99);
        assertNotEquals(t1, t3);
    }

    @Test
    void toStringMethod() {
        table.put("A", 1);
        String str = table.toString();
        assertNotNull(str);
        assertFalse(str.isEmpty());
    }

    @Test
    void iteratorExceptions() {
        table.put("A", 1);
        Iterator<HashTable.Node<String, Integer>> iterator = table.iterator();

        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());
        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void removeNonExistentAndCollisions() {
        table.put("A", 1);
        table.put("B", 2);

        table.remove("NonExistentKey");
        assertEquals(2, table.size());

        table.remove("A");
        assertNull(table.get("A"));
        assertEquals(1, table.size());
    }
}