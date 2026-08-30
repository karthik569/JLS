/**
 * JLS Chapter 15: Expressions (Deep Dive)
 *
 * Demonstrates:
 * - JLS §15.1: Evaluation Order (left-to-right)
 * - JLS §15.2: Forms of Expressions (primary, class instance creation, method invocation, etc.)
 * - JLS §15.3-15.4: Primary Expressions (literals, this, super, class literals, void, parenthesized)
 * - JLS §15.5-15.7: Class Instance Creation (new, anonymous, qualified)
 * - JLS §15.8-15.9: Array Creation and Access
 * - JLS §15.10-15.11: Field Access and Method Invocation
 * - JLS §15.12: Method Invocation (compile-time declaration, resolution, evaluation)
 * - JLS §15.13: Method References
 * - JLS §15.14: Postfix Expressions (++, --)
 * - JLS §15.15: Unary Operators (+, -, ~, !, ++, --)
 * - JLS §15.16: Cast Expressions
 * - JLS §15.17-15.22: Multiplicative, Additive, Shift, Relational, Equality Operators
 * - JLS §15.23-15.24: Bitwise and Logical Operators (&, ^, |, &&, ||)
 * - JLS §15.25: Conditional Operator (? :)
 * - JLS §15.26: Assignment Operators (=, +=, -=, etc.)
 * - JLS §15.27: Lambda Expressions
 * - JLS §15.28: Constant Expressions
 * - JLS §15.29: Switch Expressions
 */
public class Ex61_ExpressionsDeepDive {

    // ============================================================
    // JLS §15.1: Evaluation Order
    // ============================================================

    static int evalOrderCounter = 0;

    static int getValue(String name) {
        System.out.println("    Evaluating " + name + " (order " + ++evalOrderCounter + ")");
        return evalOrderCounter;
    }

    static void evaluationOrderDemo() {
        System.out.println("  Evaluation Order (JLS §15.1) - Left to Right:");
        evalOrderCounter = 0;

        // Operands evaluated left to right
        int result = getValue("a") + getValue("b") * getValue("c");
        // Order: a, b, c, then *, then +
        System.out.println("    Result: " + result);

        // Method arguments evaluated left to right
        evalOrderCounter = 0;
        printThree(getValue("arg1"), getValue("arg2"), getValue("arg3"));

        // Array access: index evaluated before array reference
        evalOrderCounter = 0;
        int[] arr = new int[5];
        arr[getValue("index")] = getValue("value");
        // Order: index, then value, then assignment
    }

    static void printThree(int a, int b, int c) {
        System.out.println("    Args received: " + a + ", " + b + ", " + c);
    }

    // ============================================================
    // JLS §15.3-15.4: Primary Expressions
    // ============================================================

    static void primaryExpressionsDemo() {
        System.out.println("  Primary Expressions:");

        // Literals
        int literal = 42;
        String strLiteral = "hello";

        // this (in instance context)
        // System.out.println(this);  // Only in non-static context

        // super (in subclass context)
        // super.toString();

        // Class literals
        Class<String> stringClass = String.class;
        Class<int[]> intArrayClass = int[].class;
        Class<Void> voidClass = Void.class;  // void.class -> Void.class (wrapper)
        System.out.println("    Class literals: " + stringClass + ", " + intArrayClass + ", " + voidClass);

        // Parenthesized expression
        int paren = (1 + 2) * 3;
        System.out.println("    Parenthesized: (1+2)*3 = " + paren);

        // Qualified this
        // Ex61_ExpressionsDeepDive.this.toString();  // In nested class
    }

    // ============================================================
    // JLS §15.5-15.7: Class Instance Creation
    // ============================================================

    static class SimpleClass {
        String name;
        SimpleClass(String name) { this.name = name; }
    }

