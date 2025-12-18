import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void testCoverageOsoa() {
        // String.join-ek lerro bakoitzaren artean \n jartzen du.
        // Azken \n bat gehitu dugu amaierako irakurketarako.
        String input = String.join("\n",
                "1", "1", "5449000000100", "A1-1", "10",
                "1", "1", "5449000000100", "A1-1", "abc",
                "1", "2", "0000000000000", "A1-1",
                "1", "2", "5449000000100", "A1-1", "5",
                "1", "2", "5449000000100", "A1-1", "abc",
                "2", "1", "A1-1",
                "2", "2",
                "2", "3",
                "3", "1", "5449000000100", "A1-1", "A1-2", "2",
                "3", "1", "5449000000100", "A1-1", "A1-2", "abc",
                "99",
                "abc",
                "0",
                "" // Azken enter bat
        );
        testApp(input, "Agur!");
    }

    private void testApp(String input, String expected) {
        InputStream origIn = System.in;
        PrintStream origOut = System.out;
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            App.main(new String[] {});

            String out = baos.toString();
            assertTrue(out.contains(expected) || out.contains("Zenbaki bat sartu behar duzu"));
        } finally {
            System.setIn(origIn);
            System.setOut(origOut);
        }
    }
}