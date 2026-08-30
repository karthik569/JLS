package jls;

/**
 * JLS 18/50: Java 21 - Pattern Matching for switch (JLS §14.11 & JLS §14.30.2)
 * Demonstrates type patterns, guarded patterns (when clause), null handling, and exhaustiveness.
 */
public class Ex18_PatternMatchingSwitch {

    public static void testSwitchPattern(Object obj) {
        // JLS §14.11: Switch statement with Type Patterns and guarded clauses (when)
        switch (obj) {
            case null -> System.out.println("Handled null explicitly in switch!");
            case Integer i when i > 100 -> System.out.println("Large integer: " + i);
            case Integer i -> System.out.println("Small/Medium integer: " + i);
            case String s -> System.out.println("String of length " + s.length() + ": " + s);
            case int[] arr -> System.out.println("Int array of size: " + arr.length);
            default -> System.out.println("Unknown object: " + obj);
        }
    }

    public static void main(String[] args) {
        testSwitchPattern(null);
        testSwitchPattern(500);
        testSwitchPattern(42);
        testSwitchPattern("Java 21 Pattern Switch");
    }
}