    static void classInstanceCreationDemo() {
        System.out.println("  Class Instance Creation (new):");

        // Basic creation
        SimpleClass obj1 = new SimpleClass("basic");
        System.out.println("    new SimpleClass(\"basic\"): " + obj1.name);

        // Anonymous class creation
        Runnable anon = new Runnable() {
            @Override public void run() { System.out.println("    Anonymous class running"); }
        };
        anon.run();

        // Anonymous class with arguments (Java 9+ diamond)
        Comparable<String> comp = new Comparable<>() {
            @Override public int compareTo(String o) { return 0; }
        };

        // Qualified class instance creation (inner class)
        Ex61_ExpressionsDeepDive outer = new Ex61_ExpressionsDeepDive();
        Ex61_ExpressionsDeepDive.Inner inner = outer.new Inner();
        System.out.println("    Qualified new: " + inner.getClass().getSimpleName());
    }

    class Inner {}

    // ============================================================
    // JLS §15.8-15.9: Array Creation and Access
    // ============================================================

    static void arrayCreationAccessDemo() {
        System.out.println("  Array Creation and Access:");

        // Array creation expression
        int[] arr1 = new int[5];
        int[][] arr2 = new int[3][4];
        int[][] arr3 = new int[3][];  // Jagged

        // Anonymous array
        int[] arr4 = new int[]{1, 2, 3};

        // Array access
        arr1[0] = 100;
        System.out.println("    arr1[0] = " + arr1[0]);

        // Multi-dimensional
        arr2[1][2] = 200;
        System.out.println("    arr2[1][2] = " + arr2[1][2]);

        // Array length (not a method!)
        System.out.println("    arr1.length = " + arr1.length);
    }

    // ============================================================
    // JLS §15.10-15.11: Field Access and Method Invocation
    // ============================================================

    static class FieldAccessDemo {
        static int staticField = 10;
        int instanceField = 20;

        static void staticMethod() { System.out.println("    Static method"); }
        void instanceMethod() { System.out.println("    Instance method"); }
    }

    static void fieldAccessMethodInvocationDemo() {
        System.out.println("  Field Access and Method Invocation:");

        // Field access
        System.out.println("    Static field: " + FieldAccessDemo.staticField);
        FieldAccessDemo demo = new FieldAccessDemo();
        System.out.println("    Instance field: " + demo.instanceField);

        // Method invocation
        FieldAccessDemo.staticMethod();
        demo.instanceMethod();

        // Qualified access
        System.out.println("    Qualified: Ex61_ExpressionsDeepDive.FieldAccessDemo.staticField");
    }

    // ============================================================
    // JLS §15.12: Method Invocation (Detailed)
    // ============================================================

    static class OverloadDemo {
        void method(int i) { System.out.println("    int: " + i); }
        void method(long l) { System.out.println("    long: " + l); }
        void method(double d) { System.out.println("    double: " + d); }
        void method(String s) { System.out.println("    String: " + s); }
        void method(Object o) { System.out.println("    Object: " + o); }
        void method(int i, int j) { System.out.println("    int,int: " + i + "," + j); }
    }

    static void methodInvocationDemo() {
        System.out.println("  Method Invocation Resolution (JLS §15.12):");

        OverloadDemo od = new OverloadDemo();

        // Phase 1: Strict invocation (no widening, no boxing, no varargs)
        od.method(42);        // Exact match: int
        od.method(42L);       // Exact match: long
        od.method(3.14);      // Exact match: double
        od.method("hello");   // Exact match: String

        // Phase 2: Loose invocation (widening, boxing)
        od.method((short) 5); // Widening: short -> int
        od.method((byte) 5);  // Widening: byte -> int
        od.method('A');       // Widening: char -> int

        // Phase 3: Varargs invocation
        // Not shown here - would need varargs method

        // Boxing
        od.method(Integer.valueOf(42));  // Boxing: int -> Integer -> Object

        // Null - matches most specific reference type
        od.method(null);  // Matches String (most specific)
    }

    // ============================================================
    // JLS §15.13: Method References
    // ============================================================

