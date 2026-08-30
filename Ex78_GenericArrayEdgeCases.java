/**
 * Ex78_GenericArrayEdgeCases.java
 *
 * This program explores the restrictions on creating arrays of generic types
 * and the concept of array covariance, as specified in JLS §4.5 and §10.
 *
 * Key Concepts:
 * 1. Generic Array Creation: Why 'new T[10]' is illegal (Type Erasure).
 * 2. Array Covariance: Arrays of subtypes are subtypes of arrays of supertypes.
 * 3. ArrayStoreException: Runtime failure when storing an incompatible type
 *    in a covariant array.
 */
import java.lang.reflect.Array;
import java.util.*;

public class Ex78_GenericArrayEdgeCases {

    static class GenericContainer<T> {
        T[] elements;

        @SuppressWarnings("unchecked")
        public GenericContainer(int size) {
            // System.out.println("Attempting: new T[size]");
            // elements = new T[size]; // ERROR: Generic array creation is forbidden

            // Workaround 1: Create an Object array and cast (most common)
            elements = (T[]) new Object[size];
        }

        public void set(int index, T value) {
            elements[index] = value;
        }

        public T get(int index) {
            return elements[index];
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Generic Array Edge Cases ---");

        // 1. Generic Container Workaround
        GenericContainer<String> strings = new GenericContainer<>(5);
        strings.set(0, "Hello");
        System.out.println("Generic array value: " + strings.get(0));

        // 2. Array Covariance (JLS §10.1)
        System.out.println("\n--- Array Covariance ---");
        Integer[] intArray = {1, 2, 3};
        Object[] objArray = intArray; // Legal: Integer[] is a subtype of Object[]
        System.out.println("Object array assigned from Integer array: OK");

        try {
            System.out.println("Attempting to put a String into an Integer[] via Object[] reference...");
            objArray[0] = "Not an Integer"; // Throws ArrayStoreException at runtime
        } catch (ArrayStoreException e) {
            System.out.println("Caught expected: " + e);
        }

        // 3. Using Reflection for True Generic Arrays
        System.out.println("\n--- Reflection Array Creation ---");
        String[] reflectiveArray = (String[]) Array.newInstance(String.class, 5);
        reflectiveArray[0] = "Reflective";
        System.out.println("Reflective array value: " + reflectiveArray[0]);
    }
}
