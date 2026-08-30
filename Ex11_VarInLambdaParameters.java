package jls;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.function.BiFunction;

/**
 * JLS 11/50: Java 11 - Local Variable Syntax for Lambda Parameters (JLS §15.27.1)
 * Demonstrates using 'var' for implicit lambda parameters to allow annotations.
 */
public class Ex11_VarInLambdaParameters {

    @Target(ElementType.PARAMETER)
    @interface Validated {}

    public static void main(String[] args) {
        // JLS §15.27.1: Using var in lambda parameters to apply annotations
        BiFunction<String, String, String> concat = (@Validated var a, @Validated var b) -> a + b;

        System.out.println("Concatenated result: " + concat.apply("Java 11 ", "Lambda Var"));
    }
}
