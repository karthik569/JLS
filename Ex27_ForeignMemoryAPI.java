package jls;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

/**
 * JLS 27/50: Java 22 - Foreign Function & Memory API (JLS §4.1 Types & Native Memory)
 * Demonstrates safe off-heap native memory allocation and accessing native memory segments.
 */
public class Ex27_ForeignMemoryAPI {

    public static void main(String[] args) {
        // JLS §4.1: Accessing native memory safely outside the JVM heap
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(ValueLayout.JAVA_INT, 4);
            
            for (int i = 0; i < 4; i++) {
                segment.setAtIndex(ValueLayout.JAVA_INT, i, (i + 1) * 10);
            }

            for (int i = 0; i < 4; i++) {
                int value = segment.getAtIndex(ValueLayout.JAVA_INT, i);
                System.out.println("Native memory index " + i + ": " + value);
            }
        } // Off-heap memory is safely deallocated here
    }
}
