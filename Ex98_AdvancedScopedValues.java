package jls;

import java.util.concurrent.Callable;

/**
 * JLS §17 & Scoped Values Specification (Java 24 Preview - JEP 487):
 * Advanced Scoped Values, Dynamic Re-binding & Lifecycle
 * 
 * Scoped Values allow safe, immutable context sharing across threads (especially millions
 * of Virtual Threads) without the memory leaks, unbounded lifecycles, and mutation hazards of ThreadLocal.
 * 
 * Compilation:
 *     javac --release 24 --enable-preview Ex98_AdvancedScopedValues.java
 *     java --enable-preview jls.Ex98_AdvancedScopedValues
 * 
 * Key Advantages over ThreadLocal:
 * 1. Immutability: ScopedValue is write-once per scope (no .set() method).
 * 2. Bounded Lifetime: Bound strictly for the duration of a Runnable or Callable execution.
 * 3. Dynamic Re-binding: A nested call can rebind the value; the outer value is cleanly restored upon exit.
 * 4. Zero-Copy Sharing: Virtual child threads inherit scoped values in O(1) time without copying maps.
 */
public class Ex98_AdvancedScopedValues {

    private static final ScopedValue<String> USER_ID = ScopedValue.newInstance();
    private static final ScopedValue<String> TENANT_ID = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {
        System.out.println("=== Java 24 Scoped Values (JEP 487) Advanced Demo ===\n");
        
        demoBasicScopedBinding();
        demoDynamicRebinding();
        demoMultipleBindings();
    }

    /**
     * Basic ScopedValue binding with where() and run()
     */
    static void demoBasicScopedBinding() {
        System.out.println("1. Scoped Execution with where().run():");
        
        System.out.println("   Before scope: USER_ID bound? " + USER_ID.isBound());
        
        ScopedValue.where(USER_ID, "alice-101").run(() -> {
            System.out.println("   Inside scope: USER_ID = " + USER_ID.get());
            helperMethod();
        });

        System.out.println("   After scope exit: USER_ID bound? " + USER_ID.isBound() + "\n");
    }

    static void helperMethod() {
        // Reads the ambient scoped value without needing it passed as a parameter
        System.out.println("     [helperMethod] Processing request for: " + USER_ID.get());
    }

    /**
     * Dynamic Re-binding in Nested Scopes
     */
    static void demoDynamicRebinding() {
        System.out.println("2. Dynamic Re-binding in Nested Scopes:");
        
        ScopedValue.where(USER_ID, "root-admin").run(() -> {
            System.out.println("   Outer Scope User: " + USER_ID.get());
            
            // Nested re-binding for an impersonation task
            ScopedValue.where(USER_ID, "impersonated-guest").run(() -> {
                System.out.println("     -> Inner Nested User: " + USER_ID.get());
            });
            
            // Automatically restored
            System.out.println("   Outer Scope User Restored: " + USER_ID.get() + "\n");
        });
    }

    /**
     * Binding multiple ScopedValues simultaneously
     */
    static void demoMultipleBindings() {
        System.out.println("3. Multi-Key Fluent Binding:");
        
        ScopedValue.where(USER_ID, "bob-202")
                   .where(TENANT_ID, "tenant-apac-1")
                   .run(() -> {
                       System.out.println("   User: " + USER_ID.get() + " | Tenant: " + TENANT_ID.get());
                   });
    }
}
