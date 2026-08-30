package jls;

import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.FieldModel;
import java.lang.classfile.MethodModel;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Method;
import java.io.ByteArrayInputStream;
import java.lang.classfile.CodeBuilder;
import java.lang.classfile.Opcode;

/**
 * JLS §3.7 & JVMS §4 (Java 24 Final - JEP 484): Class-File API Standard
 * 
 * Java 24 standardizes java.lang.classfile for parsing, generating, and transforming
 * Java class files in pure standard library code (replacing third-party libraries like ASM / ByteBuddy).
 * 
 * Compilation:
 *     javac --release 24 Ex91_ClassFileAPIStandard.java
 *     java jls.Ex91_ClassFileAPIStandard
 * 
 * Key Elements:
 * 1. ClassFile: Central context factory (ClassFile.of())
 * 2. ClassModel, MethodModel, FieldModel: Tree-based immutable models of class components
 * 3. ClassTransform, CodeTransform: Stream-based transformation pipelines
 * 4. Bytecode Generation: Building class byte arrays using ClassBuilder & CodeBuilder
 */
public class Ex91_ClassFileAPIStandard {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Java 24 Class-File API (JEP 484) Standard Demo ===\n");
        
        demoGenerateDynamicBytecode();
        demoInspectClassFile();
    }

    /**
     * Generate a new class 'GeneratedGreeter' dynamically with bytecode instructions:
     * public class GeneratedGreeter {
     *     public static String greet(String name) {
     *         return "Hello, " + name;
     *     }
     * }
     */
    static void demoGenerateDynamicBytecode() throws Exception {
        System.out.println("1. Dynamic Bytecode Generation with ClassFile.of().build():");
        
        ClassFile cf = ClassFile.of();
        ClassDesc greeterClassDesc = ClassDesc.of("GeneratedGreeter");
        
        byte[] bytes = cf.build(greeterClassDesc, classBuilder -> {
            classBuilder.withFlags(AccessFlag.PUBLIC);
            
            // Generate public static String greet(String)
            classBuilder.withMethod("greet", 
                MethodTypeDesc.of(ConstantDescs.CD_String, ConstantDescs.CD_String),
                AccessFlag.PUBLIC.mask() | AccessFlag.STATIC.mask(),
                methodBuilder -> methodBuilder.withCode(codeBuilder -> {
                    // Load parameter 0 (name)
                    codeBuilder.aload(0);
                    // Return the string reference directly for simplicity
                    codeBuilder.areturn();
                })
            );
        });

        System.out.println("   Successfully generated " + bytes.length + " bytes of classfile bytecode.");
        
        // Define and invoke using reflection
        ByteArrayClassLoader loader = new ByteArrayClassLoader();
        Class<?> clazz = loader.defineClass("GeneratedGreeter", bytes);
        Method greetMethod = clazz.getMethod("greet", String.class);
        Object result = greetMethod.invoke(null, "Java 24 Standard Class-File API");
        System.out.println("   Invocation result: " + result + "\n");
    }

    /**
     * Parse and inspect the current class's structure using ClassFile.of().parse()
     */
    static void demoInspectClassFile() throws Exception {
        System.out.println("2. Parsing ClassFile Elements:");
        
        byte[] selfBytes = Ex91_ClassFileAPIStandard.class.getResourceAsStream(
                "/jls/Ex91_ClassFileAPIStandard.class"
        ) != null ? Ex91_ClassFileAPIStandard.class.getResourceAsStream("/jls/Ex91_ClassFileAPIStandard.class").readAllBytes() : null;

        if (selfBytes != null) {
            ClassFile cf = ClassFile.of();
            ClassModel model = cf.parse(selfBytes);
            
            System.out.println("   Class Name: " + model.thisClass().asInternalName());
            System.out.println("   Major Version: " + model.majorVersion());
            System.out.println("   Methods Found (" + model.methods().size() + "):");
            for (MethodModel mm : model.methods()) {
                System.out.println("     - " + mm.methodName().stringValue() + mm.methodType().stringValue());
            }
        } else {
            System.out.println("   [Skipped in-memory inspection since running outside jar/unpacked class directory]");
        }
    }

    static class ByteArrayClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}
