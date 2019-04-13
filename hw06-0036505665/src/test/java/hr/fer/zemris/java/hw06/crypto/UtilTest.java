package hr.fer.zemris.java.hw06.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    void testNullHexString() {
        assertThrows(NullPointerException.class, () -> Util.hexToByte(null));
    }

    @Test
    void testEmptyHexString() {
        assertEquals(0, Util.hexToByte("").length);
    }

    @Test
    void testOddSizedHexString() {
        assertThrows(IllegalArgumentException.class, () -> Util.hexToByte("a"));
        assertThrows(IllegalArgumentException.class, () -> Util.hexToByte("abc"));
    }

    @Test
    void testIllegalHexString() {
        String[] illegalHexStrings = {
                "0m",
                "-12",
                "abcdefg",
                "e52217e3ee213Pf1ffdee3a192e2ac7e",
        };

        for (String illegalHexString : illegalHexStrings) {
            assertThrows(IllegalArgumentException.class, () -> Util.hexToByte(illegalHexString));
        }
    }

    @Test
    void testValidHexString() {
        assertArrayEquals(new byte[] {10}, Util.hexToByte("0A"));
        assertArrayEquals(new byte[] {0, 0}, Util.hexToByte("0000"));
        assertArrayEquals(new byte[] {1, -82, 34}, Util.hexToByte("01aE22"));
    }

    @Test
    void testNullByteArray() {
        assertThrows(NullPointerException.class, () -> Util.byteToString(null));
    }

    @Test
    void testEmptyByteArray() {
        assertEquals("", Util.byteToString(new byte[0]));
        assertEquals("00000000", Util.byteToString(new byte[4]));
    }

    @Test
    void testValidByteArray() {
        assertEquals("01ae22", Util.byteToString(new byte[] {1, -82, 34}));
        assertEquals("ff", Util.byteToString(new byte[] {-1}));
        assertEquals("abcd", Util.byteToString(new byte[] {-85, -51}));
    }
}
