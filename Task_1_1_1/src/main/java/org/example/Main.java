package org.example;

import java.util.Arrays;

/**
 * Запуск пирамидальной сортировки.
 */
public class Main {

    /**
     * Сортирует массив.
     *
     * @param arr входной массив
     * @return отсортированный по возрастанию массив
     */
    public static int[] heapsort(int[] arr) {
        Heap.sort(arr);
        return arr;
    }

    /**
     * Создается массив из примера, сортируется.
     *
     * @param args аргументы командной строки - не используются
     */
    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        heapsort(arr);
        String sortedArr = Arrays.toString(arr);
        System.out.println(sortedArr);
    }
}
