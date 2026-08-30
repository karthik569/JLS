package jls;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JLS 37/50: Java 10+ - Collectors.toUnmodifiableList/Set/Map (JLS §4.3.3)
 * Demonstrates stream collection to unmodifiable structures.
 */
public class Ex37_CollectorsUnmodifiable {

    public static void main(String[] args) {
        List<String> list = Stream.of("one", "two", "three")
                .collect(Collectors.toUnmodifiableList());

        System.out.println("Unmodifiable stream result: " + list);
    }
}
