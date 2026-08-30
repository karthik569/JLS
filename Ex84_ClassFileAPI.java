package jls;

import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.FieldModel;
import java.lang.classfile.MethodModel;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JLS §3.7 (Java 22+ Preview): Class-File API
 * 
 * The Class-File API provides a standard way to parse, generate, and
 * transform Java class files.
 * 
 * Key concepts:
 * - ClassFile: API entry point for parsing/generating class files
 * - ClassModel: represents a parsed class file
 * - ClassTransform: transforms class files during processing
 * - ConstantPool: the class file constant pool
 */
public class Ex84_ClassFileAPI {
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Class-File API Demo ===\n");
        
        demoParseClass();
        demoInspectClass();
    }
    
    /**
     * JLS §3.7: Parse a class file
     */
    static void demoParseClass() throws IOException {
        System.out.println("Demo 1: Parse a Class File");
        System.out.println("=".repeat(50));
        
        // Read class bytes
        byte[] bytes = Files.readAllBytes(Path.of("Ex84_ClassFileAPI.class"));
        
        // Parse the class file
        ClassModel classModel = ClassFile.of().parse(bytes);
        
        System.out.println("  Class: " + classModel.thisClass().asInternalName());
        System.out.println("  Major version: " + classModel.majorVersion());
        System.out.println("  Minor version: " + classModel.minorVersion());
        System.out.println("  Access flags: 0x" + Integer.toHexString(classModel.flags().flagsMask()));
        System.out.println("  Fields: " + classModel.fields().size());
        System.out.println("  Methods: " + classModel.methods().size());
        System.out.println();
    }
    
    /**
     * JLS §3.7: Inspect class members
     */
    static void demoInspectClass() throws IOException {
        System.out.println("Demo 2: Inspect Class Members");
        System.out.println("=".repeat(50));
        
        byte[] bytes = Files.readAllBytes(Path.of("Ex84_ClassFileAPI.class"));
        ClassModel classModel = ClassFile.of().parse(bytes);
        
        // List all fields
        System.out.println("  Fields:");
        for (FieldModel field : classModel.fields()) {
            System.out.println("    - " + field.fieldName().stringValue() 
                + " : " + field.fieldType().stringValue());
        }
        
        // List some methods
        System.out.println("\n  Methods (first 5):");
        int count = 0;
        for (MethodModel method : classModel.methods()) {
            if (count++ >= 5) break;
            System.out.println("    - " + method.methodName().stringValue() 
                + " : " + method.methodType().stringValue());
        }
    }
}
