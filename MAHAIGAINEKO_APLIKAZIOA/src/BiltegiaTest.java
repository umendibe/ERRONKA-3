import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BiltegiaTest {
    private Biltegia biltegia;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        biltegia = new Biltegia();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testAteraEtaHustuOsoa() {
        biltegia.ateraStocka("5449000000100", "A1-1", 15);
        assertFalse(outContent.toString().contains("hutsik dago"));
        assertFalse(biltegia.kontsultatuGelaxka("A1-1").isEmpty());
    }

    @Test
    void testAteraStocka_EzDagoNahikoa() {
        boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 20);
        assertFalse(emaitza);
    }

    @Test
    void testAteraStocka_EzDaAurkitu() {
        boolean emaitza = biltegia.ateraStocka("9999999999999", "A1-1", 1);
        assertFalse(emaitza);
    }

    @Test
    void testSartuStocka_GehituExistitzenDena() {
        boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 5);
        assertTrue(emaitza);
        assertEquals(20, biltegia.kontsultatuGelaxka("A1-1").get(0).getKantitatea());
    }

    @Test
    void testMugituEtaRollback() {
        // B2-4 betetzeko zorian jarri (80 + 15 = 95)
        biltegia.sartuStocka("8437008888001", "B2-4", 80);
        outContent.reset();

        // 10 mugitu nahi (95 + 10 = 105 > 100) -> Rollback
        boolean emaitza = biltegia.mugituProduktua("5449000000100", "A1-1", "B2-4", 10);

        assertFalse(emaitza);
        assertFalse(outContent.toString().contains("berreskuratzen"));
    }

    @Test
    void testErakutsiEzezaguna() {
        biltegia.stocka.add(new GelaxkaStock("X0-0", "0000000000000", 10));
        outContent.reset();
        biltegia.erakutsiProduktuguztiakGelaxketan();
        assertTrue(outContent.toString().contains("Ezezaguna"));
    }

    @Test
    void testBalidazioNegatiboak() {
        assertFalse(biltegia.balidatuEAN13(null));
        assertFalse(biltegia.balidatuEAN13(""));
        assertFalse(biltegia.balidatuEAN13("123"));
        assertFalse(biltegia.balidatuGelaxkaID(null));
        assertFalse(biltegia.balidatuGelaxkaID("a1-1"));
        assertFalse(biltegia.balidatuKantitatea(0));
        assertFalse(biltegia.balidatuKantitatea(2000));
        assertFalse(biltegia.balidatuGelaxkaProduktua("111", "A1-1"));
    }

    @Test
    void testSartuStocka_LimiterikGabeBainaBetetaZerrendan() throws Exception {
        // Reflection: Gelaxka bat "Beteta" zerrendan sartu eskuz, nahiz eta lekua izan.
        // Honek kodeko "if (gelaxkaBetetaLista.contains...)" adarra probatzen du.
        Field field = Biltegia.class.getDeclaredField("gelaxkaBetetaLista");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<String> lista = (List<String>) field.get(biltegia);
        lista.add("A1-1");

        outContent.reset();
        boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 1);

        assertFalse(emaitza);
        assertTrue(outContent.toString().contains("Ezin da sartu"));
    }
}