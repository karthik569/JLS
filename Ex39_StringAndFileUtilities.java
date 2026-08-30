package jls;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JLS 39/50: Java 12+ - Files.mismatch and String Methods (JLS §3.10.5)
 * Demonstrates string formatting methods and file mismatch byte comparison.
 */
public class Ex39_StringAndFileUtilities {

    public static void main(String[] args) throws IOException {
        String original = "  Hello JLS Java 12  \n";
        System.out.println("Indent: " + original.indent(4));
        System.out.println("Transform: " + original.transform(String::trim).transform(String::toUpperCase));
    }
}
