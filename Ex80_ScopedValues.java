package jls;

import java.time.Instant;
import java.lang.ScopedValue;

/**
 * JLS §20 (Java 21+): Scoped Values
 * 
 * Scoped Values provide a way to share immutable data across threads
 * without the problems of thread-local variables (memory leaks, inheritance issues).
 * 
 * Key concepts:
 * - ScopedValue.newInstance(): create a new scoped value
 * - ScopedValue.where(): bind a value to the current scope
 * - ScopedValue.get(): retrieve the value
 * - Values are inherited by child virtual threads
 * - Values cannot be modified after binding (immutable)
 * - Memory safe - values are automatically cleaned up when scope ends
 */
public class Ex80_ScopedValues {
    
    // Scoped value - shared across threads
    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();
    private static final ScopedValue<Instant> START_TIME = ScopedValue.newInstance();
    private static final ScopedValue<String> USER_CONTEXT = ScopedValue.newInstance();
    
    public static void main(String[] args) {
        System.out.println("=== Scoped Values Demo ===\n");
        
        // Run with scoped values
        ScopedValue.where(REQUEST_ID, "REQ-001")
            .where(START_TIME, Instant.now())
            .where(USER_CONTEXT, "user@example.com")
            .run(() -> {
                try {
                    processRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        
        System.out.println("\n--- Verifying isolation ---");
        System.out.println("REQUEST_ID accessible outside? " + REQUEST_ID.isBound());
    }
    
    static void processRequest() throws Exception {
        System.out.println("Processing request in: " + Thread.currentThread());
        System.out.println("  Request ID: " + REQUEST_ID.get());
        System.out.println("  Start time: " + START_TIME.get());
        System.out.println("  User: " + USER_CONTEXT.get());
        
        // Nested scope can shadow values
        ScopedValue.where(REQUEST_ID, "REQ-001-SUB").run(() -> {
            System.out.println("\n  [Nested] Request ID (shadowed): " + REQUEST_ID.get());
        });
        
        log("Task 1 processing");
        log("Task 2 processing");
    }
    
    static void log(String message) {
        System.out.println("  [LOG] " + REQUEST_ID.get() + " | " + USER_CONTEXT.get() + " | " + message);
    }
}
