/**
 * Ex70_DefiniteAssignmentEdgeCases.java
 *
 * This program explores the rules of Definite Assignment
 * as specified in JLS §16 (Definite Assignment).
 *
 * A variable is "definitely assigned" if every possible execution path
 * to that point ensures the variable has been assigned a value.
 */
public class Ex70_DefiniteAssignmentEdgeCases {

    public static void main(String[] args) {
        System.out.println("--- Definite Assignment Demo ---");

        // Case 1: Simple if-else
        int a;
        boolean condition = true;
        if (condition) {
            a = 1;
        } else {
            a = 2;
        }
        System.out.println("a is definitely assigned: " + a);

        // Case 2: The "missing else" trap
        // int b;
        // if (condition) { b = 1; }
        // System.out.println(b); // COMPILE ERROR: b might not have been initialized

        // Case 3: try-catch-finally
        int c;
        try {
            c = 10;
            // throw new Exception(); // If we throw here, c is assigned, but does the catch block affect it?
        } catch (Exception e) {
            c = 20;
        }
        // c is definitely assigned because both try and catch paths assign it.
        System.out.println("c is definitely assigned: " + c);

        // Case 4: The Finally Block Nuance
        // int d;
        // try { d = 1; } finally { /* something */ }
        // System.out.println(d); // Valid if try is guaranteed to run.

        // Case 5: Complex Control Flow
        int e = 0; // Initialized to avoid error, but let's look at the logic
        boolean flag = true;
        if (flag) {
            if (true) {
                e = 100;
            } else {
                // e = 200; // If this is commented, e is NOT definitely assigned here
            }
        } else {
            e = 300;
        }
        System.out.println("e result: " + e);

        System.out.println("\nCheck JLS §16 for formal rules on definite assignment.");
    }
}
