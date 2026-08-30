/**
 * JLS Chapter 9: Interfaces (Deep Dive)
 *
 * Demonstrates:
 * - JLS §9.1: Interface Declarations (modifiers, type parameters, extends)
 * - JLS §9.1.1-9.1.3: Interface modifiers (public, abstract, strictfp, sealed)
 * - JLS §9.2: Interface Members (constant fields, abstract methods, default methods, static methods, private methods)
 * - JLS §9.3: Field (Constant) Declarations (implicitly public, static, final)
 * - JLS §9.4: Method Declarations (abstract, default, static, private)
 * - JLS §9.5: Member Type Declarations (nested interfaces, classes, enums, records)
 * - JLS §9.6: Annotation Types
 * - JLS §9.6.1-9.6.4: Annotation modifiers, elements, default values, @Target, @Retention
 * - JLS §9.7: Annotations (marker, single-element, normal, repeating, type annotations)
 * - JLS §9.7.4-9.7.5: Repeating annotations, type annotations
 * - JLS §9.8: Functional Interfaces (SAM types, @FunctionalInterface)
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Repeatable;
import java.util.List;

public class Ex56_InterfacesDeepDive {

    // ============================================================
    // JLS §9.1: Interface Declarations
    // ============================================================

    // Normal interface (implicitly abstract)
    public interface BasicInterface {
        // All fields are implicitly public static final
        int CONSTANT = 42;

        // All methods are implicitly public abstract (before Java 8)
        void abstractMethod();

        // Default method (Java 8+) - has implementation
        default void defaultMethod() {
            System.out.println("Default method in BasicInterface");
        }

        // Static method (Java 8+) - belongs to interface
        static void staticMethod() {
            System.out.println("Static method in BasicInterface");
        }

        // Private method (Java 9+) - implementation detail
        private void privateHelper() {
            System.out.println("Private helper");
        }

        // Private static method (Java 9+)
        private static void privateStaticHelper() {
            System.out.println("Private static helper");
        }

        // Default method calling private
        default void defaultUsingPrivate() {
            privateHelper();
            privateStaticHelper();
        }
    }

    // Interface extending another interface
    public interface ExtendedInterface extends BasicInterface {
        void extendedMethod();

        // Can override default method
        @Override
        default void defaultMethod() {
            System.out.println("Overridden default in ExtendedInterface");
            BasicInterface.super.defaultMethod();  // Call super default
        }
    }

    // Functional interface (SAM - Single Abstract Method)
    @FunctionalInterface
    public interface Calculator {
        int calculate(int a, int b);

        // Can have default/static/private methods
        default int add(int a, int b) { return a + b; }
        static int multiply(int a, int b) { return a * b; }
    }

    // Another functional interface
    @FunctionalInterface
    public interface Predicate<T> {
        boolean test(T t);
    }

    // ============================================================
    // JLS §9.6: Annotation Types
    // ============================================================

    // Marker annotation (no elements)
    public @interface MarkerAnnotation {}

    // Single-element annotation (value element)
    public @interface SingleElementAnnotation {
        String value() default "default";
    }

    // Normal annotation (multiple elements)
    public @interface NormalAnnotation {
        String name();
        int count() default 1;
        String[] tags() default {};
    }

    // Annotation with enum element
    public enum Severity { LOW, MEDIUM, HIGH }

    public @interface WithEnum {
        Severity level() default Severity.MEDIUM;
    }

    // Annotation with Class element
    public @interface WithClass {
        Class<?>[] value();
    }

    // Annotation with nested annotation
    public @interface WithNested {
        NormalAnnotation annotation() default @NormalAnnotation(name="default", count=0);
    }

    // Meta-annotations
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
    public @interface RuntimeAnnotation {
        String description();
    }

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.TYPE)
    public @interface CompileTimeAnnotation {}

    // Repeating annotation (Java 8+)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Repeatable(RepeatingContainer.class)
    public @interface RepeatingAnnotation {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface RepeatingContainer {
        RepeatingAnnotation[] value();
    }

    // Type annotation target (Java 8+)
    @Target(ElementType.TYPE_USE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NonNull {}

    @Target(ElementType.TYPE_USE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ReadOnly {}

    // ============================================================
    // Implementations and Demos
    // ============================================================

    // Implementing interface
    public static class BasicImpl implements BasicInterface {
        @Override
        public void abstractMethod() {
            System.out.println("Implemented abstractMethod");
        }
    }

    public static class ExtendedImpl implements ExtendedInterface {
        @Override
        public void abstractMethod() {
            System.out.println("ExtendedImpl abstractMethod");
        }

        @Override
        public void extendedMethod() {
            System.out.println("ExtendedImpl extendedMethod");
        }
    }

    // Anonymous class implementing functional interface
    public static void functionalInterfaceDemo() {
        // Lambda expression (target typing)
        Calculator add = (a, b) -> a + b;
        Calculator subtract = (a, b) -> a - b;
        Calculator custom = (a, b) -> a * b + 10;

        System.out.println("Lambda add: " + add.calculate(5, 3));
        System.out.println("Lambda subtract: " + subtract.calculate(5, 3));
        System.out.println("Lambda custom: " + custom.calculate(5, 3));

        // Method reference
        Calculator multiply = Calculator::multiply;
        System.out.println("Method ref multiply: " + multiply.calculate(5, 3));

        // Predicate with lambda
        Predicate<String> nonEmpty = s -> s != null && !s.isEmpty();
        System.out.println("Predicate test: " + nonEmpty.test("hello"));

        // Default method from interface
        add.add(10, 20);  // Calls default method
    }

    // Annotation usage
    @MarkerAnnotation
    @SingleElementAnnotation("custom value")
    @NormalAnnotation(name = "test", count = 5, tags = {"a", "b"})
    @WithEnum(level = Severity.HIGH)
    @WithClass({String.class, Integer.class})
    @WithNested(annotation = @NormalAnnotation(name = "nested", count = 2))
    @RepeatingAnnotation("first")
    @RepeatingAnnotation("second")
    @RuntimeAnnotation(description = "This class has annotations")
    public static class AnnotatedClass {
        @NonNull
        private String nonNullField;

        @ReadOnly
        private final List<String> readOnlyList = List.of("a", "b");

        @RuntimeAnnotation(description = "Method annotation")
        public void annotatedMethod(@NonNull String param) {}
    }

    // Type annotations (Java 8+) - on type uses
    public static void typeAnnotationsDemo() {
        // Type annotations on type uses
        @NonNull String str = "hello";
        @ReadOnly List<@NonNull String> list = List.of("a", "b");

        // On casts
        Object obj = "test";
        @NonNull String casted = (@NonNull String) obj;

        // On instanceof (Java 16+ pattern matching)
        if (obj instanceof @NonNull String s) {
            System.out.println("Pattern match: " + s);
        }

        // On constructor invocation
        @NonNull String created = new @NonNull String("created");

        // On throws (Java 8+)
        // void method() throws @NonNull Exception {}

        System.out.println("Type annotations applied to various type uses");
    }

    // Nested interfaces and classes in interfaces
    public interface InterfaceWithNested {
        // Nested interface (implicitly static)
        interface NestedInterface {
            void nestedMethod();
        }

        // Nested class (implicitly static)
        class NestedClass {
            public void nestedClassMethod() {
                System.out.println("Nested class in interface");
            }
        }

        // Nested enum (implicitly static)
        enum NestedEnum { A, B, C }

        // Nested record (Java 16+, implicitly static)
        record NestedRecord(int x, int y) {}
    }

    // ============================================================
    // Main demo
    // ============================================================

    public static void main(String[] args) {
        System.out.println("=== JLS Chapter 9: Interfaces Deep Dive ===\n");

        // Basic interface
        System.out.println("--- Basic Interface (JLS §9.1, §9.2) ---");
        BasicImpl impl = new BasicImpl();
        impl.abstractMethod();
        impl.defaultMethod();
        impl.defaultUsingPrivate();
        BasicInterface.staticMethod();

        // Extended interface
        System.out.println("\n--- Extended Interface ---");
        ExtendedImpl extImpl = new ExtendedImpl();
        extImpl.abstractMethod();
        extImpl.extendedMethod();
        extImpl.defaultMethod();

        // Functional interfaces
        System.out.println("\n--- Functional Interfaces (JLS §9.8) ---");
        functionalInterfaceDemo();

        // Annotations
        System.out.println("\n--- Annotations (JLS §9.6, §9.7) ---");
        // Read annotations at runtime
        Class<?> clazz = AnnotatedClass.class;

        System.out.println("MarkerAnnotation present: " + clazz.isAnnotationPresent(MarkerAnnotation.class));
        System.out.println("SingleElementAnnotation value: " +
                clazz.getAnnotation(SingleElementAnnotation.class).value());
        System.out.println("NormalAnnotation: name=" +
                clazz.getAnnotation(NormalAnnotation.class).name() +
                ", count=" + clazz.getAnnotation(NormalAnnotation.class).count());

        // Repeating annotations
        RepeatingContainer container = clazz.getAnnotation(RepeatingContainer.class);
        if (container != null) {
            System.out.println("RepeatingAnnotation values:");
            for (RepeatingAnnotation ra : container.value()) {
                System.out.println("  " + ra.value());
            }
        }

        // Runtime annotation
        RuntimeAnnotation rt = clazz.getAnnotation(RuntimeAnnotation.class);
        System.out.println("RuntimeAnnotation: " + rt.description());

        // Type annotations
        System.out.println("\n--- Type Annotations (JLS §9.7.4, §9.7.5) ---");
        typeAnnotationsDemo();

        // Nested in interface
        System.out.println("\n--- Nested Types in Interface (JLS §9.5) ---");
        InterfaceWithNested.NestedClass nc = new InterfaceWithNested.NestedClass();
        nc.nestedClassMethod();
        System.out.println("NestedEnum: " + InterfaceWithNested.NestedEnum.A);
        InterfaceWithNested.NestedRecord nr = new InterfaceWithNested.NestedRecord(1, 2);
        System.out.println("NestedRecord: " + nr);
    }
}