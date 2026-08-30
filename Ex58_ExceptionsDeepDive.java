/**
 * JLS Chapter 11: Exceptions (Deep Dive)
 *
 * Demonstrates:
 * - JLS §11.1: Exception Kinds (checked, unchecked, Error)
 * - JLS §11.2: Compile-Time Checking of Exceptions
 * - JLS §11.3: Run-Time Handling of Exceptions
 * - JLS §11.4: Exception Hierarchy
 * - JLS §11.5: The try Statement (try-catch-finally, try-with-resources)
 * - JLS §11.5.1-11.5.3: Catch clauses, multi-catch, exception parameter finality
 * - JLS §11.5.4: The finally Block
 * - JLS §11.5.5: try-with-resources (automatic resource management)
 * - JLS §11.5.6: try-with-resources with effectively final variables (Java 9+)
 * - JLS §11.6: The throw Statement
 * - JLS §11.7: The throws Clause
 * - JLS §11.8: Exception Chaining (cause, suppressed exceptions)
 */
public class Ex58_ExceptionsDeepDive {

    // ============================================================
    // JLS §11.1: Exception Kinds
    // ============================================================

    // Checked exceptions - must be caught or declared (compile-time checked)
    // Extend Exception but not RuntimeException
    static class CheckedException extends Exception {
        public CheckedException(String msg) { super(msg); }
    }

    // Unchecked exceptions - RuntimeException and subclasses
    // Not checked at compile time
    static class UncheckedException extends RuntimeException {
        public UncheckedException(String msg) { super(msg); }
    }

    // Error - serious problems, not meant to be caught
    // Extend Error
    static class CustomError extends Error {
        public CustomError(String msg) { super(msg); }
    }

    // ============================================================
    // JLS §11.2: Compile-Time Checking
    // ============================================================

    // Method that throws checked exception - must declare or catch
    static void throwsChecked() throws CheckedException {
        throw new CheckedException("This is checked");
    }

    // Method that throws unchecked - no declaration needed
    static void throwsUnchecked() {
        throw new UncheckedException("This is unchecked");
    }

    // Method that throws Error - no declaration needed
    static void throwsError() {
        throw new CustomError("This is an error");
    }

    // ============================================================
    // JLS §11.3: Run-Time Handling
    // ============================================================

    static void runtimeHandlingDemo() {
        try {
            throwsChecked();
        } catch (CheckedException e) {
            System.out.println("  Caught checked: " + e.getMessage());
        }

        try {
            throwsUnchecked();
        } catch (UncheckedException e) {
            System.out.println("  Caught unchecked: " + e.getMessage());
        }

        try {
            throwsError();
        } catch (CustomError e) {
            System.out.println("  Caught error: " + e.getMessage());
        }
    }

    // ============================================================
    // JLS §11.5: The try Statement
    // ============================================================

    // Basic try-catch-finally
    static void basicTryCatchFinally() {
        System.out.println("  Basic try-catch-finally:");
        try {
            System.out.println("    In try block");
            throw new CheckedException("from try");
        } catch (CheckedException e) {
            System.out.println("    Caught: " + e.getMessage());
        } finally {
            System.out.println("    Finally block always executes");
        }
    }

    // Multiple catch clauses (JLS §11.5.1)
    static void multipleCatchDemo() {
        System.out.println("  Multiple catch clauses:");
        try {
            if (Math.random() > 0.5) {
                throw new CheckedException("checked");
            } else {
                throw new UncheckedException("unchecked");
            }
        } catch (CheckedException e) {
            System.out.println("    Caught CheckedException");
        } catch (UncheckedException e) {
            System.out.println("    Caught UncheckedException");
        }
    }

    // Multi-catch (Java 7+) - catch multiple exception types in one clause
    // JLS §11.5.2: Exception parameter is implicitly final
    static void multiCatchDemo() {
        System.out.println("  Multi-catch (Java 7+):");
        try {
            if (Math.random() > 0.5) {
                throw new CheckedException("checked");
            } else {
                throw new IllegalArgumentException("illegal arg");
            }
        } catch (CheckedException | IllegalArgumentException e) {
            // e is implicitly final here
            // e = new Exception(); // Compile error!
            System.out.println("    Caught multi: " + e.getClass().getSimpleName());
        }
    }