    static void methodReferenceDemo() {
        System.out.println("  Method References (JLS §15.13):");

        // Static method reference
        java.util.function.Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println("    Integer::parseInt(\"123\") = " + parseInt.apply("123"));

        // Instance method reference (bound)
        String str = "hello";
        java.util.function.Supplier<Integer> length = str::length;
        System.out.println("    str::length = " + length.get());

        // Instance method reference (unbound)
        java.util.function.Function<String, Integer> stringLength = String::length;
        System.out.println("    String::length(\"world\") = " + stringLength.apply("world"));

        // Constructor reference
        java.util.function.Supplier<java.util.ArrayList<String>> listSupplier = java.util.ArrayList::new;
        System.out.println("    ArrayList::new = " + listSupplier.get().getClass().getSimpleName());

        // Array constructor reference
        java.util.function.IntFunction<String[]> arrayCreator = String[]::new;
        String[] arr = arrayCreator.apply(3);
        System.out.println("    String[]::new(3) length = " + arr.length);
    }

    // ============================================================
    // JLS §15.14-15.16: Postfix, Unary, Cast
    // ============================================================

    static void postfixUnaryCastDemo() {
        System.out.println("  Postfix, Unary, Cast:");

        int i = 5;

        // Postfix: use then increment
        int postInc = i++;  // Returns 5, then i=6
        System.out.println("    i++: was " + postInc + ", now i=" + i);

        int postDec = i--;  // Returns 6, then i=5
        System.out.println("    i--: was " + postDec + ", now i=" + i);

        // Prefix: increment then use
        int preInc = ++i;   // i=6, returns 6
        System.out.println("    ++i: now i=" + preInc);

        int preDec = --i;   // i=5, returns 5
        System.out.println("    --i: now i=" + preDec);

        // Unary operators
        int positive = +5;
        int negative = -5;
        int bitwiseNot = ~5;  // -6 (two's complement)
        boolean logicalNot = !true;
        System.out.println("    +5=" + positive + ", -5=" + negative + ", ~5=" + bitwiseNot + ", !true=" + logicalNot);

        // Cast expressions
        double d = 3.14;
        int casted = (int) d;  // 3
        System.out.println("    (int) 3.14 = " + casted);

        Object obj = "hello";
        String castedStr = (String) obj;
        System.out.println("    (String) obj = " + castedStr);
    }

    // ============================================================
    // JLS §15.17-15.24: Arithmetic, Shift, Relational, Equality, Bitwise, Logical
    // ============================================================

    static void operatorsDemo() {
        System.out.println("  Operators:");

        // Multiplicative
        System.out.println("    10 * 3 = " + (10 * 3));
        System.out.println("    10 / 3 = " + (10 / 3));
        System.out.println("    10 % 3 = " + (10 % 3));

        // Additive
        System.out.println("    10 + 3 = " + (10 + 3));
        System.out.println("    10 - 3 = " + (10 - 3));
        System.out.println("    \"a\" + \"b\" = " + ("a" + "b"));

        // Shift
        System.out.println("    8 << 1 = " + (8 << 1));   // 16
        System.out.println("    8 >> 1 = " + (8 >> 1));   // 4
        System.out.println("    -8 >>> 1 = " + (-8 >>> 1));  // Large positive

        // Relational
        System.out.println("    5 < 10 = " + (5 < 10));
        System.out.println("    5 <= 5 = " + (5 <= 5));
        // 5 instanceof Integer - primitive patterns are preview feature in Java 21
        System.out.println("    Integer.valueOf(5) instanceof Integer = " + (Integer.valueOf(5) instanceof Integer));

        // Equality
        System.out.println("    5 == 5 = " + (5 == 5));
        System.out.println("    5 != 3 = " + (5 != 3));
        System.out.println("    \"a\" == \"a\" = " + ("a" == "a"));  // String interning
        System.out.println("    new String(\"a\") == new String(\"a\") = " + (new String("a") == new String("a")));

        // Bitwise
        System.out.println("    5 & 3 = " + (5 & 3));   // 1
        System.out.println("    5 | 3 = " + (5 | 3));   // 7
        System.out.println("    5 ^ 3 = " + (5 ^ 3));   // 6

        // Logical
        System.out.println("    true && false = " + (true && false));
        System.out.println("    true || false = " + (true || false));

        // Short-circuit evaluation
        boolean shortCircuit = false && (1/0 == 1);  // No ArithmeticException!
        System.out.println("    false && (1/0) = " + shortCircuit + " (short-circuited)");
    }

