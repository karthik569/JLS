package jls;

/**
 * JLS §17.5 (Java 5+): Final Field Semantics and Safe Publication
 * 
 * The Java Memory Model (JMM) guarantees that once a final field is set,
 * all threads see the correctly published value without synchronization,
 * provided the reference to the object is not leaked during construction.
 * 
 * Key concepts:
 * - Freeze action: final fields are guaranteed visible after construction
 * - Safe publication of final field references
 * - Definite assignment rules for final fields
 * - Final field semantics prevent "out-of-thin-air" reads
 */
public class Ex104_FinalFieldSemantics {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Final Field Semantics Demo ===\n");
        
        demoBasicFinalPublication();
        demoImproperPublication();
        demoDefiniteAssignment();
        demoFinalFieldSafepoint();
    }
    
    // Properly constructed final field
    static class FinalHolder {
        private final int value;
        private final String name;
        
        FinalHolder(int v, String n) {
            this.value = v;
            this.name = n;
            // Cannot reassign: this.value = 0; // compile error
        }
        
        public int value() { return value; }
        public String name() { return name; }
    }
    
    // Improperly published (no final, mutable during construction)
    static class NonFinalHolder {
        private int value;
        private String name;
        
        NonFinalHolder(int v, String n) {
            this.value = v;
            this.name = n;
        }
        
        public int value() { return value; }
        public String name() { return name; }
    }
    
    static void demoBasicFinalPublication() throws InterruptedException {
        System.out.println("Demo 1: Safe Publication via Final Fields");
        System.out.println("=".repeat(50));
        FinalHolder holder = new FinalHolder(42, "Final");
        Thread reader = new Thread(() -> {
            // JMM guarantees visibility of 'value' and 'name' here
            System.out.println("  Reader thread sees value=" + holder.value() + ", name=" + holder.name());
        });
        reader.start();
        reader.join();
        System.out.println();
    }
    
    static void demoImproperPublication() {
        System.out.println("Demo 2: Non-Final Holder (No Guarantees)");
        System.out.println("=".repeat(50));
        NonFinalHolder holder = new NonFinalHolder(99, "Mutable");
        // Without final, fields may appear as 0 or null in other threads
        System.out.println("  Main thread sees: value=" + holder.value() + ", name=" + holder.name());
        System.out.println("  (Other threads may see defaults without synchronization)");
        System.out.println();
    }
    
    static void demoDefiniteAssignment() {
        System.out.println("Demo 3: Definite Assignment for Final Fields");
        System.out.println("=".repeat(50));
        DefiniteAssignment da = new DefiniteAssignment(true, "default");
        System.out.println("  Created with default: " + da.value);
        DefiniteAssignment da2 = new DefiniteAssignment(false, "branched");
        System.out.println("  Created via branch: " + da2.value);
        System.out.println();
    }
    
    static class DefiniteAssignment {
        private final int value;
        
        DefiniteAssignment(boolean useDefault, String source) {
            if (useDefault) {
                this.value = 0;
            } else {
                this.value = source.length();
            }
            // All paths must definitely assign 'value' before constructor completes
        }
    }
    
    static void demoFinalFieldSafepoint() {
        System.out.println("Demo 4: Final Field Freeze Point");
        System.out.println("=".repeat(50));
        // The freeze action occurs at the end of the constructor
        // After that, no thread can see the field as its default value
        // unless it obtained the reference through a data race
        SafePublisher sp = new SafePublisher(100, "Safe");
        System.out.println("  Published: " + sp);
        System.out.println();
    }
    
    static class SafePublisher {
        private final int count;
        private final String label;
        // Final reference: also safe-published
        private final int[] data = {1, 2, 3};
        
        SafePublisher(int count, String label) {
            this.count = count;
            this.label = label;
            // 'data' array contents are NOT final-field-safe
            // (only the array reference itself is safe-published)
        }
        
        @Override
        public String toString() {
            return "SafePublisher[count=" + count + ", label=" + label + "]";
        }
    }
}
