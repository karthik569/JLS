package jls;

import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;
import java.util.List;

/**
 * JLS §15 (Java 22+): Stream Gatherers (Final in Java 22)
 * 
 * Stream Gatherers extend the Stream API to support custom intermediate
 * operations that can maintain state across multiple elements.
 * 
 * Key concepts:
 * - Gatherer: defines custom intermediate stream operation
 * - Integrator: processes each element with state
 * - Finisher: final processing when stream ends
 * - windowFixed: chunk elements into fixed-size groups
 * - windowSliding: sliding window over elements
 * - scan: running accumulation
 */
public class Ex81_StreamGatherers {
    
    public static void main(String[] args) {
        System.out.println("=== Stream Gatherers Demo ===\n");
        
        demoWindowFixed();
        demoWindowSliding();
        demoStatefulGatherer();
        demoScan();
    }
    
    /**
     * Fixed-size windows
     */
    static void demoWindowFixed() {
        System.out.println("Demo 1: Fixed-size windows of 3");
        System.out.println("=".repeat(50));
        
        List<List<Integer>> windows = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .gather(Gatherers.windowFixed(3))
            .toList();
        
        windows.forEach(window -> 
            System.out.println("  Window: " + window)
        );
        System.out.println();
    }
    
    /**
     * Sliding window
     */
    static void demoWindowSliding() {
        System.out.println("Demo 2: Sliding window of size 3");
        System.out.println("=".repeat(50));
        
        List<List<Integer>> windows = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .gather(Gatherers.windowSliding(3))
            .toList();
        
        windows.forEach(window -> 
            System.out.println("  Window: " + window)
        );
        System.out.println();
    }
    
    /**
     * Custom gatherer - stateful with running state
     */
    static void demoStatefulGatherer() {
        System.out.println("Demo 3: Custom running state gatherer");
        System.out.println("=".repeat(50));
        
        // Custom gatherer that tracks running min and max
        Gatherer<Integer, RunningStats, String> minMaxGatherer = Gatherer.of(
            RunningStats::new,                                          // initializer
            (state, element, downstream) -> {
                state.update(element);
                return true;                                            // not complete
            },
            (left, right) -> {                                          // combiner
                left.merge(right);
                return left;
            },
            (state, downstream) -> downstream.push(state.toString())    // finisher
        );
        
        List<String> result = Stream.of(5, 3, 8, 1, 9, 2, 7, 4, 6)
            .gather(minMaxGatherer)
            .toList();
        
        result.forEach(System.out::println);
        System.out.println();
    }
    
    /**
     * Scan operation - running sum
     */
    static void demoScan() {
        System.out.println("Demo 4: Scan (running sum)");
        System.out.println("=".repeat(50));
        
        List<Integer> result = Stream.of(1, 2, 3, 4, 5)
            .gather(Gatherers.scan(() -> 0, Integer::sum))
            .toList();
        
        System.out.println("  Running sum: " + result);
    }
    
    /**
     * Helper class for stateful gatherer
     */
    static class RunningStats {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int count = 0;
        long sum = 0;
        
        void update(int value) {
            min = Math.min(min, value);
            max = Math.max(max, value);
            count++;
            sum += value;
        }
        
        void merge(RunningStats other) {
            min = Math.min(min, other.min);
            max = Math.max(max, other.max);
            count += other.count;
            sum += other.sum;
        }
        
        @Override
        public String toString() {
            return String.format("count=%d, min=%d, max=%d, avg=%.2f", 
                count, min, max, (double) sum / count);
        }
    }
}
