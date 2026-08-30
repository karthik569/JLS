/**
 * Ex76_FloatingPointAndStrictfp.java
 *
 * This program demonstrates floating point semantics and the strictfp
 * modifier as specified in JLS §4.2.2.
 *
 * Key Concepts:
 * 1. Floating Point Precision: IEEE 754 standard.
 * 2. strictfp Modifier: Ensures that floating-point calculations are
 *    exactly the same across all platforms by preventing the use of
 *    extended precision intermediate results (like 80-bit registers on x86).
 * 3. Note: Since Java 17, all floating-point expressions are strict by default,
 *    rendering strictfp effectively obsolete but still valid for backward compatibility.
 */
public class Ex76_FloatingPointAndStrictfp {

    // strictfp ensures consistency across different JVM implementations
    public static strictfp double computeStrict(double a, double b) {
        return a * b + a;
    }

    public static double computeNormal(double a, double b) {
        return a * b + a;
    }

    public static void main(String[] args) {
        System.out.println("--- Floating Point and strictfp ---");

        double a = 1.23456789012345;
        double b = 9.87654321098765;

        System.out.println("Normal compute: " + computeNormal(a, b));
        System.out.println("Strict compute: " + computeStrict(a, b));

        // demonstrating the precision issues (Classic 0.1 + 0.2)
        double sum = 0.1 + 0.2;
        System.out.println("\n0.1 + 0.2 = " + sum);
        System.out.println("Is 0.1 + 0.2 == 0.3? " + (sum == 0.3));
        System.out.println("Actual result: " + sum);
    }
}
