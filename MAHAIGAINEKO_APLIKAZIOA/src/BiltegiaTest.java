import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Biltegia - Biltegi Kudeaketaren Testak")
public class BiltegiaTest {

    private Biltegia biltegia;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        biltegia = new Biltegia();
        // Capture System.out for testing output
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    void tearDown() {
        System.setOut(originalOut);
    }

    @Nested
    @DisplayName("Konstruktorea eta Hasierakoa Data")
    class KonstruktoreaBetaHasierakoa {

        @Test
        @DisplayName("Biltegia sortu eta hasierako datuak kargatu")
        void testBiltegia_HasierakoDatuak() {
            System.setOut(originalOut);
            assertNotNull(biltegia);
            assertEquals(3, biltegia.kontsultatuInbentarioa().size());
        }
    }

    @Nested
    @DisplayName("Stocka Sartu - Sartu")
    class SartuStocka {

        @Test
        @DisplayName("Produktua arrakastaz sartu gelaxkan")
        void testSartuStocka_Arrakastaz() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 10);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Produktu bERRA sartu gelaxkan")
        void testSartuStocka_ProduktaBerria() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.sartuStocka("5449000000100", "C3-3", 5);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Sartu EAN-13 invalid")
        void testSartuStocka_EAN13Invalid() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.sartuStocka("invalid", "A1-1", 10);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Sartu Gelaxka ID invalid")
        void testSartuStocka_GelaxkaIDInvalid() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.sartuStocka("5449000000100", "invalid", 10);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Sartu kantitate negatiboa")
        void testSartuStocka_KantitateaNegatiboa() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", -5);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Sartu kantitate zero")
        void testSartuStocka_KantitateaZero() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 0);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Sartu kantitate gehiegiz handia (1001)")
        void testSartuStocka_KantitateaGehiegizHandia() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 1001);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Sartu gelaxkaren kapazitate gainditzen du")
        void testSartuStocka_KapazitateGainditzen() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 95);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Gelaxka beteta dagoenean sartu ezin da")
        void testSartuStocka_GelaxkaBeteta() {
            System.setOut(originalOut);
            // Lehenengo 100 sartu
            biltegia.sartuStocka("5449000000100", "D4-4", 100);
            // Gero gehiago sartu
            boolean emaitza = biltegia.sartuStocka("1112223334445", "D4-4", 1);
            assertFalse(emaitza);
        }
    }

    @Nested
    @DisplayName("Stocka Atera - Irteera")
    class AteraStocka {

        @Test
        @DisplayName("Produktua arrakastaz atera gelaxkatik")
        void testAteraStocka_Arrakastaz() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 5);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Gelaxkatik hutsik utzi")
        void testAteraStocka_Hutsik() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 15);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Atera kantitate negatiboa")
        void testAteraStocka_KantitateaNegatiboa() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", -5);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Atera kantitate zero")
        void testAteraStocka_KantitateaZero() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 0);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Atera nahikoa stock ez dagoenean")
        void testAteraStocka_StockInsufizientea() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 100);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Atera produktu ez dena ez dago gelaxkan")
        void testAteraStocka_Produktua_EzDago() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.ateraStocka("9999999999999", "A1-1", 1);
            assertFalse(emaitza);
        }
    }

    @Nested
    @DisplayName("Produktua Mugitu")
    class MugituProduktua {

        @Test
        @DisplayName("Produktua arrakastaz mugitu gelaxka batetik bestera")
        void testMugituProduktua_Arrakastaz() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.mugituProduktua("5449000000100", "A1-1", "A1-2", 5);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Mugitu kantitate negatiboa")
        void testMugituProduktua_KantitateaNegatiboa() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.mugituProduktua("5449000000100", "A1-1", "A1-2", -5);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Mugitu kantitate zero")
        void testMugituProduktua_KantitateaZero() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.mugituProduktua("5449000000100", "A1-1", "A1-2", 0);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Jatorrizko gelaxka beteta dagoenean atera eta beste batean sartu")
        void testMugituProduktua_JatorrizkoanBueltatudu() {
            System.setOut(originalOut);
            biltegia.sartuStocka("5449000000100", "C3-3", 100);
            boolean emaitza = biltegia.mugituProduktua("5449000000100", "C3-3", "A1-2", 10);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Helmugako gelaxka kapazitate gainditzean mugitu arrakastatsua")
        void testMugituProduktua_HelmugaKapazitateGainditzen() {
            System.setOut(originalOut);
            biltegia.sartuStocka("1112223334445", "A1-2", 95);
            boolean emaitza = biltegia.mugituProduktua("5449000000100", "A1-1", "A1-2", 10);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Produktua ez dago jatorrizko gelaxkan")
        void testMugituProduktua_ProduktuzEzDago() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.mugituProduktua("9999999999999", "A1-1", "A1-2", 5);
            assertFalse(emaitza);
        }
    }

    @Nested
    @DisplayName("Kontsultatu Gelaxka")
    class KontsultatuGelaxka {

        @Test
        @DisplayName("Gelaxkan produktuak kontsultatu")
        void testKontsultatuGelaxka_ProduktaAurkitu() {
            System.setOut(originalOut);
            List<GelaxkaStock> emaitza = biltegia.kontsultatuGelaxka("A1-1");
            assertFalse(emaitza.isEmpty());
            assertEquals(1, emaitza.size());
        }

        @Test
        @DisplayName("Gelaxka hutsik da")
        void testKontsultatuGelaxka_GelaxkaHutsik() {
            System.setOut(originalOut);
            List<GelaxkaStock> emaitza = biltegia.kontsultatuGelaxka("Z99-99");
            assertTrue(emaitza.isEmpty());
        }

        @Test
        @DisplayName("Gelaxka produktu multipla ditudu")
        void testKontsultatuGelaxka_ProduktaMultipla() {
            System.setOut(originalOut);
            biltegia.sartuStocka("8437008888001", "A1-1", 10);
            List<GelaxkaStock> emaitza = biltegia.kontsultatuGelaxka("A1-1");
            assertEquals(2, emaitza.size());
        }
    }

    @Nested
    @DisplayName("Kontsultatu Inbentarioa")
    class KontsultatuInbentarioa {

        @Test
        @DisplayName("Inbentario osoa kontsultatu")
        void testKontsultatuInbentarioa() {
            System.setOut(originalOut);
            List<GelaxkaStock> emaitza = biltegia.kontsultatuInbentarioa();
            assertFalse(emaitza.isEmpty());
            assertEquals(3, emaitza.size());
        }

        @Test
        @DisplayName("Inbentario balioak direla")
        void testKontsultatuInbentarioa_BalioakDirela() {
            System.setOut(originalOut);
            List<GelaxkaStock> emaitza = biltegia.kontsultatuInbentarioa();
            for (GelaxkaStock item : emaitza) {
                assertNotNull(item.getGelaxkaID());
                assertNotNull(item.getProduktuEAN13());
                assertTrue(item.getKantitatea() > 0);
            }
        }
    }

    @Nested
    @DisplayName("EAN-13 Validazioa")
    class ValidatuEAN13 {

        @Test
        @DisplayName("Baliozkoa EAN-13: 5449000000100")
        void testBalidatuEAN13_Baliozkoa() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuEAN13("5449000000100");
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("EAN-13 hutsa")
        void testBalidatuEAN13_Hutsa() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuEAN13("");
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("EAN-13 null")
        void testBalidatuEAN13_Null() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuEAN13(null);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("EAN-13 digitu gutxi")
        void testBalidatuEAN13_DigutuGutxi() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuEAN13("544900000010");
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("EAN-13 digitu gehiegi")
        void testBalidatuEAN13_DigutuGehiegi() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuEAN13("54490000001001");
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("EAN-13 ez-zenbakikoa")
        void testBalidatuEAN13_EzZenbakia() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuEAN13("abcdefghijklm");
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("EAN-13 ez katalogoan")
        void testBalidatuEAN13_EzKatalogoan() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuEAN13("9999999999999");
            assertFalse(emaitza);
        }
    }

    @Nested
    @DisplayName("Gelaxka ID Validazioa")
    class ValidatuGelaxkaID {

        @Test
        @DisplayName("Baliozkoa Gelaxka ID: A1-1")
        void testBalidatuGelaxkaID_Baliozkoa_A1_1() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaID("A1-1");
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Baliozkoa Gelaxka ID: B2-4")
        void testBalidatuGelaxkaID_Baliozkoa_B2_4() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaID("B2-4");
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Gelaxka ID hutsa")
        void testBalidatuGelaxkaID_Hutsa() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaID("");
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Gelaxka ID null")
        void testBalidatuGelaxkaID_Null() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaID(null);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Gelaxka ID formatua okerra: minuskulak")
        void testBalidatuGelaxkaID_Minuskulak() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaID("a1-1");
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Gelaxka ID formatua okerra: etxea falta")
        void testBalidatuGelaxkaID_EtxeaFalta() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaID("A11");
            assertFalse(emaitza);
        }
    }

    @Nested
    @DisplayName("Kantitate Validazioa")
    class ValidatuKantitate {

        @Test
        @DisplayName("Baliozkoa kantitate: 10")
        void testBalidatuKantitate_Baliozkoa() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuKantitatea(10);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Kantitate zero")
        void testBalidatuKantitate_Zero() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuKantitatea(0);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Kantitate negatiboa")
        void testBalidatuKantitate_Negatiboa() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuKantitatea(-5);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Kantitate gehiegiz handia: 1001")
        void testBalidatuKantitate_GehiegizHandia() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuKantitatea(1001);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Kantitate maximoa: 1000")
        void testBalidatuKantitate_Maximoa() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuKantitatea(1000);
            assertTrue(emaitza);
        }
    }

    @Nested
    @DisplayName("Gelaxka eta Produktua Validazioa")
    class ValidatuGelaxkaProduktua {

        @Test
        @DisplayName("Produktua gelaxkan existitzen da")
        void testBalidatuGelaxkaProduktua_Existitu() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaProduktua("5449000000100", "A1-1");
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Produktua gelaxkan ez da existitu")
        void testBalidatuGelaxkaProduktua_EzExistitu() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaProduktua("9999999999999", "A1-1");
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Produktua gelaxka desberdin batean")
        void testBalidatuGelaxkaProduktua_GelaxkaDesberdina() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaProduktua("5449000000100", "Z99-99");
            assertFalse(emaitza);
        }
    }

    @Nested
    @DisplayName("Erakutsi Produktuak Gelaxketan")
    class ErakutsiProduktuguztiakGelaxketan {

        @Test
        @DisplayName("Produktuak erakustea ez du errorerik")
        void testErakutsiProduktuguztiakGelaxketan_NoError() {
            System.setOut(originalOut);
            assertDoesNotThrow(() -> {
                biltegia.erakutsiProduktuguztiakGelaxketan();
            });
        }

        @Test
        @DisplayName("Produktuak erakustea sortzen ditu datuak")
        void testErakutsiProduktuguztiakGelaxketan_OutputBadu() {
            biltegia.erakutsiProduktuguztiakGelaxketan();
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertTrue(output.contains("PRODUKTU GUZTIAK GELAXKETAN"));
        }
    }

    @Nested
    @DisplayName("Erregistroen egoera bereziak")
    class ErregistroBereziak {

        @Test
        @DisplayName("Erakutsi: Biltegia hutsa dagoenean mezu egokia")
        void testErakutsiProduktuguztiakGelaxketan_Empty() {
            // Clear current inventory
            biltegia.kontsultatuInbentarioa().clear();

            biltegia.erakutsiProduktuguztiakGelaxketan();
            String output = outputStream.toString();
            System.setOut(originalOut);

            assertTrue(output.contains("Biltegia hutsa"));
        }

        @Test
        @DisplayName("Erakutsi: Produktu ezezagunak 'Ezezaguna' erakusten du")
        void testErakutsiProduktuguztiakGelaxketan_UnknownProduct() {
            // Add a stock entry with an EAN not present in the katalogoa
            biltegia.kontsultatuInbentarioa().add(new GelaxkaStock("X1-1", "0000000000000", 10));

            biltegia.erakutsiProduktuguztiakGelaxketan();
            String output = outputStream.toString();
            System.setOut(originalOut);

            assertTrue(output.contains("Ezezaguna"));
        }

        @Test
        @DisplayName("Atera guztia eta gelaxka hutsik gelditzean")
        void testAteraStocka_EmptyAndRemoveFromStock() {
            System.setOut(originalOut);
            // First, empty out A1-1 completely
            boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 15);
            assertTrue(emaitza);
            
            // Verify stock is now smaller
            List<GelaxkaStock> items = biltegia.kontsultatuGelaxka("A1-1");
            assertTrue(items.isEmpty() || items.stream().allMatch(s -> s.getKantitatea() == 0));
        }

        @Test
        @DisplayName("Gelaxka beteta egoeratik atera eta hutsik ez dagoenean")
        void testAteraStocka_UnmarkFullWhenNotEmpty() {
            System.setOut(originalOut);
            // Fill a gelaxka completely
            biltegia.sartuStocka("5449000000100", "C3-3", 100);
            // Extract some, but not all
            boolean emaitza = biltegia.ateraStocka("5449000000100", "C3-3", 50);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Mugitu ondoren sartu huts egiten duenean lehengo kokalekua berreskuratzean")
        void testMugituProduktua_SartuFailsThenRollback() {
            // Use an anonymous subclass to simulate sartuStocka failing
            Biltegia biltegiaMock = new Biltegia() {
                @Override
                public boolean sartuStocka(String ean13, String gelaxkaID, int kantitatea) {
                    return false; // force failure to trigger rollback
                }
            };

            boolean emaitza = biltegiaMock.mugituProduktua("5449000000100", "A1-1", "A1-2", 5);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Gelaxka ID formatua okerra: zenbakiak soilik")
        void testBalidatuGelaxkaID_ZenbakiakoSoilik() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.balidatuGelaxkaID("1-1");
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Sartu eta gelaxka beteta markatua dago")
        void testSartuStocka_MarkFullOnExactCapacity() {
            System.setOut(originalOut);
            // Add exactly 85 to reach 100 (A1-1 has 15 initially)
            boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 85);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Atera produktua eta produktua ez dago gelaxkan")
        void testAteraStocka_ProductNotInGelaxka() {
            System.setOut(originalOut);
            // Try to extract a product that's not in A1-1
            boolean emaitza = biltegia.ateraStocka("8437008888001", "A1-1", 5);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Sartu lehenengo baliozko produktua gelaxka barruan")
        void testSartuStocka_SameProductMultipleTimes() {
            System.setOut(originalOut);
            // First insertion
            boolean emaitza1 = biltegia.sartuStocka("8437008888001", "F6-6", 10);
            assertTrue(emaitza1);
            // Second insertion (same product, same gelaxka)
            boolean emaitza2 = biltegia.sartuStocka("8437008888001", "F6-6", 20);
            assertTrue(emaitza2);
        }

        @Test
        @DisplayName("Mugitu eta jatorrizkon produktua atera ezin bada")
        void testMugituProduktua_SourceProductNotFound() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.mugituProduktua("9999999999999", "A1-1", "A1-2", 5);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Gelaxka kapazitate gainditzean sartu huts da")
        void testSartuStocka_FailOnCapacityExceeded() {
            System.setOut(originalOut);
            // Fill A1-1 to 100
            biltegia.sartuStocka("5449000000100", "G7-7", 100);
            // Try to add more
            boolean emaitza = biltegia.sartuStocka("1112223334445", "G7-7", 1);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Helmugako gelaxka kapazitate gainditzean mugitu huts da")
        void testMugituProduktua_FailOnDestCapacityExceeded() {
            System.setOut(originalOut);
            // Fill destination to near capacity
            biltegia.sartuStocka("8437008888001", "H8-8", 95);
            // Try to move too much
            boolean emaitza = biltegia.mugituProduktua("5449000000100", "A1-1", "H8-8", 10);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Atera eta gelaxka markatua beteta kontrola")
        void testAteraStocka_RemoveFromFullList() {
            System.setOut(originalOut);
            // Fill one completely
            biltegia.sartuStocka("5449000000100", "I9-9", 100);
            // Verify it's marked full by checking behavior
            boolean filled = biltegia.sartuStocka("1112223334445", "I9-9", 1);
            assertFalse(filled); // Should fail because full
        }

        @Test
        @DisplayName("Gelaxka kapazitate kalkulatu berri eta hutsa")
        void testAteraStocka_AllItemsEmpty() {
            System.setOut(originalOut);
            // Start with A1-2 which has 15
            List<GelaxkaStock> before = biltegia.kontsultatuGelaxka("A1-2");
            assertTrue(before.size() > 0);
            
            // Atera all
            boolean result = biltegia.ateraStocka("1112223334445", "A1-2", 15);
            assertTrue(result);
            
            // Check it's gone
            List<GelaxkaStock> after = biltegia.kontsultatuGelaxka("A1-2");
            assertTrue(after.isEmpty());
        }

        @Test
        @DisplayName("Sartu produktua EAN katalogoan ez dagoenean")
        void testSartuStocka_InvalidEAN_NotInCatalog() {
            System.setOut(originalOut);
            boolean emaitza = biltegia.sartuStocka("1234567890123", "A1-1", 5);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Biltegia hutsa erakutsi eta mezu jauzia")
        void testErakutsiProduktuguztiakGelaxketan_EmptyInventory() {
            System.setOut(originalOut);
            biltegia.kontsultatuInbentarioa().clear();
            
            assertDoesNotThrow(() -> biltegia.erakutsiProduktuguztiakGelaxketan());
        }

        @Test
        @DisplayName("Gelaxka beteta markatua dago eta beste produktua ezin sartzen")
        void testSartuStocka_AlreadyMarkedFull() {
            System.setOut(originalOut);
            // Mark a gelaxka as full
            biltegia.sartuStocka("5449000000100", "J0-10", 100);
            // Try to add different product
            boolean emaitza = biltegia.sartuStocka("8437008888001", "J0-10", 1);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Atera produktua eta gelaxka ez dagoenean (beste gelaxkaren produktua)")
        void testAteraStocka_ProductInDifferentGelaxka() {
            System.setOut(originalOut);
            // Try to remove B2-4's product from A1-1
            boolean emaitza = biltegia.ateraStocka("8437008888001", "A1-1", 5);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Sartu gelaxka beteta dagoenean (gelaxka ez zerrenda batean)")
        void testSartuStocka_GelaxkaFullNotInList() {
            System.setOut(originalOut);
            // Fill a new gelaxka
            biltegia.sartuStocka("5449000000100", "K1-11", 100);
            // Try to add to same (already full)
            boolean emaitza = biltegia.sartuStocka("1112223334445", "K1-11", 1);
            assertFalse(emaitza);
        }

        @Test
        @DisplayName("Mugitu produktua eta jatorrizko gelaxka beteta dagoenean")
        void testMugituProduktua_SourceFullButMovingLess() {
            System.setOut(originalOut);
            // First fill a gelaxka to 100 with one product
            biltegia.sartuStocka("5449000000100", "L2-12", 100);
            // Actually we need to move something that will reduce it below 100
            // Create a new biltegia and test scenario
            Biltegia b = new Biltegia();
            // This should work because moving items reduces capacity
            boolean emaitza = b.mugituProduktua("5449000000100", "A1-1", "L2-12", 5);
            assertTrue(emaitza);
        }

        @Test
        @DisplayName("Atera produktua eta gelaxka hutsik gelditzen (kendu etxea)")
        void testAteraStocka_RemoveEmptyShelf() {
            System.setOut(originalOut);
            // Extract all from A1-2 (15 units)
            boolean result = biltegia.ateraStocka("1112223334445", "A1-2", 15);
            assertTrue(result);
            // Verify it's removed from stock list
            List<GelaxkaStock> items = biltegia.kontsultatuGelaxka("A1-2");
            assertTrue(items.isEmpty());
        }

        @Test
        @DisplayName("Erakutsi produktuak gelaxka anitzen gainean")
        void testErakutsiProduktuguztiakGelaxketan_MultipleGelaxkas() {
            System.setOut(originalOut);
            // Add items to multiple gelaxkas
            biltegia.sartuStocka("8437008888001", "P1-1", 25);
            biltegia.sartuStocka("1112223334445", "P1-1", 30);
            
            assertDoesNotThrow(() -> biltegia.erakutsiProduktuguztiakGelaxketan());
        }

        @Test
        @DisplayName("Sartu produktua eta mezua 'Abisua' erakusten duenean")
        void testSartuStocka_MarkFullWarning() {
            System.setOut(new PrintStream(outputStream));
            biltegia.sartuStocka("5449000000100", "Q1-1", 100);
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertTrue(output.contains("Abisua") || output.contains("beteta"));
        }

        @Test
        @DisplayName("Atera produktua eta gelaxka hutsik mezu nagusia")
        void testAteraStocka_EmptyShelfMessage() {
            System.setOut(new PrintStream(outputStream));
            biltegia.ateraStocka("1112223334445", "A1-2", 15);
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertTrue(output.contains("hutsik") || output.contains("Gelaxka"));
        }

        @Test
        @DisplayName("Sartu gelaxka beteta mezu eta return false")
        void testSartuStocka_FullGelaxkaMessage() {
            System.setOut(new PrintStream(outputStream));
            // Fill one
            biltegia.sartuStocka("5449000000100", "R1-1", 100);
            // Try to add to full
            boolean result = biltegia.sartuStocka("8437008888001", "R1-1", 1);
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertFalse(result);
            assertTrue(output.contains("Errorea") || output.contains("beteta"));
        }

        @Test
        @DisplayName("Atera eta gelaxka hutsa dago mezu")
        void testAteraStocka_GelaxkaEmptyOutput() {
            System.setOut(new PrintStream(outputStream));
            // Empty A1-1
            biltegia.ateraStocka("5449000000100", "A1-1", 15);
            String output = outputStream.toString();
            System.setOut(originalOut);
            // Should contain either the empty message or confirmation
            assertTrue(output.length() > 0);
        }

        @Test
        @DisplayName("Mugitu eta helmuga gelaxka beteta dagoenean dago")
        void testMugituProduktua_DestFullOutput() {
            System.setOut(new PrintStream(outputStream));
            // Fill dest to 100
            biltegia.sartuStocka("8437008888001", "S1-1", 100);
            // Try to move to full
            boolean result = biltegia.mugituProduktua("5449000000100", "A1-1", "S1-1", 5);
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertFalse(result);
            assertTrue(output.contains("Errorea") || output.contains("kapazitate"));
        }

        @Test
        @DisplayName("Sartu berdina produktua berdinean gelaxketan (gehitu kantitatea)")
        void testSartuStocka_SameProductSameShelf() {
            System.setOut(new PrintStream(outputStream));
            biltegia.sartuStocka("5449000000100", "A1-1", 10);
            boolean result = biltegia.sartuStocka("5449000000100", "A1-1", 20);
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertTrue(result);
        }

        @Test
        @DisplayName("Atera eta gelaxka ez hutsik (ez 0)")
        void testAteraStocka_PartiallyEmpty() {
            System.setOut(new PrintStream(outputStream));
            biltegia.ateraStocka("5449000000100", "A1-1", 10);
            List<GelaxkaStock> items = biltegia.kontsultatuGelaxka("A1-1");
            System.setOut(originalOut);
            assertTrue(items.stream().anyMatch(item -> item.getKantitatea() == 5));
        }

        @Test
        @DisplayName("Gelaxka 100 produktu bera eta ez sartu gehiago")
        void testSartuStocka_ExactlyFull_CannotAdd() {
            System.setOut(new PrintStream(outputStream));
            biltegia.sartuStocka("5449000000100", "T1-1", 100);
            boolean result = biltegia.sartuStocka("8437008888001", "T1-1", 1);
            System.setOut(originalOut);
            assertFalse(result);
        }

        @Test
        @DisplayName("Produktuak desberdinak gelaxka berean")
        void testSartuStocka_MultipleProductsSameShelf() {
            System.setOut(new PrintStream(outputStream));
            biltegia.sartuStocka("5449000000100", "U1-1", 30);
            biltegia.sartuStocka("8437008888001", "U1-1", 40);
            biltegia.sartuStocka("1112223334445", "U1-1", 30);
            List<GelaxkaStock> items = biltegia.kontsultatuGelaxka("U1-1");
            System.setOut(originalOut);
            assertEquals(3, items.size());
        }

        @Test
        @DisplayName("Atera eta gelaxka hutsik dagoenean unmark full")
        void testAteraStocka_UnmarkFullOnEmpty() {
            System.setOut(new PrintStream(outputStream));
            // Fill it
            biltegia.sartuStocka("5449000000100", "V1-1", 100);
            // Empty it
            biltegia.ateraStocka("5449000000100", "V1-1", 100);
            // Try to add again (should not be marked full anymore)
            boolean result = biltegia.sartuStocka("8437008888001", "V1-1", 50);
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertTrue(result);
        }

        @Test
        @DisplayName("Gelaxka kapazitate kalkulua zero")
        void testKalkulatuGelaxkaKapazitate_Empty() {
            System.setOut(originalOut);
            biltegia.ateraStocka("1112223334445", "A1-2", 15);
            // Rebuild fresh instance to test empty shelves
            Biltegia newBiltegia = new Biltegia();
            // Test with non-existent shelf (should return 0)
            // We can't directly test this, but sartuStocka should work with 100 units
            System.setOut(new PrintStream(outputStream));
            boolean result = newBiltegia.sartuStocka("5449000000100", "W1-1", 100);
            System.setOut(originalOut);
            assertTrue(result);
        }

        @Test
        @DisplayName("Sartu eta atera, gelaxka ez hutsa baina ez beteta")
        void testSartuAtera_PartialState() {
            System.setOut(new PrintStream(outputStream));
            biltegia.sartuStocka("5449000000100", "Y1-1", 50);
            biltegia.ateraStocka("5449000000100", "Y1-1", 20);
            List<GelaxkaStock> items = biltegia.kontsultatuGelaxka("Y1-1");
            System.setOut(originalOut);
            assertTrue(items.stream().anyMatch(item -> item.getKantitatea() == 30));
        }

        @Test
        @DisplayName("Billatuproduktua metodo pribatua - produktua kalkulatzean")
        void testSartuStocka_WithProductSearchInDisplay() {
            System.setOut(new PrintStream(outputStream));
            biltegia.sartuStocka("5449000000100", "AA1-1", 10);
            biltegia.erakutsiProduktuguztiakGelaxketan();
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertTrue(output.contains("Kamiseta Zubieta"));
        }

        @Test
        @DisplayName("Atera eta gelaxka beteta listaren amaieran dago")
        void testAteraStocka_RemoveFullFromList_LastElement() {
            System.setOut(new PrintStream(outputStream));
            // Fill a shelf
            biltegia.sartuStocka("5449000000100", "AB1-1", 100);
            // Remove all items to trigger removal from full list
            boolean result = biltegia.ateraStocka("5449000000100", "AB1-1", 100);
            System.setOut(originalOut);
            assertTrue(result);
        }

        @Test
        @DisplayName("Sartu produktu berdinaren kantitatea eguneratzea")
        void testSartuStocka_UpdateQuantityExistingProduct() {
            System.setOut(originalOut);
            // First insertion
            boolean first = biltegia.sartuStocka("5449000000100", "AC1-1", 10);
            // Second insertion (same gelaxka and product)
            boolean second = biltegia.sartuStocka("5449000000100", "AC1-1", 20);
            assertTrue(first && second);
            List<GelaxkaStock> items = biltegia.kontsultatuGelaxka("AC1-1");
            assertTrue(items.stream().anyMatch(item -> item.getKantitatea() == 30));
        }

        @Test
        @DisplayName("Gelaxka beteta markatua dagoenean kapazitate gainditzen")
        void testSartuStocka_FullGelaxkaCapacityExceeded() {
            System.setOut(new PrintStream(outputStream));
            // Fill exactly to 100
            biltegia.sartuStocka("5449000000100", "AD1-1", 100);
            // Try to add more (should fail)
            boolean result = biltegia.sartuStocka("8437008888001", "AD1-1", 1);
            System.setOut(originalOut);
            assertFalse(result);
        }

        @Test
        @DisplayName("Atera produktua ez dago gelaxkan baina EAN baliozkoa")
        void testAteraStocka_ProductNotInSpecificGelaxka() {
            System.setOut(new PrintStream(outputStream));
            // A1-1 has 5449000000100, not 8437008888001
            boolean result = biltegia.ateraStocka("8437008888001", "A1-1", 5);
            System.setOut(originalOut);
            assertFalse(result);
        }

        @Test
        @DisplayName("Mugitu produktua helmugako kapazitate kalkulua")
        void testMugituProduktua_DestCapacityCalculation() {
            System.setOut(new PrintStream(outputStream));
            // A1-2 has 15 of 1112223334445
            // Try to move 80 to A1-1 (which has 15 of 5449000000100 = 15 capacity left)
            boolean result = biltegia.mugituProduktua("5449000000100", "A1-1", "A1-2", 80);
            System.setOut(originalOut);
            assertFalse(result); // Should fail, A1-2 doesn't have capacity for 80 more
        }

        @Test
        @DisplayName("Gelaxka ID baliazio formatua OK baina baliazio hutsean")
        void testBalidatuGelaxkaID_ValidFormatEmptyString() {
            System.setOut(originalOut);
            boolean result = biltegia.balidatuGelaxkaID("");
            assertFalse(result);
        }

        @Test
        @DisplayName("Sartu eta gelaxka kapazitate guztia darabil produktu berdinarekin")
        void testSartuStocka_ExactCapacityWithNewProduct() {
            System.setOut(originalOut);
            // Create new shelf with exactly 100
            boolean result = biltegia.sartuStocka("5449000000100", "AE1-1", 100);
            assertTrue(result);
            // Verify it's marked as full
            boolean canAdd = biltegia.sartuStocka("8437008888001", "AE1-1", 1);
            assertFalse(canAdd);
        }

        @Test
        @DisplayName("Gelaxka hutsik dagoenean eta produktu berria gehitze eta bete")
        void testSartuStocka_NewProductNewShelfExactCapacity() {
            System.setOut(new PrintStream(outputStream));
            // Try to add 100 to new shelf
            boolean result = biltegia.sartuStocka("8437008888001", "AF1-1", 100);
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertTrue(result);
            assertTrue(output.contains("beteta") || output.contains("Abisua"));
        }

        @Test
        @DisplayName("Atera produktua eta gelaxka hutsik baina badira beste produktuak")
        void testAteraStocka_EmptyButMultipleProducts() {
            System.setOut(new PrintStream(outputStream));
            // Add two products to same shelf
            biltegia.sartuStocka("5449000000100", "AG1-1", 30);
            biltegia.sartuStocka("8437008888001", "AG1-1", 30);
            // Remove first one completely
            boolean result = biltegia.ateraStocka("5449000000100", "AG1-1", 30);
            System.setOut(originalOut);
            assertTrue(result);
            // Shelf should still have second product
            List<GelaxkaStock> items = biltegia.kontsultatuGelaxka("AG1-1");
            assertEquals(1, items.size());
        }

        @Test
        @DisplayName("Mugitu eta jatorrizko sartzea huts egiten duenean")
        void testMugituProduktua_RollbackOnDestInsertFailure() {
            System.setOut(new PrintStream(outputStream));
            // Manually set up a full destination
            biltegia.sartuStocka("8437008888001", "AH1-1", 100);
            // Try to move to full shelf
            boolean result = biltegia.mugituProduktua("5449000000100", "A1-1", "AH1-1", 5);
            System.setOut(originalOut);
            assertFalse(result);
        }

        @Test
        @DisplayName("Sartu produktua ez katalogoan baina beste balitzeke")
        void testBalidatuEAN13_NotInCatalog_Format() {
            System.setOut(new PrintStream(outputStream));
            // Wrong EAN format (too short)
            boolean result = biltegia.balidatuEAN13("12345");
            System.setOut(originalOut);
            assertFalse(result);
        }

        @Test
        @DisplayName("Gel boxka beteta lista egoera kontrola")
        void testGelaxkaBetetaLista_ControlFlow() {
            System.setOut(new PrintStream(outputStream));
            // Fill one
            biltegia.sartuStocka("5449000000100", "AI1-1", 100);
            // Try to add different product (should fail and not add to full list again)
            boolean result = biltegia.sartuStocka("8437008888001", "AI1-1", 1);
            System.setOut(originalOut);
            assertFalse(result);
        }

        @Test
        @DisplayName("Atera produktua eta gelaxka ez hutsik uneko kapazitate >0")
        void testAteraStocka_PartialRemoval() {
            System.setOut(originalOut);
            List<GelaxkaStock> beforeAtera = biltegia.kontsultatuGelaxka("A1-1");
            int kantitateBefore = beforeAtera.get(0).getKantitatea();
            biltegia.ateraStocka("5449000000100", "A1-1", 5);
            List<GelaxkaStock> afterAtera = biltegia.kontsultatuGelaxka("A1-1");
            assertEquals(kantitateBefore - 5, afterAtera.get(0).getKantitatea());
        }

        @Test
        @DisplayName("Erakutsi produktuak gelaxka bakarrarekin")
        void testErakutsiProduktuguztiakGelaxketan_SingleProduct() {
            System.setOut(new PrintStream(outputStream));
            // Create fresh biltegia with minimal setup
            Biltegia freshBiltegia = new Biltegia();
            freshBiltegia.erakutsiProduktuguztiakGelaxketan();
            String output = outputStream.toString();
            System.setOut(originalOut);
            assertTrue(output.contains("Gelaxka") && output.contains("EAN"));
        }

        @Test
        @DisplayName("Mugitu produktua jatorrizko kapazitate >0")
        void testMugituProduktua_SourceCapacityRemains() {
            System.setOut(originalOut);
            biltegia.sartuStocka("5449000000100", "AJ1-1", 50);
            biltegia.mugituProduktua("5449000000100", "AJ1-1", "A1-1", 20);
            List<GelaxkaStock> items = biltegia.kontsultatuGelaxka("AJ1-1");
            assertTrue(items.stream().anyMatch(item -> item.getKantitatea() == 30));
        }

        @Test
        @DisplayName("Balidatu kantitate =100 (max valid)")
        void testBalidatuKantitate_MaxValue100Percent() {
            System.setOut(originalOut);
            boolean result = biltegia.balidatuKantitatea(100);
            assertTrue(result); // 100 is valid (<=1000)
        }
    }

}

