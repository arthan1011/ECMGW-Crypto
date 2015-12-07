package ru.atc.sb.ecmgw.crypto.client;

import org.apache.commons.codec.binary.Base64;
import ru.infocrypt.iccryptotools.ICCryptoTools;

import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

/**
 * Created by ashamsiev on 04.12.2015
 */
public class CryptoClient extends Applet {

    @Override
    public void paint(Graphics g) {
        g.drawString("Ready to sign docs", 10, 20);
        g.drawString("Trusted path: " + Config.trustedPath, 10, 40);
        g.drawString("CRL path: " + Config.crlPath, 10, 60);
        g.drawString("Private key path: " + Config.privateKeyPath, 10, 80);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    /**
     * Подписывает документ переданный в виде строки Base64 и возвращает электронную подпись в виде строки Base64
     * @param base64DocumentContent документ на подписание в виде строки Base64
     * @return электронная подпись в виде строки Base64
     */
    public String signDocument(String base64DocumentContent) {
        byte[] documentContent = Base64.decodeBase64(base64DocumentContent);
        try {
            installDLL();
            ICCryptoTools cryptoTools = new ICCryptoTools(Config.installBinPath);
        } catch (Exception e) {
            throw new RuntimeException("Что-то пошло не так", e);
        }
        return new String(documentContent);
    }

    public String test() {
        return "Sample Text";
    }

    void installDLL() throws IOException {
        ICCryptoTools cryptoTools = new ICCryptoTools();
        cryptoTools.installFiles(Config.installBinPath);
    }
}
