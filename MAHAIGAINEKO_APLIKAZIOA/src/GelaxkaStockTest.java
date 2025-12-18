import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GelaxkaStockTest {
    @Test
    void testGelaxkaOsoa() {
        GelaxkaStock s = new GelaxkaStock("A1-1", "5449000000100", 10);
        // Getter guztiak deitu coverage osatzeko
        assertEquals("A1-1", s.getGelaxkaID());
        assertEquals("5449000000100", s.getProduktuEAN13());
        assertEquals(10, s.getKantitatea());

        s.setKantitatea(50);
        assertEquals(50, s.getKantitatea());

        String esperado = "Gelaxka: A1-1, EAN: 5449000000100, Kantitate: 50";
        assertEquals(esperado, s.toString());
    }

    @Test
    void testKonstruktoreaErrorea() {
        // ID formatu okerra: Exception coverage
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new GelaxkaStock("ID-OKERRA", "123", 10));
        assertTrue(ex.getMessage().contains("GelaxkaID invalid"));
    }
}