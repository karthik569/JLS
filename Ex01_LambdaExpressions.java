package jls;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * JLS 1/50: Java 8 - Lambda Expressions (JLS §15.27)
 * Demonstrates functional interfaces, target typing, and lambda expression syntax.
 */
public class Ex01_LambdaExpressions {
    @FunctionalInterface
    interface MathOperation {
        int operate(int a, int b);
    }

    public static void main(String[] args) {
        // JLS §15.27.1: Lambda parameters and body
        MathOperation addition = (a, b) -> a + b;
        MathOperation multiplication = (int a, int b) -> a * b;

        System.out.println("Addition: " + addition.operate(5, 3));
        System.out.println("Multiplication: " + multiplication.operate(5, 3));

        // Target typing JLS §15.27.3
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        names.forEach(name -> System.out.println("Hello " + name));
    }
}
