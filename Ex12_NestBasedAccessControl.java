package jls;

/**
 * JLS 12/50: Java 11 - Nest-Based Access Control (JLS §11.1.1)
 * Demonstrates NestHost and NestMembers attributes allowing private access across nested classes.
 */
public class Ex12_NestBasedAccessControl {

    private static String outerSecret = "Secret Outer Data";

    public static class Inner {
        public void revealSecret() {
            // JLS §11.1.1: Direct private access without synthetic bridge accessors
            System.out.println("Inner class accessing: " + outerSecret);
        }
    }

    public static void main(String[] args) {
        Inner inner = new Inner();
        inner.revealSecret();
        
        System.out.println("Is Nestmate of Inner: " + Ex12_NestBasedAccessControl.class.isNestmateOf(Inner.class));
    }
}
