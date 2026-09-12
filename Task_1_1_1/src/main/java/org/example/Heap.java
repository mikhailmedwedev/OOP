package org.example;

/**
 * Пирамидальная сортировка.
 */
public class Heap {

    /**
     * Просеивает элемент вниз по дереву, если он меньше своих потомков.
     *
     * @param arr массив
     * @param n размер массива
     * @param i индекс текущего рассматриваемого элемента
     */
    public static void siftDown(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        if (left < n) {
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }
        int right = 2 * i + 2;
        if (right < n) {
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            siftDown(arr, n, largest);
        }
    }

    /**
     * Сортирует по возрастанию.
     *
     * @param arr неотсортированный массив
     */
    public static void sort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, n, i);
        }

        for (int i = n - 1; i >= 1; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            siftDown(arr, i, 0);
        }
    }
}
