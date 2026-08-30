/**
 * JLS Chapter 8: Classes (Deep Dive)
 *
 * Demonstrates:
 * - JLS §8.1: Class Declarations (modifiers, type parameters, superclass, interfaces)
 * - JLS §8.1.1-8.1.6: Class modifiers (public, abstract, final, strictfp, sealed, permits)
 * - JLS §8.2: Class Members (fields, methods, constructors, nested classes)
 * - JLS §8.3: Field Declarations (modifiers, initialization, static, final, transient, volatile)
 * - JLS §8.4: Method Declarations (modifiers, type parameters, return type, throws, body)
 * - JLS §8.4.1-8.4.9: Method modifiers, overloading, overriding, hiding
 * - JLS §8.5: Member Type Declarations (nested classes, interfaces)
 * - JLS §8.6: Instance Initializers
 * - JLS §8.7: Static Initializers
 * - JLS §8.8: Constructor Declarations (modifiers, parameters, throws, body, super/this calls)
 * - JLS §8.8.7: Constructor Body (explicit constructor invocation, statements)
 * - JLS §8.8.7.1: Statements before super() (Java 22+)
 * - JLS §8.9: Enums
 * - JLS §8.10: Records
 * - JLS §8.11: Sealed Classes
 */
public class Ex55_ClassesDeepDive {

    // ============================================================
    // JLS §8.1: Class Declarations
    // ============================================================

    // Class modifiers demonstration
    // public, abstract, final, strictfp, sealed, permits

    // Normal concrete class
    public static class ConcreteClass {
        public void method() { System.out.println("Concrete"); }
    }

    // Abstract class - cannot be instantiated
    public abstract static class AbstractClass {
        public abstract void abstractMethod();
        public void concreteMethod() { System.out.println("Abstract class concrete method"); }
    }

    // Final class - cannot be extended
    public final static class FinalClass {
        public void method() { System.out.println("Final"); }
    }

    // Strictfp class - all floating-point operations strict
    public strictfp static class StrictfpClass {
        public double compute(double a, double b) { return a * b; }
    }

    // Sealed class (JLS §8.1.6) - restricts subclasses
    public abstract sealed static class SealedShape permits Circle, Rectangle, OpenShape {
        public abstract double area();
    }

    public final static class Circle extends SealedShape {
        private final double radius;
        public Circle(double radius) { this.radius = radius; }
        public double area() { return Math.PI * radius * radius; }
    }

    public final static class Rectangle extends SealedShape {
        private final double width, height;
        public Rectangle(double w, double h) { this.width = w; this.height = h; }
        public double area() { return width * height; }
    }

    // Non-sealed class - can be extended by anything
    public non-sealed static class OpenShape extends SealedShape {
        public double area() { return 0; }
    }

    // ============================================================
    // JLS §8.3: Field Declarations
    // ============================================================

    // Field modifiers: public, protected, private, static, final, transient, volatile
    public int publicField = 1;
    protected int protectedField = 2;
    private int privateField = 3;
    int packagePrivateField = 4;

    static int staticField = 10;           // Class variable
    final int finalField = 20;             // Constant (instance)
    static final int CONSTANT = 30;        // Constant (class)
    transient int transientField = 40;     // Not serialized
    volatile int volatileField = 50;       // JLS §17.4 - visibility guarantee

    // Field initialization
    int initializedField = computeInitial();  // Instance initializer expression
    static int staticInitialized = computeStaticInitial();  // Static initializer expression

    static int computeInitial() { return 100; }
    static int computeStaticInitial() { return 200; }

    // ============================================================
    // JLS §8.4: Method Declarations
    // ============================================================

    // Method modifiers: public, protected, private, abstract, static, final,
    // synchronized, native, strictfp

    // Overloading (JLS §8.4.9) - same name, different parameters
    public void overloaded() { System.out.println("no args"); }
    public void overloaded(int i) { System.out.println("int: " + i); }
    public void overloaded(String s) { System.out.println("String: " + s); }
    public void overloaded(int i, String s) { System.out.println("int+String: " + i + ", " + s); }

