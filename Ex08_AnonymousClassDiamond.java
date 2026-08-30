package jls;

/**
 * JLS 8/50: Java 9 - Anonymous Classes with Diamond Operator (JLS §15.9.5)
 * Demonstrates inferring type arguments for generic anonymous classes when type is denotable.
 */
public class Ex08_AnonymousClassDiamond {

    interface Handler<T> {
        void handle(T item);
    }

    public static void main(String[] args) {
        // JLS §15.9.5: Diamond operator inside anonymous class creation expression
        Handler<String> stringHandler = new Handler<>() {
            @Override
            public void handle(String item) {
                System.out.println("Handled item: " + item.toUpperCase());
            }
        };

        stringHandler.handle("java 9 diamond");
    }
}
