package jls;

import java.util.Optional;

/**
 * JLS 33/50: Java 8+ - Optional Monadic Operations & Type Safety (JLS §4.3)
 * Demonstrates map, flatMap, and orElseThrow semantics avoiding NullPointerException.
 */
public class Ex33_OptionalTypeSafety {

    public static void main(String[] args) {
        Optional<String> name = Optional.of("Antigravity");

        String upper = name.map(String::toUpperCase)
                .orElse("DEFAULT");

        System.out.println("Transformed optional: " + upper);
    }
}
