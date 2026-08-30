package jls;

/**
 * Java Language Specification (JLS) - Chapter 5: Conversions and Contexts
 * 
 * Demonstrates:
 * 1. Widening Primitive Conversion (JLS §5.1.2)
 * 2. Narrowing Primitive Conversion (JLS §5.1.3)
 * 3. Boxing & Unboxing Conversions (JLS §5.1.7, §5.1.8)
 * 4. Binary Numeric Promotion (JLS §5.6.2)
 */
public class TypeConversionsDemo {

    public static void main(String[] args) {
        // JLS §5.1.2: Widening Primitive Conversion
        // int to long conversion happens automatically without loss of magnitude
        int intVal = 100;
        long longVal = intVal; 
        double doubleVal = longVal; // long to double widening
        System.out.println("Widened double value: " + doubleVal);

        // JLS §5.1.3: Narrowing Primitive Conversion
        // Requires explicit cast. May lose information about overall magnitude or precision!
        double pi = 3.14159;
        int truncatedPi = (int) pi; // Fractional part discarded
        System.out.println("Narrowed int value from pi: " + truncatedPi);

        byte overflowByte = (byte) 130; // 130 wraps around in signed 8-bit byte (-128 to 127) -> -126
        System.out.println("Narrowed byte value (overflow): " + overflowByte);

        // JLS §5.1.7 & §5.1.8: Boxing and Unboxing Conversions
        Integer boxedInt = intVal; // Autoboxing: primitive int -> Integer object
        int unboxedInt = boxedInt; // Unboxing: Integer object -> primitive int
        System.out.println("Boxed & Unboxed: " + unboxedInt);

        // JLS §5.6.2: Binary Numeric Promotion
        // When operands of a binary operator have different types, smaller types are promoted.
        byte b = 10;
        char c = 'a'; // ASCII 97
        int result = b + c; // Both byte and char promoted to int before addition
        System.out.println("Numeric Promotion result (byte + char): " + result);
    }
}
