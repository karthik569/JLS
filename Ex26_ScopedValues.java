package jls;

import java.lang.ScopedValue;

/**
 * JLS 26/50: Java 21+ - Scoped Values (JLS §17 Thread Locality)
 * Demonstrates immutable context sharing across threads without standard ThreadLocal overhead.
 */
public class Ex26_ScopedValues {

    public static final ScopedValue<String> CONTEXT_USER = ScopedValue.newInstance();

    public static void printContext() {
        System.out.println("Current Scoped User: " + CONTEXT_USER.get());
    }

    public static void main(String[] args) {
        ScopedValue.where(CONTEXT_USER, "AdminUser").run(() -> {
            printContext();
        });
    }
}
