package jls;

/**
 * Project Valhalla Specification (Java 25+ Early Access): Null-Restricted & Non-Nullable Types
 * 
 * In Java, all reference types are implicitly nullable (can hold 'null').
 * Project Valhalla introduces Null-Restricted Types to guarantee at compile-time and runtime
 * that a reference can never be null, enabling complete in-register and contiguous memory storage.
 * 
 * Key Valhalla Type Syntax:
 * - T! : Strictly non-nullable (e.g., Complex! cannot be null, initial value is default zero-instance).
 * - T? : Explicitly nullable reference.
 * - T  : Standard reference type (migration-compatible).
 * 
 * Rules & Guarantees:
 * 1. Zero-default Initialization: A null-restricted value type 'Point!' cannot be null,
 *    so its default value upon array allocation is Point(0, 0) instead of null.
 * 2. Flatness Requirement: Null-restricted value arrays (Point![]) can be flattened 100%
 *    without needing a secondary null-indicator byte channel.
 * 3. Tearing & Atomicity:
 *    - By default, multi-word value classes might tear under unsynchronized concurrent writes.
 *    - Declaring 'value class Complex' can be qualified with 'atomic' to prevent 64-bit/128-bit tearing.
 */
public class Ex94_ValhallaNullRestrictedTypes {

    public static void main(String[] args) {
        System.out.println("=== Project Valhalla: Null-Restricted Types Demo ===\n");
        
        demoNullRestrictedSemantics();
        demoZeroDefaultValues();
        demoTearingAndAtomicity();
    }

    /**
     * Null-Restricted Type Expressions
     */
    static void demoNullRestrictedSemantics() {
        System.out.println("1. Null-Restricted Types (T! vs T?):");
        System.out.println("   - 'String!' variable: Compiler prohibits assigning 'null'.");
        System.out.println("     String! s = null; // Compile-time Error!");
        System.out.println("   - 'Point!' field in class: Eliminates all NullPointerExceptions by design.");
        System.out.println("   - Non-null assertion on migration: 'T! nonNull = (T!) nullableValue;'\n");
    }

    /**
     * Zero-Default Initialization
     */
    static void demoZeroDefaultValues() {
        System.out.println("2. Zero-Default Value Arrays (Point![10]):");
        System.out.println("   - When allocating: Point![] points = new Point![10];");
        System.out.println("   - Instead of filling array with 10 null pointers, all 10 slots are initialized");
        System.out.println("     immediately to Point.default (x = 0, y = 0).");
        System.out.println("   - Guaranteed zero allocation delay and zero dereference overhead.\n");
    }

    /**
     * Tearing Prevention & Atomic Value Classes
     */
    static void demoTearingAndAtomicity() {
        System.out.println("3. Tearing Prevention (atomic value class):");
        System.out.println("   - Non-atomic value class: Allows 128-bit values (e.g. two 64-bit longs) to be written");
        System.out.println("     without bus locks for maximum speed. In race conditions, tearing can occur.");
        System.out.println("   - 'atomic value class TransactionId { long high; long low; }':");
        System.out.println("     JVM guarantees atomic 128-bit reads and writes, completely eliminating tearing.");
    }
}
