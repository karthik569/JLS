package jls;

/**
 * JLS §8.7 (Java 8+): Static Initializers
 * 
 * A static initializer declares a block of code executed exactly once when
 * the class is initialized. It is used to initialize static fields in ways
 * beyond simple assignment.
 * 
 * Key concepts:
 * - Static initializer block: 'static { ... }'
 * - Executed at class initialization time
 * - Multiple static blocks are merged by compiler in source order
 * - Can throw checked exceptions (wrapped in ExceptionInInitializerError)
 * - Synchronized implicitly (thread-safe)
 */
public class Ex108_StaticInitializers {
    
    private static final int COMPILE_TIME_CONSTANT = 100;
    
    // Computed at class loading time using a static block
    private static final int RUNTIME_COMPUTED;
    private static final String CONFIG_NAME;
    private static int instanceCount = 0;
    
    // Static initializer block
    static {
        System.out.println("  [static block 1] Executed at class init");
        RUNTIME_COMPUTED = COMPILE_TIME_CONSTANT * 2 + 7;
        CONFIG_NAME = "production";
        System.out.println("  [static block 1] RUNTIME_COMPUTED=" + RUNTIME_COMPUTED);
    }
    
    // Second static block (legal, executed in source order)
    static {
        System.out.println("  [static block 2] Executed after block 1");
        System.out.println("  [static block 2] CONFIG_NAME=" + CONFIG_NAME);
    }
    
    // A static field can be initialized in either a field initializer or a static block, but not both
    private static final int INITIALIZED_AT_DECL = 42;
    private static int initializedInBlock;
    
    static {
        initializedInBlock = 99;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Static Initializers Demo ===\n");
        System.out.println("After class initialization:");
        System.out.println("  RUNTIME_COMPUTED = " + RUNTIME_COMPUTED);
        System.out.println("  CONFIG_NAME = " + CONFIG_NAME);
        System.out.println("  INITIALIZED_AT_DECL = " + INITIALIZED_AT_DECL);
        System.out.println("  initializedInBlock = " + initializedInBlock);
        
        System.out.println("\nDemo 2: Lazy Init via Static Holder Pattern");
        System.out.println("=".repeat(50));
        // Class LoadedSingleton
        System.out.println("  Singleton value: " + LoadedSingleton.getValue());
        System.out.println("  Singleton value (again): " + LoadedSingleton.getValue());
        
        System.out.println("\nDemo 3: Static Initializer Failure");
        System.out.println("=".repeat(50));
        // Demonstrate the third class is never reached if the second fails
        try {
            Class<?> c = Class.forName("jls.Ex108_StaticInitializers$Failing");
        } catch (ExceptionInInitializerError e) {
            System.out.println("  Caught ExceptionInInitializerError: " + e.getCause());
        } catch (ClassNotFoundException e) {
            System.out.println("  Class not found");
        }
    }
    
    // Static nested class for lazy init demo
    static class LoadedSingleton {
        private static final int value = compute();
        
        private static int compute() {
            System.out.println("  [LoadedSingleton static] Computing value...");
            return 1234;
        }
        
        static int getValue() { return value; }
    }
    
    // Class that fails during static initialization
    static class Failing {
        static {
            int x = 1 / 0; // ArithmeticException at class init
        }
    }
}
