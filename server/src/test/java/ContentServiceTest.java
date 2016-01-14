import org.junit.Ignore;
import org.junit.Test;
import ru.atc.sb.ecmgw.crypto.server.service.ContentService;

/**
 * Created by ashamsiev on 11.12.2015
 */

@Ignore("Fake Integration test")
public class ContentServiceTest {

    @Test
    public void testGetDocumentContent() throws Exception {
        ContentService.getDocumentContent(null);

    }
}
