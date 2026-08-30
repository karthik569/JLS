package jls;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout;

/**
 * JLS §3.9 (Java 22+): Foreign Function & Memory API
 * 
 * The Foreign Function & Memory API allows Java programs to interoperate
 * with code and data outside the Java runtime.
 * 
 * Key concepts:
 * - MemorySegment: represents a contiguous region of memory
 * - Arena: manages memory allocation/deallocation
 * - ValueLayout: describes primitive value types
 * - MemoryLayout.sequenceLayout: creates array layouts
 */
public class Ex83_ForeignFunctionAndMemoryAPI {
    
    public static void main(String[] args) {
        System.out.println("=== Foreign Function & Memory API Demo ===\n");
        
        demoMemorySegments();
        demoStringConversion();
    }
    
    /**
     * JLS §3.9: Memory Segments
     */
    static void demoMemorySegments() {
        System.out.println("Demo 1: Memory Segments");
        System.out.println("=".repeat(50));
        
        // Allocate off-heap memory using Arena
        try (Arena arena = Arena.ofConfined()) {
            // Allocate a memory segment of 100 bytes
            MemorySegment segment = arena.allocate(100);
            
            // Write data to the segment using VarHandle from layout
            for (int i = 0; i < 10; i++) {
                segment.set(ValueLayout.JAVA_BYTE, i, (byte) (i * 10));
            }
            
            // Read data back
            System.out.println("  Written and read bytes:");
            for (int i = 0; i < 10; i++) {
                byte value = segment.get(ValueLayout.JAVA_BYTE, i);
                System.out.printf("    [%d] = %d%n", i, value);
            }
            
            // Allocate with specific layout (int array)
            MemoryLayout intArrayLayout = MemoryLayout.sequenceLayout(5, ValueLayout.JAVA_INT_UNALIGNED);
            MemorySegment intArray = arena.allocate(intArrayLayout);
            
            for (int i = 0; i < 5; i++) {
                intArray.set(ValueLayout.JAVA_INT_UNALIGNED, i * 4L, i * 100);
            }
            
            System.out.println("  Integer array:");
            for (int i = 0; i < 5; i++) {
                int value = intArray.get(ValueLayout.JAVA_INT_UNALIGNED, i * 4L);
                System.out.printf("    [%d] = %d%n", i, value);
            }
        }
        System.out.println();
    }
    
    /**
     * JLS §3.9: String to/from native
     */
    static void demoStringConversion() {
        System.out.println("Demo 2: String to Native Memory");
        System.out.println("=".repeat(50));
        
        try (Arena arena = Arena.ofConfined()) {
            String javaString = "Hello from Java!";
            
            // Allocate memory for string
            MemorySegment nativeString = arena.allocate(javaString.length() + 1);
            
            // Write string bytes
            for (int i = 0; i < javaString.length(); i++) {
                nativeString.set(ValueLayout.JAVA_BYTE, i, (byte) javaString.charAt(i));
            }
            // Null terminator
            nativeString.set(ValueLayout.JAVA_BYTE, javaString.length(), (byte) 0);
            
            // Read back as string
            String result = nativeString.getString(0, java.nio.charset.StandardCharsets.UTF_8);
            System.out.println("  Original: " + javaString);
            System.out.println("  Memory address: " + nativeString.address());
        }
        System.out.println();
    }
}
