package jls;

/**
 * JLS 19/50: Java 21 - Record Patterns (Deconstruction) (JLS §14.30.3)
 * Demonstrates deconstructing record values in pattern matching expressions and nested record patterns.
 */
public class Ex19_RecordPatterns {

    public record Point(int x, int y) {}
    public record ColoredPoint(Point point, String color) {}

    public static void printPoint(Object obj) {
        // JLS §14.30.3: Nested Record Pattern Matching
        if (obj instanceof ColoredPoint(Point(int x, int y), String color)) {
            System.out.println("Deconstructed ColoredPoint at (" + x + ", " + y + ") with color " + color);
        } else if (obj instanceof Point(int x, int y)) {
            System.out.println("Deconstructed Point at (" + x + ", " + y + ")");
        }
    }

    public static void main(String[] args) {
        Point p = new Point(10, 20);
        ColoredPoint cp = new ColoredPoint(p, "RED");

        printPoint(p);
        printPoint(cp);
    }
}
