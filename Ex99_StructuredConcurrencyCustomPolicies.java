package jls;

import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.time.Duration;
import java.util.List;

/**
 * JLS §17 & Concurrency Model (Java 24 Preview - JEP 499):
 * Structured Concurrency with Join Policies and Scoped Lifecycle
 * 
 * Structured Concurrency treats groups of concurrent tasks running in different threads
 * as a single unit of work, streamlining error handling, cancellation, and observability.
 * 
 * Compilation:
 *     javac --release 24 --enable-preview Ex99_StructuredConcurrencyCustomPolicies.java
 *     java --enable-preview jls.Ex99_StructuredConcurrencyCustomPolicies
 * 
 * Key Pillars:
 * 1. Syntactic Containment: All subtasks forked within a StructuredTaskScope must complete
 *    (or be cancelled) before the scope's try-with-resources block exits.
 * 2. Fail-Fast Short-Circuiting: If one subtask fails, sibling tasks are automatically cancelled.
 * 3. Joiner Strategies: Java 24 introduces modular Joiner policies (allSuccessful, anySuccessful, etc.)
 * 4. Observability: Thread dumps preserve the parent-child relationship between scopes and subtasks.
 */
public class Ex99_StructuredConcurrencyCustomPolicies {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Java 24 Structured Concurrency (JEP 499) Demo ===\n");
        
        demoFailFastAggregator();
        demoAnySuccessfulRace();
    }

    /**
     * Demonstrating ShutdownOnFailure / All-or-Nothing Join Policy:
     * Forks two tasks: fetching weather and fetching flight info.
     */
    static void demoFailFastAggregator() throws Exception {
        System.out.println("1. All-or-Nothing Subtask Aggregation (ShutdownOnFailure):");
        
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Subtask<String> weatherTask = scope.fork(() -> {
                Thread.sleep(50);
                return "Sunny 24°C";
            });

            Subtask<String> flightTask = scope.fork(() -> {
                Thread.sleep(70);
                return "Flight BA142 On-Time";
            });

            // Wait for all subtasks to complete or any to fail
            scope.join();
            scope.throwIfFailed(); // Propagate exception if any subtask failed

            System.out.println("   [Success] Weather: " + weatherTask.get());
            System.out.println("   [Success] Flight:  " + flightTask.get() + "\n");
        }
    }

    /**
     * Demonstrating ShutdownOnSuccess / First-To-Succeed Policy:
     * Queries multiple replica servers concurrently; cancels slower ones when the first responds.
     */
    static void demoAnySuccessfulRace() throws Exception {
        System.out.println("2. First-to-Succeed Fast Response (ShutdownOnSuccess):");
        
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            // Server A (slow)
            scope.fork(() -> {
                Thread.sleep(150);
                return "Response from Server A (Latency: 150ms)";
            });

            // Server B (fast)
            scope.fork(() -> {
                Thread.sleep(30);
                return "Response from Server B (Latency: 30ms)";
            });

            // Server C (medium)
            scope.fork(() -> {
                Thread.sleep(90);
                return "Response from Server C (Latency: 90ms)";
            });

            scope.join(); // Waits until first success, automatically cancels other tasks!
            String fastestResponse = scope.result();
            System.out.println("   Fastest Server Response: " + fastestResponse);
        }
    }
}
