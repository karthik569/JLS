package jls;

/**
 * JLS §3.7 & JavaDoc Spec (Java 23+ - JEP 467): Markdown Documentation Comments
 * 
 * Traditional JavaDoc comments begin with standard slash-star-star blocks and use HTML tags.
 * Java 23 introduces Markdown Documentation Comments, where each line starts with '///'.
 * 
 * Compilation:
 *     javac --release 23 Ex90_MarkdownDocumentationComments.java
 *     javadoc -d doc Ex90_MarkdownDocumentationComments.java
 *     java jls.Ex90_MarkdownDocumentationComments
 * 
 * Key Features of Markdown Doc Comments:
 * 1. Syntax: Every comment line begins with '///' (ignoring preceding whitespace).
 * 2. Markdown vs HTML: Uses CommonMark Markdown formatting instead of awkward HTML markup:
 *    - Headings: `# H1`, `## H2`
 *    - Emphasis: `*italic*`, `**bold**`
 *    - Code Spans and Blocks: `\`code\`` and `\`\`\`java ... \`\`\``
 *    - Lists: Bulleted `- item` and numbered `1. item`
 *    - Markdown Tables and Blockquotes
 * 3. JavaDoc Tags Integration: Supports standard block tags like `@param`, `@return`, `@throws`,
 *    as well as inline tags like `{@link ...}` or shorthand markdown links `[ClassName]`.
 */
public class Ex90_MarkdownDocumentationComments {

    public static void main(String[] args) {
        System.out.println("=== JEP 467: Markdown Documentation Comments Demo ===\n");
        
        Calculator calc = new Calculator("DemoEngine");
        int sum = calc.add(10, 25);
        System.out.println("Result of addition: " + sum);
        System.out.println("Calculator name: " + calc.name());
        
        System.out.println("\n[Note] Inspect the source code of Ex90_MarkdownDocumentationComments.java");
        System.out.println("to see how '///' comments replace HTML doc comments.");
    }
}

/// # Calculator Utility
/// 
/// A modern record demonstrating **Markdown documentation comments** introduced in Java 23.
/// 
/// ## Features
/// - Native Markdown syntax
/// - Code snippets without tedious HTML entity escapes (`&lt;` / `&gt;`)
/// - Integrated `@param` and `@return` documentation
/// 
/// ```java
/// Calculator c = new Calculator("FastCalc");
/// int res = c.add(5, 7);
/// ```
/// 
/// @param name the unique identifier for this calculator instance
record Calculator(String name) {

    /// Adds two 32-bit signed integers and returns the arithmetic sum.
    ///
    /// | Parameter | Type | Description |
    /// |---|---|---|
    /// | `a` | `int` | First augend |
    /// | `b` | `int` | Second addend |
    ///
    /// > **Note:** This method does not perform integer overflow checks.
    ///
    /// @param a first operand
    /// @param b second operand
    /// @return sum of `a` and `b`
    public int add(int a, int b) {
        return a + b;
    }
}
