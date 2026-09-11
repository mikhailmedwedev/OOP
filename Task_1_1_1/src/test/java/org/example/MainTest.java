package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MainTest {

    @Test
    public void testEmptyArray() {
        int[] input = {};
        int[] expected = {};

        int[] actual = Main.heapsort(input);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};

        int[] actual = Main.heapsort(input);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testSingleElementArray() {
        int[] input = {10};
        int[] expected = {10};

        int[] actual = Main.heapsort(input);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testWithDuplicatesAndNegatives() {
        int[] input = {-5, 3, 3, 8, 0, -5, 12, 5};
        int[] expected = {-5, -5, 0, 3, 3, 5, 8, 12};

        int[] actual = Main.heapsort(input);

        assertArrayEquals(expected, actual);
    }
}