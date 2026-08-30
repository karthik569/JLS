package jls;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * JLS §7.3 & §12.1.4 (Java 23/24 Preview - JEP 477 / JEP 495):
 * Simple Source Files, Instance Main Methods & Module Imports
 * 
 * Evolving from Implicitly Declared Classes (Java 21/22 Preview), Java 24 formalizes
 * "Simple Source Files":
 * 1. Omission of class header: A source file without 'public class Foo' is implicitly an unnamed class.
 * 2. Flexible main() signatures:
 *    - void main()
 *    - void main(String[] args)
 *    - static void main()
 *    - static void main(String[] args)
 * 3. Implicit Imports:
 *    - Automatically imports module java.base ('import module java.base;')
 *    - Automatically imports static methods of java.io.IO (println, print, readln)
 * 
 * Launching directly from source:
 *     java --enable-preview SimpleScript.java
 */
public class Ex95_SimpleSourceFilesAndIO {

    public static void main(String[] args) {
        System.out.println("=== JEP 495: Simple Source Files & IO Demo ===\n");
        
        demoMainMethodResolutionOrder();
        demoIOClassMethods();
        demoSimpleSourceConcept();
    }

    /**
     * JLS §12.1.4: Launch Main Method Selection Order
     * When launching a class, the JVM searches for entry points in this exact priority:
     * 1. static void main(String[] args)
     * 2. static void main()
     * 3. void main(String[] args)  [instantiates class with default constructor]
     * 4. void main()              [instantiates class with default constructor]
     */
    static void demoMainMethodResolutionOrder() {
        System.out.println("1. Entry-Point Method Resolution Hierarchy:");
        System.out.println("   Priority 1: static void main(String[] args)");
        System.out.println("   Priority 2: static void main()");
        System.out.println("   Priority 3: void main(String[] args)  (instance method)");
        System.out.println("   Priority 4: void main()               (instance method)");
        System.out.println("   Instance methods allow accessing instance fields and helper methods without 'static'.\n");
    }

    /**
     * java.io.IO (introduced in JEP 477/495):
     * Provides concise console interaction:
     * - IO.println(Object) -> shortcut for System.out.println
     * - IO.print(Object)   -> shortcut for System.out.print
     * - IO.readln(String)  -> read line from System.in / Console with prompt
     */
    static void demoIOClassMethods() {
        System.out.println("2. Simulated java.io.IO Console Convenience:");
        System.out.println("   - Simple source files allow: 'println(\"Hello World\");'");
        System.out.println("   - Reading input: 'String name = readln(\"Enter username: \");'");
        System.out.println("   - Completely removes the need for Scanner or BufferedReader for scripts.\n");
    }

    /**
     * What a complete Java 24 single-file script looks like:
     */
    static void demoSimpleSourceConcept() {
        System.out.println("3. Example of a Pure Simple Source File (Script.java):");
        System.out.println("   --------------------------------------------------");
        System.out.println("   // No package, no imports, no class boilerplate:");
        System.out.println("   void main() {");
        System.out.println("       List<String> items = List.of(\"Java\", \"24\", \"Simplicity\");");
        System.out.println("       println(\"Welcome: \" + items);");
        System.out.println("   }");
        System.out.println("   --------------------------------------------------");
    }
}
