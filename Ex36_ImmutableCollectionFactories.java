package jls;

import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * JLS 36/50: Java 9+ - Immutable Collection Factory Methods (JLS §4.3.3)
 * Demonstrates unmodifiable collection instances created via List.of, Set.of, and Map.of.
 */
public class Ex36_ImmutableCollectionFactories {

    public static void main(String[] args) {
        List<String> immutableList = List.of("A", "B", "C");
        Set<Integer> immutableSet = Set.of(1, 2, 3);
        Map<String, String> immutableMap = Map.of("K1", "V1", "K2", "V2");

        System.out.println("Immutable List: " + immutableList);
        try {
            immutableList.add("D");
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected mutation exception on List.of()");
        }
    }
}
