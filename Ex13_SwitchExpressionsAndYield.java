package jls;

/**
 * JLS 13/50: Java 14 - Switch Expressions & Yield (JLS §14.11)
 * Demonstrates arrow rules, switch expressions returning values, and the yield statement.
 */
public class Ex13_SwitchExpressionsAndYield {

    public enum Day { MONDAY, TUESDAY, WEDNESDAY, SATURDAY, SUNDAY }

    public static void main(String[] args) {
        Day today = Day.SATURDAY;

        // JLS §14.11.2: Switch expression with arrow syntax
        String typeOfDay = switch (today) {
            case MONDAY, TUESDAY, WEDNESDAY -> "Weekday";
            case SATURDAY, SUNDAY -> "Weekend";
        };
        System.out.println(today + " is a " + typeOfDay);

        // JLS §14.21: Yield statement in colon block switch expression
        int code = switch (today) {
            case MONDAY: yield 1;
            case SATURDAY: {
                System.out.println("Processing Saturday yield block");
                yield 6;
            }
            default: yield 0;
        };
        System.out.println("Day code: " + code);
    }
}
