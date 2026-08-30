/**
 * JLS Chapter 12: Execution (Deep Dive)
 *
 * Demonstrates:
 * - JLS §12.1: Virtual Machine Startup
 * - JLS §12.2: Loading of Classes and Interfaces
 * - JLS §12.3: Linking of Classes and Interfaces (verification, preparation, resolution)
 * - JLS §12.4: Initialization of Classes and Interfaces (<clinit>, static initializers)
 * - JLS §12.5: Creation of New Class Instances (<init>, instance initializers, constructors)
 * - JLS §12.6: Finalization of Class Instances (finalize(), Cleaner, PhantomReference)
 * - JLS §12.7: Unloading of Classes and Interfaces
 * - JLS §12.8: Program Exit
 */
public class Ex59_ExecutionAndInitialization {

    // ============================================================
    // JLS §12.4: Initialization of Classes and Interfaces
    // ============================================================

    // Static fields with initializers
    static int staticField1 = initializeStatic1();
    static int staticField2 = initializeStatic2();

    static int initializeStatic1() {
        System.out.println("  [Class Init] Initializing staticField1");
        return 10;
    }

    static int initializeStatic2() {
        System.out.println("  [Class Init] Initializing staticField2");
        return 20;
    }

    // Static initializer block
    static {
        System.out.println("  [Class Init] Static initializer block running");
        staticField1 = 100;  // Can modify
    }

    // Another static initializer (executes in textual order)
    static {
        System.out.println("  [Class Init] Second static initializer");
    }

    // ============================================================
    // JLS §12.5: Creation of New Class Instances
    // ============================================================

    // Instance fields with initializers
    int instanceField1 = initializeInstance1();
    int instanceField2 = initializeInstance2();

    int initializeInstance1() {
        System.out.println("  [Instance Init] Initializing instanceField1");
        return 1000;
    }

    int initializeInstance2() {
        System.out.println("  [Instance Init] Initializing instanceField2");
        return 2000;
    }

    // Instance initializer block (runs before constructor)
    {
        System.out.println("  [Instance Init] Instance initializer block running");
        instanceField1 = 10000;  // Can modify
    }

    // Another instance initializer
    {
        System.out.println("  [Instance Init] Second instance initializer");
    }

    // Constructor
    public Ex59_ExecutionAndInitialization() {
        System.out.println("  [Instance Init] Constructor body running");
    }

    public Ex59_ExecutionAndInitialization(int value) {
        this();  // Calls no-arg constructor first
        System.out.println("  [Instance Init] Int constructor: " + value);
    }

    // ============================================================
    // JLS §12.4.1: When Initialization Occurs
    // ============================================================

    // A class is initialized when:
    // 1. JVM starts up (main class)
    // 2. Class is loaded and:
    //    - Static field accessed (not constant)
    //    - Static method invoked
    //    - Instance created (new)
    //    - Class.forName()
    //    - Subclass initialized
    //    - Interface with default method implemented

    // Constant fields (compile-time constants) do NOT trigger initialization
    static final int COMPILE_TIME_CONSTANT = 42;  // No initialization trigger
    static final String CONSTANT_STRING = "hello";  // No trigger

    // Non-constant static field - DOES trigger initialization
    static int NON_CONSTANT_FIELD = computeNonConstant();

    static int computeNonConstant() {
        System.out.println("  [Trigger] Computing non-constant field");
        return 999;
    }

    // ============================================================
    // JLS §12.6: Finalization
    // ============================================================

    // finalize() - DEPRECATED since Java 9, removed in Java 18
    // Use Cleaner or try-with-resources instead
    @Deprecated(since = "9", forRemoval = true)
    @Override
    protected void finalize() throws Throwable {
        try {
            System.out.println("  [Finalization] finalize() called for " + this);
        } finally {
            super.finalize();
        }
    }

    // Modern replacement: java.lang.ref.Cleaner
    private static final java.lang.ref.Cleaner cleaner = java.lang.ref.Cleaner.create();

    static class CleanableResource implements AutoCloseable {
        private final String name;
        private final java.lang.ref.Cleaner.Cleanable cleanable;

        CleanableResource(String name) {
            this.name = name;
            // Register cleanup action
            this.cleanable = cleaner.register(this, () -> {
                System.out.println("  [Cleaner] Cleaning up: " + name);
            });
        }

        public void doWork() { System.out.println("  [Cleaner] " + name + " working"); }

        @Override
        public void close() {
            cleanable.clean();  // Explicit cleanup
            System.out.println("  [Cleaner] " + name + " closed explicitly");
        }
    }

    // PhantomReference for advanced finalization
    static void phantomReferenceDemo() {
        System.out.println("  PhantomReference demo:");
        Object referent = new Object();
        java.lang.ref.PhantomReference<Object> phantom =
                new java.lang.ref.PhantomReference<>(referent, new java.lang.ref.ReferenceQueue<>());

        System.out.println("    Referent reachable: " + (phantom.get() != null));  // Always null!
        referent = null;
        System.gc();  // Suggest GC

        // Phantom reference enqueued after finalization
        // But we can't easily demonstrate without ReferenceQueue polling
    }

    // ============================================================
    // JLS §12.1: VM Startup
    // ============================================================

    // Main method - entry point
    // public static void main(String[] args) - must be:
    // - public
    // - static
    // - void return
    // - String[] parameter
    // - In a class that is loaded at startup

    // ============================================================
    // JLS §12.2: Loading
    // ============================================================

