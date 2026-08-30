/**
 * Ex68_ClassLoadingAndInitialization.java
 *
 * This program demonstrates the sequence and triggers of class initialization
 * as specified in JLS §12.4.
 *
 * Key concepts:
 * 1. Static Initialization Order: Fields are initialized in order of declaration,
 *    interleaved with static blocks.
 * 2. Initialization Triggers: Accessing a static field or creating an instance.
 * 3. Class loading hierarchy: Parent classes are initialized before child classes.
 */
public class Ex68_ClassLoadingAndInitialization {

    static class Parent {
        static {
            System.out.println("1. Parent static block");
        }
        static int value = 100;
        static {
            System.out.println("2. Parent second static block");
        }
    }

    static class Child extends Parent {
        static {
            System.out.println("3. Child static block");
        }
        static int value = 200;
        static {
            System.out.println("4. Child second static block");
        }
    }

    static class LazyLoaded {
        static {
            System.out.println("!!! LazyLoaded class is now being initialized !!!");
        }
        static int data = 42;
    }

    public static void main(String[] args) {
        System.out.println("--- Starting Main ---");

        System.out.println("\n--- Triggering Child Initialization ---");
        // Accessing Child.value triggers initialization of Parent then Child
        System.out.println("Child value: " + Child.value);

        System.out.println("\n--- Demonstrating Lazy Loading ---");
        System.out.println("Before accessing LazyLoaded...");
        // LazyLoaded is not yet initialized here
        System.out.println("Accessing LazyLoaded.data now:");
        System.out.println("Data: " + LazyLoaded.data);
        System.out.println("After accessing LazyLoaded.");
    }
}
