/**
 * JLS Chapter 14: Blocks and Statements (Deep Dive)
 *
 * Demonstrates:
 * - JLS §14.1: Normal and Abrupt Completion
 * - JLS §14.2: Blocks ({ statements })
 * - JLS §14.3: Local Class Declarations
 * - JLS §14.4: Local Variable Declaration Statements (including var)
 * - JLS §14.5: Statements (empty, labeled, expression, if, switch, while, do, for, break, continue, return, throw, synchronized, try)
 * - JLS §14.6: The Empty Statement (;)
 * - JLS §14.7: Labeled Statements
 * - JLS §14.8: Expression Statements
 * - JLS §14.9: The if Statement
 * - JLS §14.10: The switch Statement (traditional, enhanced, pattern matching)
 * - JLS §14.11: Switch Expressions (yield)
 * - JLS §14.12: The while Statement
 * - JLS §14.13: The do Statement
 * - JLS §14.14: The for Statement (basic, enhanced/for-each)
 * - JLS §14.15: The break Statement
 * - JLS §14.16: The continue Statement
 * - JLS §14.17: The return Statement
 * - JLS §14.18: The throw Statement
 * - JLS §14.19: The synchronized Statement
 * - JLS §14.20: The try Statement (with resources, multi-catch)
 * - JLS §14.21: Unreachable Statements
 * - JLS §14.22: Switch Rules (arrow syntax, yield)
 * - JLS §14.30: Pattern Matching (instanceof, switch)
 */
public class Ex60_StatementsDeepDive {

    // ============================================================
    // JLS §14.2: Blocks
    // ============================================================

    static void blockDemo() {
        System.out.println("  Blocks:");
        // Block creates new scope
        {
            int blockVar = 10;
            System.out.println("    Block variable: " + blockVar);
        }
        // blockVar out of scope

        // Blocks can be nested
        {
            int outer = 1;
            {
                int inner = 2;
                System.out.println("    Nested: outer=" + outer + ", inner=" + inner);
            }
            // inner out of scope
        }
    }

    // ============================================================
    // JLS §14.4: Local Variable Declaration Statements
    // ============================================================

    static void localVariableDemo() {
        System.out.println("  Local variables:");

        // Traditional declaration
        int traditional = 10;
        String explicit = "hello";

        // var (local variable type inference) - Java 10+
        var inferred = 20;           // int
        var str = "world";           // String
        var list = java.util.List.of(1, 2, 3);  // List<Integer>

        // var with explicit initialization required
        // var notInitialized;  // Compile error!

        // Final local variables
        final int finalVar = 30;
        final var finalInferred = 40;

        // Effectively final (not declared final but never reassigned)
        int effectivelyFinal = 50;
        // effectivelyFinal = 60;  // Would make it not effectively final

        // Lambda requires effectively final
        Runnable r = () -> System.out.println("    Effectively final: " + effectivelyFinal);

        System.out.println("    traditional=" + traditional + ", inferred=" + inferred);
    }

    // ============================================================
    // JLS §14.5-14.10: Control Flow Statements
    // ============================================================

    // if statement (JLS §14.9)
    static void ifDemo(int value) {
        System.out.println("  if statement:");
        if (value > 0) {
            System.out.println("    Positive");
        } else if (value < 0) {
            System.out.println("    Negative");
        } else {
            System.out.println("    Zero");
        }

        // if as expression (not in Java, but ternary)
        String sign = value > 0 ? "positive" : value < 0 ? "negative" : "zero";
        System.out.println("    Ternary: " + sign);
    }

