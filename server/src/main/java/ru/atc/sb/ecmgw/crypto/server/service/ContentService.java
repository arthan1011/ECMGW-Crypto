package ru.atc.sb.ecmgw.crypto.server.service;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;
import org.apache.commons.io.IOUtils;
import ru.atc.sb.ecmgw.crypto.server.Config;
import ru.atc.sb.ecmgw.crypto.server.service.utils.CeHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ashamsiev on 11.12.2015
 */
public class ContentService {

    public static byte[] getDocumentContent(String docID) {
        Document document = Factory.Document.fetchInstance(
                getCurrentObjectStore(),
                new Id(docID),
                null
        );
        ContentElementList contentElements = document.get_ContentElements();

        if (contentElements.isEmpty()) {
            throw new RuntimeException("Document content wasn't found");
        }
        ContentTransfer contentTransfer = (ContentTransfer) contentElements.get(0);
        InputStream inputStream = contentTransfer.accessContentStream();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can not read document content", e);
        }
    }

    private static ObjectStore getCurrentObjectStore() {
        return CeHelper.getObjectStore(
                CeHelper.getDomain(
                        CeHelper.createConnection(
                                Config.fileNetWSI,
                                Config.fileNetLogin,
                                Config.fileNetPassword),
                        Config.fileNetDomain),
                Config.fileNetOS);
    }
}
