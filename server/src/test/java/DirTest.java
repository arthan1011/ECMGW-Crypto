import org.junit.Test;

import java.io.File;
import java.net.URL;

/**
 * Created by ashamsiev on 11.12.2015
 */
public class DirTest {

    @Test
    public void testDir() throws Exception {
        URL url = getClass().getResource("/cryptotools/bin");
        String absolutePath = new File(url.toURI()).getAbsolutePath();
        System.out.println(absolutePath);

    }
}
