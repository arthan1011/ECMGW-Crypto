package ru.atc.sb.ecmgw.crypto.client;

import netscape.javascript.JSObject;
import org.apache.commons.codec.binary.Base64;
import ru.infocrypt.iccryptotools.ICCryptoTools;

import java.applet.Applet;
import java.awt.*;
import java.io.File;
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
        try {
            checkDigitalSignResources();
        } catch (IOException e) {
            JSObject window = JSObject.getWindow(this);
            window.eval(String.format("throw new Error('%s')", e.getMessage()));
            throw new RuntimeException(e);
        }

        byte[] documentContent = Base64.decodeBase64(base64DocumentContent);
        try {
            installDLL();
            ICCryptoTools cryptoTools = new ICCryptoTools(Config.installBinPath);
        } catch (Exception e) {
            throw new RuntimeException("Что-то пошло не так", e);
        }
        return new String(documentContent) + " maven build";
    }

    /**
     * <p>Проверяет доступны ли ресурсы необходимые для создания электронной подписи:</p>
     * <ul>
     *     <li>директория содержащая цепочку доверенных сертификатов (trusted path)</li>
     *     <li>список отозванных сертификатов (CRL)</li>
     *     <li>файл секретного ключа (private key)</li>
     * </ul>
     * @throws IOException если отсутствует какой-либо из необходимых ресурсов
     */
    private void checkDigitalSignResources() throws IOException {
        File crlFile = new File(Config.crlPath);
        if (!crlFile.exists()) {
            throw new IOException("Can not find CRL file: " + Config.crlPath);
        }

        File trustedDir = new File(Config.trustedPath);
        if (!trustedDir.exists() || !trustedDir.isDirectory() || trustedDir.list().length <= 0) {
            throw new IOException("Trusted certificate directory is empty or not exists: " + Config.trustedPath);
        }

        File privateKeyFile = new File(Config.privateKeyPath);
        if (!privateKeyFile.exists()) {
            throw new IOException("Can not find private key file: " + Config.privateKeyPath);
        }
    }

    public String test() {
        return "Sample Text";
    }

    void installDLL() throws IOException {
        ICCryptoTools cryptoTools = new ICCryptoTools();
        cryptoTools.installFiles(Config.installBinPath);
    }
}
