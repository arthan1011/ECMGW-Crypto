package ru.atc.sb.ecmgw.crypto.client;

import netscape.javascript.JSObject;
import org.apache.commons.codec.binary.Base64;
import ru.infocrypt.iccryptotools.CmsCertIncludeLength;
import ru.infocrypt.iccryptotools.CmsDataOperation;
import ru.infocrypt.iccryptotools.ICCryptoTools;
import ru.infocrypt.iccryptotools.PrivateKey;

import java.applet.Applet;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            throwJSError(e.getMessage());
            throw new RuntimeException(e);
        }

        byte[] documentContent = Base64.decodeBase64(base64DocumentContent);
        byte[] digitalSignature;
        try {
            installDLL();
            ICCryptoTools cryptoTools = new ICCryptoTools(Config.installBinPath);
            // TODO: похоже что пароль никак не используется
            PrivateKey key = cryptoTools.getPrivateKeyFile(Config.privateKeyPath, "password");

            digitalSignature = cryptoTools.sign(
                    documentContent,
                    key,
                    readUserCert(),
                    Config.trustedPath,
                    Config.crlPath,
                    CmsDataOperation.NotIncludeDataInCms,
                    CmsCertIncludeLength.AllExceptRoot
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Base64.encodeBase64String(digitalSignature);
    }

    private void throwJSError(String message) {
        JSObject window = JSObject.getWindow(this);
        window.eval(String.format("throw new Error('%s')", message));
    }

    /**
     * Подписывает документ, загружая его из файловой системы
     * @param documentFilePath путь до файла документа
     * @return электронная подпись в виде строки Base64
     */
    public String signDocumentFromFile(String documentFilePath) {
        boolean documentFileNotExists = !(new File(documentFilePath)).exists();
        if (documentFileNotExists) {
            throwJSError("Can not find file to sign: " + documentFilePath);
            throw new RuntimeException();
        }

        String base65DocumentContent;
        try {
            base65DocumentContent = Base64.encodeBase64String(Files.readAllBytes(Paths.get(documentFilePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return signDocument(base65DocumentContent);
    }

    byte[] readUserCert() {
        byte[] result;
        try {
            result = Files.readAllBytes(Paths.get(Config.userCert));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
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

        File userCertFile = new File(Config.userCert);
        if (!userCertFile.exists()) {
            throw new IOException("Can not find user certificate file: " + Config.userCert);
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