    // Overriding (JLS §8.4.8) - same signature, subclass replaces superclass
    public void overridable() { System.out.println("Base overridable"); }

    // Final method - cannot be overridden
    public final void finalMethod() { System.out.println("Final method"); }

    // Static method - class method, not instance
    public static void staticMethod() { System.out.println("Static method"); }

    // Abstract method - no body, must be in abstract class
    // public abstract void abstractMethod();  // Would require abstract class

    // Synchronized method - intrinsic lock
    public synchronized void synchronizedMethod() { System.out.println("Synchronized"); }

    // Strictfp method
    public strictfp double strictfpMethod(double a, double b) { return a + b; }

    // Varargs method
    public void varargsMethod(String... args) {
        System.out.println("Varargs: " + java.util.Arrays.toString(args));
    }

    // Generic method (JLS §8.4.4)
    public <T> T genericMethod(T input) { return input; }

    // ============================================================
    // JLS §8.5: Member Type Declarations (Nested Classes)
    // ============================================================

    // Static nested class - no enclosing instance
    public static class StaticNested {
        public void print() { System.out.println("Static nested"); }
    }

    // Inner class - has enclosing instance
    public class InnerClass {
        public void print() {
            // Can access outer instance members (including private)
            System.out.println("Inner: outer privateField = " + privateField);
        }
    }

    // Local class (inside method)
    public void localClassDemo() {
        class LocalClass {
            public void print() { System.out.println("Local class"); }
        }
        new LocalClass().print();
    }

