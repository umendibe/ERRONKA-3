import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BiltegiaTest {
    private Biltegia biltegia;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        biltegia = new Biltegia();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testAteraEtaHustu() {
        // A1-1-ek 15 unitate ditu hasieran. Guztiak atera.
        boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 15);
        assertTrue(emaitza);
        // Abisua: Gelaxka hutsik dago mezua konprobatu
        assertTrue(outContent.toString().contains("hutsik dago"));
        assertTrue(biltegia.kontsultatuGelaxka("A1-1").isEmpty());
    }

    @Test
    void testGelaxkaBetetaMarkatua() {
        biltegia.sartuStocka("5449000000100", "Z1-1", 100);
        outContent.reset();
        boolean emaitza = biltegia.sartuStocka("8437008888001", "Z1-1", 1);
        assertFalse(emaitza);
        assertTrue(outContent.toString().contains("Errorea: Gelaxka 'beteta' gisa markatuta dago"));
    }

    @Test
    void testErakutsiInformazioa() {
        // Beteta dagoen gelaxka bat sortu
        biltegia.sartuStocka("5449000000100", "Z9-9", 100);
        // Ezezaguna den produktu bat forzatu inbentarioan (billatuProduktua null
        // coverage)
        biltegia.stocka.add(new GelaxkaStock("X0-0", "0000000000000", 10));

        outContent.reset();
        biltegia.erakutsiProduktuguztiakGelaxketan();
        String output = outContent.toString();

        assertTrue(output.contains("Egoera: BETETA"));
        assertTrue(output.contains("Ezezaguna")); // billatuProduktua-k null ematen duenean
    }

    @Test
    void testMugituRollbackSimulazioa() {
        boolean mugitu = biltegia.mugituProduktua("5449000000100", "A1-1", "B2-4", 90);
        assertFalse(mugitu);
        assertTrue(biltegia.balidatuGelaxkaProduktua("5449000000100", "A1-1"));
    }

    @Test
    void testBalidazioakErroreak() {
        assertFalse(biltegia.balidatuEAN13(null));
        assertFalse(biltegia.balidatuEAN13("   "));
        assertFalse(biltegia.balidatuEAN13("123")); // Digitu gutxi
        assertFalse(biltegia.balidatuEAN13("9999999999999")); // Ez dago katalogoan

        assertFalse(biltegia.balidatuGelaxkaID(null));
        assertFalse(biltegia.balidatuGelaxkaID("   "));

        assertFalse(biltegia.balidatuKantitatea(1001)); // Max 1000
    }
}