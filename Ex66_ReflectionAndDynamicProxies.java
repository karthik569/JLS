/**
 * Ex66_ReflectionAndDynamicProxies.java
 *
 * This program demonstrates Java's Reflection API and the creation of
 * Dynamic Proxies as described in the Java Language Specification and API.
 *
 * Key concepts:
 * 1. Runtime class inspection via java.lang.reflect.
 * 2. java.lang.reflect.Proxy for creating objects that implement interfaces at runtime.
 * 3. InvocationHandler for intercepting method calls.
 */
import java.lang.reflect.*;
import java.util.*;

public class Ex66_ReflectionAndDynamicProxies {

    // Interface to be proxied
    interface UserService {
        void createUser(String name);
        String getUserName(int id);
    }

    // Concrete implementation (the "Real Subject")
    static class UserServiceImpl implements UserService {
        @Override
        public void createUser(String name) {
            System.out.println("Actual Logic: Creating user " + name);
        }

        @Override
        public String getUserName(int id) {
            return "User_" + id;
        }
    }

    // InvocationHandler to intercept calls to the proxy
    static class LoggingHandler implements InvocationHandler {
        private final Object target;

        public LoggingHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("[LOG] Intercepting call to method: " + method.getName());
            System.out.println("[LOG] Arguments: " + Arrays.toString(args));

            // Execute the actual method on the target object
            Object result = method.invoke(target, args);

            System.out.println("[LOG] Method " + method.getName() + " returned: " + result);
            return result;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Runtime Reflection ---");
        Class<?> clazz = UserServiceImpl.class;
        System.out.println("Class Name: " + clazz.getName());
        System.out.println("Methods:");
        for (Method m : clazz.getDeclaredMethods()) {
            System.out.println(" - " + m.getName() + " returning " + m.getReturnType());
        }

        System.out.println("\n--- Dynamic Proxy ---");
        UserService realService = new UserServiceImpl();

        // Create a proxy that implements UserService and uses LoggingHandler
        UserService proxyService = (UserService) Proxy.newProxyInstance(
                UserService.class.getClassLoader(),
                new Class<?>[]{UserService.class},
                new LoggingHandler(realService)
        );

        // These calls will be intercepted by LoggingHandler
        proxyService.createUser("Alice");
        System.out.println();
        String name = proxyService.getUserName(42);
        System.out.println("Final Result: " + name);
    }
}
