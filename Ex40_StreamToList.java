package jls;

import java.util.stream.Stream;

/**
 * JLS 40/50: Java 16+ - Stream.toList Direct Collection (JLS §15.12)
 * Demonstrates Stream.toList() producing an unmodifiable list efficiently.
 */
public class Ex40_StreamToList {

    public static void main(String[] args) {
        var list = Stream.of("Alpha", "Beta", "Gamma")
                .map(String::toLowerCase)
                .toList();

        System.out.println("Direct Stream.toList(): " + list);
    }
}
