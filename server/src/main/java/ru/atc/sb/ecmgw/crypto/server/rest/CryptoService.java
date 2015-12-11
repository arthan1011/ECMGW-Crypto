package ru.atc.sb.ecmgw.crypto.server.rest;

import ru.atc.sb.ecmgw.crypto.server.Config;
import ru.atc.sb.ecmgw.crypto.server.service.VerificationService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Created by ashamsiev on 11.12.2015
 */

@Path("/")
public class CryptoService {

    @GET
    @Path("/verify")
    public String verify(
            @QueryParam("docID") String docID,
            @QueryParam("signature") String signature
    ) {
        boolean verified = VerificationService.verifyDoc(docID, signature);
        return verified ? docID + " Verified" : docID + " not Verified";
    }

    @GET
    @Path("/binPath")
    public String binPath() {
        return Config.cryptoToolsBinPath;
    }
}
