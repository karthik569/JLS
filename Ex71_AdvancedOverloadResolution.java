/**
 * Ex71_AdvancedOverloadResolution.java
 *
 * This program demonstrates the precise phases of Overload Resolution
 * as specified in JLS §15.12.2.
 *
 * The compiler searches for the most specific method in three phases:
 * Phase 1: No boxing/unboxing or varargs.
 * Phase 2: Boxing/unboxing allowed, but no varargs.
 * Phase 3: Varargs allowed.
 */
import java.util.*;

public class Ex71_AdvancedOverloadResolution {

    // --- Target Methods ---
    public static void resolve(int i) {
        System.out.println("Resolved to: resolve(int)");
    }

    public static void resolve(Integer i) {
        System.out.println("Resolved to: resolve(Integer)");
    }

    public static void resolve(long l) {
        System.out.println("Resolved to: resolve(long)");
    }

    public static void resolve(int... is) {
        System.out.println("Resolved to: resolve(int...)");
    }

    public static void resolve(String s) {
        System.out.println("Resolved to: resolve(String)");
    }

    public static void resolve(Object o) {
        System.out.println("Resolved to: resolve(Object)");
    }

    public static void main(String[] args) {
        System.out.println("--- Overload Resolution Phases ---");

        // Scenario 1: Exact match (Phase 1)
        System.out.print("Call with 10: ");
        resolve(10); // Matches resolve(int) exactly

        // Scenario 2: Widening (Phase 1)
        // byte/short/char widen to int.
        System.out.print("Call with (byte)5: ");
        resolve((byte) 5); // Matches resolve(int) via widening

        // Scenario 3: Boxing (Phase 2)
        // If no exact or widening match exists, try boxing.
        // But wait, int -> long (widening) takes priority over int -> Integer (boxing).
        System.out.print("Call with 10 (int) vs long/Integer: ");
        // In our current set: resolve(int) is an exact match.
        // If we remove resolve(int), then resolve(long) (widening) wins over resolve(Integer) (boxing).
        resolve(10);

        // Scenario 4: Varargs (Phase 3)
        System.out.print("Call with varargs: ");
        resolve(new int[]{1, 2, 3}); // Matches resolve(int...)

        // Scenario 5: Ambiguity
        // resolve(null) matches both resolve(String) and resolve(Object).
        // The most specific one (String) wins.
        System.out.print("Call with (String)null: ");
        resolve((String) null);

        System.out.println("\nPriority Summary:");
        System.out.println("1. Exact Match -> 2. Widening -> 3. Boxing -> 4. Varargs");
    }
}
