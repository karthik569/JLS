package jls;

import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles;

/**
 * JLS 46/50: Java 9+ - VarHandle Atomic & Memory Fence Operations (JLS §17.4)
 * Demonstrates low-level volatile and atomic variable updates via VarHandle.
 */
public class Ex46_VarHandleAtomicOperations {

    private volatile int counter = 0;
    private static final VarHandle HANDLE;

    static {
        try {
            HANDLE = MethodHandles.lookup().findVarHandle(Ex46_VarHandleAtomicOperations.class, "counter", int.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String[] args) {
        Ex46_VarHandleAtomicOperations demo = new Ex46_VarHandleAtomicOperations();

        // Atomic compare-and-set operation
        boolean success = HANDLE.compareAndSet(demo, 0, 42);
        System.out.println("CAS Success: " + success + ", Counter value: " + demo.counter);
    }
}
