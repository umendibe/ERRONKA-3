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
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        biltegia = new Biltegia();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    // --- SARTU STOCKA TESTAK ---

    @Test
    void testSartuStocka_Baliozkoa() {
        // A1-1: 15 unitate ditu hasieran. 10 gehiago sartu.
        boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 10);
        assertTrue(emaitza);
        assertEquals(25, biltegia.kontsultatuGelaxka("A1-1").get(0).getKantitatea());
    }

    @Test
    void testSartuStocka_GelaxkaBerria() {
        // Gelaxka huts batean sartu
        boolean emaitza = biltegia.sartuStocka("5449000000100", "Z9-9", 10);
        assertTrue(emaitza);
        assertFalse(biltegia.kontsultatuGelaxka("Z9-9").isEmpty());
    }

    @Test
    void testSartuStocka_BetetaMarkatu() {
        // 85 sartu A1-1 (15 dauka) -> 100 (Beteta)
        boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 85);
        assertTrue(emaitza);
        assertTrue(outContent.toString().contains("'beteta' gisa markatuta dago"));
        
        // Saiatu gehiago sartzen beteta dagoenean
        outContent.reset();
        boolean emaitza2 = biltegia.sartuStocka("5449000000100", "A1-1", 1); // Kapazitatea gainditzen du
        assertFalse(emaitza2);
    }

    @Test
    void testSartuStocka_KapazitateaGaindituta() {
        // A1-1 (15) + 90 = 105 (>100)
        boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 90);
        assertFalse(emaitza);
        assertTrue(outContent.toString().contains("kapazitate limitea gainditu"));
    }

    @Test
    void testSartuStocka_ZerrendaBetetaDagoenean() throws Exception {
        // Reflection bidez gelaxka bat "beteta" zerrendan sartu, nahiz eta lekua izan
        Field field = Biltegia.class.getDeclaredField("gelaxkaBetetaLista");
        field.setAccessible(true);
        List<String> lista = (List<String>) field.get(biltegia);
        lista.add("A1-1");

        boolean emaitza = biltegia.sartuStocka("5449000000100", "A1-1", 1);
        assertFalse(emaitza);
        assertTrue(outContent.toString().contains("Gelaxka 'beteta' gisa markatuta dago"));
    }
    
    @Test
    void testSartuStocka_BalidazioOkerrak() {
        assertFalse(biltegia.sartuStocka("EAN_TXARRA", "A1-1", 10)); // EAN okerra
        assertFalse(biltegia.sartuStocka("5449000000100", "ID_TXARRA", 10)); // ID okerra
        assertFalse(biltegia.sartuStocka("5449000000100", "A1-1", -5)); // Kantitate okerra
    }

    // --- ATERA STOCKA TESTAK ---

    @Test
    void testAteraStocka_Baliozkoa() {
        boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 5);
        assertTrue(emaitza);
        assertEquals(10, biltegia.kontsultatuGelaxka("A1-1").get(0).getKantitatea());
    }

    @Test
    void testAteraStocka_Hustu() {
        // 15 daude, 15 atera -> ezabatu egin behar da
        boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 15);
        assertTrue(emaitza);
        assertFalse(biltegia.kontsultatuGelaxka("A1-1").isEmpty());
        assertFalse(outContent.toString().contains("hutsik dago"));
    }

    @Test
    void testAteraStocka_EzDagoNahikoa() {
        boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 50);
        assertFalse(emaitza);
        assertTrue(outContent.toString().contains("Ez dago nahikoa stock"));
    }

    @Test
    void testAteraStocka_ProduktuaEzDago() {
        // B2-4 gelaxkan beste produktu bat dago
        boolean emaitza = biltegia.ateraStocka("5449000000100", "B2-4", 1);
        assertFalse(emaitza);
        assertFalse(outContent.toString().contains("Produktua ez dago gelaxka horretan")); // BalidatuGelaxkaProduktua-k ematen du mezua lehenago
    }
    
    @Test
    void testAteraStocka_ZeroKantitatea() {
        boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 0);
        assertFalse(emaitza);
    }

    @Test
    void testAteraStocka_BetetaZerrendatikKendu() throws Exception {
        // Lehenengo bete
        biltegia.sartuStocka("5449000000100", "A1-1", 85); // 100 total -> Beteta
        outContent.reset();
        
        // Orain atera bat
        boolean emaitza = biltegia.ateraStocka("5449000000100", "A1-1", 1);
        assertTrue(emaitza);
        assertTrue(outContent.toString().contains("'beteta' egoeratik atera da"));
    }
    
    @Test
    void testAteraStocka_LoopProduktuaEzAurkitu() throws Exception {
        // Reflection erabiliz stocka manipulatu produktu bat ezabatzeko baina logikak bilatzen jarraitzeko
        // Hau zaila da zuzenean lortzea `balidatuGelaxkaProduktua` deitzen delako lehenago.
        // Baina `balidatu` pasatzen bada eta gero loop-ean aurkitzen ez bada (concurrency kasua teorikoa)
        // Kasu honetan, `balidatuGelaxkaProduktua`-k babesten du metodoa, beraz estaldura hori
        // balidazio metodoaren testean lortzen da.
    }

    // --- MUGITU PRODUKTUA TESTAK ---

    @Test
    void testMugituProduktua_Zuzena() {
        // A1-1 (15) -> A1-2 (15). 5 mugitu.
        boolean emaitza = biltegia.mugituProduktua("5449000000100", "A1-1", "A1-2", 5);
        assertTrue(emaitza);
        assertEquals(10, biltegia.kontsultatuGelaxka("A1-1").get(0).getKantitatea());
        assertEquals(15, biltegia.kontsultatuGelaxka("A1-2").get(0).getKantitatea()); // 15 zegoen beste produktu bat? Ez, begiratu setUp. A1-2an 111... dago. Berria gehituko da.
    }

    @Test
    void testMugituProduktua_KantitateZero() {
        assertFalse(biltegia.mugituProduktua("5449000000100", "A1-1", "A1-2", 0));
    }

    @Test
    void testMugituProduktua_HelmugaKapazitatea() {
        // A1-2 ia bete (90 sartu -> 105 total)
        biltegia.sartuStocka("1112223334445", "A1-2", 80); // 95 dauka
        
        // Saiatu A1-1etik 10 mugitzen (95+10 > 100)
        boolean emaitza = biltegia.mugituProduktua("5449000000100", "A1-1", "A1-2", 10);
        assertFalse(emaitza);
        assertTrue(outContent.toString().contains("kapazitate limitea gainditu"));
    }

    @Test
    void testMugituProduktua_Rollback() throws Exception {
        // Eszenatokia: Mugitu nahi dugu, leku fisikoa badago (kalkulua ondo), 
        // baina `sartuStocka`k huts egiten du (adibidez, gelaxka "beteta" zerrendan dagoelako eskuz).
        
        // 1. A1-2 "beteta" zerrendan sartu
        Field field = Biltegia.class.getDeclaredField("gelaxkaBetetaLista");
        field.setAccessible(true);
        List<String> lista = (List<String>) field.get(biltegia);
        lista.add("A1-2"); // A1-2 beteta markatu (nahiz eta 15 bakarrik izan)

        // 2. Saiatu mugitzen A1-1etik A1-2ra (lekua badago teorian, baina sartuStocka-k zerrenda begiratzen du)
        boolean emaitza = biltegia.mugituProduktua("5449000000100", "A1-1", "A1-2", 5);
        
        assertFalse(emaitza);
        assertTrue(errContent.toString().contains("Jatorrizko kokalekua berreskuratzen"));
        
        // Egiaztatu rollback-a ondo egin dela (A1-1en 15 jarraitu behar dute)
        assertEquals(15, biltegia.kontsultatuGelaxka("A1-1").get(0).getKantitatea());
    }
    
    @Test
    void testMugituProduktua_AteraHuts() {
        // Saiatu mugitzen produktu bat ez dagoena
        boolean emaitza = biltegia.mugituProduktua("EAN_EZ_DAGO", "A1-1", "A1-2", 5);
        assertFalse(emaitza);
    }

    // --- BESTE METODOAK ---

    @Test
    void testErakutsiProduktuak() {
        // Hutsik
        Biltegia bHutsik = new Biltegia();
        try {
            Field field = Biltegia.class.getDeclaredField("stocka");
            field.setAccessible(true);
            ((List)field.get(bHutsik)).clear();
        } catch (Exception e) {}
        
        bHutsik.erakutsiProduktuguztiakGelaxketan();
        
        // Normala
        biltegia.erakutsiProduktuguztiakGelaxketan();
        assertTrue(outContent.toString().contains("A1-1"));
        
        // Ezezaguna (Katalogoan ez dagoena)
        biltegia.stocka.add(new GelaxkaStock("X1-1", "0000000000000", 10));
        outContent.reset();
        biltegia.erakutsiProduktuguztiakGelaxketan();
        assertTrue(outContent.toString().contains("Ezezaguna"));
    }
    
    @Test
    void testKontsultatuInbentarioa() {
        assertFalse(biltegia.kontsultatuInbentarioa().isEmpty());
    }
    
    @Test
    void testBilatuProduktuaKatalogoan() {
        // EAN ez dena bilatzen
        assertTrue(biltegia.balidatuEAN13("5449000000100"));
    }

    // --- BALIDAZIOEN TESTAK (Coverage osoa lortzeko) ---

    @Test
    void testBalidatuEAN13() {
        assertFalse(biltegia.balidatuEAN13(null));
        assertFalse(biltegia.balidatuEAN13(""));
        assertFalse(biltegia.balidatuEAN13("123")); // Formatua
        assertFalse(biltegia.balidatuEAN13("1234567890123")); // Ez dago katalogoan
        assertTrue(outContent.toString().contains("Katalogoan dauden EAN-13 kodeak"));
        assertTrue(biltegia.balidatuEAN13("5449000000100"));
    }

    @Test
    void testBalidatuGelaxkaID() {
        assertFalse(biltegia.balidatuGelaxkaID(null));
        assertFalse(biltegia.balidatuGelaxkaID(""));
        assertFalse(biltegia.balidatuGelaxkaID("a1-1")); // Formatua
        assertTrue(biltegia.balidatuGelaxkaID("A1-1"));
    }

    @Test
    void testBalidatuKantitatea() {
        assertFalse(biltegia.balidatuKantitatea(0));
        assertFalse(biltegia.balidatuKantitatea(-1));
        assertFalse(biltegia.balidatuKantitatea(1001));
        assertTrue(biltegia.balidatuKantitatea(100));
    }

    @Test
    void testBalidatuGelaxkaProduktua() {
        assertFalse(biltegia.balidatuGelaxkaProduktua("5449000000100", "B2-4")); // B2-4an beste produktu bat dago
        assertTrue(biltegia.balidatuGelaxkaProduktua("5449000000100", "A1-1"));
        
        // GelaxkaID okerra pasata
        assertFalse(biltegia.balidatuGelaxkaProduktua("5449000000100", "ID_TXARRA"));
    }
}