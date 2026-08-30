/**
 * JLS Chapter 3: Lexical Structure
 *
 * Demonstrates:
 * - JLS §3.8: Identifiers (valid/invalid, Unicode, keywords)
 * - JLS §3.9: Keywords (reserved words, contextual keywords)
 * - JLS §3.10: Literals (integer, floating-point, boolean, character, string, null)
 * - JLS §3.10.1-3.10.5: Integer literals (decimal, hex, binary, octal, underscores)
 * - JLS §3.10.2: Floating-point literals (decimal, hex, underscores, exponents)
 * - JLS §3.10.3: Boolean literals
 * - JLS §3.10.4: Character literals (escape sequences, Unicode escapes)
 * - JLS §3.10.5: String literals (escape sequences, text blocks)
 * - JLS §3.10.6: Text blocks (multiline strings, incidental indentation)
 * - JLS §3.10.7: Escape sequences in text blocks (\s, \<line-terminator>)
 * - JLS §3.11: Separators (parentheses, braces, brackets, semicolon, comma, period)
 * - JLS §3.12: Operators (assignment, arithmetic, relational, logical, bitwise)
 * - JLS §3.9: Contextual keywords (var, yield, record, sealed, permits, non-sealed, module, etc.)
 */
public class Ex51_LexicalStructure {

    // JLS §3.8: Identifiers - must start with letter, $, or _; subsequent chars can be letters, digits, $, _
    // Valid identifiers:
    int validIdentifier = 1;
    int _underscoreStart = 2;
    int $dollarStart = 3;
    int unicodeIdentifiér = 4;  // Unicode letters allowed
    int identi_fier123 = 5;     // Digits allowed after first char

    // Invalid identifiers (compile errors):
    // int 123invalid = 6;      // Cannot start with digit
    // int invalid-identifier = 7; // Hyphen not allowed
    // int class = 8;           // Keyword cannot be identifier

    // JLS §3.9: Keywords - 53 reserved keywords in Java 21+
    // abstract, assert, boolean, break, byte, case, catch, char, class, const,
    // continue, default, do, double, else, enum, exports, extends, final,
    // finally, float, for, goto, if, implements, import, instanceof, int,
    // interface, long, module, native, new, package, private, protected,
    // provides, public, requires, return, short, static, strictfp, super,
    // switch, synchronized, this, throw, throws, transient, try, uses,
    // var, void, volatile, while, _, yield, record, sealed, permits, non-sealed

    // Contextual keywords (valid as identifiers but have special meaning in context):
    int var = 10;        // Allowed as variable name (but not recommended)
    int yield = 20;      // Allowed as variable name
    int record = 30;     // Allowed as variable name
    int sealed = 40;     // Allowed as variable name
    // int _ = 50;       // '_' is a keyword from Java 22+ (unnamed variable)

    // JLS §3.10.1: Integer Literals
    // Decimal: 0, 1, 123, 0 (leading zero is octal in old versions, but not in Java 9+ for decimal)
    int decimalLiteral = 123;
    int decimalWithUnderscores = 1_000_000;  // JLS §3.10.1: Underscores for readability (Java 7+)

    // Hexadecimal: 0x or 0X prefix
    int hexLiteral = 0xFF;           // 255
    int hexWithUnderscores = 0xFF_FF_FF_FF;  // Underscores in hex (Java 7+)

    // Binary: 0b or 0B prefix (Java 7+)
    int binaryLiteral = 0b1010;      // 10
    int binaryWithUnderscores = 0b1111_0000_1111_0000;

    // Octal: leading 0 (legacy, discouraged)
    int octalLiteral = 077;          // 63 decimal

    // Long literals: L or l suffix (uppercase L preferred)
    long longLiteral = 123L;
    long longWithUnderscores = 1_000_000_000_000L;

    // JLS §3.10.2: Floating-Point Literals
    // Decimal floating-point
    double doubleLiteral = 3.14159;
    double doubleWithUnderscores = 1_000_000.000_001;  // Underscores in floating-point (Java 7+)
    double scientificNotation = 1.23e10;      // 1.23 × 10^10
    double negativeExponent = 1.23e-5;        // 1.23 × 10^-5
    double uppercaseExponent = 1.23E+5;       // Uppercase E allowed

    // Float literals: f or F suffix
    float floatLiteral = 3.14f;
    float floatScientific = 1.23e5f;

    // Hexadecimal floating-point (Java 5+): 0x prefix, p/P exponent (base 2)
    double hexFloat = 0x1.921fb54442d18p1;   // ≈ 3.14159 (π)
    float hexFloatF = 0x1.0p0f;              // 1.0f

    // JLS §3.10.3: Boolean Literals
    boolean booleanTrue = true;
    boolean booleanFalse = false;

    // JLS §3.10.4: Character Literals
    // Single quotes, single character or escape sequence
    char simpleChar = 'A';
    char unicodeChar = 'A';           // 'A' via Unicode escape
    char escapeNewline = '\n';             // Line feed
    char escapeTab = '\t';                 // Horizontal tab
    char escapeBackspace = '\b';           // Backspace
    char escapeFormFeed = '\f';            // Form feed
    char escapeCarriageReturn = '\r';      // Carriage return
    char escapeSingleQuote = '\'';         // Single quote
    char escapeDoubleQuote = '\"';         // Double quote
    char escapeBackslash = '\\';           // Backslash
    char octalEscape = '\101';             // Octal escape (up to 3 digits, max \377)
    char unicodeEscape = 'A';         // Unicode escape (exactly 4 hex digits)

