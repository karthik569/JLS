package jls;

import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/**
 * JLS 28/50: Java 22+ - Stream Gatherers (JLS §15.12 Stream Processing Model)
 * Demonstrates custom windowed stream gathering and intermediate transformations.
 */
public class Ex28_StreamGatherers {

    public static void main(String[] args) {
        // Stream gatherers allow custom intermediate operations like fixed sliding windows
        var slidingWindows = Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.windowFixed(2))
                .toList();

        System.out.println("Fixed window gathered streams: " + slidingWindows);
    }
}
