/**
 * JLS Chapter 10: Arrays (Deep Dive)
 *
 * Demonstrates:
 * - JLS §10.1: Array Types (component type, dimensions)
 * - JLS §10.2: Array Variables (declaration, initialization)
 * - JLS §10.3: Array Creation (new expressions, anonymous arrays)
 * - JLS §10.4: Array Access (indexing, bounds checking)
 * - JLS §10.5: Array Store Exception (covariance runtime check)
 * - JLS §10.6: Array Initializers (brace syntax, nested)
 * - JLS §10.7: Array Members (length, clone, Object methods)
 * - JLS §10.8: Array Class Objects (runtime representation)
 * - JLS §10.9: An Array of Array Types (multi-dimensional, jagged)
 */
public class Ex57_ArraysDeepDive {

    // ============================================================
    // JLS §10.1: Array Types
    // ============================================================

    // Array type: component type + dimensions
    // int[] - single dimensional
    // int[][] - two dimensional
    // int[][][] - three dimensional

    // Component types can be:
    // - Primitive: int[], double[], boolean[]
    // - Reference: String[], Object[], Runnable[]
    // - Array: int[][], String[][]

    // ============================================================
    // JLS §10.2: Array Variables
    // ============================================================

    // Declaration styles (all equivalent for single-dim):
    int[] array1;           // Preferred: type[] variable
    int array2[];           // C-style: type variable[] (allowed but discouraged)
    int[] array3, array4;   // Multiple declarations

    // For multi-dimensional:
    int[][] matrix1;        // Preferred
    int matrix2[][];        // C-style
    int[] matrix3[], matrix4[][];  // Mixed (discouraged)

    // ============================================================
    // JLS §10.3: Array Creation
    // ============================================================

    public static void arrayCreationDemo() {
        // Array creation expression: new componentType[length]
        int[] arr1 = new int[5];           // Length 5, all zeros
        double[] arr2 = new double[3];     // Length 3, all 0.0
        boolean[] arr3 = new boolean[2];   // Length 2, all false
        String[] arr4 = new String[4];     // Length 4, all null

        // Anonymous array creation (with initializer)
        int[] arr5 = new int[]{1, 2, 3, 4, 5};
        int[] arr6 = {1, 2, 3, 4, 5};      // Shorthand (only in declaration)

        // Multi-dimensional array creation
        int[][] matrix1 = new int[3][4];   // 3 rows, 4 cols each
        int[][] matrix2 = new int[3][];    // 3 rows, null cols (jagged)
        matrix2[0] = new int[2];
        matrix2[1] = new int[4];
        matrix2[2] = new int[1];

        // Anonymous multi-dimensional
        int[][] matrix3 = new int[][]{{1, 2}, {3, 4, 5}, {6}};

        System.out.println("Array creation:");
        System.out.println("  int[5]: " + java.util.Arrays.toString(arr1));
        System.out.println("  Anonymous: " + java.util.Arrays.toString(arr5));
        System.out.println("  Matrix 3x4: " + java.util.Arrays.deepToString(matrix1));
        System.out.println("  Jagged: " + java.util.Arrays.deepToString(matrix2));
        System.out.println("  Anonymous matrix: " + java.util.Arrays.deepToString(matrix3));
    }

    // ============================================================
    // JLS §10.4: Array Access
    // ============================================================

    public static void arrayAccessDemo() {
        int[] arr = {10, 20, 30, 40, 50};

        // Index expression: arr[index]
        // Index must be int (or narrower: byte, short, char - promoted to int)
        int first = arr[0];        // 10
        int last = arr[arr.length - 1];  // 50

        // Bounds checking - ArrayIndexOutOfBoundsException at runtime
        try {
            int oob = arr[5];      // Throws exception
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }

        // Negative index
        try {
            int neg = arr[-1];     // Throws exception
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  Caught negative index exception");
        }

        // Multi-dimensional access
        int[][] matrix = {{1, 2}, {3, 4, 5}};
        int val = matrix[1][2];    // 5 (row 1, col 2)
        System.out.println("  Matrix[1][2] = " + val);

        // Array expressions can be used anywhere an expression is valid
        System.out.println("  arr[0] + arr[1] = " + (arr[0] + arr[1]));
    }

    // ============================================================
    // JLS §10.5: Array Store Exception (Covariance)
    // ============================================================

    // Array covariance: if S is subtype of T, then S[] is subtype of T[]
    // String[] is subtype of Object[]
    // But this requires runtime check on store

