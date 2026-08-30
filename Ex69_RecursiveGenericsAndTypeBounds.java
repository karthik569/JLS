/**
 * Ex69_RecursiveGenericsAndTypeBounds.java
 *
 * This program demonstrates advanced generic type bounds and recursive generics
 * as specified in JLS §4.5 (Parameterized Types).
 *
 * Key concepts:
 * 1. Recursive Type Bounds: T extends Comparable<T>.
 * 2. The standard "Flexible" bound: T extends Comparable<? super T>.
 * 3. Multiple Bounds: T extends ClassA & InterfaceB.
 * 4. Generic Type Nesting.
 */
import java.util.*;

public class Ex69_RecursiveGenericsAndTypeBounds {

    // JLS §4.5: Recursive Type Bound
    // This ensures that T is Comparable with itself.
    static <T extends Comparable<T>> T findMax(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    // JLS §4.5: The la-standard pattern for Comparables
    // Using '<? super T>' allows T to be compared against a supertype's implementation
    // of Comparable, making the method more flexible.
    static <T extends Comparable<? super T>> T flexibleMax(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    // JLS §4.5: Multiple Bounds
    // T must be a subclass of Number AND implement Comparable.
    static <T extends Number & Comparable<T>> void printNumericComparable(T val) {
        System.out.println("Value: " + val + ", Double value: " + val.doubleValue());
    }

    // Recursive Generic Class
    static class Node<T extends Node<T>> {
        T next;
        public void setNext(T next) { this.next = next; }
    }

    static class IntNode extends Node<IntNode> {}
    static class StringNode extends Node<StringNode> {}

    public static void main(String[] args) {
        System.out.println("--- Recursive Type Bounds ---");
        System.out.println("Max of 10, 20: " + findMax(10, 20));
        System.out.println("Max of \"apple\", \"banana\": " + findMax("apple", "banana"));

        System.out.println("\n--- Flexible Bounds (<? super T>) ---");
        // This would work even if T's Comparable was defined in a superclass
        System.out.println("Flexible Max: " + flexibleMax(10, 20));

        System.out.println("\n--- Multiple Bounds ---");
        printNumericComparable(10);      // Integer extends Number & Comparable<Integer>
        printNumericComparable(3.14);    // Double extends Number & Comparable<Double>
        // printNumericComparable("hi"); // COMPILE ERROR: String does not extend Number

        System.out.println("\n--- Recursive Generic Classes ---");
        IntNode in = new IntNode();
        in.setNext(new IntNode());

        StringNode sn = new StringNode();
        sn.setNext(new StringNode());

        System.out.println("Recursive nodes created successfully.");
    }
}
