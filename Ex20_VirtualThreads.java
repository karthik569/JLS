package jls;

/**
 * JLS 20/50: Java 21 - Virtual Threads Thread Rules (JLS §17 Thread Execution Model)
 * Demonstrates high-throughput lightweight Virtual Threads created via Thread.ofVirtual().
 */
public class Ex20_VirtualThreads {

    public static void main(String[] args) throws InterruptedException {
        // JLS §17: Virtual thread creation adhering to standard Java Thread semantics
        Thread vThread = Thread.ofVirtual().start(() -> {
            System.out.println("Hello from Virtual Thread: " + Thread.currentThread());
        });

        vThread.join();
        System.out.println("Virtual thread execution completed.");
    }
}
