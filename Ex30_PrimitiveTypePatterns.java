package jls;

/**
 * JLS 30/50: Java 23+ - Primitive Types in Patterns and Switch (JLS §14.30.2)
 * Demonstrates exact primitive type pattern matching across all primitive types.
 */
public class Ex30_PrimitiveTypePatterns {

    public static void testPrimitive(Object val) {
        switch (val) {
            case byte b -> System.out.println("Matched primitive byte: " + b);
            case int i -> System.out.println("Matched primitive int: " + i);
            case double d -> System.out.println("Matched primitive double: " + d);
            default -> System.out.println("Other type: " + val);
        }
    }

    public static void main(String[] args) {
        testPrimitive((byte) 5);
        testPrimitive(42);
        testPrimitive(3.14);
    }
}
