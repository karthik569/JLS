package jls;

/**
 * JLS 17/50: Java 17 - Sealed Classes and Interfaces (JLS §8.1.6 & JLS §9.1.4)
 * Demonstrates restricting subtyping hierarchy using permits clause, final, sealed, and non-sealed subclasses.
 */
public class Ex17_SealedClasses {

    // JLS §8.1.6: Sealed Interface restricting permitted implementations
    public sealed interface Vehicle permits Car, Truck, Bicycle {}

    // Permitted subclass must be final, sealed, or non-sealed
    public static final class Car implements Vehicle {
        public String drive() { return "Vroom in Car"; }
    }

    public static sealed class Truck implements Vehicle permits HeavyTruck {
        public String haul() { return "Hauling heavy load"; }
    }

    public static final class HeavyTruck extends Truck {}

    // Non-sealed allows un-sealed extension breaking restriction strictly at this node
    public static non-sealed class Bicycle implements Vehicle {
        public String pedal() { return "Pedaling bike"; }
    }

    public static void main(String[] args) {
        Vehicle v = new Car();
        System.out.println("Vehicle object instance: " + v.getClass().getSimpleName());
    }
}
