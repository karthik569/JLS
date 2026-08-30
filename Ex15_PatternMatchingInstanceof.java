package jls;

/**
 * JLS 15/50: Java 16 - Pattern Matching for instanceof (JLS §14.30.1)
 * Demonstrates pattern variables, scope rules, and flow scoping.
 */
public class Ex15_PatternMatchingInstanceof {

    public static void process(Object obj) {
        // JLS §14.30.1: Pattern variable scope is limited by control flow analysis
        if (obj instanceof String s && s.length() > 5) {
            System.out.println("Long String (length " + s.length() + "): " + s.toUpperCase());
        } else if (obj instanceof Integer i) {
            System.out.println("Integer value squared: " + (i * i));
        } else {
            System.out.println("Other object: " + obj);
        }
    }

    public static void main(String[] args) {
        process("Pattern Matching Java 16");
        process(42);
        process(true);
    }
}
