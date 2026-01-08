import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GelaxkaStockTest {

    // --- PUBLIKO METODOAK PROBATU ---
    @Test
    void testGelaxkaMetodoGuztiak() {
        GelaxkaStock s = new GelaxkaStock("A1-1", "5449000000100", 10);

        // Getter-ak
        assertEquals("A1-1", s.getGelaxkaID());
        assertEquals("5449000000100", s.getProduktuEAN13());
        assertEquals(10, s.getKantitatea());

        // Setter-a
        s.setKantitatea(50);
        assertEquals(50, s.getKantitatea());

        // toString
        String esperatua = "Gelaxka: A1-1, EAN: 5449000000100, Kantitate: 50";
        assertEquals(esperatua, s.toString());
    }

    @Test
    void testKonstruktoreaSalbuespenak() {
        // Eraikitzailearen erroreak
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new GelaxkaStock("ID_OKERRA", "123", 10);
        });
        assertTrue(ex1.getMessage().contains("GelaxkaID invalid"));

        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> {
            new GelaxkaStock(null, "123", 10);
        });
        assertTrue(ex2.getMessage().contains("GelaxkaID invalid"));
    }

    // --- PRIBATU METODOAK PROBATU (REFLECTION) Coverage %100 lortzeko ---
    @Test
    void testIsValidGelaxkaID_Reflection() throws Exception {
        // 'isValidGelaxkaID' pribatua da, beraz Reflection erabiltzen dugu
        // regex-aren adar guztiak probatzeko.
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        // 1. Kasu Baliozkoak
        assertTrue((Boolean) m.invoke(null, "A1-1"));
        assertTrue((Boolean) m.invoke(null, "Z99-99"));

        // 2. Kasu Baliogabeak (Adarrak estaltzeko)
        assertFalse((Boolean) m.invoke(null, "a1-1")); // Minuskula
        assertFalse((Boolean) m.invoke(null, "A-1")); // Zenbaki falta
        assertFalse((Boolean) m.invoke(null, "A1-")); // Bukaera falta
        assertFalse((Boolean) m.invoke(null, "1-1")); // Letra falta
        assertFalse((Boolean) m.invoke(null, "AA1-1")); // Bi letra
        assertFalse((Boolean) m.invoke(null, "")); // Hutsik
        assertFalse((Boolean) m.invoke(null, new Object[] { null })); // Null
    }
}