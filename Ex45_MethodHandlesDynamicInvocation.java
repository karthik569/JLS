package jls;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * JLS 45/50: Java 7/8+ - MethodHandles & Dynamic Invocations (JLS §15.12)
 * Demonstrates strongly typed, direct dynamic method invocation using MethodHandles.
 */
public class Ex45_MethodHandlesDynamicInvocation {

    public String greet(String name) {
        return "Hello " + name + " from MethodHandle";
    }

    public static void main(String[] Throwable) throws Throwable {
        Ex45_MethodHandlesDynamicInvocation target = new Ex45_MethodHandlesDynamicInvocation();

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class, String.class);

        MethodHandle mh = lookup.findVirtual(Ex45_MethodHandlesDynamicInvocation.class, "greet", mt);
        String result = (String) mh.invokeExact(target, "World");

        System.out.println(result);
    }
}