    // finally block (JLS §11.5.4)
    static void finallyDemo() {
        System.out.println("  Finally block behavior:");
        try {
            System.out.println("    Try");
            return;  // Finally still executes!
        } finally {
            System.out.println("    Finally (even after return)");
        }
    }

    // try-with-resources (JLS §11.5.5) - automatic resource management
    static void tryWithResourcesDemo() {
        System.out.println("  try-with-resources:");

        // Resource must implement AutoCloseable
        class MyResource implements AutoCloseable {
            private String name;
            MyResource(String name) { this.name = name; System.out.println("    " + name + " opened"); }
            public void doWork() { System.out.println("    " + name + " working"); }
            @Override public void close() { System.out.println("    " + name + " closed"); }
        }

        // Basic try-with-resources
        try (MyResource r1 = new MyResource("Resource1")) {
            r1.doWork();
        }  // r1.close() called automatically

        // Multiple resources (closed in reverse order)
        try (MyResource r1 = new MyResource("R1");
             MyResource r2 = new MyResource("R2")) {
            r1.doWork();
            r2.doWork();
        }  // r2.close(), then r1.close()

        // With catch and finally
        try (MyResource r = new MyResource("WithCatch")) {
            throw new RuntimeException("error in try");
        } catch (RuntimeException e) {
            System.out.println("    Caught: " + e.getMessage());
        } finally {
            System.out.println("    Finally after try-with-resources");
        }
    }

    // try-with-resources with effectively final variables (Java 9+)
    // JLS §11.5.6
    static void tryWithResourcesEffectivelyFinal() {
        System.out.println("  try-with-resources with effectively final (Java 9+):");

        class Resource implements AutoCloseable {
            public void close() { System.out.println("    Resource closed"); }
        }

        Resource resource = new Resource();  // Effectively final (not reassigned)
        try (resource) {  // Can use effectively final variable
            System.out.println("    Using resource");
        }
        // resource is closed here
    }

    // ============================================================
    // JLS §11.6: The throw Statement
    // ============================================================

    static void throwStatementDemo() {
        System.out.println("  throw statement:");

        // Throw checked exception
        try {
            throw new CheckedException("thrown explicitly");
        } catch (CheckedException e) {
            System.out.println("    Caught thrown checked: " + e.getMessage());
        }

        // Throw unchecked
        try {
            throw new UncheckedException("thrown unchecked");
        } catch (UncheckedException e) {
            System.out.println("    Caught thrown unchecked: " + e.getMessage());
        }

        // Throw null - throws NullPointerException
        try {
            throw null;  // Throws NullPointerException
        } catch (NullPointerException e) {
            System.out.println("    throw null -> NullPointerException");
        }

        // Throw in expression context (Java 8+ lambda, etc.)
        // String s = (true) ? "ok" : (throw new IllegalStateException());  // Java 21+ throw expression
    }

    // ============================================================
    // JLS §11.7: The throws Clause
    // ============================================================

    // Checked exceptions must be declared in throws clause
    static void methodWithThrows() throws CheckedException, IllegalArgumentException {
        if (Math.random() > 0.5) {
            throw new CheckedException("checked from method");
        } else {
            throw new IllegalArgumentException("unchecked from method");
        }
    }

    // Overriding method can throw fewer or narrower exceptions
    static class Base {
        void method() throws CheckedException, IllegalArgumentException {}
    }

    static class Derived extends Base {
        @Override
        void method() throws CheckedException {}  // Narrower - OK
        // void method() throws Exception {}  // Broader - compile error!
    }

    // ============================================================
    // JLS §11.8: Exception Chaining
    // ============================================================

