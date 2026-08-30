package jls;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * JLS §17: Synchronization, Monitors & Locking Deep Dive
 * 
 * Comprehensive coverage of synchronization mechanisms in Java:
 * - synchronized blocks and methods (intrinsic locks)
 * - ReentrantLock (explicit locking)
 * - StampedLock (optimistic + read/write)
 * - wait/notify (monitor methods)
 * - volatile variables
 * - Lock striping and concurrent collections
 */
public class Ex85_SynchronizedMonitorsAndLocking {
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Synchronization Deep Dive ===\n");
        
        demoIntrinsicLock();
        demoReentrantLock();
        demoStampedLock();
        demoWaitNotify();
    }
    
    /**
     * JLS §8.4.3.6: synchronized methods (intrinsic lock)
     */
    static void demoIntrinsicLock() throws Exception {
        System.out.println("Demo 1: Intrinsic Lock (synchronized)");
        System.out.println("=".repeat(50));
        
        Counter counter = new Counter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });
        
        t1.start(); t2.start();
        t1.join(); t2.join();
        
        System.out.println("  Final count: " + counter.get() + " (expected 2000)");
        System.out.println();
    }
    
    /**
     * JLS §17: ReentrantLock
     */
    static void demoReentrantLock() throws Exception {
        System.out.println("Demo 2: ReentrantLock (explicit)");
        System.out.println("=".repeat(50));
        
        LockCounter counter = new LockCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });
        
        t1.start(); t2.start();
        t1.join(); t2.join();
        
        System.out.println("  Final count: " + counter.get() + " (expected 2000)");
        System.out.println();
    }
    
    /**
     * JLS §17: StampedLock
     */
    static void demoStampedLock() throws Exception {
        System.out.println("Demo 3: StampedLock (optimistic + read/write)");
        System.out.println("=".repeat(50));
        
        StampedCounter counter = new StampedCounter();
        
        // Write thread
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 100; i++) counter.write(i);
        });
        
        // Read threads
        Thread reader1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                long v = counter.optimisticRead();
                System.out.println("  [O] Read: " + v);
            }
        });
        
        writer.start();
        reader1.start();
        writer.join();
        reader1.join();
        
        System.out.println("  Final value: " + counter.read());
        System.out.println();
    }
    
    /**
     * JLS §17.2: wait() and notify()
     */
    static void demoWaitNotify() throws Exception {
        System.out.println("Demo 4: wait() and notify()");
        System.out.println("=".repeat(50));
        
        MessageQueue queue = new MessageQueue();
        
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                queue.put("Message " + i);
                try { Thread.sleep(100); } catch (Exception e) {}
            }
        });
        
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("  Consumed: " + queue.take());
            }
        });
        
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
        
        System.out.println();
    }
    
    // Intrinsic lock
    static class Counter {
        private int count = 0;
        
        public synchronized void increment() {
            count++;
        }
        
        public synchronized int get() {
            return count;
        }
    }
    
    // Explicit lock
    static class LockCounter {
        private int count = 0;
        private final ReentrantLock lock = new ReentrantLock();
        
        public void increment() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }
        
        public int get() {
            lock.lock();
            try {
                return count;
            } finally {
                lock.unlock();
            }
        }
    }
    
    // Stamped lock
    static class StampedCounter {
        private int value = 0;
        private final StampedLock lock = new StampedLock();
        
        public void write(int v) {
            long stamp = lock.writeLock();
            try {
                value = v;
            } finally {
                lock.unlockWrite(stamp);
            }
        }
        
        public int read() {
            long stamp = lock.readLock();
            try {
                return value;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        
        public int optimisticRead() {
            long stamp = lock.tryOptimisticRead();
            int v = value;
            if (!lock.validate(stamp)) {
                stamp = lock.readLock();
                try {
                    v = value;
                } finally {
                    lock.unlockRead(stamp);
                }
            }
            return v;
        }
    }
    
    // wait/notify queue
    static class MessageQueue {
        private String message;
        private boolean empty = true;
        
        public synchronized void put(String msg) {
            while (!empty) {
                try { wait(); } catch (InterruptedException e) {}
            }
            empty = false;
            message = msg;
            System.out.println("  Produced: " + msg);
            notifyAll();
        }
        
        public synchronized String take() {
            while (empty) {
                try { wait(); } catch (InterruptedException e) {}
            }
            empty = true;
            String msg = message;
            notifyAll();
            return msg;
        }
    }
}
