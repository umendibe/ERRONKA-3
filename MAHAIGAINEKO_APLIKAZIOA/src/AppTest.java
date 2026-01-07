import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void testMainFlujo() {
        String input = "1\n1\n5449000000100\nA1-1\n5\n0\n";
        testAppExecution(input, "Agur!");
    }

    @Test
    void testMenuOkerra() {
        String input = "9\n0\n";
        testAppExecution(input, "Aukera okerra.");
    }

    @Test
    void testLetraMenuan() {
        String input = "abc\n0\n";
        testAppExecution(input, "Zenbaki bat sartu behar duzu.");
    }

    @Test
    void testIrakurriZenbakiaErrorea() {
        String input = "1\nabc\n1\n5449000000100\nA1-1\nabc\n10\n0\n";
        testAppExecution(input, "Zenbaki bat sartu behar duzu.");
    }

    @Test
    void testKontsultakEtaMugimenduak() {
        String input = "2\n1\nA1-1\n2\n2\n2\n3\nKamisetak\n3\n1\n5449000000100\nA1-1\nA1-2\n5\n3\n2\nA1-1\nB2-2\n0\n";
        testAppExecution(input, "Agur!");
    }

    private void testAppExecution(String input, String expected) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        
        App.main(new String[]{});
        
        String out = baos.toString();
        // Baldintza logikoak assertTrue bakarrean jarri beharrean, assertTrue bat espero den bakoitzeko
        if (expected != null) {
            assertTrue(out.contains(expected));
        }
    }
}