package ru.atc.sb.ecmgw.crypto.server.rest;

import ru.atc.sb.ecmgw.crypto.server.Config;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by ashamsiev on 11.12.2015
 */

@Path("/")
public class CryptoService {

    @GET
    @Path("/verify")
    public String verify() {
        System.out.println("Verify method");
        return "Verified";
    }

    @GET
    @Path("/binPath")
    public String binPath() {
        return Config.cryptoToolsBinPath;
    }
}
