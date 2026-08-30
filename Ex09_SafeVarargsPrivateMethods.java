package jls;

import java.util.ArrayList;
import java.util.List;

/**
 * JLS 9/50: Java 9 - SafeVarargs on Private Methods (JLS §9.6.4.5)
 * Demonstrates applying @SafeVarargs to private instance methods.
 */
public class Ex09_SafeVarargsPrivateMethods {

    @SafeVarargs
    private <T> void printElements(T... elements) {
        for (T element : elements) {
            System.out.println("Element: " + element);
        }
    }

    public static void main(String[] args) {
        Ex09_SafeVarargsPrivateMethods demo = new Ex09_SafeVarargsPrivateMethods();
        demo.printElements("Alpha", "Beta", "Gamma");
    }
}
