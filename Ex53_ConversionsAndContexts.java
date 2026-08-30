/**
 * JLS Chapter 5: Conversions and Contexts
 *
 * Demonstrates:
 * - JLS §5.1: Kinds of Conversions
 *   - Identity conversion
 *   - Widening/narrowing primitive conversions
 *   - Widening/narrowing reference conversions
 *   - Boxing/unboxing conversions
 *   - Unchecked conversions
 *   - Capture conversions
 *   - String conversions
 *   - Value set conversions
 * - JLS §5.2: Assignment Contexts (assignment, initialization, parameter passing)
 * - JLS §5.3: Invocation Contexts (method/constructor arguments)
 * - JLS §5.4: String Contexts (string concatenation)
 * - JLS §5.5: Casting Contexts (explicit casts)
 * - JLS §5.6: Numeric Contexts (binary numeric promotion, unary numeric promotion)
 */
public class Ex53_ConversionsAndContexts {

    // JLS §5.1.1: Identity Conversion
    // A type converts to itself (no change)
    static void identityConversion() {
        int i = 42;
        int j = i;  // Identity conversion: int -> int
        String s = "hello";
        String t = s;  // Identity conversion: String -> String
        System.out.println("Identity: i=" + i + ", j=" + j + ", s=" + s + ", t=" + t);
    }

    // JLS §5.1.2: Widening Primitive Conversion
    // byte -> short -> int -> long -> float -> double
    // char -> int -> long -> float -> double
    // No loss of magnitude, possible loss of precision (float/double)
    static void wideningPrimitiveConversion() {
        byte b = 100;
        short s = b;    // byte -> short
        int i = s;      // short -> int
        long l = i;     // int -> long
        float f = l;    // long -> float (possible precision loss)
        double d = f;   // float -> double

        char c = 'A';
        int i2 = c;     // char -> int (widens to Unicode code point)

        System.out.println("Widening: byte=" + b + " -> short=" + s + " -> int=" + i
                + " -> long=" + l + " -> float=" + f + " -> double=" + d);
        System.out.println("char 'A' -> int: " + i2);
    }

    // JLS §5.1.3: Narrowing Primitive Conversion
    // double -> float -> long -> int -> short -> byte
    // May lose magnitude and precision
    static void narrowingPrimitiveConversion() {
        double d = 123.456;
        float f = (float) d;   // double -> float (explicit cast)
        long l = (long) f;     // float -> long (truncates)
        int i = (int) l;       // long -> int (possible overflow)
        short s = (short) i;   // int -> short (possible overflow)
        byte b = (byte) s;     // short -> byte (possible overflow)

        // char narrowing
        int i2 = 65;
        char c = (char) i2;    // int -> char

        System.out.println("Narrowing: double=" + d + " -> float=" + f + " -> long=" + l
                + " -> int=" + i + " -> short=" + s + " -> byte=" + b);
        System.out.println("int 65 -> char: '" + c + "'");

        // Overflow examples
        int bigInt = 1_000_000;
        byte overflowByte = (byte) bigInt;  // -128 to 127, wraps around
        System.out.println("1_000_000 -> byte (overflow): " + overflowByte);
    }

    // JLS §5.1.4: Widening and Narrowing Primitive Conversions followed by
    // boxing/unboxing - handled in respective sections

    // JLS §5.1.5: Widening Reference Conversion
    // Subclass -> Superclass, Subinterface -> Superinterface
    // Array covariance: S[] -> T[] if S -> T
    // Interface -> Object, Class -> Object
    static class Animal {}
    static class Dog extends Animal {}
    static class Cat extends Animal {}

    interface Pet {}
    interface DogPet extends Pet {}

    static void wideningReferenceConversion() {
        Dog dog = new Dog();
        Animal animal = dog;        // Dog -> Animal (subclass to superclass)
        Object obj = dog;           // Dog -> Object

        DogPet dogPet = new DogPet() {};
        Pet pet = dogPet;           // Subinterface -> Superinterface

        Dog[] dogs = new Dog[10];
        Animal[] animals = dogs;    // Dog[] -> Animal[] (array covariance)
        Object[] objects = dogs;    // Dog[] -> Object[]

        System.out.println("Widening reference: Dog -> Animal, Dog -> Object");
        System.out.println("Array covariance: Dog[] -> Animal[] -> Object[]");
    }

    // JLS §5.1.6: Narrowing Reference Conversion
    // Superclass -> Subclass (requires runtime check)
    // Superinterface -> Subinterface
    // Object -> Class/Interface
    // Array narrowing: T[] -> S[] (runtime check)
    static void narrowingReferenceConversion() {
        Animal animal = new Dog();
        Dog dog = (Dog) animal;     // Animal -> Dog (runtime check passes)

        Animal cat = new Cat();
        // Dog badDog = (Dog) cat;   // ClassCastException at runtime!

        Object obj = "hello";
        String str = (String) obj;  // Object -> String

        Object[] objects = new Dog[10];
        Dog[] dogs = (Dog[]) objects;  // Object[] -> Dog[] (runtime check)

        System.out.println("Narrowing reference: Animal -> Dog (checked)");
        System.out.println("Object -> String (checked)");
        System.out.println("Array narrowing: Object[] -> Dog[] (checked)");
    }

