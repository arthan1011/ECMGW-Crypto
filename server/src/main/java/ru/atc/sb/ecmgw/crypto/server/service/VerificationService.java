package ru.atc.sb.ecmgw.crypto.server.service;

import org.apache.commons.codec.binary.Base64;
import ru.atc.sb.ecmgw.crypto.server.Config;
import ru.infocrypt.iccryptotools.CheckResult;
import ru.infocrypt.iccryptotools.ICCryptoTools;

import java.util.List;

/**
 * Created by ashamsiev on 11.12.2015
 */
public class VerificationService {
    public static boolean verifyDoc(String docID, String digitalSignature) {
        // TODO: а вместо самого документа, здесь должен быть хэш этого документа, так как подписывался именно хэш
        byte[] documentContent = ContentService.getDocumentContent(docID);
        byte[] signature = Base64.decodeBase64(digitalSignature);

        List<CheckResult> checkResults;
        try {
            ICCryptoTools cryptoTools = new ICCryptoTools(Config.cryptoToolsBinPath);
            checkResults = cryptoTools.check(
                    documentContent,
                    signature,
                    Config.trustedCertPath,
                    Config.crlPath
            );
        } catch (Exception e) {
            throw new RuntimeException("CryptoTools error", e);
        }

        return checkIfVerified(checkResults);
    }

    private static boolean checkIfVerified(List<CheckResult> checkResults) {
        for (CheckResult checkResult : checkResults) {
            Boolean verified = checkResult.getResult();
            if (!verified) {
                return false;
            }
        }
        return true;
    }
}
