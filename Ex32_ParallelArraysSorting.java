package jls;

import java.util.concurrent.ForkJoinPool;

/**
 * JLS 32/50: Java 8+ - Parallel Arrays & Stream Execution Rules (JLS §15.12 & JLS §17)
 * Demonstrates thread-safe parallel sorting using parallelSort under JLS memory model.
 */
public class Ex32_ParallelArraysSorting {

    public static void main(String[] args) {
        int[] data = {9, 3, 1, 5, 13, 2, 8, 4};
        // Dual-Pivot Quicksort parallel execution
        java.util.Arrays.parallelSort(data);

        System.out.println("Sorted parallel array: " + java.util.Arrays.toString(data));
    }
}
