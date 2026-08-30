package jls;

import java.util.ArrayList;
import java.util.List;

/**
 * JLS §15.7.2 (Java 22+ Preview): Statements Before Super
 * 
 * This preview feature allows developers to place statements before
 * super() or this() in constructors.
 * 
 * Key concepts:
 * - Useful for validation, transformation, or preparation logic
 * - Ensures object invariants are established before any subclass initialization
 * - Makes certain design patterns more straightforward
 */
public class Ex87_StatementsBeforeSuper {
    
    public static void main(String[] args) {
        System.out.println("=== Statements Before Super Demo ===\n");
        
        // This example demonstrates the concept
        // Note: Requires Java 22+ with --enable-preview
        
        System.out.println("Demo: Constructor Validation");
        System.out.println("=".repeat(50));
        
        try {
            // This would use statements before super to validate
            var person = new Person("Alice", 25);
            System.out.println("  Created: " + person);
        } catch (IllegalArgumentException e) {
            System.out.println("  Validation failed: " + e.getMessage());
        }
        
        try {
            var invalidPerson = new Person(null, 25);
        } catch (IllegalArgumentException e) {
            System.out.println("  Validation failed: " + e.getMessage());
        }
        
        try {
            var underage = new Person("Bob", 15);
        } catch (IllegalArgumentException e) {
            System.out.println("  Validation failed: " + e.getMessage());
        }
    }
    
    static class Person {
        private final String name;
        private final int age;
        
        public Person(String name, int age) {
            // Statement before super - validate arguments first
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be null or blank");
            }
            if (age < 18) {
                throw new IllegalArgumentException("Age must be at least 18");
            }
            
            super();  // super() must come after validation
            
            this.name = name;
            this.age = age;
        }
        
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }
}
