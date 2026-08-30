package jls;

/**
 * JLS 7/50: Java 9 - Private Interface Methods (JLS §9.4)
 * Demonstrates encapsulation of helper code inside interfaces using private instance and static methods.
 */
public class Ex07_PrivateInterfaceMethods {

    interface Calculator {
        default int addEvens(int... numbers) {
            return filterAndSum(numbers, true);
        }

        default int addOdds(int... numbers) {
            return filterAndSum(numbers, false);
        }

        // JLS §9.4: Private instance method inside interface
        private int filterAndSum(int[] numbers, boolean even) {
            int sum = 0;
            for (int num : numbers) {
                if ((num % 2 == 0) == even) {
                    sum += num;
                }
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator() {};
        System.out.println("Sum evens: " + calc.addEvens(1, 2, 3, 4, 5, 6));
        System.out.println("Sum odds: " + calc.addOdds(1, 2, 3, 4, 5, 6));
    }
}
