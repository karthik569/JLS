package jls;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

/**
 * JLS §4.1 & FFM API Specification (Java 22+ Final - JEP 454):
 * Foreign Function & Memory (FFM) API - Native Linker & Upcalls/Downcalls
 * 
 * Java 22 finalized the Foreign Function & Memory API, completely replacing legacy JNI
 * (Java Native Interface) with type-safe, pure-Java native interop.
 * 
 * Compilation:
 *     javac --release 22 Ex97_ForeignFunctionLinker.java
 *     java --enable-native-access=ALL-UNNAMED jls.Ex97_ForeignFunctionLinker
 * 
 * Core Components:
 * 1. Linker: Bridges foreign native functions and Java bytecode (Linker.nativeLinker()).
 * 2. FunctionDescriptor: Defines native C function signature (return type and argument types).
 * 3. SymbolLookup: Finds symbols in dynamic libraries (e.g. standard C library).
 * 4. Arena & MemorySegment: Manages off-heap allocation lifecycle safely (Arena.ofConfined(), Arena.ofAuto()).
 * 5. Downcalls: Java invoking native C functions.
 * 6. Upcalls: Native C code invoking Java methods via function pointers.
 */
public class Ex97_ForeignFunctionLinker {

    public static void main(String[] args) throws Throwable {
        System.out.println("=== Java 22+ Foreign Function & Linker API (JEP 454) Demo ===\n");
        
        demoNativeDowncallStrlen();
        demoArenaLifecycle();
        demoUpcallConcept();
    }

    /**
     * Downcall: Find standard C library 'strlen' function and invoke it from Java
     */
    static void demoNativeDowncallStrlen() {
        System.out.println("1. Native Downcall to C Library (strlen):");
        
        try {
            Linker linker = Linker.nativeLinker();
            SymbolLookup stdlib = linker.defaultLookup();
            
            MemorySegment strlenSymbol = stdlib.find("strlen").orElse(null);
            if (strlenSymbol != null) {
                // FunctionDescriptor: size_t strlen(const char *s)
                FunctionDescriptor descriptor = FunctionDescriptor.of(
                    ValueLayout.JAVA_LONG,      // size_t return value (64-bit on 64-bit OS)
                    ValueLayout.ADDRESS        // char* pointer argument
                );
                
                MethodHandle strlenHandle = linker.downcallHandle(strlenSymbol, descriptor);
                
                try (Arena arena = Arena.ofConfined()) {
                    String testStr = "Java 22 Native Interop";
                    MemorySegment cString = arena.allocateFrom(testStr);
                    
                    long len = (long) strlenHandle.invokeExact(cString);
                    System.out.println("   String: \"" + testStr + "\"");
                    System.out.println("   Calculated native C strlen: " + len + " (matches java length: " + (len == testStr.length()) + ")\n");
                }
            } else {
                System.out.println("   'strlen' symbol not found in default lookup (platform specific).\n");
            }
        } catch (Throwable t) {
            System.out.println("   Native call caught exception: " + t.getMessage() + "\n");
        }
    }

    /**
     * Arena Memory Safety: Confined, Shared, and Automatic Arenas
     */
    static void demoArenaLifecycle() {
        System.out.println("2. Off-Heap Memory Safety & Deterministic Deallocation:");
        
        MemorySegment offHeapSegment;
        try (Arena confinedArena = Arena.ofConfined()) {
            offHeapSegment = confinedArena.allocate(1024);
            offHeapSegment.set(ValueLayout.JAVA_INT, 0, 42_000);
            System.out.println("   Allocated 1024 bytes off-heap in confined arena.");
            System.out.println("   Value at offset 0: " + offHeapSegment.get(ValueLayout.JAVA_INT, 0));
        } // Arena is deterministically deallocated and freed here!

        System.out.println("   Arena exited: Off-heap memory is freed.");
        try {
            // Attempting to access segment after arena closed throws IllegalStateException (Spatial & Temporal safety)
            offHeapSegment.get(ValueLayout.JAVA_INT, 0);
        } catch (IllegalStateException e) {
            System.out.println("   Temporal Safety Enforced: Access after close prevented -> " + e.getClass().getSimpleName() + "\n");
        }
    }

    /**
     * Upcalls: Passing Java lambda as C function pointer (e.g. for qsort comparator)
     */
    static void demoUpcallConcept() {
        System.out.println("3. Native Upcalls (Java Callback in C):");
        System.out.println("   - Java methods can be converted into native function pointers using:");
        System.out.println("     'linker.upcallStub(methodHandle, descriptor, arena);'");
        System.out.println("   - The resulting MemorySegment can be passed directly to C functions like 'qsort()'");
        System.out.println("     without writing a single line of C/C++ or using JNI headers!");
    }
}
