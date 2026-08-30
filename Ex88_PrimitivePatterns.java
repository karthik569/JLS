package jls;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JLS §14.30.2 (Java 23+ Preview): Primitive Patterns
 * 
 * Primitive patterns extend pattern matching to primitive types.
 * 
 * Key concepts:
 * - Pattern matching for primitive types (int, long, etc.)
 * - Integrates with instanceof, switch, and record patterns
 * - Type checking: long, int, short, byte, char, boolean, double, float
 * - Unboxing patterns in switch expressions
 * - Record patterns with primitive components
 */
public class Ex88_PrimitivePatterns {
    
    public static void main(String[] args) {
        System.out.println("=== Primitive Patterns Demo ===\n");
        
        demoInstanceofPrimitive();
        demoSwitchPrimitive();
        demoUnboxing();
    }
    
    /**
     * JLS §14.30: Pattern matching for primitive types
     */
    static void demoInstanceofPrimitive() {
        System.out.println("Demo 1: Pattern Matching with Primitives");
        System.out.println("=".repeat(50));
        
        Object[] values = {42, 3.14, "hello", 100L, true};
        
        for (Object v : values) {
            String description = describe(v);
            System.out.println("  " + v + " -> " + description);
        }
        System.out.println();
    }
    
    static String describe(Object v) {
        if (v instanceof int i) {
            return "int with value " + i;
        } else if (v instanceof double d) {
            return "double with value " + d;
        } else if (v instanceof long l) {
            return "long with value " + l;
        } else if (v instanceof boolean b) {
            return "boolean with value " + b;
        } else if (v instanceof String s) {
            return "String: " + s;
        } else {
            return "Unknown type: " + v;
        }
    }
    
    /**
     * JLS §14.11: Switch on primitive types
     */
    static void demoSwitchPrimitive() {
        System.out.println("Demo 2: Switch on Primitives");
        System.out.println("=".repeat(50));
        
        int[] values = {1, 2, 3, 100, 200, 1000};
        
        for (int value : values) {
            String result = classify(value);
            System.out.println("  " + value + " -> " + result);
        }
        System.out.println();
    }
    
    static String classify(int v) {
        return switch (v) {
            case int i when i < 10 -> "single digit";
            case int i when i < 100 -> "two digits";
            case int i when i < 1000 -> "three digits";
            default -> "big number";
        };
    }
    
    /**
     * JLS §5.1.8: Unboxing patterns
     */
    static void demoUnboxing() {
        System.out.println("Demo 3: Unboxing Patterns");
        System.out.println("=".repeat(50));
        
        List<Number> numbers = new ArrayList<>();
        numbers.add(42);       // Integer
        numbers.add(3.14);     // Double
        numbers.add(100L);     // Long
        
        for (Number n : numbers) {
            String result = process(n);
            System.out.println("  " + n + " -> " + result);
        }
        System.out.println();
    }
    
    static String process(Number n) {
        // Unboxing pattern
        return switch (n) {
            case Integer i -> "Integer " + i;
            case Double d -> "Double " + d;
            case Long l -> "Long " + l;
            case null -> "null";
            default -> "Other: " + n;
        };
    }
}
