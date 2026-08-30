/**
 * JLS Chapter 6: Names
 * JLS Chapter 7: Packages and Modules
 *
 * Demonstrates:
 * - JLS §6.1: Declarations (packages, types, members, parameters, local variables)
 * - JLS §6.2: Names and Identifiers (simple names, qualified names)
 * - JLS §6.3: Scope of a Declaration
 * - JLS §6.4: Shadowing and Obscuring
 * - JLS §6.5: Determining the Meaning of a Name
 *   - Package names, type names, expression names, method names
 * - JLS §6.6: Access Control (public, protected, package-private, private)
 * - JLS §7.1: Package Members (classes, interfaces, subpackages)
 * - JLS §7.2: Host Support for Packages
 * - JLS §7.3: Compilation Units (package declaration, imports, type declarations)
 * - JLS §7.4: Package Declarations
 * - JLS §7.5: Import Declarations (single-type, type-import-on-demand, static imports)
 * - JLS §7.6: Top Level Type Declarations
 * - JLS §7.7: Module Declarations (module, requires, exports, opens, uses, provides)
 */
// JLS §7.3: Compilation Units
// This file structure:
// [PackageDeclaration] (optional - defaults to unnamed package)
// [ImportDeclarations]
// [TypeDeclarations]

// JLS §7.4: Package Declarations
// package jls;  // Would be at top if in package

// JLS §7.5: Import Declarations
// Single-type import
import java.util.List;
import java.util.Map;

// Type-import-on-demand (wildcard)
import java.util.*;

// Static imports
import static java.lang.Math.PI;
import static java.lang.Math.max;
import static java.util.Collections.emptyList;

public class Ex54_NamesAndPackages {

    // JLS §6.1: Declarations - this class declares a type name
    // JLS §6.2: Names and Identifiers
    // Simple name: Ex54_NamesAndPackages
    // Qualified name: jls.Ex54_NamesAndPackages (if in package jls)

    // JLS §6.4: Shadowing and Obscuring
    // Field declaration shadows parameter name
    static int x = 100;  // Class variable (static field)

    // Static initializer demonstrates scope
    static {
        // JLS §6.3: Scope - static field x is in scope here
        System.out.println("Static initializer: x = " + x);
    }

    // Instance variable
    int instanceX = 200;

    // JLS §6.4.1: Shadowing
    // Local variable shadows field
    void shadowingDemo(int x) {  // Parameter x shadows field x
        // Actually Java doesn't allow this - parameter and local can't have same name
        // But local CAN shadow field:
        int instanceX = 300;  // Shadows instance field instanceX
        System.out.println("Local instanceX: " + instanceX);
        System.out.println("Field instanceX: " + this.instanceX);
        System.out.println("Parameter x: " + x);
        System.out.println("Field x (static): " + Ex54_NamesAndPackages.x);
    }

    // JLS §6.4.2: Obscuring
    // Type name obscures package name, variable obscures type
    // Example: package name 'java' and class name 'java' in same scope
    // (not easily demonstrable in single file)

    // JLS §6.5: Determining the Meaning of a Name
    // Context determines if name is package, type, expression, or method

    // JLS §6.6: Access Control
    public int publicField = 1;           // Accessible everywhere
    protected int protectedField = 2;     // Same package + subclasses
    int packagePrivateField = 3;          // Same package only (default)
    private int privateField = 4;         // This class only

    // Access control demo
    void accessControlDemo() {
        // All accessible within same class
        System.out.println("Public: " + publicField);
        System.out.println("Protected: " + protectedField);
        System.out.println("Package-private: " + packagePrivateField);
        System.out.println("Private: " + privateField);
    }

    // Nested classes for access control demonstration
    static class NestedStatic {
        void accessOuter() {
            Ex54_NamesAndPackages outer = new Ex54_NamesAndPackages();
            // Static nested can access static members of outer
            System.out.println("Static nested accessing outer.x: " + Ex54_NamesAndPackages.x);
            // Cannot access instance members without instance
            System.out.println("Static nested accessing outer.instanceX: " + outer.instanceX);
            // Can access private static members
            // System.out.println(Ex54_NamesAndPackages.privateField); // Error - instance field
        }
    }

    class InnerClass {
        void accessOuter() {
            // Inner class can access ALL members of outer (including private)
            System.out.println("Inner accessing privateField: " + privateField);
            System.out.println("Inner accessing instanceX: " + instanceX);
        }
    }

