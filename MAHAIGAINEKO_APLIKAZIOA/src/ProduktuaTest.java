import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Produktua - Biltegi Kudeaketa Produktuaren Testak")
public class ProduktuaTest {

    private Produktua produktua;

    @BeforeEach
    void setUp() {
        produktua = new Produktua("5449000000100", "Kamiseta Zubieta", "Kamisetak");
    }

    @Nested
    @DisplayName("Konstruktorea eta Getters")
    class KonstruktoreaBetaGettersak {

        @Test
        @DisplayName("Produktua sortu eta balioak berreskuratu")
        void testProduktua_Konstrutoreak() {
            assertNotNull(produktua);
            assertEquals("5449000000100", produktua.getEan13());
            assertEquals("Kamiseta Zubieta", produktua.getIzena());
            assertEquals("Kamisetak", produktua.getKategoria());
        }

        @Test
        @DisplayName("EAN-13 kodea berreskuratu")
        void testGetEan13() {
            assertEquals("5449000000100", produktua.getEan13());
        }

        @Test
        @DisplayName("Produktuaren izena berreskuratu")
        void testGetIzena() {
            assertEquals("Kamiseta Zubieta", produktua.getIzena());
        }

        @Test
        @DisplayName("Produktuaren kategoria berreskuratu")
        void testGetKategoria() {
            assertEquals("Kamisetak", produktua.getKategoria());
        }
    }

    @Nested
    @DisplayName("toString metodoaren testak")
    class ToStringTestak {

        @Test
        @DisplayName("toString formatua egokia da")
        void testToString_FormatuEgokia() {
            String esperatua = "[5449000000100] Kamiseta Zubieta (Kamisetak)";
            assertEquals(esperatua, produktua.toString());
        }

        @Test
        @DisplayName("toString EAN-13 dituzu")
        void testToString_EAN13Badu() {
            assertTrue(produktua.toString().contains("5449000000100"));
        }

        @Test
        @DisplayName("toString izena dituzu")
        void testToString_IzenaaBadu() {
            assertTrue(produktua.toString().contains("Kamiseta Zubieta"));
        }

        @Test
        @DisplayName("toString kategoria dituzu")
        void testToString_KategoriaBadu() {
            assertTrue(produktua.toString().contains("Kamisetak"));
        }
    }

    @Nested
    @DisplayName("Bestelako produktuak")
    class BestElakoProduktuak {

        @Test
        @DisplayName("Jertsea sortu")
        void testBestElakoProduktuaJertseua() {
            Produktua jertsea = new Produktua("8437008888001", "Kirol Jertse Gorria", "Kirol-jertseak");
            assertEquals("8437008888001", jertsea.getEan13());
            assertEquals("Kirol Jertse Gorria", jertsea.getIzena());
            assertEquals("Kirol-jertseak", jertsea.getKategoria());
        }

        @Test
        @DisplayName("Galtza sortu")
        void testBestElakoProduktuaGaltza() {
            Produktua galtza = new Produktua("1112223334445", "Galtza Bakeroak Unisex", "Galtzak");
            assertEquals("1112223334445", galtza.getEan13());
            assertEquals("Galtza Bakeroak Unisex", galtza.getIzena());
            assertEquals("Galtzak", galtza.getKategoria());
        }
    }

    @Nested
    @DisplayName("Balio desberdina dituzen produktuak")
    class BaloDesberdakinak {

        @Test
        @DisplayName("Produktuak ez dira denak berdintsuak")
        void testProduktua_DesberdiBalioak() {
            Produktua beste = new Produktua("9999999999999", "Beste", "Bestelakoa");
            assertNotEquals(produktua.getEan13(), beste.getEan13());
            assertNotEquals(produktua.getIzena(), beste.getIzena());
            assertNotEquals(produktua.getKategoria(), beste.getKategoria());
        }
    }
}
