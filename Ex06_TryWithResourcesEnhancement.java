package jls;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * JLS 6/50: Java 9 - Effectively Final Variables in Try-With-Resources (JLS §14.20.3)
 * Demonstrates referencing existing effectively final variables directly in try-with-resources.
 */
public class Ex06_TryWithResourcesEnhancement {

    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Hello JLS Java 9 Try-With-Resources!");
        BufferedReader bufferedReader = new BufferedReader(reader);

        // JLS §14.20.3: Java 9 allows effectively final resource variables directly in try-with-resources
        try (bufferedReader) {
            System.out.println("Line: " + bufferedReader.readLine());
        } // bufferedReader is automatically closed here
    }
}