    // JLS §7.7: Module Declarations (module-info.java)
    // Not in this file - would be in module-info.java:
    /*
    module com.example.demo {
        requires java.base;
        exports com.example.demo.api;
        opens com.example.demo.internal to com.example.test;
        uses com.example.demo.spi.Service;
        provides com.example.demo.spi.Service with com.example.demo.impl.ServiceImpl;
    }
    */

    // Demonstration of imports
    void importDemo() {
        // Single-type import used
        List<String> list = List.of("a", "b");

        // Wildcard import used
        Map<String, Integer> map = new HashMap<>();

        // Static imports used
        double circumference = 2 * PI * 10;
        int maximum = max(10, 20);
        List<String> empty = emptyList();

        System.out.println("List: " + list);
        System.out.println("Map: " + map);
        System.out.println("2 * PI * 10 = " + circumference);
        System.out.println("max(10, 20) = " + maximum);
        System.out.println("emptyList: " + empty);
    }

    // JLS §6.5.1: Package Names
    // Fully qualified: java.lang.String
    // Simple name: String (after import)

    // JLS §6.5.2: Type Names
    // Simple: String, List
    // Qualified: java.util.List, java.lang.String

    // JLS §6.5.3: Expression Names
    // Variable names, method calls returning values
    void expressionNames() {
        int localVar = 42;        // Expression name: localVar
        String str = "hello";     // Expression name: str
        int len = str.length();   // Expression name: str.length()

        System.out.println("Expression names: localVar=" + localVar + ", str=" + str + ", len=" + len);
    }

    // JLS §6.5.4: Method Names
    // Simple: main, println
    // Qualified: System.out.println, Math.max

    // JLS §6.5.5: Ambiguous Names
    // A name that could be package, type, or expression
    // Resolved by context

    // JLS §6.3: Scope Rules
    // - Class/interface body: scope of members
    // - Method/constructor: scope of parameters + body
    // - Block: scope of local variables
    // - For loop: scope of loop variable
    // - Try-with-resources: scope of resource variables
    // - Catch clause: scope of exception parameter

    void scopeDemo() {
        // Method scope: parameters in scope for entire method
        int methodParam = 10;

        // Block scope
        {
            int blockVar = 20;
            System.out.println("Block var: " + blockVar);
        }
        // blockVar out of scope here

        // For loop scope (Java 8+)
        for (int i = 0; i < 3; i++) {
            // i in scope only in loop
            System.out.println("Loop var i: " + i);
        }
        // i out of scope here

        // Try-with-resources scope
        try (var resource = new AutoCloseable() {
            @Override public void close() { System.out.println("Closing"); }
        }) {
            // resource in scope here
        }
        // resource out of scope (closed)

        // Catch clause scope
        try {
            throw new Exception("test");
        } catch (Exception e) {
            // e in scope only in catch block
            System.out.println("Caught: " + e.getMessage());
        }
        // e out of scope
    }

    // JLS §7.1: Package Members
    // Classes, interfaces, enums, records, annotations, subpackages
    // All top-level types in this compilation unit are package members

    // JLS §7.2: Host Support for Packages
    // File system mapping: package com.example -> com/example/
    // Not directly demonstrable in code

    public static void main(String[] args) {
        Ex54_NamesAndPackages demo = new Ex54_NamesAndPackages();

        System.out.println("=== JLS Chapters 6 & 7: Names, Packages, and Modules Demo ===\n");

        // Shadowing
        System.out.println("--- Shadowing (JLS §6.4) ---");
        demo.shadowingDemo(50);

        // Access control
        System.out.println("\n--- Access Control (JLS §6.6) ---");
        demo.accessControlDemo();

        // Nested class access
        System.out.println("\n--- Nested Class Access ---");
        new NestedStatic().accessOuter();
        demo.new InnerClass().accessOuter();

        // Imports
        System.out.println("\n--- Import Declarations (JLS §7.5) ---");
        demo.importDemo();

        // Expression names
        System.out.println("\n--- Expression Names (JLS §6.5.3) ---");
        demo.expressionNames();

        // Scope
        System.out.println("\n--- Scope Rules (JLS §6.3) ---");
        demo.scopeDemo();

        // Qualified vs simple names
        System.out.println("\n--- Qualified vs Simple Names ---");
        System.out.println("Simple name: String");
        System.out.println("Qualified name: java.lang.String");
        System.out.println("Simple name (imported): List");
        System.out.println("Qualified name: java.util.List");

        // Module system (conceptual)
        System.out.println("\n--- Module System (JLS §7.7) ---");
        System.out.println("Module declarations go in module-info.java");
        System.out.println("module com.example { requires java.base; exports com.example.api; }");
    }
}