    // ============================================================
    // JLS §15.25: Conditional Operator (? :)
    // ============================================================

    static void conditionalOperatorDemo() {
        System.out.println("  Conditional Operator (? :):");

        int a = 10, b = 20;
        int max = (a > b) ? a : b;
        System.out.println("    max(10, 20) = " + max);

        // Type of conditional: common type of both branches
        Object obj = true ? "string" : 42;  // Both Object
        System.out.println("    true ? \"string\" : 42 = " + obj + " (type: " + obj.getClass().getSimpleName() + ")");

        // Nested
        int sign = (a > 0) ? 1 : (a < 0) ? -1 : 0;
        System.out.println("    Nested ternary for sign: " + sign);
    }

    // ============================================================
    // JLS §15.26: Assignment Operators
    // ============================================================

    static void assignmentOperatorsDemo() {
        System.out.println("  Assignment Operators:");

        int x = 10;
        x += 5;  // x = x + 5
        System.out.println("    x += 5 -> " + x);

        x -= 3;  // x = x - 3
        System.out.println("    x -= 3 -> " + x);

        x *= 2;  // x = x * 2
        System.out.println("    x *= 2 -> " + x);

        x /= 4;  // x = x / 4
        System.out.println("    x /= 4 -> " + x);

        x %= 3;  // x = x % 3
        System.out.println("    x %= 3 -> " + x);

        // Bitwise assignment
        x = 12;  // 1100
        x &= 10; // 1100 & 1010 = 1000 (8)
        System.out.println("    x &= 10 -> " + x);

        x = 12;
        x |= 3;  // 1100 | 0011 = 1111 (15)
        System.out.println("    x |= 3 -> " + x);

        x = 12;
        x ^= 5;  // 1100 ^ 0101 = 1001 (9)
        System.out.println("    x ^= 5 -> " + x);

        // Shift assignment
        x = 8;
        x <<= 1;  // 16
        System.out.println("    x <<= 1 -> " + x);

        // Compound assignment with casting
        byte b = 10;
        b += 5;  // Equivalent to b = (byte)(b + 5) - implicit cast!
        System.out.println("    byte b=10; b+=5 -> " + b + " (implicit cast)");

        // Assignment expression has value
        int y = (x = 20);  // x=20, y=20
        System.out.println("    int y = (x = 20) -> x=" + x + ", y=" + y);
    }

    // ============================================================
    // JLS §15.27: Lambda Expressions
    // ============================================================

    static void lambdaExpressionsDemo() {
        System.out.println("  Lambda Expressions (JLS §15.27):");

        // Basic lambda
        Runnable r1 = () -> System.out.println("    No params");
        r1.run();

        // Single param (parens optional)
        java.util.function.IntUnaryOperator square = x -> x * x;
        System.out.println("    x -> x*x: 5^2 = " + square.applyAsInt(5));

        // Multiple params
        java.util.function.IntBinaryOperator add = (a, b) -> a + b;
        System.out.println("    (a,b) -> a+b: 3+4 = " + add.applyAsInt(3, 4));

        // Block body with return
        java.util.function.IntFunction<Integer> factorial = n -> {
            int result = 1;
            for (int i = 2; i <= n; i++) result *= i;
            return result;
        };
        System.out.println("    Block lambda factorial(5) = " + factorial.apply(5));

        // Target typing - same lambda, different types
        java.util.function.Predicate<String> nonEmpty = s -> !s.isEmpty();
        java.util.function.Function<String, Boolean> nonEmptyFunc = s -> !s.isEmpty();
        System.out.println("    Target typing: Predicate and Function from same lambda");

        // Method reference vs lambda
        java.util.function.Function<String, Integer> mr = String::length;
        java.util.function.Function<String, Integer> lambda = s -> s.length();
        System.out.println("    Method ref and lambda equivalent: " + mr.apply("test") + " == " + lambda.apply("test"));
    }

    // ============================================================
    // JLS §15.28: Constant Expressions
    // ============================================================

