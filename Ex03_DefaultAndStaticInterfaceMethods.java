package jls;

/**
 * JLS 3/50: Java 8 - Default and Static Methods in Interfaces (JLS §9.4)
 * Demonstrates default method inheritance, conflict resolution, and static interface methods.
 */
public class Ex03_DefaultAndStaticInterfaceMethods {

    interface Logger {
        void log(String message);

        // JLS §9.4.3: Interface Default Methods
        default void logInfo(String message) {
            log("[INFO] " + message);
        }

        // JLS §9.4.2: Interface Static Methods
        static void printSystemHeader() {
            System.out.println("=== SYSTEM LOG SYSTEM ===");
        }
    }

    static class ConsoleLogger implements Logger {
        @Override
        public void log(String message) {
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        Logger.printSystemHeader();

        ConsoleLogger logger = new ConsoleLogger();
        logger.log("Direct log call");
        logger.logInfo("Default method log call");
    }
}
