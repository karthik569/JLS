package jls;

import java.util.SequencedCollection;
import java.util.SequencedSet;
import java.util.LinkedHashSet;

/**
 * JLS 24/50: Java 21 - Sequenced Collections Hierarchy (JLS §8.1.5)
 * Demonstrates SequencedCollection, SequencedSet, and uniform first/last element access.
 */
public class Ex24_SequencedCollections {

    public static void main(String[] args) {
        SequencedSet<String> set = new LinkedHashSet<>();
        set.addFirst("Second");
        set.addFirst("First");
        set.addLast("Third");

        System.out.println("Full set: " + set);
        System.out.println("First element: " + set.getFirst());
        System.out.println("Last element: " + set.getLast());
        System.out.println("Reversed set: " + set.reversed());
    }
}