    public static void arrayStoreExceptionDemo() {
        String[] strings = new String[3];
        Object[] objects = strings;  // Valid: String[] -> Object[]

        objects[0] = "hello";        // Valid: String -> Object
        System.out.println("  Store String: OK");

        try {
            objects[1] = new Object();  // ArrayStoreException! Object is not String
        } catch (ArrayStoreException e) {
            System.out.println("  ArrayStoreException caught: " + e.getMessage());
        }

        // This also applies to interfaces
        Runnable[] runnables = new Runnable[2];
        Object[] objArray = runnables;
        objArray[0] = (Runnable) () -> System.out.println("Runnable");  // OK - cast to functional interface
        try {
            objArray[1] = "not a Runnable";  // ArrayStoreException
        } catch (ArrayStoreException e) {
            System.out.println("  ArrayStoreException on interface array");
        }

        // Primitive arrays are NOT covariant
        // int[] is NOT subtype of Object[]
        // int[] ints = new int[3];
        // Object[] obj = ints;  // Compile error!

        // But int[] IS subtype of Cloneable and Serializable
        Cloneable cloneable = new int[3];  // Valid
        java.io.Serializable serializable = new int[3];  // Valid - fully qualified
        System.out.println("  int[] implements Cloneable and Serializable");
    }

    // ============================================================
    // JLS §10.6: Array Initializers
    // ============================================================

    public static void arrayInitializerDemo() {
        // Single-dimensional
        int[] primes = {2, 3, 5, 7, 11, 13};
        String[] words = {"hello", "world", "java"};

        // Multi-dimensional (nested initializers)
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // Jagged array initializer
        int[][] jagged = {
            {1, 2},
            {3, 4, 5, 6},
            {7}
        };

        // Empty array initializer
        int[] empty = {};

        // Nested array initializers with different lengths
        String[][] mixed = {
            {"a", "b"},
            {"c"},
            {"d", "e", "f", "g"}
        };

        System.out.println("  Primes: " + java.util.Arrays.toString(primes));
        System.out.println("  Words: " + java.util.Arrays.toString(words));
        System.out.println("  Matrix: " + java.util.Arrays.deepToString(matrix));
        System.out.println("  Jagged: " + java.util.Arrays.deepToString(jagged));
        System.out.println("  Empty: " + java.util.Arrays.toString(empty));
        System.out.println("  Mixed: " + java.util.Arrays.deepToString(mixed));
    }

    // ============================================================
    // JLS §10.7: Array Members
    // ============================================================

    public static void arrayMembersDemo() {
        int[] arr = {1, 2, 3};

        // length field (not a method!) - JLS §10.7
        int len = arr.length;  // 3
        System.out.println("  length field: " + len);

        // clone() method - returns shallow copy
        int[] cloned = arr.clone();
        System.out.println("  clone(): " + java.util.Arrays.toString(cloned));
        System.out.println("  Same array? " + (arr == cloned));  // false
        System.out.println("  Same contents? " + java.util.Arrays.equals(arr, cloned));  // true

        // All Object methods inherited (arrays are Objects)
        System.out.println("  getClass(): " + arr.getClass().getName());
        System.out.println("  toString(): " + arr.toString());  // Not pretty!
        System.out.println("  hashCode(): " + arr.hashCode());
        System.out.println("  equals(arr): " + arr.equals(arr));  // Reference equality

        // Arrays.toString for pretty printing
        System.out.println("  Arrays.toString(): " + java.util.Arrays.toString(arr));
    }

    // ============================================================
    // JLS §10.8: Array Class Objects
    // ============================================================

    public static void arrayClassObjectsDemo() {
        int[] arr1 = new int[5];
        int[] arr2 = new int[10];
        String[] strArr = new String[3];
        int[][] matrix = new int[2][3];

        // All arrays of same component type share same Class object
        Class<?> cls1 = arr1.getClass();
        Class<?> cls2 = arr2.getClass();
        System.out.println("  int[5].getClass() == int[10].getClass(): " + (cls1 == cls2));

        // Different component types have different Class objects
        Class<?> cls3 = strArr.getClass();
        System.out.println("  int[].getClass() == String[].getClass(): " + (cls1 == cls3));

        // Multi-dimensional arrays
        Class<?> cls4 = matrix.getClass();
        System.out.println("  int[][].getClass(): " + cls4.getName());
        System.out.println("  Component type: " + cls4.getComponentType().getName());
        System.out.println("  Component's component: " + cls4.getComponentType().getComponentType().getName());

        // Array class names
        System.out.println("  int[].class.getName(): " + int[].class.getName());
        System.out.println("  String[].class.getName(): " + String[].class.getName());
        System.out.println("  int[][].class.getName(): " + int[][].class.getName());

        // isArray()
        System.out.println("  int[].class.isArray(): " + int[].class.isArray());
        System.out.println("  String.class.isArray(): " + String.class.isArray());
    }

