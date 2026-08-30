package jls;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ExecutionException;

/**
 * JLS 25/50: Java 21+ - Structured Concurrency (Preview/Model) (JLS §17)
 * Demonstrates treating subtasks in different threads as a single unit of work.
 */
public class Ex25_StructuredConcurrency {

    public static String fetchUser() throws InterruptedException {
        Thread.sleep(100);
        return "User Alice";
    }

    public static int fetchScore() throws InterruptedException {
        Thread.sleep(100);
        return 95;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        try (var scope = StructuredTaskScope.open()) {
            StructuredTaskScope.Subtask<String> userTask = scope.fork(Ex25_StructuredConcurrency::fetchUser);
            StructuredTaskScope.Subtask<Integer> scoreTask = scope.fork(Ex25_StructuredConcurrency::fetchScore);

            scope.join();

            System.out.println("Structured Concurrency result: " + userTask.get() + " with score " + scoreTask.get());
        }
    }
}
