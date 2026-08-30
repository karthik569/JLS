package jls;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JLS 35/50: Java 9+ - Stream TakeWhile and DropWhile (JLS §15.12)
 * Demonstrates short-circuiting stream evaluation based on predicate boundaries.
 */
public class Ex35_StreamTakeWhileDropWhile {

    public static void main(String[] args) {
        var numbers = Stream.of(1, 2, 3, 4, 10, 5, 6);

        var taken = numbers.takeWhile(n -> n < 5).collect(Collectors.toList());
        System.out.println("Taken while < 5: " + taken);
    }
}
