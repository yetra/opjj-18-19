package hr.fer.zemris.java.hw06.crypto;

import java.util.Objects;

public class Util {

    private static final String HEX_DIGITS = "0123456789abcdef"; // TODO change to array for O(1)

    public static byte[] hexToByte(String hex) {
        Objects.requireNonNull(hex);
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex-encoded string length is an odd number.");
        }

        byte[] byteArray = new byte[hex.length() / 2];

        for (int i = 0, length = hex.length(); i < length; i += 2) {
            int firstNibble = hexCharToInt(hex.toLowerCase().charAt(i));
            int secondNibble = hexCharToInt(hex.toLowerCase().charAt(i+1));

            if (firstNibble == -1 || secondNibble == -1) {
                throw new IllegalArgumentException("Hex-encoded string contains invalid characters.");
            }

            byteArray[i / 2] = (byte) ((firstNibble << 4) + secondNibble);
        }

        return byteArray;
    }

    private static int hexCharToInt(char c) {
        return HEX_DIGITS.indexOf(c);
    }

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
