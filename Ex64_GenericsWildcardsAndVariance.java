/**
 * Ex64_GenericsWildcardsAndVariance.java
 *
 * This program demonstrates the rules of Generic Variance and Wildcards
 * as specified in JLS §4.5.3 (Wildcards) and §4.5.4 (Variance).
 *
 * Key concept: PECS (Producer Extends, Consumer Super)
 * - Use '<? extends T>' when you only need to read from the structure (Producer).
 * - Use '<? super T>' when you only need to write into the structure (Consumer).
 */
import java.util.*;

public class Ex64_GenericsWildcardsAndVariance {

    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List<Double> doubles = Arrays.asList(1.1, 2.2, 3.3);
        List<Number> nums = new ArrayList<>();

        System.out.println("--- Producer Extends (<? extends T>) ---");
        // We can read from a list of any subtype of Number, but we cannot write to it.
        sumOfList(ints);
        sumOfList(doubles);

        System.out.println("\n--- Consumer Super (<? super T>) ---");
        // We can write Integers into a list of Number or any of its supertypes (like Object).
        addNumbers(nums, ints);

        List<Object> objects = new ArrayList<>();
        addNumbers(objects, ints);

        System.out.println("\n--- Variance Constraints ---");
        demonstrateVariance();
    }

    /**
     * JLS §4.5.3: Upper-bounded wildcards.
     * This method acts as a 'Producer'. It reads from the list.
     * The list can be a List of Number or any subclass of Number.
     */
    public static double sumOfList(List<? extends Number> list) {
        double s = 0.0;
        for (Number n : list) {
            s += n.doubleValue();
        }
        System.out.println("Sum: " + s);
        return s;
        // list.add(10); // COMPILE ERROR: Cannot add to a list with an upper-bounded wildcard.
    }

    /**
     * JLS §4.5.3: Lower-bounded wildcards.
     * This method acts as a 'Consumer'. It writes to the list.
     * The list can be a List of Integer or any supertype of Integer.
     */
    public static void addNumbers(List<? super Integer> list, List<Integer> source) {
        for (Integer i : source) {
            list.add(i);
        }
        System.out.println("Added " + source.size() + " elements to list.");
        // Integer i = list.get(0); // COMPILE ERROR: Returns Object, not necessarily Integer.
    }

    private static void demonstrateVariance() {
        // JLS §4.5.4: Generic types are invariant.
        // List<Integer> is NOT a subtype of List<Number>.

        List<Integer> intList = new ArrayList<>();
        // List<Number> numList = intList; // COMPILE ERROR: Incompatible types

        System.out.println("Generic types are invariant: List<Integer> is not a subtype of List<Number>.");

        // However, List<? extends Number> IS a supertype of List<Integer>.
        List<? extends Number> wildList = intList;
        System.out.println("Wildcard types allow variance: List<? extends Number> can hold List<Integer>.");
    }
}
