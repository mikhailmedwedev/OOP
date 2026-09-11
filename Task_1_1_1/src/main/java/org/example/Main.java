package org.example;

import java.util.Arrays;

public class Main {

    public static int[] heapsort(int[] arr) {
        Heap.sort(arr);
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        heapsort(arr);
        String sortedArr = Arrays.toString(arr);
        System.out.println(sortedArr);
    }
}
