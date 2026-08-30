package jls;

import java.util.Spliterator;
import java.util.ArrayList;
import java.util.List;

/**
 * JLS 49/50: Java 8+ - Custom Spliterator Partitioning (JLS §15.12)
 * Demonstrates splitting collections for parallel stream processing.
 */
public class Ex49_CustomSpliterator {

    public static void main(String[] args) {
        List<String> items = List.of("One", "Two", "Three", "Four");
        Spliterator<String> spliterator1 = items.spliterator();
        Spliterator<String> spliterator2 = spliterator1.trySplit();

        System.out.println("Part 1 estimate: " + (spliterator2 != null ? spliterator2.estimateSize() : 0));
        System.out.println("Part 2 estimate: " + spliterator1.estimateSize());
    }
}
