/**
 * Ex74_NestedAndInnerClasses.java
 *
 * This program demonstrates the different types of nested classes in Java
 * and their access rules as specified in JLS §8.1.5.
 *
 * Key Concepts:
 * 1. Static Nested Classes: Do not have access to the outer instance.
 * 2. Member Inner Classes: Have an implicit reference to the outer instance.
 * 3. Local Inner Classes: Defined inside a method, can access local variables (if effectively final).
 * 4. Anonymous Inner Classes: Classes without a name, used for one-off implementations.
 */
public class Ex74_NestedAndInnerClasses {

    private String outerField = "Outer Field Value";
    private final String finalOuterField = "Final Outer Field";

    // 1. Static Nested Class (JLS §8.1.5)
    // It is associated with the outer class, not an instance.
    static class StaticNested {
        void display() {
            // System.out.println(outerField); // ERROR: Cannot access non-static field from static context
            System.out.println("Hello from Static Nested Class");
        }
    }

    // 2. Member Inner Class (JLS §8.1.5)
    // It is associated with an instance of the outer class.
    class MemberInner {
        void display() {
            // Can access private fields of outer class directly
            System.out.println("Member Inner accessing: " + outerField);
        }
    }

    public void testLocalAndAnonymous() {
        String localVal = "Local Variable"; // Effectively final

        // 3. Local Inner Class (JLS §8.1.5)
        class LocalInner {
            void display() {
                System.out.println("Local Inner accessing outer: " + outerField);
                System.out.println("Local Inner accessing local: " + localVal);
            }
        }
        new LocalInner().display();

        // 4. Anonymous Inner Class (JLS §15.9.5)
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Inner accessing: " + finalOuterField);
            }
        };
        r.run();
    }

    public static void main(String[] args) {
        Ex74_NestedAndInnerClasses outer = new Ex74_NestedAndInnerClasses();

        System.out.println("--- Static Nested ---");
        StaticNested sn = new StaticNested();
        sn.display();

        System.out.println("\n--- Member Inner ---");
        // Note the syntax: outerInstance.new InnerClass()
        Ex74_NestedAndInnerClasses.MemberInner mi = outer.new MemberInner();
        mi.display();

        System.out.println("\n--- Local and Anonymous ---");
        outer.testLocalAndAnonymous();
    }
}
