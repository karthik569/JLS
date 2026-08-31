package jls;

/**
 * JLS §8.6 (Java 8+): Instance Initializer Blocks
 * 
 * An instance initializer is a block of code '{ ... }' declared in a class
 * body that runs for every constructor invocation, BEFORE the constructor
 * body executes.
 * 
 * Key concepts:
 * - Instance initializer block executes in source order
 * - Runs BEFORE the constructor body
 * - Merged with field initializers in source order
 * - Useful for anonymous classes that can't define a constructor
 */
public class Ex109_InstanceInitializerBlocks {
    
    private final int id;
    private final String label;
    private final int computed;
    
    // Field initializer
    private int sequence = 0;
    
    // First instance initializer
    {
        System.out.println("  [init block 1] Running before constructor body");
        // 'this' is available here
        sequence++;
    }
    
    // Field initializer (runs in source order with init blocks)
    private int incremented = ++sequence;
    
    // Second instance initializer
    {
        System.out.println("  [init block 2] sequence=" + sequence + ", incremented=" + incremented);
    }
    
    public Ex109_InstanceInitializerBlocks(int id, String label) {
        // Constructor body
        System.out.println("  [constructor] Begin");
        this.id = id;
        this.label = label;
        // The init blocks have already run; 'sequence' is already 2
        this.computed = this.sequence * 10;
        System.out.println("  [constructor] End with computed=" + computed);
    }
    
    public int id() { return id; }
    public String label() { return label; }
    public int computed() { return computed; }
    public int sequence() { return sequence; }
    
    public static void main(String[] args) {
        System.out.println("=== Instance Initializer Blocks Demo ===\n");
        
        System.out.println("Demo 1: First Object Construction");
        System.out.println("=".repeat(50));
        Ex109_InstanceInitializerBlocks a = new Ex109_InstanceInitializerBlocks(1, "alpha");
        System.out.println("  Final: id=" + a.id() + ", label=" + a.label() + 
                           ", computed=" + a.computed() + ", sequence=" + a.sequence());
        
        System.out.println("\nDemo 2: Second Object - Initializer Runs Again");
        System.out.println("=".repeat(50));
        Ex109_InstanceInitializerBlocks b = new Ex109_InstanceInitializerBlocks(2, "beta");
        System.out.println("  Final: id=" + b.id() + ", label=" + b.label() + 
                           ", computed=" + b.computed() + ", sequence=" + b.sequence());
        
        System.out.println("\nDemo 3: Instance Initializer in Anonymous Class");
        System.out.println("=".repeat(50));
        // Anonymous classes can't have a constructor, so initializer blocks are essential
        Object anon = new Object() {
            private String anonName;
            
            // Anonymous class instance initializer
            {
                System.out.println("  [anon init] running");
                anonName = "anonymous-instance";
            }
            
            @Override
            public String toString() { return "Object[" + anonName + "]"; }
        };
        System.out.println("  Created: " + anon);
    }
}
