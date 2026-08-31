package jls;

/**
 * JLS §14.3 (Java 8+): Local Class Declarations
 * 
 * A local class is a nested class declared inside a block. It can access
 * effectively final local variables from the enclosing scope.
 * 
 * Key concepts:
 * - Local class is scoped to a block
 * - Has access to enclosing class members
 * - Can access effectively final local variables
 * - Cannot declare static fields (except static final constants)
 * - Cannot be declared with the 'public', 'private', 'protected' modifiers
 */
public class Ex106_LocalClasses {
    
    private String instanceField = "outer-field";
    private static String staticField = "outer-static";
    
    public void demonstrate() {
        final String finalLocal = "final-local";
        String effectivelyFinal = "effectively-final";
        // String reassigned = "x"; reassigned = "y"; // would NOT be effectively final
        
        System.out.println("=== Local Class Demo ===\n");
        
        // Local class inside a method
        class LocalGreeter {
            private String message = "Hello from local class";
            
            void greet() {
                System.out.println("  " + message);
                System.out.println("  Sees instanceField: " + instanceField);
                System.out.println("  Sees staticField: " + staticField);
                System.out.println("  Sees finalLocal: " + finalLocal);
                System.out.println("  Sees effectivelyFinal: " + effectivelyFinal);
            }
        }
        
        LocalGreeter g = new LocalGreeter();
        g.greet();
        System.out.println();
    }
    
    public Object createCounter(final int start) {
        // Local class implementing an interface
        class Counter {
            private int current = start;
            int next() { return current++; }
        }
        return new Counter();
    }
    
    public static void main(String[] args) {
        Ex106_LocalClasses outer = new Ex106_LocalClasses();
        outer.demonstrate();
        
        System.out.println("Demo 2: Local Class in a Block");
        System.out.println("=".repeat(50));
        // Local class inside a block (not a method)
        {
            class BlockLocal {
                void print() { System.out.println("  Inside block scope"); }
            }
            new BlockLocal().print();
        }
        // BlockLocal reference is not visible here
        
        System.out.println("\nDemo 3: Local Class with Constructor Parameters");
        System.out.println("=".repeat(50));
        Object c = outer.createCounter(10);
        System.out.println("  Counter object type: " + c.getClass().getSimpleName());
        System.out.println("  Enclosing method: " + c.getClass().getEnclosingMethod().getName());
        System.out.println();
        
        System.out.println("Demo 4: Local Class Implementing Interface");
        System.out.println("=".repeat(50));
        final int multiplier = 3;
        // Local class implementing Runnable
        class Task implements Runnable {
            @Override
            public void run() {
                System.out.println("  Task running with multiplier=" + multiplier);
            }
        }
        new Task().run();
    }
}
