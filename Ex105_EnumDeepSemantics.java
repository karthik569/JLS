package jls;

/**
 * JLS §8.9 (Java 5+): Enum Types - Constants Body, Methods, and Constructors
 * 
 * Enums are special classes that extend java.lang.Enum. Each declared enum
 * constant is a public static final instance of the enum type.
 * 
 * Key concepts:
 * - Enum constants call the (private) constructor exactly once
 * - Enum can have fields, methods, and per-constant body
 * - values() and valueOf() are compiler-generated
 * - Enums can implement interfaces
 * - Each constant can override abstract methods
 */
public class Ex105_EnumDeepSemantics {
    
    public static void main(String[] args) {
        System.out.println("=== Enum Deep Semantics Demo ===\n");
        
        demoEnumBasics();
        demoEnumWithFields();
        demoEnumBehavior();
        demoEnumImplementsInterface();
    }
    
    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
        
        public boolean isWeekend() { return this == SATURDAY || this == SUNDAY; }
    }
    
    enum Planet {
        MERCURY(3.303e+23, 2.4397e6),
        VENUS(4.869e+24, 6.0518e6),
        EARTH(5.976e+24, 6.37814e6);
        
        private final double mass;   // in kilograms
        private final double radius; // in meters
        
        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }
        
        // Universal gravitational constant
        private static final double G = 6.67300E-11;
        
        double surfaceGravity() {
            return G * mass / (radius * radius);
        }
    }
    
    enum Operation {
        PLUS { double apply(double x, double y) { return x + y; } },
        MINUS { double apply(double x, double y) { return x - y; } },
        TIMES { double apply(double x, double y) { return x * y; } },
        DIVIDE { double apply(double x, double y) { return x / y; } };
        
        abstract double apply(double x, double y);
    }
    
    static void demoEnumBasics() {
        System.out.println("Demo 1: Basic Enum Mechanics");
        System.out.println("=".repeat(50));
        System.out.println("  Day.values() length: " + Day.values().length);
        System.out.println("  Day.valueOf(\"FRIDAY\"): " + Day.valueOf("FRIDAY"));
        System.out.println("  MONDAY ordinal: " + Day.MONDAY.ordinal());
        System.out.println("  FRIDAY isWeekend: " + Day.FRIDAY.isWeekend());
        System.out.println("  SATURDAY isWeekend: " + Day.SATURDAY.isWeekend());
        System.out.println();
    }
    
    static void demoEnumWithFields() {
        System.out.println("Demo 2: Enum with Fields and Constructors");
        System.out.println("=".repeat(50));
        for (Planet p : Planet.values()) {
            System.out.printf("  %-8s  gravity=%.2f m/s^2%n", p, p.surfaceGravity());
        }
        System.out.println();
    }
    
    static void demoEnumBehavior() {
        System.out.println("Demo 3: Per-Constant Method Override");
        System.out.println("=".repeat(50));
        double a = 10, b = 4;
        for (Operation op : Operation.values()) {
            System.out.printf("  %s %.0f and %.0f = %.2f%n", op, a, b, op.apply(a, b));
        }
        System.out.println();
    }
    
    interface Describable {
        String describe();
    }
    
    enum Color implements Describable {
        RED("warm"), GREEN("cool"), BLUE("cool");
        
        private final String tone;
        Color(String tone) { this.tone = tone; }
        
        @Override
        public String describe() { return name() + " is a " + tone + " color"; }
    }
    
    static void demoEnumImplementsInterface() {
        System.out.println("Demo 4: Enum Implementing Interface");
        System.out.println("=".repeat(50));
        for (Color c : Color.values()) {
            System.out.println("  " + c.describe());
        }
        System.out.println();
    }
}
