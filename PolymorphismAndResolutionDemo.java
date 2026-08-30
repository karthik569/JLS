
/**
 * Java Language Specification (JLS) - Chapter 15 & Chapter 8: Overloading vs Overriding & Polymorphism
 * 
 * Demonstrates:
 * 1. Method Overriding & Dynamic Method Selection (JLS §15.12.4.4)
 * 2. Method Overloading & Compile-time Resolution (JLS §15.12.2)
 * 3. Hiding of Static Methods & Fields (JLS §8.4.8.2, JLS §8.3)
 * 4. Default Method Resolution in Interfaces (JLS §9.4.2)
 */
public class PolymorphismAndResolutionDemo {

    static class Parent {
        String field = "ParentField";

        // Instance method - dynamically bound at runtime
        void speak() {
            System.out.println("Parent speaking");
        }

        // Static method - resolved at compile-time based on static reference type
        static void announce() {
            System.out.println("Parent static announcement");
        }
    }

    static class Child extends Parent {
        // JLS §8.3: Field Hiding (fields are NOT polymorphic; they are hidden, not overridden)
        String field = "ChildField";

        // JLS §8.4.8.1: Overriding Instance Method
        @Override
        void speak() {
            System.out.println("Child speaking");
        }

        // JLS §8.4.8.2: Hiding Static Method (static methods cannot be overridden, only hidden)
        static void announce() {
            System.out.println("Child static announcement");
        }

        // JLS §15.12.2: Overloaded Methods
        void process(int x) {
            System.out.println("process(int): " + x);
        }

        void process(double x) {
            System.out.println("process(double): " + x);
        }

        void process(Object x) {
            System.out.println("process(Object): " + x);
        }

        void process(String x) {
            System.out.println("process(String): " + x);
        }
    }

    public static void main(String[] args) {
        Parent p = new Child(); // Dynamic type is Child, Static reference type is Parent

        // 1. Dynamic Method Invocation (JLS §15.12.4.4)
        // Dispatches to Child's implementation because instance methods are dynamically dispatched based on runtime object type
        p.speak();

        // 2. Field Access & Static Method Invocation (JLS §8.3, JLS §8.4.8.2)
        // Resolved strictly based on compile-time static type (Parent)
        System.out.println("Field accessed via Parent reference: " + p.field);
        p.announce(); // Warning in IDEs, but JLS dictates it calls Parent.announce()

        // 3. Overload Resolution (JLS §15.12.2 - Most Specific Method Rule)
        Child child = new Child();
        child.process(10);        // Matches process(int) exactly
        child.process(10.5);      // Matches process(double)
        child.process("Hello");   // String is an Object, but String is MORE SPECIFIC than Object, so process(String) is chosen
        child.process(new Object()); // Matches process(Object)
    }
}