    // Anonymous class
    public void anonymousClassDemo() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous class");
            }
        };
        r.run();
    }

    // ============================================================
    // JLS §8.6: Instance Initializers
    // ============================================================

    // Instance initializer block - runs before constructor
    {
        System.out.println("Instance initializer running");
        instanceInitValue = 999;
    }

    int instanceInitValue = 0;

    // ============================================================
    // JLS §8.7: Static Initializers
    // ============================================================

    // Static initializer block - runs when class loaded
    static {
        System.out.println("Static initializer running");
        staticInitValue = 888;
    }

    static int staticInitValue = 0;

    // ============================================================
    // JLS §8.8: Constructor Declarations
    // ============================================================

    // Constructor modifiers: public, protected, private
    public Ex55_ClassesDeepDive() {
        this(42);  // Explicit constructor invocation - this()
        System.out.println("No-arg constructor");
    }

    public Ex55_ClassesDeepDive(int value) {
        super();  // Explicit constructor invocation - super()
        System.out.println("Int constructor: " + value);
    }

    // Constructor with throws
    public Ex55_ClassesDeepDive(String s) throws IllegalArgumentException {
        this();
        if (s == null) throw new IllegalArgumentException("null not allowed");
    }

    // JLS §8.8.7.1: Statements before super() (Java 22+ preview)
    // This would be valid in Java 22+ with --enable-preview:
    /*
    public Ex55_ClassesDeepDive(boolean flag) {
        int x = 10;  // Statement before super() - allowed in Java 22+
        super();
        System.out.println("x = " + x);
    }
    */

    // ============================================================
    // JLS §8.9: Enums
    // ============================================================

    // Enum declaration (implicitly extends java.lang.Enum)
    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

        // Enum can have fields, methods, constructors
        public boolean isWeekend() {
            return this == SATURDAY || this == SUNDAY;
        }
    }

    // Enum with fields and constructor
    public enum Planet {
        MERCURY(3.303e+23, 2.4397e6),
        VENUS(4.869e+24, 6.0518e6),
        EARTH(5.976e+24, 6.37814e6);

        private final double mass;
        private final double radius;

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }

        public double surfaceGravity() {
            return 6.67300E-11 * mass / (radius * radius);
        }
    }

    // ============================================================
    // JLS §8.10: Records (Java 16+)
    // ============================================================

    // Record declaration - implicitly final, extends java.lang.Record
    public record Point(int x, int y) {
        // Canonical constructor (implicit)
        // Compact constructor (validation)
        public Point {
            if (x < 0 || y < 0) throw new IllegalArgumentException("Coordinates must be non-negative");
        }

        // Additional methods
        public double distanceFromOrigin() {
            return Math.sqrt(x * x + y * y);
        }

        // Static factory method
        public static Point origin() { return new Point(0, 0); }
    }

    // ============================================================
    // JLS §8.11: Sealed Classes (Java 17+)
    // ============================================================

    // Already demonstrated above with SealedShape

    // ============================================================
    // Main demo
    // ============================================================

    public static void main(String[] args) {
        System.out.println("=== JLS Chapter 8: Classes Deep Dive ===\n");

        // Class modifiers
        System.out.println("--- Class Modifiers (JLS §8.1) ---");
        new ConcreteClass().method();
        new AbstractClass() { public void abstractMethod() { System.out.println("Anonymous impl"); } }.abstractMethod();
        new FinalClass().method();
        System.out.println("Strictfp: " + new StrictfpClass().compute(1.5, 2.0));

        SealedShape circle = new Circle(5);
        SealedShape rect = new Rectangle(4, 6);
        System.out.println("Sealed Circle area: " + circle.area());
        System.out.println("Sealed Rectangle area: " + rect.area());

        // Fields
        System.out.println("\n--- Fields (JLS §8.3) ---");
        Ex55_ClassesDeepDive obj = new Ex55_ClassesDeepDive();
        System.out.println("Static field: " + staticField);
        System.out.println("Final instance field: " + obj.finalField);
        System.out.println("Static final CONSTANT: " + CONSTANT);
        System.out.println("Transient field: " + obj.transientField);
        System.out.println("Volatile field: " + obj.volatileField);
        System.out.println("Instance initializer value: " + obj.instanceInitValue);
        System.out.println("Static initializer value: " + staticInitValue);

        // Methods
        System.out.println("\n--- Methods (JLS §8.4) ---");
        obj.overloaded();
        obj.overloaded(42);
        obj.overloaded("hello");
        obj.overloaded(1, "world");
        obj.overridable();
        obj.finalMethod();
        staticMethod();
        obj.synchronizedMethod();
        obj.varargsMethod("a", "b", "c");
        System.out.println("Generic method: " + obj.genericMethod("generic"));

        // Nested classes
        System.out.println("\n--- Nested Classes (JLS §8.5) ---");
        new StaticNested().print();
        obj.new InnerClass().print();
        obj.localClassDemo();
        obj.anonymousClassDemo();

        // Constructors
        System.out.println("\n--- Constructors (JLS §8.8) ---");
        new Ex55_ClassesDeepDive();
        new Ex55_ClassesDeepDive(100);

        // Enums
        System.out.println("\n--- Enums (JLS §8.9) ---");
        for (Day d : Day.values()) {
            System.out.println(d + " weekend? " + d.isWeekend());
        }
        for (Planet p : Planet.values()) {
            System.out.println(p + " gravity: " + p.surfaceGravity());
        }

        // Records
        System.out.println("\n--- Records (JLS §8.10) ---");
        Point p1 = new Point(3, 4);
        Point p2 = Point.origin();
        System.out.println("Point: " + p1);  // toString() auto-generated
        System.out.println("x=" + p1.x() + ", y=" + p1.y());  // Accessors auto-generated
        System.out.println("Distance from origin: " + p1.distanceFromOrigin());
        System.out.println("Origin: " + p2);
        System.out.println("equals: " + p1.equals(new Point(3, 4)));
        System.out.println("hashCode: " + p1.hashCode());

        // Record with validation (compact constructor)
        try {
            new Point(-1, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }

        // Sealed classes
        System.out.println("\n--- Sealed Classes (JLS §8.11) ---");
        System.out.println("SealedShape permits: Circle, Rectangle");
        System.out.println("OpenShape is non-sealed, can be extended freely");
    }
}