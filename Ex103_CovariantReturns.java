package jls;

/**
 * JLS §8.4.8.3 (Java 5+): Covariant Return Types in Method Overriding
 * 
 * An overriding method may declare a return type that is a SUBTYPE of the
 * overridden method's return type. The method signature is still considered
 * compatible.
 * 
 * Key concepts:
 * - Override return type can narrow to a subtype
 * - Caller can use the more specific return without casting
 * - Inherited covariant chain: each subclass may narrow further
 * - Applicable to interface methods and abstract class methods
 */
public class Ex103_CovariantReturns {
    
    public static void main(String[] args) {
        System.out.println("=== Covariant Return Types Demo ===\n");
        
        demoBasicCovariance();
        demoCovarianceChain();
        demoInterfaceCovariance();
        demoStaticBinding();
    }
    
    static void demoBasicCovariance() {
        System.out.println("Demo 1: Basic Covariant Return");
        System.out.println("=".repeat(50));
        AnimalProducer ap = new DogProducer();
        Animal a = ap.produce();
        System.out.println("  AnimalProducer.produce() -> " + a.getClass().getSimpleName());
        // With covariance, we can call the more specific type directly via the concrete reference
        DogProducer dogRef = new DogProducer();
        Dog d = dogRef.produce();
        System.out.println("  DogProducer.produce() -> " + d.getClass().getSimpleName());
        System.out.println();
    }
    
    static void demoCovarianceChain() {
        System.out.println("Demo 2: Multi-Level Covariance Chain");
        System.out.println("=".repeat(50));
        PuppyProducer pp = new PuppyProducer();
        Puppy puppy = pp.produce();
        System.out.println("  Puppy name: " + puppy.name);
        System.out.println();
    }
    
    static void demoInterfaceCovariance() {
        System.out.println("Demo 3: Covariant Return in Interface Implementations");
        System.out.println("=".repeat(50));
        // To get the covariant return, we use the concrete type
        CircleFactory factory = new CircleFactory();
        Circle c = factory.create();
        System.out.println("  Shape created: " + c);
        
        // Through the interface, we get the interface return type
        IShapeFactory ifaceRef = factory;
        Shape s = ifaceRef.create();
        System.out.println("  Via interface: " + s.getClass().getSimpleName());
        System.out.println();
    }
    
    static void demoStaticBinding() {
        System.out.println("Demo 4: Static Return Type Binding");
        System.out.println("=".repeat(50));
        AnimalProducer aRef = new DogProducer();
        // Static type is AnimalProducer, so compiler uses declared return type
        Animal result = aRef.produce();
        System.out.println("  Declared type: " + result.getClass().getSuperclass().getSimpleName());
        System.out.println("  Actual object: " + result.getClass().getSimpleName());
        System.out.println();
    }
}

class Animal {
    @Override
    public String toString() { return "Animal"; }
}

class Dog extends Animal {
    @Override
    public String toString() { return "Dog"; }
}

class Puppy extends Dog {
    String name = "Tiny";
    @Override
    public String toString() { return "Puppy(" + name + ")"; }
}

class AnimalProducer {
    public Animal produce() { return new Animal(); }
}

class DogProducer extends AnimalProducer {
    // Covariant return: Dog IS-A Animal, so this is legal
    @Override
    public Dog produce() { return new Dog(); }
}

class PuppyProducer extends DogProducer {
    // Further covariance: Puppy IS-A Dog
    @Override
    public Puppy produce() { return new Puppy(); }
}

interface Shape { String describe(); }
class Circle implements Shape {
    @Override
    public String describe() { return "Circle"; }
    @Override
    public String toString() { return "Circle(radius=1)"; }
}

interface IShapeFactory {
    Shape create();
}

class CircleFactory implements IShapeFactory {
    // Covariant return: Circle IS-A Shape
    @Override
    public Circle create() { return new Circle(); }
}
