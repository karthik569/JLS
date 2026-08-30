/**
 * Ex73_MemoryModelVisibilityDeepDive.java
 *
 * This program demonstrates the visibility problems and solutions
 * described in the Java Memory Model (JLS §17.4).
 *
 * Key concepts:
 * 1. Visibility: Changes made by one thread may not be seen by others immediately.
 * 2. Data Race: Two threads accessing the same variable, at least one is a write, and no happens-before.
 * 3. Volatile: Ensures visibility and prevents certain reorderings.
 * 4. Synchronized: Estables happens-before edges via monitor locks.
 */
public class Ex73_MemoryModelVisibilityDeepDive {

    // Non-volatile variable: prone to visibility issues
    private static boolean stopRequested = false;
    // Volatile variable: guarantees visibility
    private static volatile boolean volatileStopRequested = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Memory Model Visibility Demo ---");

        // Scenario 1: Visibility issue with non-volatile
        Thread t1 = new Thread(() -> {
            System.out.println("Worker 1 (non-volatile): Running...");
            while (!stopRequested) {
                // Tight loop: JIT might optimize this to 'if(!stopRequested) while(true)'
                // because stopRequested is not volatile.
            }
            System.out.println("Worker 1 stopped!");
        });

        t1.start();
        Thread.sleep(1000);
        System.out.println("Main: Requesting stop (non-volatile)...");
        stopRequested = true;

        // We wait a bit, but t1 might never stop if the JIT optimized the loop.
        t1.join(2000);
        if (t1.isAlive()) {
            System.out.println("Worker 1 is STILL running (Visibility issue!)");
            t1.interrupt(); // Force stop for demo purposes
        } else {
            System.out.println("Worker 1 stopped.");
        }

        System.out.println("\n--- Now testing with volatile ---");

        // Scenario 2: Guaranteed visibility with volatile
        Thread t2 = new Thread(() -> {
            System.out.println("Worker 2 (volatile): Running...");
            while (!volatileStopRequested) {
                // Volatile read ensures we see the latest write from main thread
            }
            System.out.println("Worker 2 stopped!");
        });

        t2.start();
        Thread.sleep(1000);
        System.out.println("Main: Requesting stop (volatile)...");
        volatileStopRequested = true;

        t2.join();
        System.out.println("Worker 2 stopped as expected.");
    }
}