    // ============================================================
    // JLS §10.9: Array of Array Types (Multi-dimensional)
    // ============================================================

    public static void multiDimensionalDemo() {
        // Rectangular array (all rows same length)
        int[][] rectangular = new int[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                rectangular[i][j] = i * 4 + j;
            }
        }

        // Jagged array (rows different lengths)
        int[][] jagged = new int[3][];
        jagged[0] = new int[2];
        jagged[1] = new int[5];
        jagged[2] = new int[1];

        // 3D array
        int[][][] threeD = new int[2][3][4];
        threeD[0][1][2] = 99;

        // Array of arrays of different types (not directly possible in Java)
        // But can use Object[] for heterogeneous
        Object[] heterogeneous = new Object[3];
        heterogeneous[0] = new int[]{1, 2};
        heterogeneous[1] = new String[]{"a", "b"};
        heterogeneous[2] = new double[]{1.1, 2.2};

        System.out.println("  Rectangular: " + java.util.Arrays.deepToString(rectangular));
        System.out.println("  Jagged: " + java.util.Arrays.deepToString(jagged));
        System.out.println("  3D[0][1][2]: " + threeD[0][1][2]);
        System.out.println("  Heterogeneous array: " + java.util.Arrays.toString(heterogeneous));
    }

    // Additional: Array utilities and patterns
    public static void arrayUtilitiesDemo() {
        int[] source = {5, 2, 8, 1, 9};

        // Copying arrays
        int[] dest1 = new int[source.length];
        System.arraycopy(source, 0, dest1, 0, source.length);
        System.out.println("  System.arraycopy: " + java.util.Arrays.toString(dest1));

        int[] dest2 = java.util.Arrays.copyOf(source, source.length);
        System.out.println("  Arrays.copyOf: " + java.util.Arrays.toString(dest2));

        int[] dest3 = java.util.Arrays.copyOfRange(source, 1, 4);
        System.out.println("  Arrays.copyOfRange(1,4): " + java.util.Arrays.toString(dest3));

        // Sorting
        int[] toSort = {5, 2, 8, 1, 9};
        java.util.Arrays.sort(toSort);
        System.out.println("  Sorted: " + java.util.Arrays.toString(toSort));

        // Parallel sorting (Java 8+)
        int[] largeArray = new int[1000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (int) (Math.random() * 1000);
        }
        java.util.Arrays.parallelSort(largeArray);
        System.out.println("  Parallel sort first 10: " +
                java.util.Arrays.toString(java.util.Arrays.copyOfRange(largeArray, 0, 10)));

        // Searching
        int[] sorted = {1, 2, 3, 4, 5};
        int index = java.util.Arrays.binarySearch(sorted, 3);
        System.out.println("  binarySearch(3): " + index);

        // Filling
        int[] toFill = new int[5];
        java.util.Arrays.fill(toFill, 42);
        System.out.println("  fill(42): " + java.util.Arrays.toString(toFill));

        // Comparing
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 2, 3};
        int[] a3 = {1, 2, 4};
        System.out.println("  equals(a1,a2): " + java.util.Arrays.equals(a1, a2));
        System.out.println("  equals(a1,a3): " + java.util.Arrays.equals(a1, a3));
        System.out.println("  mismatch(a1,a3): " + java.util.Arrays.mismatch(a1, a3));

        // Streaming (Java 8+)
        int sum = java.util.Arrays.stream(new int[]{1, 2, 3, 4, 5}).sum();
        System.out.println("  Stream sum: " + sum);
    }

    public static void main(String[] args) {
        System.out.println("=== JLS Chapter 10: Arrays Deep Dive ===\n");

        System.out.println("--- Array Creation (JLS §10.3) ---");
        arrayCreationDemo();

        System.out.println("\n--- Array Access (JLS §10.4) ---");
        arrayAccessDemo();

        System.out.println("\n--- Array Store Exception (JLS §10.5) ---");
        arrayStoreExceptionDemo();

        System.out.println("\n--- Array Initializers (JLS §10.6) ---");
        arrayInitializerDemo();

        System.out.println("\n--- Array Members (JLS §10.7) ---");
        arrayMembersDemo();

        System.out.println("\n--- Array Class Objects (JLS §10.8) ---");
        arrayClassObjectsDemo();

        System.out.println("\n--- Multi-dimensional Arrays (JLS §10.9) ---");
        multiDimensionalDemo();

        System.out.println("\n--- Array Utilities ---");
        arrayUtilitiesDemo();
    }
}