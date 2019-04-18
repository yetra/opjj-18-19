package hr.fer.zemris.java.hw06.crypto;

import java.util.Objects;

/**
 * A class containing utility methods used in {@link Crypto}.
 *
 * @author Bruna Dujmović
 *
 */
public class Util {

    /**
     * A string of all possible hex digits.
     */
    private static final String HEX_DIGITS = "0123456789abcdef";

    /**
     * Converts a given hex-encoded string to an array of bytes. This method supports
     * both uppercase and lowercase letters.
     *
     * @param hex the hex-encoded string to convert
     * @return an array of bytes created from the given hex-encoded string
     */
    public static byte[] hexToByte(String hex) {
        Objects.requireNonNull(hex);
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException(
                    "Hex-encoded string length is an odd number.");
        }

        byte[] byteArray = new byte[hex.length() / 2];

        for (int i = 0, length = hex.length(); i < length; i += 2) {
            int firstNibble = hexCharToInt(hex.toLowerCase().charAt(i));
            int secondNibble = hexCharToInt(hex.toLowerCase().charAt(i+1));

            if (firstNibble == -1 || secondNibble == -1) {
                throw new IllegalArgumentException(
                        "Hex-encoded string contains invalid characters.");
            }

            byteArray[i / 2] = (byte) ((firstNibble << 4) + secondNibble);
        }

        return byteArray;
    }

    /**
     * Returns the integer value of a given hex character or -1 if it is not a valid
     * hex digit.
     *
     * @param c the character to convert
     * @return the integer value of the given hex character or -1 if it is not a valid
     *         hex digit
     */
    private static int hexCharToInt(char c) {
        return HEX_DIGITS.indexOf(c);
    }

    /**
     * Converts a given byte array to a hex-encoded string. The letters in the hex-
     * encoded string will always be lowercase.
     *
     * @param byteArray the byte array to convert
     * @return a hex-ecnoded string created from the given byte-array
     */
    public static String byteToString(byte[] byteArray) {
        Objects.requireNonNull(byteArray);
        char[] hexChars = new char[byteArray.length * 2];

        for (int i = 0; i < byteArray.length; i++) {
            int hexDigits = byteArray[i] & 0xFF;

            hexChars[i * 2] = HEX_DIGITS.charAt(hexDigits >>> 4);
            hexChars[i * 2 + 1] = HEX_DIGITS.charAt(hexDigits & 0x0F);
        }

        return new String(hexChars);
    }
}
