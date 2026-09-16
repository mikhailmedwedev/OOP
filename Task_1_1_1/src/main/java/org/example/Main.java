package org.example;

import java.util.Arrays;

/**
 * Запуск пирамидальной сортировки.
 */
public class Main {

    /**
     * Создается массив из примера, сортируется.
     *
     * @param args аргументы командной строки - не используются
     */
    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        Heap.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
