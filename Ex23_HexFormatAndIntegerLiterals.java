package jls;

import java.util.HexFormat;

/**
 * JLS 23/50: Java 17+ - HexFormat Utility & Primitives Specification (JLS §3.10.1)
 * Demonstrates hexadecimal formatting of numbers and byte arrays.
 */
public class Ex23_HexFormatAndIntegerLiterals {

    public static void main(String[] args) {
        // JLS §3.10.1: Hexadecimal Integer Literals
        int hexVal = 0xDEAD_BEEF;
        System.out.println("Parsed hex literal: " + Integer.toUnsignedString(hexVal));

        HexFormat hexFormat = HexFormat.of().withUpperCase();
        String formatted = hexFormat.formatHex(new byte[]{(byte) 0xDE, (byte) 0xAD, (byte) 0xBE, (byte) 0xEF});
        System.out.println("Formatted hex string: " + formatted);
    }
}
