package jls;

import java.util.Objects;

/**
 * JLS 41/50: Java 9+ - Objects.requireNonNullElse & Validation (JLS §4.3)
 * Demonstrates defensive programming and non-null guarantees.
 */
public class Ex41_ObjectsValidation {

    public static void main(String[] args) {
        String input = null;
        String fallback = Objects.requireNonNullElse(input, "Default Fallback");
        System.out.println("Validated string: " + fallback);
    }
}
