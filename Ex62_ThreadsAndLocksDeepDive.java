/**
 * JLS Chapter 17: Threads and Locks (Deep Dive)
 *
 * Demonstrates:
 * - JLS §17.1: Synchronization
 * - JLS §17.2: Wait Sets and Notification (wait, notify, notifyAll)
 * - JLS §17.3: Sleep and Yield (Thread.sleep, Thread.yield)
 * - JLS §17.4: Memory Model (happens-before, visibility, atomicity)
 * - JLS §17.4.1-17.4.5: Happens-before order, synchronization order, volatile, final fields
 * - JLS §17.5: final Field Semantics
 * - JLS §17.6: Word Tearing (long/double non-atomic)
 * - JLS §17.7: Non-atomic Treatment of double and long
 */
public class Ex62_ThreadsAndLocksDeepDive {

    // ============================================================
    // JLS §17.1: Synchronization
    // ============================================================

    // Intrinsic lock (monitor) - every object has one
    static class Counter {
        private int count = 0;

        // Synchronized method - locks on 'this'
        public synchronized void increment() {
            count++;
        }

        // Synchronized block - explicit lock object
        private final Object lock = new Object();
        public void incrementWithBlock() {
            synchronized (lock) {
                count++;
            }
        }

        // Static synchronized - locks on Class object
        private static int staticCount = 0;
        public static synchronized void incrementStatic() {
            staticCount++;
        }

        public int getCount() { return count; }
        public static int getStaticCount() { return staticCount; }
    }

