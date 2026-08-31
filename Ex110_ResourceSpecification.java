package jls;

import java.io.*;

/**
 * JLS §14.20.3 (Java 9+): try-with-resources Resource Specification
 * 
 * Java 9 expanded the try-with-resources statement to allow the resource
 * to be declared OUTSIDE the try header, provided it is effectively final
 * or explicitly final.
 * 
 * Key concepts:
 * - Java 7: try (Resource r = new Resource()) { ... }
 * - Java 9: try (existing final/effectively final reference) { ... }
 * - Multiple resources are closed in REVERSE order
 * - Suppressed exceptions are accessible via Throwable.getSuppressed()
 * - The resource must implement AutoCloseable
 */
public class Ex110_ResourceSpecification {
    
    public static void main(String[] args) {
        System.out.println("=== Resource Specification Demo ===\n");
        
        demoJava7Style();
        demoJava9Style();
        demoMultipleResources();
        demoSuppressedExceptions();
    }
    
    static void demoJava7Style() {
        System.out.println("Demo 1: Java 7 Style - Resource Declared in Header");
        System.out.println("=".repeat(50));
        // Java 7 form: resource is declared in the try header
        try (BufferedReader br = new BufferedReader(new StringReader("Hello Java 7"))) {
            System.out.println("  Read: " + br.readLine());
        } catch (IOException e) {
            System.out.println("  IO error: " + e.getMessage());
        }
        System.out.println("  Resource was auto-closed\n");
    }
    
    static void demoJava9Style() {
        System.out.println("Demo 2: Java 9 Style - Effectively Final Outside");
        System.out.println("=".repeat(50));
        // Java 9 form: resource declared outside, reference in try header
        BufferedReader br = new BufferedReader(new StringReader("Hello Java 9"));
        // 'br' is effectively final because it is never reassigned
        try (br) {
            System.out.println("  Read: " + br.readLine());
        } catch (IOException e) {
            System.out.println("  IO error: " + e.getMessage());
        }
        System.out.println("  Resource was auto-closed\n");
    }
    
    static void demoMultipleResources() {
        System.out.println("Demo 3: Multiple Resources (Closed in Reverse Order)");
        System.out.println("=".repeat(50));
        StringReader sr = new StringReader("Multi-resource test");
        BufferedReader br = new BufferedReader(sr);
        try (sr; br) {
            System.out.println("  Read: " + br.readLine());
        } catch (IOException e) {
            System.out.println("  IO error: " + e.getMessage());
        }
        System.out.println("  br closed first, then sr (reverse declaration order)\n");
    }
    
    static void demoSuppressedExceptions() {
        System.out.println("Demo 4: Suppressed Exceptions");
        System.out.println("=".repeat(50));
        FailingResource r1 = new FailingResource("R1", false);
        FailingResource r2 = new FailingResource("R2", true);  // throws on close
        
        try (r1; r2) {
            // Body also throws
            throw new RuntimeException("Body exception");
        } catch (RuntimeException e) {
            System.out.println("  Primary: " + e.getMessage());
            // Suppressed exceptions from close() are accessible here
            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("  Suppressed: " + suppressed.getMessage());
            }
        }
        System.out.println();
    }
    
    // Custom resource that throws on close
    static class FailingResource implements AutoCloseable {
        private final String name;
        private final boolean throwOnClose;
        
        FailingResource(String name, boolean throwOnClose) {
            this.name = name;
            this.throwOnClose = throwOnClose;
            System.out.println("  [open] " + name);
        }
        
        @Override
        public void close() {
            System.out.println("  [close] " + name);
            if (throwOnClose) {
                throw new RuntimeException("Failed to close " + name);
            }
        }
    }
}
