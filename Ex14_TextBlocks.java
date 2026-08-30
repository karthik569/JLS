package jls;

/**
 * JLS 14/50: Java 15 - Text Blocks (JLS §3.10.6 & JLS §3.10.7)
 * Demonstrates multi-line text block literals, stripIndent, and escape sequences (\, \s).
 */
public class Ex14_TextBlocks {

    public static void main(String[] args) {
        // JLS §3.10.6: Text Block Literal
        String json = """
                {
                    "name": "Java Text Block",
                    "version": 15,
                    "features": [
                        "Multi-line string",
                        "Automatic stripIndent"
                    ]
                }
                """;

        System.out.println("JSON Output:\n" + json);

        // Escape sequences: \ (line continuation), \s (explicit space retention)
        String html = """
                <html>\
                <body>\
                    <p>Line 1 with trailing space\s\s\s</p>\
                </body>\
                </html>""";

        System.out.println("HTML Output: " + html);
    }
}
