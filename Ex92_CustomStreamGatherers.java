package jls;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/**
 * JLS §15.12 & Stream Pipeline Specification (Java 24 Final - JEP 485): Stream Gatherers
 * 
 * Stream Gatherers provide an extensible mechanism for custom intermediate operations
 * in Java Streams, overcoming the long-standing limitation where intermediate stream operations
 * were fixed (filter, map, flatMap).
 * 
 * Compilation:
 *     javac --release 24 Ex92_CustomStreamGatherers.java
 *     java jls.Ex92_CustomStreamGatherers
 * 
 * Architecture of a Gatherer<T, A, R>:
 * 1. Initializer: Supplier<A> to create private intermediate state.
 * 2. Integrator: Evaluates elements and pushes results downstream via Downstream<? super R>.
 * 3. Combiner: BinaryOperator<A> to merge states in parallel pipelines.
 * 4. Finisher: BiConsumer<A, Downstream<? super R>> emits any lingering buffered elements.
 */
public class Ex92_CustomStreamGatherers {

    public static void main(String[] args) {
        System.out.println("=== Java 24 Stream Gatherers (JEP 485) Standard Demo ===\n");
        
        demoBuiltInGatherers();
        demoCustomDistinctAdjacentGatherer();
        demoCustomMovingAverageGatherer();
    }

    /**
     * Built-in Gatherers in java.util.stream.Gatherers:
     * - windowFixed(int windowSize)
     * - windowSliding(int windowSize)
     * - fold(Supplier, BiFunction)
     * - scan(Supplier, BiFunction)
     */
    static void demoBuiltInGatherers() {
        System.out.println("1. Built-in Gatherers (windowFixed & windowSliding):");
        
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        
        List<List<Integer>> fixedChunks = numbers.stream()
                .gather(Gatherers.windowFixed(3))
                .toList();
        System.out.println("   windowFixed(3): " + fixedChunks);

        List<List<Integer>> slidingWindows = numbers.stream()
                .gather(Gatherers.windowSliding(3))
                .toList();
        System.out.println("   windowSliding(3): " + slidingWindows);
        
        List<Integer> runningSums = numbers.stream()
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .toList();
        System.out.println("   scan(running sum): " + runningSums + "\n");
    }

    /**
     * Custom Gatherer: deduplicate consecutive identical elements (e.g. [1, 1, 2, 3, 3, 3, 2] -> [1, 2, 3, 2])
     */
    static void demoCustomDistinctAdjacentGatherer() {
        System.out.println("2. Custom Sequential Gatherer: distinctAdjacent():");
        
        List<String> input = List.of("apple", "apple", "banana", "banana", "banana", "apple", "cherry");
        
        // Custom stateful gatherer
        Gatherer<String, ?, String> distinctAdjacent = Gatherer.ofSequential(
            // Initializer: Holder holding last seen item
            () -> new Object() { String last = null; boolean first = true; },
            // Integrator:
            Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                if (state.first || (state.last == null && element != null) || (state.last != null && !state.last.equals(element))) {
                    state.first = false;
                    state.last = element;
                    return downstream.push(element);
                }
                return true; // continue without pushing duplicates
            })
        );

        List<String> result = input.stream().gather(distinctAdjacent).toList();
        System.out.println("   Input:  " + input);
        System.out.println("   Output: " + result + "\n");
    }

    /**
     * Custom Moving Average Gatherer over rolling windows
     */
    static void demoCustomMovingAverageGatherer() {
        System.out.println("3. Custom Moving Average Gatherer:");
        
        List<Double> sensorReadings = List.of(10.0, 20.0, 30.0, 40.0, 50.0);
        
        List<Double> movingAverages = sensorReadings.stream()
                .gather(Gatherers.windowSliding(3))
                .map(window -> window.stream().mapToDouble(Double::doubleValue).average().orElse(0.0))
                .toList();
                
        System.out.println("   Sensor readings: " + sensorReadings);
        System.out.println("   3-point moving averages: " + movingAverages);
    }
}
