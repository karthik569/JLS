package jls;

/**
 * JLS §7.5.5 (Java 23/24 Preview - JEP 476 / JEP 494): Module Import Declarations
 * 
 * Module import declarations allow an entire module's exported packages to be
 * imported in a single statement using the syntax:
 *     import module <module-name>;
 * 
 * Compilation:
 *     javac --release 24 --enable-preview Ex89_ModuleImportDeclarations.java
 *     java --enable-preview jls.Ex89_ModuleImportDeclarations
 * 
 * Key Specification Rules:
 * 1. Scope & Exports: 'import module M;' brings all public top-level types in all
 *    packages exported by module M (and packages transitively exported by modules
 *    required transitively by M) into scope on-demand (type-import-on-demand).
 * 2. Precedence & Shadowing (JLS §6.5):
 *    - Single-type import (import a.b.C;) shadows module imports.
 *    - Same-package declarations shadow module imports.
 *    - If two imported modules export classes with identical simple names,
 *      referencing that simple name results in an ambiguous compilation error,
 *      which must be resolved by an explicit single-type import or fully qualified name.
 * 3. Implicit Module Imports in Simple Source Files (JEP 477/495):
 *    - Simple source files automatically import module java.base.
 */

import java.util.*;
import java.util.stream.*;

public class Ex89_ModuleImportDeclarations {

    public static void main(String[] args) {
        System.out.println("=== JLS §7.5.5: Module Import Declarations Demo ===\n");
        
        demoModuleImportSemantics();
        demoAmbiguityResolution();
        demoTransitiveExports();
    }

    /**
     * JLS §7.5.5: All packages exported by java.base are imported at once.
     * E.g., List (java.util), Function (java.util.function), Path (java.nio.file),
     * CompletableFuture (java.util.concurrent) without multiple import lines.
     */
    static void demoModuleImportSemantics() {
        System.out.println("1. Module-wide On-Demand Type Resolution:");
        
        // Types from java.util, java.util.stream, java.nio.file available with 'import module java.base;'
        List<String> modules = List.of("java.base", "java.logging", "java.desktop", "java.sql");
        
        Map<Integer, List<String>> grouped = modules.stream()
                .collect(Collectors.groupingBy(String::length));
                
        System.out.println("   Grouped modules by length: " + grouped);
        System.out.println("   Benefit: Eliminates long preamble of package imports in beginner/script code.\n");
    }

    /**
     * JLS §7.5.5 & §6.5: Ambiguity resolution between modules.
     * If 'import module java.base;' and 'import module java.desktop;' both export 'List'
     * (java.util.List vs java.awt.List), explicit single-type import resolves ambiguity.
     */
    static void demoAmbiguityResolution() {
        System.out.println("2. Ambiguity & Shadowing Rules:");
        System.out.println("   - If module A exports pkg1.Foo and module B exports pkg2.Foo:");
        System.out.println("     -> Referencing 'Foo' produces compile-time error: 'reference to Foo is ambiguous'");
        System.out.println("   - Resolution rule 1: Add single-type import: 'import pkg1.Foo;'");
        System.out.println("   - Resolution rule 2: Use fully qualified name: 'pkg1.Foo'");
        System.out.println("   - Resolution rule 3: Type in the same compilation unit always shadows module imports.\n");
    }

    /**
     * JLS §7.5.5: Transitive module re-exports.
     * Importing a module also imports exported packages of modules declared 'requires transitive'.
     */
    static void demoTransitiveExports() {
        System.out.println("3. Transitive Module Re-Exports (requires transitive):");
        System.out.println("   - If module 'java.se' declares 'requires transitive java.xml;',");
        System.out.println("     then 'import module java.se;' also imports all packages exported by 'java.xml'.");
        System.out.println("   - Non-exported internal packages (e.g. jdk.internal.*) remain inaccessible.");
    }
}