    // Constant expressions - evaluated at compile time
    static final int CONST_EXPR_1 = 1 + 2 * 3;  // 7
    static final String CONST_EXPR_2 = "Hello" + " World";  // "Hello World"
    static final boolean CONST_EXPR_3 = true && false;  // false

    // NOT constant expressions (evaluated at runtime)
    static final int NOT_CONST_1 = Math.max(5, 10);
    static final String NOT_CONST_2 = new String("test");
    static final int NOT_CONST_3 = getRuntimeValue();

    static int getRuntimeValue() { return 42; }

    static void constantExpressionDemo() {
        System.out.println("  Constant Expressions (JLS §15.28):");

        System.out.println("    1 + 2 * 3 = " + CONST_EXPR_1);
        System.out.println("    \"Hello\" + \" World\" = " + CONST_EXPR_2);
        System.out.println("    true && false = " + CONST_EXPR_3);

        // Can be used in case labels
        switch (CONST_EXPR_1) {
            case 7 -> System.out.println("    Case label works with constant expression");
            default -> System.out.println("    Other");
        }

        // Can be used in annotations
        // @Annotation(value = CONST_EXPR_1)

        // Not constant expressions
        System.out.println("    Math.max(5,10) = " + NOT_CONST_1 + " (runtime)");
        System.out.println("    new String() = " + NOT_CONST_2 + " (runtime)");
    }

    // ============================================================
    // JLS §15.29: Switch Expressions
    // ============================================================

    static void switchExpressionDemo() {
        System.out.println("  Switch Expressions (JLS §15.29):");

        // Expression switch (returns value)
        String dayType = switch (3) {
            case 1, 2, 3, 4, 5 -> "Weekday";
            case 6, 7 -> "Weekend";
            default -> "Invalid";
        };
        System.out.println("    Switch expression result: " + dayType);

        // Yield in block
        int value = switch (2) {
            case 1 -> {
                int temp = 10;
                yield temp * 2;
            }
            case 2 -> {
                yield 42;
            }
            default -> 0;
        };
        System.out.println("    Switch with yield: " + value);

        // Pattern matching switch (Java 21+)
        Object obj = "hello";
        String result = switch (obj) {
            case String s -> "String: " + s.toUpperCase();
            case Integer i -> "Integer: " + i;
            default -> "Other";
        };
        System.out.println("    Pattern switch: " + result);
    }

    // ============================================================
    // Main demo
    // ============================================================

    public static void main(String[] args) {
        System.out.println("=== JLS Chapter 15: Expressions Deep Dive ===\n");

        System.out.println("--- Evaluation Order (JLS §15.1) ---");
        evaluationOrderDemo();

        System.out.println("\n--- Primary Expressions (JLS §15.3-15.4) ---");
        primaryExpressionsDemo();

        System.out.println("\n--- Class Instance Creation (JLS §15.5-15.7) ---");
        classInstanceCreationDemo();

        System.out.println("\n--- Array Creation/Access (JLS §15.8-15.9) ---");
        arrayCreationAccessDemo();

        System.out.println("\n--- Field Access/Method Invocation (JLS §15.10-15.11) ---");
        fieldAccessMethodInvocationDemo();

        System.out.println("\n--- Method Invocation Resolution (JLS §15.12) ---");
        methodInvocationDemo();

        System.out.println("\n--- Method References (JLS §15.13) ---");
        methodReferenceDemo();

        System.out.println("\n--- Postfix/Unary/Cast (JLS §15.14-15.16) ---");
        postfixUnaryCastDemo();

        System.out.println("\n--- Arithmetic/Logical Operators (JLS §15.17-15.24) ---");
        operatorsDemo();

        System.out.println("\n--- Conditional Operator (JLS §15.25) ---");
        conditionalOperatorDemo();

        System.out.println("\n--- Assignment Operators (JLS §15.26) ---");
        assignmentOperatorsDemo();

        System.out.println("\n--- Lambda Expressions (JLS §15.27) ---");
        lambdaExpressionsDemo();

        System.out.println("\n--- Constant Expressions (JLS §15.28) ---");
        constantExpressionDemo();

        System.out.println("\n--- Switch Expressions (JLS §15.29) ---");
        switchExpressionDemo();
    }
}