    // JLS §5.1.7: Boxing Conversion
    // Primitive -> Corresponding wrapper type
    static void boxingConversion() {
        boolean b = true;
        Boolean boolObj = b;        // boolean -> Boolean

        byte by = 100;
        Byte byteObj = by;          // byte -> Byte

        short sh = 32000;
        Short shortObj = sh;        // short -> Short

        int i = 42;
        Integer intObj = i;         // int -> Integer

        long l = 1000L;
        Long longObj = l;           // long -> Long

        char c = 'A';
        Character charObj = c;      // char -> Character

        float f = 3.14f;
        Float floatObj = f;         // float -> Float

        double d = 3.14159;
        Double doubleObj = d;       // double -> Double

        System.out.println("Boxing: " + boolObj + ", " + byteObj + ", " + shortObj
                + ", " + intObj + ", " + longObj + ", " + charObj
                + ", " + floatObj + ", " + doubleObj);
    }

    // JLS §5.1.8: Unboxing Conversion
    // Wrapper type -> Primitive
    static void unboxingConversion() {
        Boolean boolObj = true;
        boolean b = boolObj;        // Boolean -> boolean

        Byte byteObj = (byte) 100;
        byte by = byteObj;          // Byte -> byte

        Integer intObj = 42;
        int i = intObj;             // Integer -> int

        System.out.println("Unboxing: " + b + ", " + by + ", " + i);
    }

    // JLS §5.1.9: Unchecked Conversion
    // Raw type -> Parameterized type (or vice versa)
    // Generates unchecked warning
    @SuppressWarnings("unchecked")
    static void uncheckedConversion() {
        java.util.List rawList = new java.util.ArrayList();
        rawList.add("hello");

        // Unchecked: raw List -> List<String>
        java.util.List<String> stringList = (java.util.List<String>) rawList;

        // Unchecked: raw type in generic context
        java.util.List<Integer> intList = new java.util.ArrayList();  // Diamond infers Integer
        // Actually this uses diamond, not raw

        System.out.println("Unchecked conversion: raw List -> List<String>");
        System.out.println("First element: " + stringList.get(0));
    }

    // JLS §5.1.10: Capture Conversion
    // Wildcard type -> Fresh type variable
    static void captureConversion() {
        // Capture converts ? extends Number to a fresh type variable
        java.util.List<? extends Number> wildcardList = new java.util.ArrayList<Integer>();
        // The actual type is "capture of ? extends Number"
        // We can't add elements (except null), but can read as Number
        Number n = wildcardList.isEmpty() ? 0 : wildcardList.get(0);
        System.out.println("Capture conversion: List<? extends Number> -> capture type");
        System.out.println("Read as Number: " + n);
    }

    // JLS §5.1.11: String Conversion
    // Any type -> String (via toString())
    static void stringConversion() {
        int i = 42;
        String s = i + "";          // int -> String via concatenation
        String s2 = String.valueOf(i);  // Explicit

        Object obj = new Object();
        String s3 = obj + "";       // Object -> String via toString()

        System.out.println("String conversion: int -> " + s + ", Object -> " + s3);
    }

    // JLS §5.1.12: Value Set Conversion
    // For float/double: strictfp vs non-strictfp
    // Not directly demonstrable without strictfp context

    // JLS §5.2: Assignment Contexts
    // Permitted: identity, widening primitive, widening reference, boxing,
    //             boxing + widening reference, unchecked, capture
    // NOT permitted: narrowing primitive, narrowing reference, unboxing (except with widening)
    static void assignmentContexts() {
        // Widening primitive allowed
        int i = 10;
        double d = i;               // int -> double (widening)

        // Widening reference allowed
        Dog dog = new Dog();
        Animal animal = dog;        // Dog -> Animal

        // Boxing allowed
        Integer intObj = i;         // int -> Integer

        // Boxing + widening reference
        Integer intObj2 = 10;
        Number num = intObj2;       // Integer -> Number (boxing + widening)

        // Unchecked allowed (with warning)
        @SuppressWarnings("unchecked")
        java.util.List<String> list = (java.util.List<String>) new java.util.ArrayList();

        // Capture allowed
        java.util.List<? extends Number> wild = new java.util.ArrayList<Integer>();

        // NOT allowed (compile errors):
        // int j = 10.5;             // narrowing primitive
        // Dog d2 = new Animal();    // narrowing reference
        // int k = intObj;           // unboxing not directly allowed in assignment
        // Actually unboxing IS allowed in assignment context (JLS §5.2)
        int k = intObj;             // Integer -> int (unboxing allowed!)

        System.out.println("Assignment contexts work for widening, boxing, unboxing, etc.");
    }

