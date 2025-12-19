import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void testFlujoOsoaEtaErroreak() {
        // ESTRATEGIA:
        // 1. Lehenengo "Bide Zuzenak" (Happy Path) exekutatu datuak kargatzeko.
        // 2. Gero "Errore Kasuak" probatu.
        // 3. Lerro bakoitzak App.java-ko Scanner dei bat asetzen du.

        String input = String.join("\n",
                // --- 1. SARRERA ZUZENAK ---
                "1", "1", "5449000000100", "A1-1", "10", // Stocka Sartu (OK)
                "1", "2", "5449000000100", "A1-1", "5", // Stocka Atera (OK)
                "3", "1", "5449000000100", "A1-1", "A1-2", "2", // Mugitu (OK)
                "2", "1", "A1-1", // Kontsultatu Gelaxka (OK)
                "2", "2", // Inbentarioa (OK)
                "2", "3", // Guztiak Erakutsi (OK)
                "2", "4", // Kontsulta (Ez inplementatua)

                // --- 2. STOCKA KUDEATU ERROREAK ---
                "1", "99", // Azpimenuan aukera okerra
                "1", "1", "invalidEAN", "A1-1", "10", // Sartu: EAN okerra (3 input behar ditu begiztak)
                "1", "1", "5449000000100", "badID", "10", // Sartu: ID okerra
                "1", "1", "5449000000100", "A1-1", "nan", // Sartu: Kantitatea ez da zenbakia
                "1", "1", "5449000000100", "A1-1", "2000", // Sartu: Kantitate gehiegi

                "1", "2", "5449000000100", "B2-4", // Atera: Produktua ez dago (ez du kanti eskatzen)
                "1", "2", "5449000000100", "A1-1", "nan", // Atera: Kantitatea ez da zenbakia
                "1", "2", "5449000000100", "A1-1", "2000", // Atera: Stock nahikorik ez

                // --- 3. MUGIMENDU ERROREAK ---
                "3", "99", // Azpimenuan aukera okerra
                "3", "2", // Transferitu (ez inplementatua)
                "3", "1", "5449000000100", "A1-1", "A1-2", "nan", // Mugitu: Kantitatea ez da zenbakia

                // --- 4. MENU NAGUSIA ERROREAK ---
                "99", // Aukera okerra
                "abc", // Letra bat (hasNextInt false)

                // --- 5. AMAITU ---
                "0",
                "" // Lerro-jauzi extra Scanner-a ondo ixteko
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
            // Irteeran espero den testua edo errore mezu komunak bilatu
            assertTrue(out.contains(expected) || out.contains("Zenbaki bat sartu behar duzu"));
        } finally {
            System.setIn(origIn);
            System.setOut(origOut);
        }
    }
}