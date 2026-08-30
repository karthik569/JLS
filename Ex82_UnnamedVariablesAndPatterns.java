package jls;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * JLS §14.30.3 (Java 22): Unnamed Variables and Patterns
 * 
 * Unnamed variables (using _) can now be used where a variable is not needed.
 * This improves code readability by clearly indicating "we don't care about this".
 * 
 * Key concepts:
 * - Unnamed variable (_): discards a value
 * - Unnamed pattern (_): matches without binding
 * - Unnamed field (_): in records, discard unused fields
 * - Must be initialized (for variables)
 * - Cannot be read or used
 * 
 * JLS §3.11: Unnamed Classes and Instance Main Methods (Java 21)
 */
public class Ex82_UnnamedVariablesAndPatterns {
    
    public static void main(String[] args) {
        System.out.println("=== Unnamed Variables and Patterns Demo ===\n");
        
        demoUnnamedVariables();
        demoUnnamedInPatternMatching();
        demoUnnamedInLambdas();
    }
    
    /**
     * JLS §14.4.1: Unnamed local variables
     */
    static void demoUnnamedVariables() {
        System.out.println("Demo 1: Unnamed Variables");
        System.out.println("=".repeat(50));
        
        int[] numbers = {1, 2, 3, 4, 5};
        
        // Traditional: unused variable
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("  Value: " + numbers[i]);
        }
        
        // Try-with-resources
        try (var resource = new AutoCloseableImpl()) {
            System.out.println("  Resource acquired");
        } catch (Exception e) {
            System.out.println("  Exception: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * JLS §14.30.3: Unnamed patterns in pattern matching
     */
    static void demoUnnamedInPatternMatching() {
        System.out.println("Demo 2: Unnamed Patterns");
        System.out.println("=".repeat(50));
        
        Object[] values = {
            "Hello",
            42,
            3.14,
            new int[]{1, 2, 3},
            Map.of("key", "value")
        };
        
        for (Object value : values) {
            String result = switch (value) {
                case String s -> "String: " + s;
                case Integer i -> "Integer: " + i;
                case Double d -> "Double: " + d;
                case int[] arr -> "Array length: " + arr.length;
                case null -> "null value";
                default -> "Unknown type";
            };
            System.out.println("  " + result);
        }
        System.out.println();
    }
    
    /**
     * JLS §15.27.3: Unnamed in lambda parameters
     */
    static void demoUnnamedInLambdas() {
        System.out.println("Demo 3: Unnamed in Lambdas");
        System.out.println("=".repeat(50));
        
        // Ignore first parameter
        BiConsumer<String, Integer> biConsumer = (String _, Integer value) -> 
            System.out.println("  Received value: " + value);
        
        biConsumer.accept("ignored", 42);
        System.out.println();
    }
    
    static class AutoCloseableImpl implements AutoCloseable {
        @Override
        public void close() {
            System.out.println("  Resource closed");
        }
    }
}