    static void exceptionChainingDemo() {
        System.out.println("  Exception chaining (cause):");

        try {
            try {
                throw new CheckedException("root cause");
            } catch (CheckedException e) {
                // Wrap in unchecked with cause
                throw new RuntimeException("wrapped", e);
            }
        } catch (RuntimeException e) {
            System.out.println("    Caught: " + e.getMessage());
            System.out.println("    Cause: " + e.getCause().getMessage());
            System.out.println("    Cause class: " + e.getCause().getClass().getSimpleName());
        }

        // Multiple causes (chained)
        try {
            try {
                try {
                    throw new Exception("level 1");
                } catch (Exception e) {
                    throw new Exception("level 2", e);
                }
            } catch (Exception e) {
                throw new Exception("level 3", e);
            }
        } catch (Exception e) {
            System.out.println("    Chain: " + e.getMessage());
            Throwable cause = e.getCause();
            int level = 2;
            while (cause != null) {
                System.out.println("    Cause level " + level++ + ": " + cause.getMessage());
                cause = cause.getCause();
            }
        }

        // Suppressed exceptions (try-with-resources)
        System.out.println("  Suppressed exceptions:");
        class FailingResource implements AutoCloseable {
            private String name;
            FailingResource(String name) { this.name = name; }
            public void work() { throw new RuntimeException(name + " work failed"); }
            @Override public void close() { throw new RuntimeException(name + " close failed"); }
        }

        try {
            try (FailingResource r = new FailingResource("Resource")) {
                r.work();  // Throws exception
            }
        } catch (RuntimeException e) {
            System.out.println("    Primary: " + e.getMessage());
            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("    Suppressed: " + suppressed.getMessage());
            }
        }
    }

    // ============================================================
    // Additional: Stack trace, finally with return, exception in finally
    // ============================================================

    static void stackTraceDemo() {
        System.out.println("  Stack trace:");
        try {
            methodA();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    static void methodA() throws Exception { methodB(); }
    static void methodB() throws Exception { methodC(); }
    static void methodC() throws Exception { throw new Exception("from methodC"); }

    // Finally with return value
    static int finallyWithReturn() {
        try {
            return 10;
        } finally {
            // This return would override the try return!
            // return 20;  // Uncomment to see finally return win
        }
    }

    // Exception in finally masks original exception
    static void exceptionInFinally() {
        System.out.println("  Exception in finally masks original:");
        try {
            try {
                throw new Exception("original");
            } finally {
                throw new Exception("from finally");  // Masks original!
            }
        } catch (Exception e) {
            System.out.println("    Caught: " + e.getMessage());
            System.out.println("    Original lost!");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== JLS Chapter 11: Exceptions Deep Dive ===\n");

        System.out.println("--- Exception Kinds (JLS §11.1) ---");
        System.out.println("CheckedException extends Exception");
        System.out.println("UncheckedException extends RuntimeException");
        System.out.println("CustomError extends Error");

        System.out.println("\n--- Compile-Time Checking (JLS §11.2) ---");
        runtimeHandlingDemo();

        System.out.println("\n--- Run-Time Handling (JLS §11.3) ---");
        basicTryCatchFinally();
        multipleCatchDemo();
        multiCatchDemo();
        finallyDemo();

        System.out.println("\n--- try-with-resources (JLS §11.5.5, §11.5.6) ---");
        tryWithResourcesDemo();
        tryWithResourcesEffectivelyFinal();

        System.out.println("\n--- throw Statement (JLS §11.6) ---");
        throwStatementDemo();

        System.out.println("\n--- throws Clause (JLS §11.7) ---");
        try {
            methodWithThrows();
        } catch (Exception e) {
            System.out.println("  Caught from methodWithThrows: " + e.getClass().getSimpleName());
        }

        System.out.println("\n--- Exception Chaining (JLS §11.8) ---");
        exceptionChainingDemo();

        System.out.println("\n--- Stack Trace ---");
        stackTraceDemo();

        System.out.println("\n--- finally with return ---");
        System.out.println("  finallyWithReturn(): " + finallyWithReturn());

        System.out.println("\n--- Exception in finally ---");
        exceptionInFinally();
    }
}