    static void synchronizationDemo() throws InterruptedException {
        System.out.println("  Synchronization (JLS §17.1):");

        Counter counter = new Counter();
        int threads = 10;
        int incrementsPerThread = 1000;

        Thread[] workers = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            });
        }

        for (Thread t : workers) t.start();
        for (Thread t : workers) t.join();

        System.out.println("    Expected: " + (threads * incrementsPerThread) + ", Actual: " + counter.getCount());

        // Synchronized block
        Counter counter2 = new Counter();
        Thread[] workers2 = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            workers2[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter2.incrementWithBlock();
                }
            });
        }
        for (Thread t : workers2) t.start();
        for (Thread t : workers2) t.join();
        System.out.println("    With block: Expected: " + (threads * incrementsPerThread) + ", Actual: " + counter2.getCount());
    }

    // ============================================================
    // JLS §17.2: Wait Sets and Notification
    // ============================================================

    static class BoundedBuffer {
        private final Object[] buffer;
        private int count = 0, head = 0, tail = 0;

        BoundedBuffer(int capacity) { buffer = new Object[capacity]; }

        public synchronized void put(Object item) throws InterruptedException {
            while (count == buffer.length) {
                wait();  // Releases lock, waits for notification
            }
            buffer[tail] = item;
            tail = (tail + 1) % buffer.length;
            count++;
            notifyAll();  // Wakes all waiting threads
        }

        public synchronized Object take() throws InterruptedException {
            while (count == 0) {
                wait();
            }
            Object item = buffer[head];
            buffer[head] = null;
            head = (head + 1) % buffer.length;
            count--;
            notifyAll();
            return item;
        }
    }

    static void waitNotifyDemo() throws InterruptedException {
        System.out.println("  Wait/Notify (JLS §17.2):");

        BoundedBuffer buffer = new BoundedBuffer(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    buffer.put("Item " + i);
                    System.out.println("    Produced: Item " + i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    String item = (String) buffer.take();
                    System.out.println("    Consumed: " + item);
                    Thread.sleep(15);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

    // ============================================================
    // JLS §17.3: Sleep and Yield
    // ============================================================

    static void sleepYieldDemo() throws InterruptedException {
        System.out.println("  Sleep and Yield (JLS §17.3):");

        long start = System.currentTimeMillis();
        Thread.sleep(100);  // Sleep for ~100ms
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("    Thread.sleep(100) took ~" + elapsed + "ms");

        // Yield - hints scheduler to run other threads
        Thread.yield();
        System.out.println("    Thread.yield() called");
    }

    // ============================================================
    // JLS §17.4: Memory Model - Happens-Before
    // ============================================================

    // Key happens-before rules:
    // 1. Program order: each action happens-before subsequent actions in same thread
    // 2. Monitor lock: unlock happens-before subsequent lock
    // 3. Volatile: write happens-before subsequent read
    // 4. Thread start: start() happens-before thread actions
    // 5. Thread join: thread actions happen-before join() returns
    // 6. Interrupt: interrupt() happens-before interrupted thread detects it
    // 7. Final fields: constructor end happens-before final field reads
    // 8. Transitivity: if A hb B and B hb C, then A hb C

    static class HappensBeforeDemo {
        // Without synchronization - visibility not guaranteed
        int nonVolatile = 0;
        boolean ready = false;

        // With volatile - write happens-before read
        volatile int volatileField = 0;
        volatile boolean volatileReady = false;

        // Final field - safely published
        final int finalField;

        HappensBeforeDemo(int val) { this.finalField = val; }

        void writer() {
            nonVolatile = 42;
            ready = true;  // No happens-before!

            volatileField = 42;
            volatileReady = true;  // volatile write hb volatile read
        }

        void reader() {
            if (ready) {
                // May see 0 or 42 - no guarantee!
                System.out.println("    Non-volatile: ready=" + ready + ", value=" + nonVolatile);
            }
            if (volatileReady) {
                // Guaranteed to see 42!
                System.out.println("    Volatile: ready=" + volatileReady + ", value=" + volatileField);
            }
            // Final field always visible after constructor
            System.out.println("    Final field: " + finalField);
        }
    }

    static void happensBeforeDemo() throws InterruptedException {
        System.out.println("  Happens-Before (JLS §17.4):");

        // Demonstrate volatile happens-before
        HappensBeforeDemo demo = new HappensBeforeDemo(100);

        Thread writer = new Thread(demo::writer);
        Thread reader = new Thread(demo::reader);

        writer.start();
        reader.start();
        writer.join();
        reader.join();

        // Thread start/join happens-before
        System.out.println("    Thread start hb thread actions");
        System.out.println("    Thread actions hb join() return");
    }

    // ============================================================
    // JLS §17.5: final Field Semantics
    // ============================================================

    static class FinalFieldSemantics {
        final int finalField;
        int regularField;

        FinalFieldSemantics(int f, int r) {
            this.finalField = f;
            this.regularField = r;
        }

        // Final field guarantees:
        // 1. Constructor end hb final field reads (if constructor doesn't leak 'this')
        // 2. No reordering of final field writes with constructor end
    }

    static void finalFieldDemo() {
        System.out.println("  Final Field Semantics (JLS §17.5):");
        System.out.println("    Final fields safely published after constructor");
        System.out.println("    No happens-before needed for final field visibility");
        System.out.println("    BUT: constructor must not leak 'this' (publish before complete)");
    }

    // ============================================================
    // JLS §17.6-17.7: Word Tearing (long/double non-atomic)
    // ============================================================

    // On 32-bit JVMs, long/double writes may not be atomic
    // (torn reads/writes - see half-old, half-new value)
    // On 64-bit JVMs, usually atomic but not guaranteed by JLS

    static class WordTearingDemo {
        // volatile guarantees atomicity for long/double
        volatile long volatileLong = 0;
        long nonVolatileLong = 0;

        void writer() {
            for (int i = 0; i < 100000; i++) {
                volatileLong = i;
                nonVolatileLong = i;
            }
        }

        void reader() {
            long prevVolatile = -1;
            long prevNonVolatile = -1;
            for (int i = 0; i < 100000; i++) {
                long v = volatileLong;
                long nv = nonVolatileLong;
                // Check for tearing (impossible for volatile, possible for non-volatile on 32-bit)
                if (v != prevVolatile && v != prevVolatile + 1) {
                    System.out.println("    Volatile tear detected: " + prevVolatile + " -> " + v);
                }
                if (nv != prevNonVolatile && nv != prevNonVolatile + 1) {
                    System.out.println("    Non-volatile tear: " + prevNonVolatile + " -> " + nv);
                }
                prevVolatile = v;
                prevNonVolatile = nv;
            }
        }
    }

    static void wordTearingDemo() {
        System.out.println("  Word Tearing (JLS §17.6-17.7):");
        System.out.println("    long/double non-atomic on 32-bit JVMs");
        System.out.println("    volatile guarantees atomic read/write");
        System.out.println("    On 64-bit JVMs usually atomic but not guaranteed by spec");
    }

    // ============================================================
    // Advanced: java.util.concurrent atomic classes
    // ============================================================

    static void atomicClassesDemo() {
        System.out.println("  Atomic Classes (java.util.concurrent.atomic):");

        // AtomicInteger - CAS operations
        java.util.concurrent.atomic.AtomicInteger atomicInt = new java.util.concurrent.atomic.AtomicInteger(0);
        atomicInt.incrementAndGet();
        atomicInt.compareAndSet(1, 2);
        System.out.println("    AtomicInteger: " + atomicInt.get());

        // AtomicReference - for object references
        java.util.concurrent.atomic.AtomicReference<String> atomicRef = new java.util.concurrent.atomic.AtomicReference<>("initial");
        atomicRef.compareAndSet("initial", "updated");
        System.out.println("    AtomicReference: " + atomicRef.get());

        // LongAdder - high contention counter
        java.util.concurrent.atomic.LongAdder adder = new java.util.concurrent.atomic.LongAdder();
        adder.add(5);
        adder.increment();
        System.out.println("    LongAdder: " + adder.sum());

        // StampedLock - optimistic locking
        java.util.concurrent.locks.StampedLock lock = new java.util.concurrent.locks.StampedLock();
        long stamp = lock.tryOptimisticRead();
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                System.out.println("    StampedLock read lock acquired");
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    // ============================================================
    // Thread interruption
    // ============================================================

    static void interruptionDemo() throws InterruptedException {
        System.out.println("  Thread Interruption:");

        Thread t = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("    Working " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("    Interrupted! Cleaning up...");
                Thread.currentThread().interrupt();  // Restore interrupt status
            }
        });

        t.start();
        Thread.sleep(250);
        t.interrupt();  // Request interruption
        t.join();
        System.out.println("    Thread interrupted status: " + t.isInterrupted());
    }

    // ============================================================
    // ThreadLocal and InheritableThreadLocal
    // ============================================================

    static void threadLocalDemo() {
        System.out.println("  ThreadLocal and InheritableThreadLocal:");

        // ThreadLocal - per-thread value
        ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "initial");

        Thread t1 = new Thread(() -> {
            System.out.println("    Thread 1 initial: " + threadLocal.get());
            threadLocal.set("Thread 1 value");
            System.out.println("    Thread 1 after set: " + threadLocal.get());
        });

        Thread t2 = new Thread(() -> {
            System.out.println("    Thread 2 initial: " + threadLocal.get());
            threadLocal.set("Thread 2 value");
            System.out.println("    Thread 2 after set: " + threadLocal.get());
        });

        t1.start();
        t2.start();
        try { t1.join(); t2.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // InheritableThreadLocal - passed to child threads
        InheritableThreadLocal<String> inheritable = new InheritableThreadLocal<>();
        inheritable.set("parent value");

        Thread child = new Thread(() -> {
            System.out.println("    Child thread sees: " + inheritable.get());
        });
        child.start();
        try { child.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // ============================================================
    // Virtual Threads (Java 21+)
    // ============================================================

    static void virtualThreadsDemo() {
        System.out.println("  Virtual Threads (Java 21+, JLS §17):");

        // Create virtual thread
        Thread vThread = Thread.ofVirtual().start(() -> {
            System.out.println("    Running on virtual thread: " + Thread.currentThread());
        });

        try { vThread.join(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // Virtual thread executor
        try (var executor = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> System.out.println("    Virtual thread from executor"));
        }

        System.out.println("    Virtual threads: lightweight, scheduled by JVM");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== JLS Chapter 17: Threads and Locks Deep Dive ===\n");

        System.out.println("--- Synchronization (JLS §17.1) ---");
        synchronizationDemo();

        System.out.println("\n--- Wait/Notify (JLS §17.2) ---");
        waitNotifyDemo();

        System.out.println("\n--- Sleep/Yield (JLS §17.3) ---");
        sleepYieldDemo();

        System.out.println("\n--- Happens-Before (JLS §17.4) ---");
        happensBeforeDemo();

        System.out.println("\n--- Final Field Semantics (JLS §17.5) ---");
        finalFieldDemo();

        System.out.println("\n--- Word Tearing (JLS §17.6-17.7) ---");
        wordTearingDemo();

        System.out.println("\n--- Atomic Classes ---");
        atomicClassesDemo();

        System.out.println("\n--- Thread Interruption ---");
        interruptionDemo();

        System.out.println("\n--- ThreadLocal ---");
        threadLocalDemo();

        System.out.println("\n--- Virtual Threads (Java 21+) ---");
        virtualThreadsDemo();
    }
}