package jls;

import java.util.concurrent.CompletableFuture;

/**
 * JLS 34/50: Java 8+ - CompletableFuture Asynchronous Pipelines (JLS §17.4)
 * Demonstrates happens-before relationships created via reactive Future completion stages.
 */
public class Ex34_CompletableFuturePipelines {

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Task Completed")
                .thenApply(res -> res + " -> Processed Stage 1")
                .thenApply(res -> res + " -> Final Stage");

        System.out.println("Async Future output: " + future.get());
    }
}
