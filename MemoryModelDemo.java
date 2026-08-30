package jls;

/**
 * Java Language Specification (JLS) - Chapter 17: Threads and Locks
 * 
 * Demonstrates:
 * 1. Memory Model & Happens-Before Relationships (JLS §17.4.5)
 * 2. Volatile Variable Rules & Visibility Guarantees (JLS §17.4.5)
 * 3. Synchronized Blocks & Monitor Lock Release/Acquire (JLS §17.1)
 * 4. Thread Join & Termination Visibility (JLS §17.4.5)
 */
public class MemoryModelDemo {

    // JLS §17.4.5: Writes to a volatile variable happen-before every subsequent read of that volatile variable.
    private static volatile boolean flag = false;
    private static int data = 0; // Non-volatile variable piggybacks on volatile visibility guarantee

    private static int syncData = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        // --- 1. Volatile & Happens-Before Guarantee ---
        Thread writerThread = new Thread(() -> {
            data = 42; // Step 1: Write to non-volatile data
            flag = true; // Step 2: Write to volatile flag (Acts as a release fence)
        });

        Thread readerThread = new Thread(() -> {
            while (!flag) {
                // Busy wait until flag becomes true
            }
            // JLS §17.4.5: Because flag is volatile, reading flag == true guarantees 
            // that 'data = 42' is visible to this thread!
            System.out.println("Volatile Visibility Test - Data: " + data);
        });

        readerThread.start();
        writerThread.start();

        writerThread.join();
        readerThread.join();

        // --- 2. Synchronized Lock Acquisition & Release (JLS §17.1 & §17.4.5) ---
        // An unlock on a monitor happens-before every subsequent lock on that monitor.
        Thread syncWriter = new Thread(() -> {
            synchronized (lock) {
                syncData = 100; // Protected mutation
            } // Unlock happens here
        });

        Thread syncReader = new Thread(() -> {
            synchronized (lock) { // Lock acquired here
                // Guaranteed to see syncData = 100
                System.out.println("Synchronized Visibility Test - Data: " + syncData);
            }
        });

        syncWriter.start();
        syncWriter.join(); // JLS §17.4.5: Thread.join() happens-before any action after join returns
        syncReader.start();
        syncReader.join();
    }
}
