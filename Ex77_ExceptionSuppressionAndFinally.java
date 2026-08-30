/**
 * Ex77_ExceptionSuppressionAndFinally.java
 *
 * This program demonstrates advanced exception handling patterns, including
 * exception suppression and the behavior of 'finally' blocks as specified
 * in JLS §11 and §14.20.
 *
 * Key Concepts:
 * 1. Exception Suppression: Using Throwable.addSuppressed() to attach
 *    secondary exceptions (common in try-with-resources).
 * 2. Finally-Return Gotcha: A return statement in a finally block
 *    overwrites any exception thrown in the try or catch blocks.
 */
public class Ex77_ExceptionSuppressionAndFinally {

    public static void demonstrateSuppression() {
        System.out.println("--- Exception Suppression ---");
        try {
            throw new RuntimeException("Primary Exception");
        } catch (RuntimeException e) {
            try {
                throw new RuntimeException("Suppressed Exception");
            } catch (RuntimeException se) {
                e.addSuppressed(se); // Attaching the secondary exception
            }
            throw e;
        }
    }

    public static int finallyReturnGotcha() {
        System.out.println("\n--- Finally Return Gotcha ---");
        try {
            System.out.println("Throwing exception in try...");
            throw new RuntimeException("Exception in try");
        } finally {
            System.out.println("Executing finally block and returning 42...");
            return 42; // This swallows the exception!
        }
    }

    public static void main(String[] args) {
        try {
            demonstrateSuppression();
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
            for (Throwable s : e.getSuppressed()) {
                System.out.println(" - Suppressed: " + s.getMessage());
            }
        }

        int result = finallyReturnGotcha();
        System.out.println("Method returned: " + result);
    }
}
