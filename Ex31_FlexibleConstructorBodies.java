package jls;

/**
 * JLS 31/50: Java 23+ - Flexible Constructor Bodies (JLS §8.8.7)
 * Demonstrates field initialization before explicitly invoking super/this constructor.
 */
public class Ex31_FlexibleConstructorBodies {

    static class Parent {
        public Parent() {
            System.out.println("Parent constructor called.");
        }
    }

    static class Child extends Parent {
        private final String name;

        public Child(String input) {
            // JLS §8.8.7: Direct field mutation allowed before super()
            this.name = input.toUpperCase();
            super();
        }

        public String getName() { return name; }
    }

    public static void main(String[] args) {
        Child c = new Child("antigravity");
        System.out.println("Child initialized name: " + c.getName());
    }
}