    // JLS §5.3: Invocation Contexts (method/constructor arguments)
    // Similar to assignment but NO implicit narrowing primitive
    static void invocationContexts(int i) { }  // expects int
    static void invocationContexts(double d) { }  // expects double

    static void demonstrateInvocationContexts() {
        // Widening primitive allowed
        invocationContexts(10);       // int -> int (identity)
        invocationContexts(10L);      // long -> int? NO - narrowing not allowed!
        // invocationContexts(10.5);  // double -> int? NO!

        // But widening works:
        invocationContexts(10);       // int -> double (widening) calls double version
    }

    // JLS §5.4: String Contexts
    // String concatenation with +
    static void stringContexts() {
        String s = "Value: " + 42;        // int -> String
        String s2 = "Value: " + 3.14;     // double -> String
        String s3 = "Value: " + true;     // boolean -> String
        String s4 = "Value: " + new Object();  // Object -> String

        System.out.println("String contexts: " + s + ", " + s2 + ", " + s3);
    }

    // JLS §5.5: Casting Contexts
    // Explicit cast operator (type)
    // Permits: identity, widening/narrowing primitive, widening/narrowing reference,
    //          boxing/unboxing, boxing + widening, unboxing + widening, unchecked, capture
    static void castingContexts() {
        // Narrowing primitive with cast
        double d = 3.14;
        int i = (int) d;              // double -> int (narrowing)

        // Narrowing reference with cast
        Animal animal = new Dog();
        Dog dog = (Dog) animal;       // Animal -> Dog

        // Boxing with cast
        int i2 = 42;
        Object obj = (Object) i2;     // int -> Integer -> Object (boxing + widening)

        // Unboxing with cast
        Integer intObj = 42;
        int i3 = (int) intObj;        // Integer -> int (unboxing)

        System.out.println("Casting: double->int=" + i + ", Animal->Dog, int->Object, Integer->int");
    }

    // JLS §5.6: Numeric Contexts
    // Unary numeric promotion: operand of unary +, -, ~
    // Binary numeric promotion: operands of binary operators
    static void numericContexts() {
        // Unary numeric promotion
        byte b = 10;
        int promoted = +b;            // byte -> int (unary +)
        int negated = -b;             // byte -> int (unary -)
        int complemented = ~b;        // byte -> int (bitwise ~)

        // Binary numeric promotion
        int i = 10;
        long l = 20L;
        long result = i + l;          // int + long -> long (binary promotion)

        float f = 1.5f;
        double d = 2.5;
        double result2 = f + d;       // float + double -> double

        char c = 'A';
        int charResult = c + 1;       // char + int -> int

        System.out.println("Unary promotion: byte->int for +, -, ~");
        System.out.println("Binary promotion: int+long=long, float+double=double, char+int=int");
    }

    public static void main(String[] args) {
        System.out.println("=== JLS Chapter 5: Conversions and Contexts Demo ===\n");

        System.out.println("--- Identity Conversion (JLS §5.1.1) ---");
        identityConversion();

        System.out.println("\n--- Widening Primitive Conversion (JLS §5.1.2) ---");
        wideningPrimitiveConversion();

        System.out.println("\n--- Narrowing Primitive Conversion (JLS §5.1.3) ---");
        narrowingPrimitiveConversion();

        System.out.println("\n--- Widening Reference Conversion (JLS §5.1.5) ---");
        wideningReferenceConversion();

        System.out.println("\n--- Narrowing Reference Conversion (JLS §5.1.6) ---");
        narrowingReferenceConversion();

        System.out.println("\n--- Boxing Conversion (JLS §5.1.7) ---");
        boxingConversion();

        System.out.println("\n--- Unboxing Conversion (JLS §5.1.8) ---");
        unboxingConversion();

        System.out.println("\n--- Unchecked Conversion (JLS §5.1.9) ---");
        uncheckedConversion();

        System.out.println("\n--- Capture Conversion (JLS §5.1.10) ---");
        captureConversion();

        System.out.println("\n--- String Conversion (JLS §5.1.11) ---");
        stringConversion();

        System.out.println("\n--- Assignment Contexts (JLS §5.2) ---");
        assignmentContexts();

        System.out.println("\n--- Invocation Contexts (JLS §5.3) ---");
        demonstrateInvocationContexts();

        System.out.println("\n--- String Contexts (JLS §5.4) ---");
        stringContexts();

        System.out.println("\n--- Casting Contexts (JLS §5.5) ---");
        castingContexts();

        System.out.println("\n--- Numeric Contexts (JLS §5.6) ---");
        numericContexts();
    }
}