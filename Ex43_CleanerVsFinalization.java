package jls;

import java.lang.ref.Cleaner;

/**
 * JLS 43/50: Java 9+ - Cleaner API vs Deprecated Finalization (JLS §12.6)
 * Demonstrates memory cleanup using Cleaner instead of Object.finalize().
 */
public class Ex43_CleanerVsFinalization {

    private static final Cleaner cleaner = Cleaner.create();

    static class State implements Runnable {
        @Override
        public void run() {
            System.out.println("Cleaning resource off-heap or native handle safely.");
        }
    }

    static class Resource implements AutoCloseable {
        private final Cleaner.Cleanable cleanable;

        public Resource() {
            this.cleanable = cleaner.register(this, new State());
        }

        @Override
        public void close() {
            cleanable.clean();
        }
    }

    public static void main(String[] args) {
        try (Resource r = new Resource()) {
            System.out.println("Resource active.");
        } // Cleanable invoked here
    }
}
