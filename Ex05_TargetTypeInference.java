package jls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JLS 5/50: Java 8 - Enhanced Target Type Inference (JLS §15.12.2.8)
 * Demonstrates how Java 8 infers generic types from context without explicit type arguments.
 */
public class Ex05_TargetTypeInference {

    public static <T> List<T> emptyList() {
        return Collections.emptyList();
    }

    public static void processStrings(List<String> list) {
        System.out.println("Processing list size: " + list.size());
    }

    public static void main(String[] args) {
        // In Java 7, processStrings(Ex05_TargetTypeInference.<String>emptyList()) was required.
        // JLS §15.12.2.8: Java 8 target typing infers T as String from argument context.
        processStrings(emptyList());
    }
}
