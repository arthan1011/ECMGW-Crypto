package ru.atc.sb.ecmgw.crypto.server.webservices;

/**
 * Created by ashamsiev on 14.01.2016
 */

public interface CryptoWebService {

    String verifySignature(
            String docID,
            String base64signature
    );
}
