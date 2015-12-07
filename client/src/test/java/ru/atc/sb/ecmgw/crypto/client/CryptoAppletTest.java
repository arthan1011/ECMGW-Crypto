package ru.atc.sb.ecmgw.crypto.client;

import org.junit.Test;

/**
 * Created by ashamsiev on 07.12.2015
 */
public class CryptoAppletTest {

    @Test
    public void testInstallDLL() throws Exception {
        CryptoClient cryptoClient = new CryptoClient();
        cryptoClient.installDLL();

    }

    @Test
    public void testSignDoc() throws Exception {
        CryptoClient cryptoClient = new CryptoClient();
        System.out.println(cryptoClient.signDocument("U2FtcGxlVGV4dA=="));
    }
}
