package jls;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.stream.Stream;

/**
 * JLS §17 (Java 21+): Structured Concurrency
 * 
 * Structured Concurrency treats multiple tasks running in different threads
 * as a single unit of work, simplifying error handling and cancellation.
 * 
 * Key concepts:
 * - StructuredTaskScope: groups related tasks and waits for all to complete
 * - Joiner: strategy for how to combine subtask results
 * - Joiner.allSuccessfulOrThrow: fails fast if any subtask fails
 * - Joiner.anySuccessfulResultOrThrow: returns result of first successful subtask
 */
public class Ex79_StructuredConcurrency {
    
    public static void main(String[] args) throws Throwable {
        System.out.println("=== Structured Concurrency Demo ===\n");
        
        demoAllSuccessfulOrThrow();
        demoAnySuccessfulResultOrThrow();
        demoWaitForAll();
    }
    
    /**
     * JLS §17.4: allSuccessfulOrThrow - fails fast
     * If any subtask fails, other subtasks are cancelled and the exception is propagated.
     */
    static void demoAllSuccessfulOrThrow() throws Throwable {
        System.out.println("Demo 1: allSuccessfulOrThrow (fails fast)");
        System.out.println("=".repeat(50));
        
        try (var scope = StructuredTaskScope.open(
                Joiner.<String>allSuccessfulOrThrow())) {
            
            Subtask<String> userFuture = scope.fork(() -> fetchUserData());
            Subtask<String> orderFuture = scope.fork(() -> fetchOrderData());
            
            // Wait for all forks; throws if any failed
            Stream<Subtask<String>> results = scope.join();
            
            // All tasks succeeded - get individual results
            String user = userFuture.get();
            String order = orderFuture.get();
            
            System.out.println("User: " + user);
            System.out.println("Order: " + order);
            results.close();
        } catch (Exception e) {
            System.out.println("Task failed: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * JLS §17.4: anySuccessfulResultOrThrow
     * Returns result of first successfully completed subtask.
     */
    static void demoAnySuccessfulResultOrThrow() throws Throwable {
        System.out.println("Demo 2: anySuccessfulResultOrThrow (first success)");
        System.out.println("=".repeat(50));
        
        try (var scope = StructuredTaskScope.open(
                Joiner.<String>anySuccessfulResultOrThrow())) {
            
            scope.fork(() -> {
                Thread.sleep(100);
                return "Primary source";
            });
            scope.fork(() -> "Backup source");
            
            // Get the first successful result
            String result = scope.join();
            System.out.println("First successful: " + result);
        } catch (Exception e) {
            System.out.println("All tasks failed: " + e.getMessage());
        }
        System.out.println();
    }
    
    /**
     * JLS §17.4: awaitAll - wait for all tasks
     */
    static void demoWaitForAll() throws Exception {
        System.out.println("Demo 3: awaitAll (wait for all tasks)");
        System.out.println("=".repeat(50));
        
        try (var scope = StructuredTaskScope.open(
                Joiner.<Void>awaitAll())) {
            
            for (int i = 1; i <= 3; i++) {
                final int taskId = i;
                scope.fork(() -> {
                    System.out.println("Task " + taskId + " running in " + Thread.currentThread());
                    Thread.sleep(50);
                    return null;
                });
            }
            
            scope.join();  // Wait for all
            System.out.println("All tasks completed!");
        }
        System.out.println();
    }
    
    static String fetchUserData() throws Exception {
        Thread.sleep(50);
        return "User{ id: 1, name: 'Alice' }";
    }
    
    static String fetchOrderData() throws Exception {
        Thread.sleep(50);
        return "Order{ id: 100, total: 99.99 }";
    }
}
