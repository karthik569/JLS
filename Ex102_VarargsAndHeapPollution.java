package jls;

import java.util.Arrays;

/**
 * JLS §15.12.4.2 (Java 5+): Varargs Invocation and Heap Pollution
 * 
 * Demonstrates variable arity methods, automatic array creation,
 * and heap pollution warnings with generics.
 * 
 * Key concepts:
 * - Varargs (var-arity) methods: T...
 * - Automatic array allocation at call site
 * - Heap pollution when generic varargs receive non-reifiable types
 * - @SafeVarargs annotation
 */
public class Ex102_VarargsAndHeapPollution {
    
    public static void main(String[] args) {
        System.out.println("=== Varargs & Heap Pollution Demo ===\n");
        
        demoBasicVarargs();
        demoVarargsAsArray();
        demoGenericVarargs();
        demoSafeVarargs();
    }
    
    static void demoBasicVarargs() {
        System.out.println("Demo 1: Basic Varargs Invocation");
        System.out.println("=".repeat(50));
        printAll("Hello", "World", "Java", "25");
        printAll(new String[]{"single", "array"});
        printAll(); // empty call
        System.out.println();
    }
    
    static void printAll(String... items) {
        System.out.println("  Received " + items.length + " item(s): " + Arrays.toString(items));
    }
    
    static void demoVarargsAsArray() {
        System.out.println("Demo 2: Varargs Parameter is Just an Array");
        System.out.println("=".repeat(50));
        int total = sumInts(1, 2, 3, 4, 5);
        System.out.println("  Sum: " + total);
        // Passing an explicit array
        int[] explicit = {10, 20, 30};
        System.out.println("  Sum (explicit array): " + sumInts(explicit));
        System.out.println();
    }
    
    static int sumInts(int... numbers) {
        int sum = 0;
        for (int n : numbers) sum += n;
        return sum;
    }
    
    static void demoGenericVarargs() {
        System.out.println("Demo 3: Generic Varargs (Heap Pollution Risk)");
        System.out.println("=".repeat(50));
        // Generic varargs cause heap pollution warning
        // The array T[] is created but elements are added via unsafe operations
        String[] result = pickStrings("a", "b", "c");
        System.out.println("  pickStrings result: " + Arrays.toString(result));
        System.out.println();
    }
    
    // Heap pollution risk: T... creates Object[] not T[]
    static <T> T[] pickStrings(T... items) {
        // Mixing types into a generic varargs parameter would cause pollution
        // Object[] arr = items; // legal but unsafe
        // arr[0] = 42; // would cause ClassCastException later
        return items;
    }
    
    static void demoSafeVarargs() {
        System.out.println("Demo 4: @SafeVarargs on Final Methods");
        System.out.println("=".repeat(50));
        String[] first = firstElement("alpha", "beta", "gamma");
        Integer[] second = firstElement(1, 2, 3, 4);
        System.out.println("  First string: " + first[0]);
        System.out.println("  First integer: " + second[0]);
        System.out.println();
    }
    
    // @SafeVarargs: tells compiler that this method does NOT cause heap pollution
    @SafeVarargs
    static <T> T[] firstElement(T... items) {
        // Safe because we don't perform any unsafe array assignments
        return items;
    }
}
