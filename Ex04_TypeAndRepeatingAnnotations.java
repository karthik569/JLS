package jls;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

/**
 * JLS 4/50: Java 8 - Type Annotations (JLS §9.7.4) & Repeating Annotations (JLS §9.6.3)
 * Demonstrates target element types, type use annotations, and repeating annotation containers.
 */
public class Ex04_TypeAndRepeatingAnnotations {

    @Target(ElementType.TYPE_USE)
    @interface NonNull {}

    @Target(ElementType.METHOD)
    @java.lang.annotation.Repeatable(Schedules.class)
    @interface Schedule {
        String dayOfWeek();
    }

    @Target(ElementType.METHOD)
    @interface Schedules {
        Schedule[] value();
    }

    @Schedule(dayOfWeek = "Monday")
    @Schedule(dayOfWeek = "Friday")
    public void performTask() {
        // JLS §9.7.4: Type annotation in variable declaration
        @NonNull String str = "Annotated type string";
        System.out.println(str);
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Ex04_TypeAndRepeatingAnnotations demo = new Ex04_TypeAndRepeatingAnnotations();
        demo.performTask();

        Schedule[] annotations = demo.getClass()
                .getMethod("performTask")
                .getAnnotationsByType(Schedule.class);
        System.out.println("Schedules count: " + annotations.length);
    }
}
