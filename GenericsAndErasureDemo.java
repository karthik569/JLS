package jls;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Language Specification (JLS) - Chapter 4 (Types, Values, and Variables) & Chapter 15 (Expressions)
 * 
 * Demonstrates:
 * 1. Type Erasure & Bridge Methods (JLS §4.6)
 * 2. Wildcards & Use-Site Variance (Producer Extends, Consumer Super - PECS) (JLS §4.5.1)
 * 3. Array Type Covariance vs Generic Invariance (JLS §4.10.3)
 */
public class GenericsAndErasureDemo {

    // JLS §4.10.3: Array Subtyping (Arrays are Covariant)
    public static void demonstrateArrayCovariance() {
        String[] strArray = new String[]{"Hello", "JLS"};
        Object[] objArray = strArray; // Allowed due to array covariance

        System.out.println("Array element 0: " + objArray[0]);

        try {
            // JLS §10.5: ArrayStoreException at runtime because actual array is String[]
            objArray[0] = 123; 
        } catch (ArrayStoreException e) {
            System.out.println("Caught expected ArrayStoreException: " + e.getMessage());
        }
    }

    // JLS §4.5.1: Covariant Wildcards (? extends T - Producer Extends)
    public static double sumOfList(List<? extends Number> list) {
        double sum = 0.0;
        for (Number n : list) { // Safe to READ as Number
            sum += n.doubleValue();
        }
        // list.add(10); // COMPILE ERROR! Cannot write to ? extends Number except null
        return sum;
    }

    // JLS §4.5.1: Contravariant Wildcards (? super T - Consumer Super)
    public static void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 3; i++) {
            list.add(i); // Safe to WRITE Integer or sub-types
        }
        // Object item = list.get(0); // Can only safely READ as Object
    }

    // JLS §4.6: Type Erasure Example
    static class Node<T> {
        private T data;
        public Node(T data) { this.data = data; }
        public void setData(T data) { this.data = data; }
        public T getData() { return data; }
    }

    // Subclass specifying concrete type Integer
    static class MyNode extends Node<Integer> {
        public MyNode(Integer data) { super(data); }
        
        @Override
        public void setData(Integer data) {
            System.out.println("MyNode.setData(Integer): " + data);
            super.setData(data);
        }
        // Compiler automatically generates a synthetic Bridge Method at bytecode level:
        // public void setData(Object data) { setData((Integer) data); }
    }

    public static void main(String[] args) {
        System.out.println("--- Array Covariance ---");
        demonstrateArrayCovariance();

        System.out.println("\n--- Wildcards (PECS) ---");
        List<Double> doubleList = List.of(1.5, 2.5, 3.0);
        System.out.println("Sum of doubles: " + sumOfList(doubleList));

        List<Number> numList = new ArrayList<>();
        addNumbers(numList);
        System.out.println("Consumer Super List contents: " + numList);

        System.out.println("\n--- Type Erasure & Bridge Methods ---");
        MyNode mn = new MyNode(5);
        Node rawNode = mn; // Raw type reference
        rawNode.setData(10); // Invokes synthesized bridge method setData(Object) -> setData(Integer)
    }
}
