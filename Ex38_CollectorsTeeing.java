package jls;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JLS 38/50: Java 12+ - Collectors.teeing (JLS §15.12)
 * Demonstrates combining two downstream collectors into a merged result.
 */
public class Ex38_CollectorsTeeing {

    public record StreamSummary(double sum, double average) {}

    public static void main(String[] args) {
        StreamSummary summary = Stream.of(10, 20, 30, 40)
                .collect(Collectors.teeing(
                        Collectors.summingDouble(i -> i),
                        Collectors.averagingDouble(i -> i),
                        StreamSummary::new
                ));

        System.out.println("Teeing summary: " + summary);
    }
}
