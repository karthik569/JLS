package jls;

import java.util.function.*;

/**
 * JLS §8.1.3 & §15.27.2 (Java 8+): Effectively Final Variable Capture
 * 
 * A local variable that is never reassigned after initialization is
 * "effectively final" and can be captured by lambdas and anonymous classes
 * without requiring the explicit 'final' modifier.
 * 
 * Key concepts:
 * - Effectively final: a local variable whose value is never modified
 *   after initialization (compile-time concept)
 * - Lambdas and anonymous classes can capture effectively final variables
 * - Reassignment breaks effectively-final status
 * - For-loop variables: the loop variable in a traditional 'for' is NOT
 *   effectively final because it is reassigned each iteration; copy it
 *   into a new local to capture. (Enhanced for-loops and per-iteration
 *   loop variables in 'for' have different capture rules.)
 */
public class Ex107_EffectivelyFinalCapture {
    
    public static void main(String[] args) {
        System.out.println("=== Effectively Final Capture Demo ===\n");
        
        demoEffectivelyFinal();
        demoNotEffectivelyFinal();
        demoForLoopCapture();
        demoMultiCapture();
    }
    
    static void demoEffectivelyFinal() {
        System.out.println("Demo 1: Effectively Final Variable Capture");
        System.out.println("=".repeat(50));
        String name = "Alice"; // never reassigned -> effectively final
        int age = 30;          // never reassigned -> effectively final
        
        Supplier<String> summary = () -> name + " is " + age;
        System.out.println("  " + summary.get());
        System.out.println();
    }
    
    static void demoNotEffectivelyFinal() {
        System.out.println("Demo 2: Variable That Is NOT Effectively Final");
        System.out.println("=".repeat(50));
        int counter = 0;
        counter++; // reassignment: counter is NOT effectively final
        // Supplier<Integer> bad = () -> counter; // COMPILE ERROR
        
        // Solution: copy into a new effectively final variable
        int snapshot = counter;
        Supplier<Integer> ok = () -> snapshot;
        System.out.println("  Snapshot value: " + ok.get());
        System.out.println();
    }
    
    static void demoForLoopCapture() {
        System.out.println("Demo 3: For-Loop Variable Capture (via Copy)");
        System.out.println("=".repeat(50));
        // The traditional 'for' loop variable 'i' is reassigned each iteration
        // and is therefore NOT effectively final. We must copy each iteration
        // value into a new local variable to capture it.
        Runnable[] tasks = new Runnable[3];
        for (int i = 0; i < 3; i++) {
            final int captured = i; // effectively final copy for this iteration
            tasks[i] = () -> System.out.println("  Task captured i=" + captured);
        }
        for (Runnable r : tasks) r.run();
        System.out.println();
    }
    
    static void demoMultiCapture() {
        System.out.println("Demo 4: Multiple Captured Variables");
        System.out.println("=".repeat(50));
        String firstName = "Jane";
        String lastName = "Doe";
        int year = 2026;
        
        Function<String, String> formatter = prefix -> 
            prefix + ": " + firstName + " " + lastName + " (" + year + ")";
        
        System.out.println("  " + formatter.apply("Info"));
        System.out.println();
    }
    
    // Demonstration: anonymous class also captures effectively final
    static IntSupplier buildAdder(int base) {
        // 'base' is a method parameter, inherently effectively final
        return new IntSupplier() {
            @Override
            public int getAsInt() { return base + 100; }
        };
    }
}
