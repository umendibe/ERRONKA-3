import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    // --- HELPER METODOA: App.java aldatu gabe Scanner berritzeko (Reflection) ---
    private void testAppExecution(String input, String... expectedOutputs) throws Exception {
        // Inputa prestatu
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Outputa harrapatzeko prestatu
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // REFLECTION: App.java-ko 'scanner' estatikoa berritu
        Field field = App.class.getDeclaredField("scanner");
        field.setAccessible(true);
        field.set(null, new Scanner(System.in));

        // Main exekutatu
        App.main(new String[]{});

        // Egiaztapenak
        String output = baos.toString();
        for (String expected : expectedOutputs) {
            assertTrue(output.contains(expected), 
                "Espero zen testua: '" + expected + "'\nEz da aurkitu irteeran.");
        }
    }

    @Test
    void testMenuNagusia_Aukerak() throws Exception {
        // 1. Aukera okerra (99) -> 2. Letra (abc) -> 3. Irten (0)
        String input = "99\nabc\n0\n";
        testAppExecution(input, 
            "Aukera desegokia", 
            "Zenbaki bat sartu behar duzu", 
            "Aplikazioa ixten");
    }

    @Test
    void testStockaSartu_FluxuZuzena() throws Exception {
        // 1 (Stocka) -> 1 (Sartu) -> EAN -> ID -> Kantitatea -> 0 (Irten)
        String input = "1\n1\n5449000000100\nA1-1\n10\n0\n";
        testAppExecution(input, 
            "Stocka arrakastaz sartu da");
    }

    @Test
    void testStockaSartu_Erroreak() throws Exception {
        // Fluxua errorekin:
        // 1 -> 1 
        // -> EAN okerra (menura itzuli)
        // 1 -> 1 -> EAN ona -> ID okerra (menura itzuli)
        // 1 -> 1 -> EAN ona -> ID ona -> Kantitatea letra (errorea) -> Kantitatea zenbakia baina balidazio okerra (menura)
        // 1 -> 1 -> EAN ona -> ID ona -> Kantitatea ona (sartu)
        // 0
        
        String input = 
            // EAN okerra
            "1\n1\nEAN_TXARRA\n" +
            // ID okerra
            "1\n1\n5449000000100\nID_TXARRA\n" +
            // Kantitatea letra
            "1\n1\n5449000000100\nA1-1\nabc\n" + // Hemen 'abc' irakurtzen saiatzen da, Scanner-ek garbitzen du, eta menura itzultzen da (return egiten duelako metodoak errorearekin)
            // Kantitatea balio okerra (logic)
            "1\n1\n5449000000100\nA1-1\n-5\n" +
            // 0 (Irten)
            "0\n";
            
        testAppExecution(input, 
            "EAN-13 kodeak 13 digitu", 
            "Gelaxka IDaren formatua okerra", 
            "Kantitatea zenbaki bat izan behar da",
            "Kantitatea 0 baino handiagoa");
    }
    
    @Test
    void testStockaSartu_KapazitateaGainditutaApp() throws Exception {
        // Biltegiak false itzultzen duenean
        // 1 -> 1 -> EAN -> ID -> Kantitatea 90 (15+90 > 100) -> 0
        String input = "1\n1\n5449000000100\nA1-1\n90\n0\n";
        testAppExecution(input, "Errorea Stocka sartzerakoan");
    }

    @Test
    void testStockaAtera_FluxuZuzena() throws Exception {
        // 1 (Stocka) -> 2 (Atera) -> EAN -> ID -> Kantitatea -> 0
        String input = "1\n2\n5449000000100\nA1-1\n5\n0\n";
        testAppExecution(input, "Stocka arrakastaz atera da");
    }

    @Test
    void testStockaAtera_Erroreak() throws Exception {
        // 1 -> 2 -> EAN okerra
        // 1 -> 2 -> EAN -> ID okerra
        // 1 -> 2 -> EAN -> ID -> Produktua ez dago
        // 1 -> 2 -> EAN -> ID -> Kantitatea letra
        // 1 -> 2 -> EAN -> ID -> Kantitatea negatiboa
        // 0
        String input = 
            "1\n2\nEAN_TXARRA\n" +
            "1\n2\n5449000000100\nID_TXARRA\n" +
            "1\n2\n5449000000100\nB2-4\n" + // B2-4an beste produktu bat dago
            "1\n2\n5449000000100\nA1-1\nabc\n" +
            "1\n2\n5449000000100\nA1-1\n-5\n" +
            "0\n";
            
        testAppExecution(input, 
            "EAN-13 kodeak", 
            "Gelaxka IDaren formatua", 
            "Produktua ez dago gelaxka horretan", // BalidatuGelaxkaProduktua-k ematen du
            "Kantitatea zenbaki bat izan behar da",
            "Kantitatea 0 baino handiagoa");
    }
    
    @Test
    void testStockaAtera_BiltegiaFail() throws Exception {
        // Biltegiak false itzultzen du (adibidez nahikoa stock ez dagoenean)
        // 1 -> 2 -> EAN -> ID -> Kantitatea 50 -> 0
        String input = "1\n2\n5449000000100\nA1-1\n50\n0\n";
        testAppExecution(input, "Errorea Stocka ateratzerakoan");
    }

    @Test
    void testKontsultak() throws Exception {
        // 2 -> 1 (Gelaxka) -> ID -> 2 -> 2 (Inbentarioa) -> 2 -> 3 (Guztiak) -> 2 -> 4 (Inplementatu gabe) -> 0
        String input = "2\n1\nA1-1\n" + 
                       "2\n1\nZ9-9\n" + // Hutsik dagoen gelaxka
                       "2\n2\n" + 
                       "2\n3\n" + 
                       "2\n4\n" + 
                       "0\n";
        testAppExecution(input, 
            "Gelaxka A1-1 Edukia", 
            "Gelaxka hutsa dago edo ez da aurkitu", 
            "Inbentario Osoaren Txostena", 
            "PRODUKTU GUZTIAK GELAXKETAN", 
            "ez dago inplementatuta");
    }

    @Test
    void testMugimenduak_Zuzena() throws Exception {
        // 3 -> 1 (Mugitu) -> EAN -> Jat -> Hel -> Kant -> 0
        String input = "3\n1\n5449000000100\nA1-1\nA1-2\n5\n0\n";
        testAppExecution(input, "Mugimendua arrakastatsua");
    }
    
    @Test
    void testMugimenduak_Erroreak() throws Exception {
        // 3 -> 1 -> EAN okerra
        // 3 -> 1 -> EAN -> Jat okerra
        // 3 -> 1 -> EAN -> Jat -> Produktua ez dago
        // 3 -> 1 -> EAN -> Jat -> Hel okerra
        // 3 -> 1 -> EAN -> Jat -> Hel -> Kantitatea letra
        // 3 -> 1 -> EAN -> Jat -> Hel -> Kantitatea negatiboa
        // 3 -> 2 (Transferitu inplementatu gabe)
        // 0
        String input = 
            "3\n1\nEAN_TXARRA\n" +
            "3\n1\n5449000000100\nID_TXARRA\n" +
            "3\n1\n5449000000100\nB2-4\n" +
            "3\n1\n5449000000100\nA1-1\nID_TXARRA\n" +
            "3\n1\n5449000000100\nA1-1\nA1-2\nabc\n" +
            "3\n1\n5449000000100\nA1-1\nA1-2\n-5\n" +
            "3\n2\n" + 
            "0\n";
            
        testAppExecution(input, 
            "EAN-13 kodeak", 
            "Gelaxka IDaren formatua", 
            "Operazioa bertan behera", 
            "Kantitatea zenbaki bat",
            "ez dago inplementatuta");
    }
    
    @Test
    void testMugimenduak_BiltegiaFail() throws Exception {
        // Helmugako kapazitatea gainditzen duena
        // 3 -> 1 -> EAN -> Jat -> Hel -> Kant 100 -> 0
        String input = "3\n1\n5449000000100\nA1-1\nA1-2\n100\n0\n";
        testAppExecution(input, "Mugimendua ez da burutu");
    }
}