    // switch statement - traditional (JLS §14.10)
    static void traditionalSwitchDemo(int day) {
        System.out.println("  Traditional switch:");
        switch (day) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                System.out.println("    Weekday");
                break;
            case 6:
            case 7:
                System.out.println("    Weekend");
                break;
            default:
                System.out.println("    Invalid day");
        }
    }

    // switch statement - enhanced (Java 12+)
    static void enhancedSwitchDemo(String day) {
        System.out.println("  Enhanced switch (arrow syntax):");
        switch (day) {
            case "Mon", "Tue", "Wed", "Thu", "Fri" -> System.out.println("    Weekday");
            case "Sat", "Sun" -> System.out.println("    Weekend");
            default -> System.out.println("    Invalid: " + day);
        }
    }

    // switch expression (Java 14+) - returns value
    static String switchExpressionDemo(int day) {
        System.out.println("  Switch expression:");
        return switch (day) {
            case 1, 2, 3, 4, 5 -> "Weekday";
            case 6, 7 -> "Weekend";
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        };
    }

    // switch with yield (for complex cases)
    static String switchWithYield(int day) {
        System.out.println("  Switch with yield:");
        return switch (day) {
            case 1, 2, 3, 4, 5 -> {
                String result = "Weekday";
                yield result;  // yield instead of return
            }
            case 6, 7 -> {
                yield "Weekend";
            }
            default -> {
                yield "Invalid";
            }
        };
    }

    // Pattern matching switch (Java 21+)
    static void patternSwitchDemo(Object obj) {
        System.out.println("  Pattern matching switch (Java 21+):");
        String result = switch (obj) {
            case null -> "null";
            case String s -> "String: " + s;
            case Integer i -> "Integer: " + i;
            case Double d -> "Double: " + d;
            case int[] arr -> "int array length: " + arr.length;
            default -> "Unknown: " + obj.getClass().getSimpleName();
        };
        System.out.println("    " + result);
    }

    // while statement (JLS §14.12)
    static void whileDemo() {
        System.out.println("  while statement:");
        int count = 0;
        while (count < 3) {
            System.out.println("    Count: " + count);
            count++;
        }

        // while with condition
        var list = new java.util.ArrayList<>(java.util.List.of(1, 2, 3));
        while (!list.isEmpty()) {
            System.out.println("    Pop: " + list.remove(list.size() - 1));
        }
    }

    // do-while statement (JLS §14.13)
    static void doWhileDemo() {
        System.out.println("  do-while statement:");
        int i = 0;
        do {
            System.out.println("    i = " + i);
            i++;
        } while (i < 3);

        // Executes at least once
        int j = 5;
        do {
            System.out.println("    This runs once even though j=5");
        } while (j < 3);
    }

    // for statement - basic (JLS §14.14.1)
    static void basicForDemo() {
        System.out.println("  Basic for statement:");
        for (int i = 0; i < 3; i++) {
            System.out.println("    i = " + i);
        }

        // Multiple initialization/update
        for (int i = 0, j = 10; i < j; i++, j--) {
            System.out.println("    i=" + i + ", j=" + j);
        }
    }

    // for statement - enhanced (for-each) (JLS §14.14.2)
    static void enhancedForDemo() {
        System.out.println("  Enhanced for (for-each):");
        int[] array = {1, 2, 3, 4, 5};
        for (int element : array) {
            System.out.println("    Element: " + element);
        }

        for (String s : java.util.List.of("a", "b", "c")) {
            System.out.println("    String: " + s);
        }

        // Works with any Iterable or array
    }

    // break statement (JLS §14.15)
    static void breakDemo() {
        System.out.println("  break statement:");
        // Unlabeled break - exits innermost loop/switch
        for (int i = 0; i < 10; i++) {
            if (i == 5) break;
            System.out.println("    i = " + i);
        }

        // Labeled break - exits labeled statement
        outerLoop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) {
                    System.out.println("    Breaking outer loop at i=1, j=1");
                    break outerLoop;
                }
                System.out.println("    i=" + i + ", j=" + j);
            }
        }
    }

    // continue statement (JLS §14.16)
    static void continueDemo() {
        System.out.println("  continue statement:");
        // Unlabeled continue - skips to next iteration
        for (int i = 0; i < 5; i++) {
            if (i == 2) continue;
            System.out.println("    i = " + i);
        }

        // Labeled continue
        outerLoop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 1) {
                    System.out.println("    Continuing outer at i=" + i);
                    continue outerLoop;
                }
                System.out.println("    i=" + i + ", j=" + j);
            }
        }
    }

    // return statement (JLS §14.17)
    static int returnDemo(int x) {
        System.out.println("  return statement:");
        if (x < 0) return -1;  // Early return
        if (x == 0) return 0;
        return x * 2;
    }

    // throw statement (JLS §14.18)
    static void throwDemo() {
        System.out.println("  throw statement:");
        try {
            throw new IllegalArgumentException("Thrown explicitly");
        } catch (IllegalArgumentException e) {
            System.out.println("    Caught: " + e.getMessage());
        }
    }

    // synchronized statement (JLS §14.19)
    static void synchronizedDemo() {
        System.out.println("  synchronized statement:");
        Object lock = new Object();
        synchronized (lock) {
            System.out.println("    In synchronized block");
        }

        // Synchronized method is equivalent to:
        // public synchronized void method() { ... }
        // ->
        // public void method() { synchronized(this) { ... } }
    }

    // try statement (JLS §14.20)
    static void tryDemo() {
        System.out.println("  try-catch-finally:");

        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("    Caught ArithmeticException");
        } finally {
            System.out.println("    Finally block");
        }

        // try-with-resources (JLS §14.20.3)
        try (var resource = new AutoCloseable() {
            public void close() { System.out.println("    Resource closed"); }
        }) {
            System.out.println("    Using resource");
        }
    }

    // ============================================================
    // JLS §14.21: Unreachable Statements
    // ============================================================

    static void unreachableDemo() {
        System.out.println("  Unreachable statements (compile errors if uncommented):");
        // while (false) { System.out.println("unreachable"); }  // Error!
        // if (false) { System.out.println("unreachable"); }  // Error! (but if (true) is OK)
        // for (; false; ) { }  // Error!
        // throw new RuntimeException(); System.out.println("after throw");  // Error!
        // return; System.out.println("after return");  // Error!
        // System.out.println("This is reachable");
    }

    // ============================================================
    // JLS §14.30: Pattern Matching
    // ============================================================

    // Pattern matching for instanceof (Java 16+)
    static void patternInstanceofDemo(Object obj) {
        System.out.println("  Pattern matching instanceof:");
        if (obj instanceof String s) {
            System.out.println("    String: " + s.toUpperCase());
        } else if (obj instanceof Integer i) {
            System.out.println("    Integer doubled: " + (i * 2));
        } else if (obj instanceof int[] arr) {
            System.out.println("    Array length: " + arr.length);
        } else {
            System.out.println("    Other: " + obj);
        }
    }

    // Pattern matching with && (Java 21+)
    static void patternWithAnd(Object obj) {
        System.out.println("  Pattern with && (Java 21+):");
        if (obj instanceof String s && s.length() > 5) {
            System.out.println("    Long string: " + s);
        }
    }

    // Record patterns (Java 21+)
    record Point(int x, int y) {}
    record ColoredPoint(Point p, String color) {}

    static void recordPatternDemo(Object obj) {
        System.out.println("  Record patterns:");
        if (obj instanceof ColoredPoint(Point p, String color)) {
            System.out.println("    ColoredPoint: " + color + " at (" + p.x() + "," + p.y() + ")");
        } else if (obj instanceof Point p) {
            System.out.println("    Point: (" + p.x() + "," + p.y() + ")");
        }
    }

    // ============================================================
    // JLS §14.1: Normal and Abrupt Completion
    // ============================================================

    static void completionDemo() {
        System.out.println("  Normal vs Abrupt Completion:");

        // Normal completion - reaches end of statement
        {
            int x = 1;
            x = 2;
        } // Normal completion

        // Abrupt completion - break, continue, return, throw
        try {
            // break;  // Would be abrupt completion of loop
            // return; // Abrupt completion of method
            throw new RuntimeException("Abrupt!");
        } catch (RuntimeException e) {
            System.out.println("    Caught abrupt completion");
        }
    }

    // ============================================================
    // Main demo
    // ============================================================

    public static void main(String[] args) {
        System.out.println("=== JLS Chapter 14: Blocks and Statements Deep Dive ===\n");

        System.out.println("--- Blocks (JLS §14.2) ---");
        blockDemo();

        System.out.println("\n--- Local Variables (JLS §14.4) ---");
        localVariableDemo();

        System.out.println("\n--- if Statement (JLS §14.9) ---");
        ifDemo(5);
        ifDemo(-3);
        ifDemo(0);

        System.out.println("\n--- Traditional Switch (JLS §14.10) ---");
        traditionalSwitchDemo(3);
        traditionalSwitchDemo(7);

        System.out.println("\n--- Enhanced Switch (JLS §14.10) ---");
        enhancedSwitchDemo("Mon");
        enhancedSwitchDemo("Sat");

        System.out.println("\n--- Switch Expression (JLS §14.11) ---");
        System.out.println("    " + switchExpressionDemo(2));
        System.out.println("    " + switchExpressionDemo(6));

        System.out.println("\n--- Switch with yield ---");
        System.out.println("    " + switchWithYield(3));
        System.out.println("    " + switchWithYield(7));

        System.out.println("\n--- Pattern Matching Switch (JLS §14.30) ---");
        patternSwitchDemo("hello");
        patternSwitchDemo(42);
        patternSwitchDemo(3.14);
        patternSwitchDemo(new int[]{1,2,3});

        System.out.println("\n--- while (JLS §14.12) ---");
        whileDemo();

        System.out.println("\n--- do-while (JLS §14.13) ---");
        doWhileDemo();

        System.out.println("\n--- Basic for (JLS §14.14.1) ---");
        basicForDemo();

        System.out.println("\n--- Enhanced for (JLS §14.14.2) ---");
        enhancedForDemo();

        System.out.println("\n--- break (JLS §14.15) ---");
        breakDemo();

        System.out.println("\n--- continue (JLS §14.16) ---");
        continueDemo();

        System.out.println("\n--- return (JLS §14.17) ---");
        System.out.println("    returnDemo(5) = " + returnDemo(5));
        System.out.println("    returnDemo(-1) = " + returnDemo(-1));

        System.out.println("\n--- throw (JLS §14.18) ---");
        throwDemo();

        System.out.println("\n--- synchronized (JLS §14.19) ---");
        synchronizedDemo();

        System.out.println("\n--- try (JLS §14.20) ---");
        tryDemo();

        System.out.println("\n--- Unreachable Statements (JLS §14.21) ---");
        unreachableDemo();

        System.out.println("\n--- Pattern Matching instanceof (JLS §14.30) ---");
        patternInstanceofDemo("hello");
        patternInstanceofDemo(42);
        patternInstanceofDemo(new int[]{1,2,3});
        patternInstanceofDemo(3.14);

        System.out.println("\n--- Pattern with && ---");
        patternWithAnd("short");
        patternWithAnd("this is long");

        System.out.println("\n--- Record Patterns ---");
        recordPatternDemo(new ColoredPoint(new Point(1,2), "red"));
        recordPatternDemo(new Point(3,4));

        System.out.println("\n--- Normal/Abrupt Completion (JLS §14.1) ---");
        completionDemo();
    }
}