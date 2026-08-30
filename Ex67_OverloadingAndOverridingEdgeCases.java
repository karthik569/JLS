/**
 * Ex67_OverloadingAndOverridingEdgeCases.java
 *
 * This program demonstrates subtle rules regarding method overloading and overriding,
 * focusing on Covariant Returns and Overload Resolution.
 *
 * Key concepts:
 * 1. Covariant Return Types (JLS §8.4.8.1).
 * 2. Overload Resolution Priority: Exact Match -> Widening -> Boxing/Unboxing -> Varargs (JLS §15.12.2).
 * 3. Bridge Methods: Compiler-generated methods for generic overrides.
 */
import java.util.*;

public class Ex67_OverloadingAndOverridingEdgeCases {

    // --- Covariant Return Types ---
    static class Base {
        public Object getValue() {
            return "Base Object";
        }
    }

    static class Derived extends Base {
        @Override
        public String getValue() { // Covariant return: String is a subtype of Object
            return "Derived String";
        }
    }

    // --- Overload Resolution ---
    static class OverloadDemo {
        // Method A: Exact match for int
        public void print(int i) {
            System.out.println("print(int): " + i);
        }

        // Method B: Widening (int -> long)
        public void print(long l) {
            System.out.println("print(long): " + l);
        }

        // Method C: Boxing (int -> Integer)
        public void print(Integer i) {
            System.out.println("print(Integer): " + i);
        }

        // Method D: Varargs (int...)
        public void print(int... is) {
            System.out.println("print(int...): " + Arrays.toString(is));
        }
    }

    // --- Bridge Methods ---
    static class GenericBase<T> {
        public void process(T t) {
            System.out.println("GenericBase.process(T): " + t);
        }
    }

    static class GenericDerived extends GenericBase<String> {
        @Override
        public void process(String s) {
            System.out.println("GenericDerived.process(String): " + s);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Covariant Return Types ---");
        Base b = new Derived();
        System.out.println("Result: " + b.getValue()); // Dynamic dispatch returns "Derived String"

        System.out.println("\n--- Overload Resolution Priority ---");
        OverloadDemo demo = new OverloadDemo();
        int val = 10;
        demo.print(val); // Should call print(int) - Exact match

        // demo.print((long)val); // would call print(long)
        // demo.print((Integer)val); // would call print(Integer)

        System.out.println("\n--- Bridge Methods ---");
        GenericBase gb = new GenericDerived();
        gb.process("Hello"); // Calls bridge method which then calls process(String)

        // Inspecting the methods of GenericDerived
        System.out.println("Methods in GenericDerived:");
        for (java.lang.reflect.Method m : GenericDerived.class.getDeclaredMethods()) {
            System.out.println(" - " + m.getName() + "(" + Arrays.toString(m.getParameterTypes()) + ") " +
                               (m.isBridge() ? "[BRIDGE METHOD]" : ""));
        }
    }
}
