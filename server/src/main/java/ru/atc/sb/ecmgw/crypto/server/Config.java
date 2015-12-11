package ru.atc.sb.ecmgw.crypto.server;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by ashamsiev on 11.12.2015
 */
public class Config {
    public static final String cryptoToolsBinPath;

    static {
        URI uri= null;
        try {
            uri = Config.class.getResource("/cryptotools/bin").toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        cryptoToolsBinPath = new File(uri).getAbsolutePath();
    }
}
