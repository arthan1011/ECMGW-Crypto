package ru.atc.sb.ecmgw.crypto.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ashamsiev on 04.12.2015
 */
public class Config {
    private static final String userHome = System.getProperty("user.home").replace("\\", "/") + "/";

    public static final String crlPath;
    public static final String installBinPath;
    public static final String privateKeyPath;
    public static final String trustedPath;
    public static final String userCert;

    static {
        Properties props = new Properties();
        try {
            props.load(Config.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        installBinPath = userHome + props.getProperty("path.install.bin");
        trustedPath = userHome + props.getProperty("path.trusted");
        crlPath = userHome + props.getProperty("path.crl");
        privateKeyPath = userHome + props.getProperty("path.private.key");
        userCert = userHome + props.getProperty("path.user.certificate");
    }

}
