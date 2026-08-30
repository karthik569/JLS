package jls;

/**
 * JLS 16/50: Java 16 - Record Classes (JLS §8.10)
 * Demonstrates immutable data carriers, canonical constructors, compact constructors, and record components.
 */
public class Ex16_RecordClasses {

    // JLS §8.10: Record Class Declaration
    public record Point(int x, int y) {
        // JLS §8.10.4: Compact Constructor (parameter list omitted, auto-assigned after validation)
        public Point {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Coordinates must be non-negative");
            }
        }

        // Custom accessor or method
        public double distanceFromOrigin() {
            return Math.sqrt(x * x + y * y);
        }
    }

    public static void main(String[] args) {
        Point p = new Point(3, 4);
        System.out.println("Record toString: " + p);
        System.out.println("Component accessor x(): " + p.x());
        System.out.println("Distance from origin: " + p.distanceFromOrigin());
    }
}
