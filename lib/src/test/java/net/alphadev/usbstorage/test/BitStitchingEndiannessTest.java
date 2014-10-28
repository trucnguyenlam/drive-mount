package net.alphadev.usbstorage.test;

import net.alphadev.usbstorage.util.BitStitching;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test correct handling of endianness in BitStitching util class.
 *
 * @author Jan Seeger <jan@alphadev.net>
 *
 * Used sample values:
 * http://de.wikipedia.org/wiki/Byte-Reihenfolge#Beispiel:_Speicherung_einer_Integer-Zahl_von_32_Bit_in_4_Bytes
 */
public class BitStitchingEndiannessTest {
    /**
     * Convert little endian byte[] to big endian number (java default).
     */
    @Test
    public void convertsProperlyToLittleEndianByteArray() {
        byte[] expected = new byte[]{0x4d, 0x3c, 0x2b, 0x1a};
        int input = 439041101;
        byte[] result = new byte[4];
        BitStitching.setBytesFromInt(input, result, 0);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void convertsProperlyToInteger() {
        int expected = 512;
        byte[] input = new byte[]{0, 2, 0, 0};
        int result = BitStitching.convertToInt(input, 0);
        Assert.assertEquals(expected, result);
    }
}