    // Supplementary characters (surrogate pairs) - need two chars
    // char supplementary = '😀';  // 😀 - requires two char values

    // JLS §3.10.5: String Literals
    String simpleString = "Hello, World!";
    String withEscapes = "Line 1\nLine 2\tTabbed";
    String withUnicode = "Unicode: ABC";  // "ABC"
    String emptyString = "";

    // JLS §3.10.6: Text Blocks (Java 15+ preview, Java 16+ standard)
    // Multiline strings with automatic incidental whitespace removal
    String textBlock = """
        This is a text block.
        It spans multiple lines.
        Incidental indentation is removed.
        """;

    // Text block with explicit trailing whitespace (using \s escape - Java 15+)
    String textBlockWithTrailingSpaces = """
        Line with trailing spaces:\s
        Next line""";

    // Text block with escaped newline (using \<line-terminator> - Java 15+)
    String textBlockSingleLine = """
        This is actually \
        a single line""";

    // JLS §3.10.7: Null Literal
    String nullLiteral = null;
    Object nullObject = null;

    // JLS §3.11: Separators
    // ( ) { } [ ] ; , . ... ::
    // Used throughout the code above

    // JLS §3.12: Operators
    // Demonstrated throughout expressions below

    public static void main(String[] args) {
        Ex51_LexicalStructure demo = new Ex51_LexicalStructure();

        System.out.println("=== JLS Chapter 3: Lexical Structure Demo ===\n");

        // Integer literals
        System.out.println("--- Integer Literals (JLS §3.10.1) ---");
        System.out.println("Decimal: " + demo.decimalLiteral);
        System.out.println("Decimal with underscores: " + demo.decimalWithUnderscores);
        System.out.println("Hex: 0xFF = " + demo.hexLiteral);
        System.out.println("Hex with underscores: " + demo.hexWithUnderscores);
        System.out.println("Binary: 0b1010 = " + demo.binaryLiteral);
        System.out.println("Binary with underscores: " + demo.binaryWithUnderscores);
        System.out.println("Octal: 077 = " + demo.octalLiteral);
        System.out.println("Long: " + demo.longLiteral);

        // Floating-point literals
        System.out.println("\n--- Floating-Point Literals (JLS §3.10.2) ---");
        System.out.println("Double: " + demo.doubleLiteral);
        System.out.println("Double with underscores: " + demo.doubleWithUnderscores);
        System.out.println("Scientific (e10): " + demo.scientificNotation);
        System.out.println("Scientific (e-5): " + demo.negativeExponent);
        System.out.println("Float: " + demo.floatLiteral);
        System.out.println("Hex float (π): " + demo.hexFloat);

        // Boolean literals
        System.out.println("\n--- Boolean Literals (JLS §3.10.3) ---");
        System.out.println("true: " + demo.booleanTrue);
        System.out.println("false: " + demo.booleanFalse);

        // Character literals
        System.out.println("\n--- Character Literals (JLS §3.10.4) ---");
        System.out.println("Simple char: '" + demo.simpleChar + "'");
        System.out.println("Unicode escape \\u0041: '" + demo.unicodeChar + "'");
        System.out.println("Escape sequences: newline=" + (int)demo.escapeNewline + ", tab=" + (int)demo.escapeTab);
        System.out.println("Octal escape \\101: '" + demo.octalEscape + "'");

        // String literals
        System.out.println("\n--- String Literals (JLS §3.10.5) ---");
        System.out.println("Simple: " + demo.simpleString);
        System.out.println("With escapes: " + demo.withEscapes.replace("\n", "\\n"));
        System.out.println("With Unicode: " + demo.withUnicode);

        // Text blocks
        System.out.println("\n--- Text Blocks (JLS §3.10.6) ---");
        System.out.println("Multiline text block:");
        System.out.println(demo.textBlock);
        System.out.println("With trailing space escape (\\s):");
        System.out.println(demo.textBlockWithTrailingSpaces);
        System.out.println("Single-line text block (escaped newline):");
        System.out.println(demo.textBlockSingleLine);

        // Contextual keywords as identifiers (discouraged but valid)
        System.out.println("\n--- Contextual Keywords as Identifiers (JLS §3.9) ---");
        System.out.println("var = " + demo.var);
        System.out.println("yield = " + demo.yield);
        System.out.println("record = " + demo.record);
        System.out.println("sealed = " + demo.sealed);

        // Operators demonstration
        System.out.println("\n--- Operators (JLS §3.12) ---");
        int a = 10, b = 3;
        System.out.println("Arithmetic: " + a + " + " + b + " = " + (a + b));
        System.out.println("Arithmetic: " + a + " - " + b + " = " + (a - b));
        System.out.println("Arithmetic: " + a + " * " + b + " = " + (a * b));
        System.out.println("Arithmetic: " + a + " / " + b + " = " + (a / b));
        System.out.println("Arithmetic: " + a + " % " + b + " = " + (a % b));
        System.out.println("Relational: " + a + " > " + b + " = " + (a > b));
        System.out.println("Relational: " + a + " == " + b + " = " + (a == b));
        System.out.println("Logical: true && false = " + (true && false));
        System.out.println("Logical: true || false = " + (true || false));
        System.out.println("Bitwise: " + a + " & " + b + " = " + (a & b));
        System.out.println("Bitwise: " + a + " | " + b + " = " + (a | b));
        System.out.println("Bitwise: " + a + " ^ " + b + " = " + (a ^ b));
        System.out.println("Shift: " + a + " << 1 = " + (a << 1));
        System.out.println("Shift: " + a + " >> 1 = " + (a >> 1));
    }
}