package jls;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * JLS 2/50: Java 8 - Method References (JLS §15.13)
 * Demonstrates static, instance, and constructor method references.
 */
public class Ex02_MethodReferences {

    public static int compareInts(int a, int b) {
        return Integer.compare(a, b);
    }

    public static void main(String[] args) {
        // JLS §15.13.1: Reference to a Static Method
        BiFunction<Integer, Integer, Integer> staticRef = Ex02_MethodReferences::compareInts;
        System.out.println("Static ref compare: " + staticRef.apply(10, 20));

        // Reference to an Instance Method of a Particular Object
        String prefix = "JLS_";
        Function<String, String> instanceRef = prefix::concat;
        System.out.println("Instance ref concat: " + instanceRef.apply("Java8"));

        // Reference to a Constructor
        Supplier<StringBuilder> constrRef = StringBuilder::new;
        StringBuilder sb = constrRef.get();
        sb.append("Built with constructor reference");
        System.out.println(sb.toString());
    }
}
