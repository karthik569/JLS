package jls;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * JLS 10/50: Java 10 - Local Variable Type Inference (var) (JLS §14.4)
 * Demonstrates local variable type inference, strict scope rules, and non-denotable types.
 */
public class Ex10_LocalVariableTypeInference {

    public static void main(String[] args) {
        // JLS §14.4: Local variable declaration with 'var'
        var message = "Hello, Java 10 var!"; // Inferred as String
        var numbers = new ArrayList<Integer>(); // Inferred as ArrayList<Integer>
        numbers.add(10);
        numbers.add(20);

        var map = new HashMap<String, Integer>(); // Inferred as HashMap<String, Integer>
        map.put("Java", 10);

        System.out.println(message + " | " + numbers + " | " + map);

        // Anonymous non-denotable type retention with var
        var anon = new Object() {
            String name = "Anonymous Type Member";
        };
        System.out.println("Anon property access via var: " + anon.name);
    }
}
