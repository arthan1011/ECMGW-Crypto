package ru.atc.sb.ecmgw.crypto.client;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by ashamsiev on 07.12.2015
 */
public class CodecTest {

    @Test
    public void testCodec() throws Exception {
        String originalString = "SampleText";
        System.out.println("Original string: " + originalString);
        byte[] originalBytes = originalString.getBytes();
        System.out.println("Original byte array: " + Arrays.toString(originalBytes));
        String encodedString = Base64.encodeBase64String(originalBytes);
        System.out.println("Encoded string: " + encodedString);
        System.out.println("Decoded byte array: " + Arrays.toString(Base64.decodeBase64(encodedString)));
    }
}
