/**
 * JLS Chapter 18: Type Inference (Deep Dive)
 *
 * Demonstrates:
 * - JLS §18.1: Concepts and Notation (constraints, bounds, inference variables)
 * - JLS §18.2: Reduction (equality, subtyping, type compatibility)
 * - JLS §18.3: Incorporation (adding bounds to inference variables)
 * - JLS §18.4: Resolution (solving inference variables to proper types)
 * - JLS §18.5: Uses of Inference (generic method invocation, diamond, lambda, var)
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Simple @NonNull annotation for demonstration
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.PARAMETER})
@interface NonNull {}

public class Ex63_TypeInferenceDeepDive {

    // ============================================================
    // JLS §18.5.1: Generic Method Invocation Type Inference
    // ============================================================

    // Generic method with type parameter
    static <T> T identity(T t) { return t; }

    static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    static <T> java.util.List<T> asList(T... elements) {
        return java.util.Arrays.asList(elements);
    }

    static <T, U> U convert(T input, java.util.function.Function<T, U> converter) {
        return converter.apply(input);
    }

    static void genericMethodInferenceDemo() {
        System.out.println("  Generic Method Invocation Inference (JLS §18.5.1):");

        // Explicit type arguments
        String s1 = Ex63_TypeInferenceDeepDive.<String>identity("explicit");
        System.out.println("    Explicit: " + s1);

        // Implicit inference from arguments
        String s2 = identity("implicit");  // T inferred as String
        Integer i1 = identity(42);         // T inferred as Integer
        System.out.println("    Implicit String: " + s2);
        System.out.println("    Implicit Integer: " + i1);

        // Inference from bounds (Comparable)
        String maxStr = max("apple", "banana");  // T=String
        Integer maxInt = max(10, 20);            // T=Integer
        System.out.println("    max String: " + maxStr);
        System.out.println("    max Integer: " + maxInt);

        // Varargs inference
        java.util.List<String> strList = asList("a", "b", "c");  // T=String
        java.util.List<Integer> intList = asList(1, 2, 3);       // T=Integer
        System.out.println("    Varargs String: " + strList);
        System.out.println("    Varargs Integer: " + intList);

        // Inference from functional interface (target typing)
        Integer converted = convert("123", Integer::parseInt);  // T=String, U=Integer
        System.out.println("    Function inference: " + converted);

        // Diamond with generic method (Java 9+)
        // var list = Ex63_TypeInferenceDeepDive.<String>asList("x", "y");
    }

    // ============================================================
    // JLS §18.5.2: Diamond Operator Inference
    // ============================================================

    static class Box<T> {
        private T value;
        Box(T value) { this.value = value; }
        T get() { return value; }
    }

    static void diamondInferenceDemo() {
        System.out.println("  Diamond Operator Inference (JLS §18.5.2):");

        // Diamond with constructor - type inferred from target
        Box<String> box1 = new Box<>("hello");  // <> infers String
        Box<Integer> box2 = new Box<>(42);      // <> infers Integer

        // Without target type - infers Object (raw-ish)
        var box3 = new Box<>("raw");  // var infers Box<String>
        Box<?> box4 = new Box<>("wildcard");  // <> infers capture of ?

        // Anonymous class with diamond (Java 9+)
        Comparable<String> comp = new Comparable<>() {
            @Override public int compareTo(String o) { return 0; }
        };

        System.out.println("    Box<String>: " + box1.get());
        System.out.println("    Box<Integer>: " + box2.get());
        System.out.println("    var Box: " + box3.get() + " (type: " + box3.getClass().getTypeName() + ")");
    }

    // ============================================================
    // JLS §18.5.3: Lambda Expression Target Typing
    // ============================================================

    interface StringProcessor { String process(String s); }
    interface IntProcessor { int process(int i); }

    static void lambdaTargetTypingDemo() {
        System.out.println("  Lambda Target Typing (JLS §18.5.3):");

        // Same lambda, different target types
        StringProcessor upper = s -> s.toUpperCase();
        IntProcessor square = i -> i * i;

        System.out.println("    StringProcessor: " + upper.process("hello"));
        System.out.println("    IntProcessor: " + square.process(5));

        // Target typing with method references
        java.util.function.Function<String, Integer> toLength = String::length;
        java.util.function.ToIntFunction<String> toLengthInt = String::length;
        System.out.println("    Function: " + toLength.apply("test"));
        System.out.println("    ToIntFunction: " + toLengthInt.applyAsInt("test"));

        // Poly expressions - lambda type depends on context
        // java.util.List<String> list = java.util.List.of("a", "b");
        // list.forEach(s -> System.out.println(s));  // Consumer<String>
    }

    // ============================================================
    // JLS §18.5.4: Local Variable Type Inference (var)
    // ============================================================

    static void varInferenceDemo() {
        System.out.println("  Local Variable Type Inference - var (JLS §18.5.4):");

        // Basic var
        var i = 10;              // int
        var s = "hello";         // String
        var list = java.util.List.of(1, 2, 3);  // List<Integer>
        var map = java.util.Map.of("a", 1, "b", 2);  // Map<String, Integer>

        // var with generics
        var stringList = new java.util.ArrayList<String>();  // ArrayList<String>
        var wildcardList = new java.util.ArrayList<>();      // ArrayList<Object>

        // var in for-each
        for (var element : java.util.List.of("a", "b", "c")) {
            System.out.println("    for-each var: " + element);
        }

        // var in try-with-resources (Java 9+)
        try (var reader = new java.io.StringReader("test")) {
            System.out.println("    try-with-resources var: " + reader.getClass().getSimpleName());
        } catch (Exception e) {}

        // var with lambda (must have explicit target type)
        // var predicate = s -> s.isEmpty();  // Error: lambda needs target type
        java.util.function.Predicate<String> pred = str -> str.isEmpty();
        var pred2 = (java.util.function.Predicate<String>) str -> str.isEmpty();  // With cast

        // var limitations
        // var uninitialized;  // Error: must initialize
        // var nullVar = null;  // Error: null type not allowed
        // var array = {1, 2, 3};  // Error: array initializer needs target type

        System.out.println("    var int: " + i);
        System.out.println("    var String: " + s);
        System.out.println("    var List<Integer>: " + list);
        System.out.println("    var Map: " + map);
        System.out.println("    var ArrayList<String>: " + stringList.getClass().getSimpleName());
    }

    // ============================================================
    // JLS §18.5.5: Var in Lambda Parameters (Java 11+)
    // ============================================================

    static void varInLambdaDemo() {
        System.out.println("  var in Lambda Parameters (JLS §18.5.5, Java 11+):");

        // var allows annotations on lambda parameters
        java.util.function.BiFunction<String, String, String> annotated =
                (@NonNull var a, @NonNull var b) -> a + b;

        System.out.println("    Annotated lambda: " + annotated.apply("Hello, ", "World!"));

        // Without var, annotations not possible on implicit lambda params
        java.util.function.BiFunction<String, String, String> withoutVar =
                (a, b) -> a + b;
    }

    // ============================================================
    // Advanced: Constraint Formulation
    // ============================================================

    // Inference variables and constraints
    // When you call a generic method, compiler creates inference variables
    // and adds constraints based on arguments and target type

    static <T> void constrain(T t1, T t2) {}  // T must be same for both args

    static void constraintDemo() {
        System.out.println("  Constraint Formulation:");

        // Both args String -> T = String
        constrain("a", "b");

        // Both args Integer -> T = Integer
        constrain(1, 2);

        // String and Integer -> T = Object (common supertype)
        // constrain("a", 1);  // Actually compiles with T=Object & Serializable & Comparable<?>
        // But with bounds: T extends Comparable<T> -> would fail for mixed types

        // Complex constraint: multiple bounds
        // <T extends Comparable<T> & Serializable> T max(T a, T b)
        // Both args must satisfy ALL bounds
    }

    // ============================================================
    // Type Inference with Wildcards
    // ============================================================

    static <T> void consumeList(java.util.List<? extends T> list) {}
    static <T> void produceList(java.util.List<? super T> list) {}

    static void wildcardInferenceDemo() {
        System.out.println("  Wildcard Inference:");

        java.util.List<String> stringList = java.util.List.of("a", "b");
        java.util.List<Integer> intList = java.util.List.of(1, 2);
        java.util.List<Object> objectList = java.util.List.of("a", 1);

        // PECS: Producer Extends, Consumer Super
        consumeList(stringList);  // T inferred as String (or Object)
        consumeList(objectList);  // T inferred as Object

        produceList(stringList);  // T inferred as String
        produceList(objectList);  // T inferred as Object

        // Wildcard capture
        java.util.List<?> wildcardList = stringList;
        // Can't add to wildcard (except null)
        // wildcardList.add("x");  // Error!
    }

    // ============================================================
    // Type Inference in Nested Generic Calls
    // ============================================================

    static <T> java.util.Optional<T> optionalOf(T value) {
        return java.util.Optional.ofNullable(value);
    }

    static <T> java.util.List<T> singletonList(T value) {
        return java.util.Collections.singletonList(value);
    }

    static void nestedInferenceDemo() {
        System.out.println("  Nested Generic Call Inference:");

        // Nested calls - inference flows outward
        var opt = optionalOf("hello");  // T=String -> Optional<String>
        System.out.println("    optionalOf(\"hello\"): " + opt.getClass().getSimpleName());

        var list = singletonList(42);  // T=Integer -> List<Integer>
        System.out.println("    singletonList(42): " + list.getClass().getSimpleName());

        // Complex nesting
        var complex = optionalOf(singletonList("nested"));  // Optional<List<String>>
        System.out.println("    optionalOf(singletonList): " + complex.getClass().getSimpleName());
    }

    // ============================================================
    // Inference Failure Cases
    // ============================================================

    static void inferenceFailureDemo() {
        System.out.println("  Inference Failure Cases:");

        // Ambiguous - no target type, multiple applicable methods
        // System.out.println(max("a", 1));  // Error: no unique max

        // Lambda needs target type
        // var x = () -> "hello";  // Error: lambda needs target type
        // Fix: cast or assign to functional interface
        java.util.function.Supplier<String> supplier = () -> "hello";
        var supplier2 = (java.util.function.Supplier<String>) () -> "hello";

        // Method reference needs target type
        // var mr = String::length;  // Error
        java.util.function.Function<String, Integer> mr = String::length;

        // Diamond with no target type
        // var box = new Box<>();  // Infers Box<Object>
        // Better: explicit type or var with explicit constructor arg
        var box = new Box<>("explicit");  // Box<String>
    }

    // ============================================================
    // Type Inference with Records (Java 16+)
    // ============================================================

    record Pair<T, U>(T first, U second) {}

    static void recordInferenceDemo() {
        System.out.println("  Record Type Inference (Java 16+):");

        // Canonical constructor inference
        var pair1 = new Pair<>("hello", 42);  // Pair<String, Integer>
        var pair2 = new Pair<>(1, 2);         // Pair<Integer, Integer>

        // Component types inferred
        System.out.println("    Pair<String, Integer>: " + pair1.first() + ", " + pair1.second());
        System.out.println("    Pair<Integer, Integer>: " + pair2.first() + ", " + pair2.second());

        // Pattern matching with records (Java 21+)
        Object obj = new Pair<>("test", 123);
        if (obj instanceof Pair(String f, Integer s)) {
            System.out.println("    Pattern match: " + f + ", " + s);
        }
    }

    // ============================================================
    // Target Typing in Expressions
    // ============================================================

    static void targetTypingExpressionsDemo() {
        System.out.println("  Target Typing in Expressions:");

        // Conditional expression target typing
        Object obj = true ? "string" : 42;  // Target: Object
        System.out.println("    Conditional to Object: " + obj.getClass().getSimpleName());

        // Assignment context target typing
        java.util.List<String> list = java.util.List.of("a", "b");  // Target: List<String>

        // Invocation context target typing
        processList(java.util.List.of("x", "y"));  // Target: List<String>

        // Casting context
        Number num = (Integer) 42;  // Target: Number (via cast)
    }

    static void processList(java.util.List<String> list) {
        System.out.println("    Process list: " + list);
    }

    // ============================================================
    // Main demo
    // ============================================================

    public static void main(String[] args) {
        System.out.println("=== JLS Chapter 18: Type Inference Deep Dive ===\n");

        System.out.println("--- Generic Method Invocation (JLS §18.5.1) ---");
        genericMethodInferenceDemo();

        System.out.println("\n--- Diamond Operator (JLS §18.5.2) ---");
        diamondInferenceDemo();

        System.out.println("\n--- Lambda Target Typing (JLS §18.5.3) ---");
        lambdaTargetTypingDemo();

        System.out.println("\n--- var Local Variable Inference (JLS §18.5.4) ---");
        varInferenceDemo();

        System.out.println("\n--- var in Lambda Parameters (JLS §18.5.5) ---");
        varInLambdaDemo();

        System.out.println("\n--- Constraint Formulation ---");
        constraintDemo();

        System.out.println("\n--- Wildcard Inference ---");
        wildcardInferenceDemo();

        System.out.println("\n--- Nested Generic Calls ---");
        nestedInferenceDemo();

        System.out.println("\n--- Inference Failures ---");
        inferenceFailureDemo();

        System.out.println("\n--- Record Inference (Java 16+) ---");
        recordInferenceDemo();

        System.out.println("\n--- Target Typing in Expressions ---");
        targetTypingExpressionsDemo();
    }
}