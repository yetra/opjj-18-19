package hr.fer.zemris.java.tecaj_13.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * A class of utility methods and static variables.
 *
 * @author Bruna Dujmović
 *
 */
public class Utility {

    /**
     * A simple regex for validating e-mails.
     */
    public static final String EMAIL_REGEX = "^[^@]+@[^@]+\\.[^@]+$";

    /**
     * The default algorithm for calculating a file's digest.
     */
    private static final String ALGORITHM = "SHA-256";

    /**
     * A string of all possible hex digits.
     */
    private static final String HEX_DIGITS = "0123456789abcdef";

    /**
     * Calculates the digest of a given string and returns it as a hex string.
     *
     * @param string the string whose digest will be calculated
     * @return the hex string representation of the calculated digest
     * @throws NullPointerException if the obtained digest cannot be represented
     *         as a hex string
     */
    public static String getDigestOf(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(string.getBytes());

            return byteToString(messageDigest.digest());

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm \"" + ALGORITHM + "\".");
            System.exit(1);

        }

        return null;
    }

    /**
     * Converts a given byte array to a hex-encoded string. The letters in the hex-
     * encoded string will always be lowercase.
     *
     * @param byteArray the byte array to convert
     * @return a hex-ecnoded string created from the given byte-array
     */
    private static String byteToString(byte[] byteArray) {
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
