/**
 * Ex72_ConstantExpressionsAndCompileTime.java
 *
 * This program explores Constant Expressions as specified in JLS §15.28.
 *
 * A constant expression is one that can be evaluated by the compiler
 * at compile-time, resulting in a constant value.
 */
public class Ex72_ConstantExpressionsAndCompileTime {

    // Constant Variables (JLS §4.12.4)
    // must be final and initialized with a constant expression
    public static final int MAX_USERS = 100;
    public static final String APP_NAME = "JLS Demo";
    public static final String VERSION = APP_NAME + " v1.0"; // Constant expression

    // Not a constant variable (initialized at runtime)
    public static final long START_TIME = System.currentTimeMillis();

    public static void main(String[] args) {
        System.out.println("--- Constant Expressions ---");

        // This is a constant expression
        final int result = 10 * 5 + 2;
        System.out.println("Result: " + result);

        // JLS §15.28: Constant expressions are required for case labels
        int day = 2;
        switch (day) {
            case MAX_USERS: // Allowed because MAX_USERS is a constant variable
                System.out.println("Max users case");
                break;
            case 2:
                System.out.println("Day 2 case");
                break;
            default:
                System.out.println("Default case");
        }

        // The following would cause a COMPILE ERROR because START_TIME is not a constant
        // switch (day) {
        //     case START_TIME: System.out.println("Time"); break;
        // }

        System.out.println("\nNote: The Java compiler performs 'constant folding' where");
        System.out.println("constant expressions are replaced by their values in the bytecode.");
    }
}
