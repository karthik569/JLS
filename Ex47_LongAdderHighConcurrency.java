package jls;

import java.util.concurrent.atomic.LongAdder;

/**
 * JLS 47/50: Java 8+ - Striped LongAdder Concurrency (JLS §17)
 * Demonstrates high-concurrency atomic counters using thread-cell striping.
 */
public class Ex47_LongAdderHighConcurrency {

    public static void main(String[] args) {
        LongAdder adder = new LongAdder();
        adder.increment();
        adder.add(10);

        System.out.println("LongAdder sum: " + adder.sum());
    }
}
