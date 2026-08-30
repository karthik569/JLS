/**
 * Ex75_SynchronizationAndMonitors.java
 *
 * This program demonstrates the Java synchronization mechanism and the
 * use of monitor locks as specified in JLS §17.4.
 *
 * Key Concepts:
 * 1. Mutual Exclusion: Only one thread can hold the monitor lock of an object.
 * 2. Synchronized Methods: Lock is acquired on 'this' (for instance methods) or the Class object (for static).
 * 3. Synchronized Blocks: Lock can be acquired on any arbitrary object.
 * 4. Memory Visibility: Entering/exiting a synchronized block creates a happens-before relationship.
 */
public class Ex75_SynchronizationAndMonitors {

    private int counter = 0;
    private final Object lock = new Object();

    // Synchronized instance method: Locks on 'this'
    public synchronized void incrementSynchronized() {
        counter++;
    }

    // Synchronized block: Locks on a specific object 'lock'
    public void incrementBlock() {
        synchronized (lock) {
            counter++;
        }
    }

    // Unsynchronized method: Subject to data races
    public void incrementUnsafe() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Synchronization and Monitor Locks ---");

        // Test 1: Synchronized access
        Ex75_SynchronizationAndMonitors syncDemo = new Ex75_SynchronizationAndMonitors();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) syncDemo.incrementSynchronized();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) syncDemo.incrementSynchronized();
        });
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("Synchronized total (Expected 2000): " + syncDemo.getCounter());

        // Test 2: Unsynchronized access (Data Race)
        Ex75_SynchronizationAndMonitors unsafeDemo = new Ex75_SynchronizationAndMonitors();
        Thread u1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) unsafeDemo.incrementUnsafe();
        });
        Thread u2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) unsafeDemo.incrementUnsafe();
        });
        u1.start(); u2.start();
        u1.join(); u2.join();
        System.out.println("Unsynchronized total (Likely < 2000): " + unsafeDemo.getCounter());
    }
}
