package jls;

import java.lang.annotation.*;
import java.lang.reflect.*;

/**
 * JLS §9.7 (Java 8+): Annotations, Retention Targets & Repeatable Annotations
 * 
 * Demonstrates custom annotations, retention policies, target locations,
 * and the repeatable annotation mechanism.
 * 
 * Key concepts:
 * - @Retention: SOURCE, CLASS, RUNTIME
 * - @Target: TYPE, FIELD, METHOD, PARAMETER, etc.
 * - @Repeatable: allows an annotation to be applied multiple times
 * - Reflection-based introspection of annotations
 */
public class Ex101_AnnotationsAndRetention {
    
    public static void main(String[] args) {
        System.out.println("=== Annotations & Retention Demo ===\n");
        
        demoClassLevelAnnotations();
        demoFieldAnnotations();
        demoMethodAnnotations();
        demoRepeatableAnnotations();
    }
    
    @Author(name = "Alice", date = "2026-01-15")
    @Version(major = 1, minor = 2)
    static class ServiceA {}
    
    @Author(name = "Bob", date = "2026-02-20")
    @Author(name = "Charlie", date = "2026-03-10")
    static class ServiceB {}
    
    static class ServiceC {
        @Deprecated(since = "2.0", forRemoval = true)
        private String legacyField = "old";
        
        @Author(name = "Dave", date = "2026-04-01")
        @Deprecated(since = "2.0")
        @SuppressWarnings("unchecked") // RetentionPolicy.SOURCE -> discarded at compile-time
        public void legacyMethod() {
            System.out.println("  Legacy method invoked");
        }
    }
    
    static void demoClassLevelAnnotations() {
        System.out.println("Demo 1: Class-Level Annotations");
        System.out.println("=".repeat(50));
        Class<?> c = ServiceA.class;
        for (Annotation a : c.getAnnotations()) {
            System.out.println("  " + a.annotationType().getSimpleName() + " -> " + a);
        }
        System.out.println();
    }
    
    static void demoFieldAnnotations() {
        System.out.println("Demo 2: Field Annotations via Reflection");
        System.out.println("=".repeat(50));
        try {
            Field f = ServiceC.class.getDeclaredField("legacyField");
            for (Annotation a : f.getAnnotations()) {
                System.out.println("  Field '" + f.getName() + "' has " + a);
            }
        } catch (NoSuchFieldException e) {
            System.out.println("  Field not found");
        }
        System.out.println();
    }
    
    static void demoMethodAnnotations() {
        System.out.println("Demo 3: Method Annotations via Reflection (RUNTIME vs SOURCE retention)");
        System.out.println("=".repeat(50));
        try {
            Method m = ServiceC.class.getMethod("legacyMethod");
            for (Annotation a : m.getAnnotations()) {
                System.out.println("  Method '" + m.getName() + "' has " + a);
            }
            System.out.println("  Note: @SuppressWarnings (SOURCE retention) is discarded and absent at runtime.");
        } catch (NoSuchMethodException e) {
            System.out.println("  Method not found");
        }
        System.out.println();
    }
    
    static void demoRepeatableAnnotations() {
        System.out.println("Demo 4: Repeatable Annotations");
        System.out.println("=".repeat(50));
        Class<?> c = ServiceB.class;
        Author[] authors = c.getAnnotationsByType(Author.class);
        System.out.println("  ServiceB has " + authors.length + " @Author entries:");
        for (Author a : authors) {
            System.out.println("    - " + a.name() + " on " + a.date());
        }
        System.out.println();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Repeatable(Authors.class)
    public @interface Author {
        String name();
        String date();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Version {
        int major();
        int minor();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Authors {
        Author[] value();
    }
}
