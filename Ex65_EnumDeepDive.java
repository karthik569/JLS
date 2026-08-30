/**
 * Ex65_EnumDeepDive.java
 *
 * This program explores the advanced features of Enum classes
 * as specified in JLS §8.9.
 *
 * Key concepts:
 * 1. Constant-specific class bodies (Overriding methods per constant).
 * 2. EnumSet and EnumMap (Specialized high-performance collections).
 * 3. Enum properties: ordinal, name, and custom fields.
 */
import java.util.*;

public class Ex65_EnumDeepDive {

    // JLS §8.9.1: Enum constants can have their own class bodies.
    enum Operation {
        PLUS {
            @Override
            public double apply(double x, double y) { return x + y; }
        },
        MINUS {
            @Override
            public double apply(double x, double y) { return x - y; }
        },
        TIMES {
            @Override
            public double apply(double x, double y) { return x * y; }
        },
        DIVIDE {
            @Override
            public double apply(double x, double y) {
                if (y == 0) throw new ArithmeticException("Division by zero");
                return x / y;
            }
        };

        // Abstract method that must be implemented by each constant
        public abstract double apply(double x, double y);
    }

    enum Status {
        PENDING, PROCESSING, COMPLETED, FAILED
    }

    public static void main(String[] args) {
        System.out.println("--- Constant-Specific Class Bodies ---");
        double x = 10.0, y = 5.0;
        for (Operation op : Operation.values()) {
            System.out.printf("%s result of %.1f and %.1f: %.1f%n",
                    op.name(), x, y, op.apply(x, y));
        }

        System.out.println("\n--- EnumSet (High Performance Bit-Set) ---");
        // JLS §8.9: EnumSet is a specialized Set implementation for Enums.
        EnumSet<Status> activeStatuses = EnumSet.of(Status.PENDING, Status.PROCESSING);
        System.out.println("Active statuses: " + activeStatuses);
        System.out.println("Contains COMPLETED? " + activeStatuses.contains(Status.COMPLETED));

        System.out.println("\n--- EnumMap (High Performance Array-based Map) ---");
        // JLS §8.9: EnumMap uses an internal array for mapping, making it very fast.
        EnumMap<Status, String> statusDescriptions = new EnumMap<>(Status.class);
        statusDescriptions.put(Status.PENDING, "Waiting for start");
        statusDescriptions.put(Status.PROCESSING, "Currently working");
        statusDescriptions.put(Status.COMPLETED, "Finished successfully");
        statusDescriptions.put(Status.FAILED, "Stopped due to error");

        for (Map.Entry<Status, String> entry : statusDescriptions.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\n--- Enum Metadata ---");
        Status s = Status.COMPLETED;
        System.out.println("Name: " + s.name());
        System.out.println("Ordinal: " + s.ordinal()); // JLS §8.9: Position in declaration
    }
}
