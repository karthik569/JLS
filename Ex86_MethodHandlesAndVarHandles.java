package jls;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.List;

/**
 * JLS §15.12: Method Handles and VarHandles
 * 
 * Method handles provide a way to invoke methods with type safety.
 * VarHandles allow atomic access to object fields and array elements.
 * 
 * Key concepts:
 * - MethodHandles.Lookup: factory for method handles
 * - MethodHandle: typed, directly executable reference to a method
 * - VarHandle: variable access (field or array element) with atomicity modes
 * - Access modes: read, write, compareAndSet, getAndSet, etc.
 */
public class Ex86_MethodHandlesAndVarHandles {
    
    public static void main(String[] args) throws Throwable {
        System.out.println("=== Method Handles and Var Handles Demo ===\n");
        
        demoMethodHandles();
        demoVarHandle();
    }
    
    /**
     * JLS §15.12: Method Handles
     */
    static void demoMethodHandles() throws Throwable {
        System.out.println("Demo 1: Method Handles");
        System.out.println("=".repeat(50));
        
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        
        // Method handle to String.length()
        MethodHandle lengthHandle = lookup.findVirtual(
            String.class, "length", MethodType.methodType(int.class)
        );
        
        // Invoke it
        int length = (int) lengthHandle.invokeExact("Hello");
        System.out.println("  String.length('Hello') = " + length);
        
        // Method handle to static method
        MethodHandle parseInt = lookup.findStatic(
            Integer.class, "parseInt", 
            MethodType.methodType(int.class, String.class)
        );
        
        int parsed = (int) parseInt.invokeExact("42");
        System.out.println("  Integer.parseInt('42') = " + parsed);
        
        // Method handle to instance method bound to receiver
        MethodHandle boundLength = lengthHandle.bindTo("World");
        int len2 = (int) boundLength.invokeExact();
        System.out.println("  Bound 'World'.length() = " + len2);
        
        // Method handle to constructor
        MethodHandle ctor = lookup.findConstructor(
            StringBuilder.class, MethodType.methodType(void.class, String.class)
        );
        StringBuilder sb = (StringBuilder) ctor.invoke("Hello");
        System.out.println("  StringBuilder('Hello') created: " + sb);
        System.out.println();
    }
    
    /**
     * JLS §15.12: VarHandle
     */
    static void demoVarHandle() throws Throwable {
        System.out.println("Demo 2: VarHandle");
        System.out.println("=".repeat(50));
        
        // VarHandle for an array element
        int[] arr = new int[10];
        VarHandle arrHandle = MethodHandles.arrayElementVarHandle(int[].class);
        
        // Set array element atomically
        arrHandle.set(arr, 0, 100);
        arrHandle.set(arr, 1, 200);
        
        // Compare and set
        boolean updated = arrHandle.compareAndSet(arr, 0, 100, 999);
        System.out.println("  CAS success: " + updated);
        System.out.println("  arr[0] = " + arr[0]);
        
        // Get and set
        int old = (int) arrHandle.getAndSet(arr, 1, 0);
        System.out.println("  getAndSet old=" + old + ", arr[1]=" + arr[1]);
        
        // VarHandle for field
        var counter = new Counter();
        VarHandle counterHandle = MethodHandles.lookup()
            .findVarHandle(Counter.class, "count", int.class);
        
        counterHandle.set(counter, 50);
        int current = (int) counterHandle.get(counter);
        System.out.println("  counter.count = " + current);
        
        // Atomic add
        int prev = (int) counterHandle.getAndAdd(counter, 5);
        System.out.println("  getAndAdd prev=" + prev + ", now=" + counterHandle.get(counter));
        System.out.println();
    }
    
    // Helper class
    static class Counter {
        volatile int count = 0;
    }
}
