package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MainTest {

    @Test
    void emptyArray() {
        int[] input = {};
        int[] expected = {};

        Heap.sort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    void alreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        Heap.sort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    void singleElementArray() {
        int[] input = {10};
        int[] expected = {10};

        Heap.sort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    void withDuplicatesAndNegatives() {
        int[] input = {-5, 3, 3, 8, 0, -5, 12, 5};
        int[] expected = {-5, -5, 0, 3, 3, 5, 8, 12};

        Heap.sort(input);

        assertArrayEquals(expected, input);
    }
}