    // Class loading demonstration
    static void classLoadingDemo() {
        System.out.println("  Class loading:");

        // Bootstrap loader loads java.*
        Class<?> stringClass = String.class;
        System.out.println("    String class loader: " + stringClass.getClassLoader());  // null = bootstrap

        // Platform loader loads standard extensions
        // Class<?> someExtClass = SomeExtensionClass.class;

        // Application loader loads classpath classes
        Class<?> thisClass = Ex59_ExecutionAndInitialization.class;
        System.out.println("    This class loader: " + thisClass.getClassLoader().getClass().getName());

        // Class.forName() triggers loading AND initialization
        try {
            Class<?> loaded = Class.forName("java.util.ArrayList");
            System.out.println("    Class.forName loaded: " + loaded.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("    Class not found");
        }

        // ClassLoader.loadClass() loads but does NOT initialize
        try {
            Class<?> loaded = thisClass.getClassLoader().loadClass("java.util.HashMap");
            System.out.println("    loadClass loaded: " + loaded.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("    Class not found");
        }
    }

    // ============================================================
    // JLS §12.3: Linking
    // ============================================================

    // Linking phases (happen after loading, before initialization):
    // 1. Verification - bytecode validity
    // 2. Preparation - allocate static fields, set default values
    // 3. Resolution - symbolic references to direct references

    // Can't directly demonstrate, but we can show effects
    static void linkingDemo() {
        System.out.println("  Linking phases (verification, preparation, resolution):");
        System.out.println("    Verification: bytecode structural checks");
        System.out.println("    Preparation: static fields get default values (0, null, false)");
        System.out.println("    Resolution: symbolic refs -> direct refs (lazy or eager)");
    }

    // ============================================================
    // JLS §12.7: Unloading
    // ============================================================

    // Classes can be unloaded when:
    // - ClassLoader that loaded them becomes unreachable
    // - All instances of the class are unreachable
    // - Class object itself is unreachable

    // Demonstrating with custom ClassLoader
    static class SimpleClassLoader extends ClassLoader {
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if (name.equals("Ex59_ExecutionAndInitialization")) {
                return Ex59_ExecutionAndInitialization.class;
            }
            return super.loadClass(name);
        }
    }

    static void unloadingDemo() {
        System.out.println("  Class unloading:");
        System.out.println("    Classes unloaded when ClassLoader becomes unreachable");
        System.out.println("    Requires no live instances, no references to Class object");
        System.out.println("    Not guaranteed to happen - depends on GC");
    }

    // ============================================================
    // JLS §12.8: Program Exit
    // ============================================================

    // Program exits when:
    // - All non-daemon threads terminate
    // - System.exit() called
    // - Runtime.halt() called (forcible)

    static void programExitDemo() {
        System.out.println("  Program exit:");
        System.out.println("    Normal: all non-daemon threads finish");
        System.out.println("    System.exit(status): terminates JVM");
        System.out.println("    Runtime.halt(): forcible termination (no shutdown hooks)");
        System.out.println("    Shutdown hooks: Runtime.addShutdownHook()");
    }

    // ============================================================
    // Demonstration of initialization order
    // ============================================================

    static class Parent {
        static {
            System.out.println("  [Parent] Static initializer");
        }

        {
            System.out.println("  [Parent] Instance initializer");
        }

        Parent() {
            System.out.println("  [Parent] Constructor");
        }
    }

    static class Child extends Parent {
        static {
            System.out.println("  [Child] Static initializer");
        }

        {
            System.out.println("  [Child] Instance initializer");
        }

        Child() {
            System.out.println("  [Child] Constructor");
        }
    }

    // Interface initialization (only when default method used or constant accessed)
    interface MyInterface {
        int CONSTANT = computeConstant();  // This DOES trigger interface init!

        static int computeConstant() {
            System.out.println("  [Interface] Computing constant");
            return 42;
        }

        default void defaultMethod() {
            System.out.println("  [Interface] Default method called");
        }
    }

    static class InterfaceImpl implements MyInterface {}

    public static void main(String[] args) {
        System.out.println("=== JLS Chapter 12: Execution and Initialization ===\n");

        // Class initialization triggered by main method
        System.out.println("--- Class Initialization (JLS §12.4) ---");
        System.out.println("Class already initialized (main class)");

        // Accessing static field triggers initialization if not already
        System.out.println("\n--- Accessing static field ---");
        System.out.println("NON_CONSTANT_FIELD = " + NON_CONSTANT_FIELD);  // Triggers init

        // Constant field does NOT trigger initialization
        System.out.println("\n--- Constant field (no init trigger) ---");
        System.out.println("COMPILE_TIME_CONSTANT = " + COMPILE_TIME_CONSTANT);

        // Creating instance triggers initialization
        System.out.println("\n--- Creating instance (JLS §12.5) ---");
        new Ex59_ExecutionAndInitialization();
        System.out.println("---");
        new Ex59_ExecutionAndInitialization(42);

        // Inheritance initialization order
        System.out.println("\n--- Inheritance Initialization Order ---");
        new Child();

        // Interface initialization
        System.out.println("\n--- Interface Initialization ---");
        System.out.println("MyInterface.CONSTANT = " + MyInterface.CONSTANT);
        new InterfaceImpl().defaultMethod();

        // Class loading
        System.out.println("\n--- Class Loading (JLS §12.2) ---");
        classLoadingDemo();

        // Linking
        System.out.println("\n--- Linking (JLS §12.3) ---");
        linkingDemo();

        // Finalization
        System.out.println("\n--- Finalization (JLS §12.6) ---");
        System.out.println("  finalize() deprecated, use Cleaner");
        try (CleanableResource r = new CleanableResource("TestResource")) {
            r.doWork();
        }

        // Phantom reference
        phantomReferenceDemo();

        // Unloading
        System.out.println("\n--- Class Unloading (JLS §12.7) ---");
        unloadingDemo();

        // Program exit
        System.out.println("\n--- Program Exit (JLS §12.8) ---");
        programExitDemo();
    }
}