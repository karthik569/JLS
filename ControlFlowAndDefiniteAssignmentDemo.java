package jls;

/**
 * Java Language Specification (JLS) - Chapter 14: Blocks, Statements, and Patterns
 * 
 * Demonstrates:
 * 1. Definitive Assignment & Definite Unassignment (JLS §16)
 * 2. Unreachable Statements (JLS §14.21)
 * 3. Pattern Matching for switch / Sealed Interfaces (JLS §14.11 / JLS §8.1.6)
 */
public class ControlFlowAndDefiniteAssignmentDemo {

    // JLS §8.1.6: Sealed Classes / Interfaces
    sealed interface Shape permits Circle, Rectangle {}

    static final record Circle(double radius) implements Shape {}
    static final record Rectangle(double width, double height) implements Shape {}

    // JLS §14.11: Exhaustive Switch Expression with Pattern Matching
    public static double calculateArea(Shape shape) {
        return switch (shape) {
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
            // Exhaustive: Compiler verifies all permitted subtypes of Shape are covered!
        };
    }

    public static void testDefiniteAssignment(boolean flag) {
        // JLS §16: Definite Assignment Rule
        // A local variable must be definitely assigned before every access.
        int x;

        if (flag) {
            x = 10;
        } else {
            x = 20;
        }

        // Complies because x is definitely assigned in both branches of the if statement
        System.out.println("Definitive assignment x: " + x);

        final int y;
        y = 100; // First assignment
        // y = 200; // COMPILE ERROR! JLS §16: y must be definitely unassigned before assignment.
        System.out.println("Final variable y: " + y);
    }

    public static void testConstantExpressions() {
        // JLS §14.21: Unreachable Statements Rule
        // A statement that can never be executed is a compile-time error.
        
        final boolean ALWAYS_FALSE = false; // Constant expression (JLS §15.29)

        if (ALWAYS_FALSE) {
            // NOTE: The JLS explicitly permits unreachable code inside 'if (false)' for conditional compilation!
            System.out.println("This is unreachable, but permitted inside an 'if' statement.");
        }

        // while (false) { System.out.println("This cause compile error!"); } // Compile error JLS §14.21
    }

    public static void main(String[] args) {
        System.out.println("--- Switch Pattern Matching & Sealed Interfaces ---");
        Shape circle = new Circle(5.0);
        System.out.println("Circle area: " + calculateArea(circle));

        System.out.println("\n--- Definite Assignment ---");
        testDefiniteAssignment(true);

        System.out.println("\n--- Constant Expressions & Unreachability ---");
        testConstantExpressions();
    }
}
