package ru.atc.sb.ecmgw.crypto.server;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by ashamsiev on 11.12.2015
 */
public class Config {
    private static final String userHome = System.getProperty("user.home").replace("\\", "/") + "/";

    public static final String cryptoToolsBinPath;
    public static final String trustedCertPath;
    public static final String crlPath;
    public static final String fileNetWSI;
    public static final String fileNetLogin;
    public static final String fileNetPassword;
    public static final String fileNetDomain;
    public static final String fileNetOS;

    static {
        URI uri;
        try {
            uri = Config.class.getResource("/cryptotools/bin").toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Properties props = new Properties();
        try {
            props.load(Config.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cryptoToolsBinPath = new File(uri).getAbsolutePath();
        trustedCertPath = userHome + props.getProperty("path.trusted");
        crlPath = userHome + props.getProperty("path.crl");
        fileNetWSI = props.getProperty("fn.wsi");
        fileNetLogin = props.getProperty("fn.login");
        fileNetPassword = props.getProperty("fn.password");
        fileNetDomain = props.getProperty("fn.domain");
        fileNetOS = props.getProperty("fn.domain");
    }
}
