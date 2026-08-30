package jls;

/**
 * JLS 22/50: Java 22 - Statements Before super(...) in Constructors (JLS §8.8.7.1)
 * Demonstrates executing validation and argument computation statements before super/this constructor call.
 */
public class Ex22_StatementsBeforeSuper {

    static class Base {
        private final int value;
        public Base(int value) {
            this.value = value;
            System.out.println("Base constructor called with value: " + value);
        }
    }

    static class Sub extends Base {
        public Sub(int rawInput) {
            // JLS §8.8.7.1 (Java 22+): Statements allowed before super(...)!
            if (rawInput < 0) {
                throw new IllegalArgumentException("rawInput cannot be negative");
            }
            int processed = rawInput * 2;
            
            super(processed); // Prohibited from referencing 'this' before super call completes
        }
    }

    public static void main(String[] args) {
        Sub sub = new Sub(21);
    }
}
