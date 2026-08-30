package jls;

/**
 * JLS 21/50: Java 22 - Unnamed Variables and Patterns (_) (JLS §6.1, §14.30)
 * Demonstrates using underscore (_) to denote unused variables and pattern components.
 */
public class Ex21_UnnamedVariablesAndPatterns {

    public record Person(String name, int age) {}

    public static void main(String[] args) {
        // JLS §6.1: Unnamed local variable using underscore
        try {
            int number = Integer.parseInt("123");
            System.out.println("Parsed number: " + number);
        } catch (NumberFormatException _) { // Unnamed exception variable
            System.out.println("Caught invalid number syntax");
        }

        Person person = new Person("Alice", 30);
        // JLS §14.30: Unnamed record component pattern
        if (person instanceof Person(String name, _)) {
            System.out.println("Matched person with name: " + name + " (age ignored)");
        }
    }
}
