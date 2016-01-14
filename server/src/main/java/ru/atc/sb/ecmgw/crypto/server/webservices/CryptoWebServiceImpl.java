package ru.atc.sb.ecmgw.crypto.server.webservices;

import ru.atc.sb.ecmgw.crypto.server.service.VerificationService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by ashamsiev on 14.01.2016
 */

@WebService(
        targetNamespace = "http://www.ecmgw.ru/",
        serviceName = "CryptoWebService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class CryptoWebServiceImpl implements CryptoWebService {

    @Override
    @WebMethod
    @WebResult(name = "result")
    public String verifySignature(
            @WebParam(name = "docID") String docID,
            @WebParam(name = "base64signature") String base64signature
    ) {
        boolean verified = VerificationService.verifyDoc(docID, base64signature);
        return verified ? docID + "Verified" : docID + "not Verified";
    }
}
