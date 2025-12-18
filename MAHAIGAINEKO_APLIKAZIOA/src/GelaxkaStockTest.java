import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("GelaxkaStock - Gelaxka Stock Kudeaketaren Testak")
public class GelaxkaStockTest {

    private GelaxkaStock gelaxkaStock;

    @BeforeEach
    void setUp() {
        gelaxkaStock = new GelaxkaStock("A1-1", "5449000000100", 15);
    }

    @Nested
    @DisplayName("Konstruktorea eta Getters")
    class KonstruktoreaBetaGettersak {

        @Test
        @DisplayName("GelaxkaStock sortu eta balioak berreskuratu")
        void testGelaxkaStock_Konstrutoreak() {
            assertNotNull(gelaxkaStock);
            assertEquals("A1-1", gelaxkaStock.getGelaxkaID());
            assertEquals("5449000000100", gelaxkaStock.getProduktuEAN13());
            assertEquals(15, gelaxkaStock.getKantitatea());
        }

        @Test
        @DisplayName("setKantitatea balioa aldatu eta berreskuratu")
        void testSetKantitatea() {
            gelaxkaStock.setKantitatea(25);
            assertEquals(25, gelaxkaStock.getKantitatea());
        }

        @Test
        @DisplayName("toString formatua egokia")
        void testToString() {
            String esperatua = "Gelaxka: A1-1, EAN: 5449000000100, Kantitate: 15";
            assertEquals(esperatua, gelaxkaStock.toString());
        }

        @Test
        @DisplayName("GelaxkaStock konstruktorearen baliazio egokia")
        void testGelaxkaStock_ValidID() {
            GelaxkaStock stock = new GelaxkaStock("B2-3", "8437008888001", 20);
            assertEquals("B2-3", stock.getGelaxkaID());
            assertEquals("8437008888001", stock.getProduktuEAN13());
            assertEquals(20, stock.getKantitatea());
        }

        @Test
        @DisplayName("GelaxkaStock konstruktorearen baliazio egokia Z-rekin")
        void testGelaxkaStock_ValidID_Z() {
            GelaxkaStock stock = new GelaxkaStock("Z99-99", "5449000000100", 30);
            assertEquals("Z99-99", stock.getGelaxkaID());
        }

        @Test
        @DisplayName("GelaxkaStock baliazio faltsua (invalid ID)")
        void testGelaxkaStock_InvalidID() {
            assertThrows(IllegalArgumentException.class, () -> {
                new GelaxkaStock("invalid", "5449000000100", 15);
            });
        }

        @Test
        @DisplayName("GelaxkaStock baliazio faltsua (ez du etxea)")
        void testGelaxkaStock_InvalidID_NoDash() {
            assertThrows(IllegalArgumentException.class, () -> {
                new GelaxkaStock("A11", "5449000000100", 15);
            });
        }

        @Test
        @DisplayName("GelaxkaStock baliazio faltsua (minuskulak)")
        void testGelaxkaStock_InvalidID_Lowercase() {
            assertThrows(IllegalArgumentException.class, () -> {
                new GelaxkaStock("a1-1", "5449000000100", 15);
            });
        }

        @Test
        @DisplayName("GelaxkaStock baliazio faltsua (null)")
        void testGelaxkaStock_InvalidID_Null() {
            assertThrows(IllegalArgumentException.class, () -> {
                new GelaxkaStock(null, "5449000000100", 15);
            });
        }

        @Test
        @DisplayName("GelaxkaStock baliazio faltsua (zenbaki soilik)")
        void testGelaxkaStock_InvalidID_NumbersOnly() {
            assertThrows(IllegalArgumentException.class, () -> {
                new GelaxkaStock("1-1", "5449000000100", 15);
            });
        }

        @Test
        @DisplayName("GelaxkaStock baliazio faltsua (letra baino gehiago)")
        void testGelaxkaStock_InvalidID_MultipleLetters() {
            assertThrows(IllegalArgumentException.class, () -> {
                new GelaxkaStock("AB1-1", "5449000000100", 15);
            });
        }

        @Test
        @DisplayName("GelaxkaStock setKantitatea negatiboa")
        void testSetKantitatea_Negative() {
            gelaxkaStock.setKantitatea(-5);
            assertEquals(-5, gelaxkaStock.getKantitatea());
        }

        @Test
        @DisplayName("GelaxkaStock setKantitatea zero")
        void testSetKantitatea_Zero() {
            gelaxkaStock.setKantitatea(0);
            assertEquals(0, gelaxkaStock.getKantitatea());
        }

        @Test
        @DisplayName("GelaxkaStock setKantitatea handiz")
        void testSetKantitatea_Large() {
            gelaxkaStock.setKantitatea(1000);
            assertEquals(1000, gelaxkaStock.getKantitatea());
        }

        @Test
        @DisplayName("GelaxkaStock toString balioa aldatuta")
        void testToString_AfterModification() {
            gelaxkaStock.setKantitatea(50);
            String esperatua = "Gelaxka: A1-1, EAN: 5449000000100, Kantitate: 50";
            assertEquals(esperatua, gelaxkaStock.toString());
        }
    }
}
