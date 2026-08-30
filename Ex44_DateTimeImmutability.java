package jls;

import java.time.Duration;
import java.time.Instant;

/**
 * JLS 44/50: Java 8+ - Date & Time API Immutable Semantics (JLS §4.3)
 * Demonstrates thread-safe time manipulation using Instant and Duration.
 */
public class Ex44_DateTimeImmutability {

    public static void main(String[] args) {
        Instant start = Instant.now();
        Instant end = start.plus(Duration.ofHours(2));

        System.out.println("Start Instant: " + start);
        System.out.println("End Instant (+2h): " + end);
    }
}
