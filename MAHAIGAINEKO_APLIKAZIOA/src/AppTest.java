import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void testMain_ExitsOnZeroOption() {
        testMenu("0\n", "Aplikazioa ixten");
    }

    // ===== stockaKudeatuMenua = Menu 1 =====
    @Test
    void testStockaKudeatuMenua_Option1_Sartu() {
        testMenu("1\n1\n5449000000100\nE5-5\n5\n0\n", "arrakastaz");
    }

    @Test
    void testStockaKudeatuMenua_Option2_Atera() {
        testMenu("1\n2\n5449000000100\nA1-1\n5\n0\n", "arrakastaz");
    }

    @Test
    void testStockaKudeatuMenua_InvalidOption() {
        testMenu("1\n99\n0\n", "Menu");
    }

    @Test
    void testStockaKudeatuMenua_NoOption_ReturnToMain() {
        testMenu("1\n3\n0\n", "Amaitu");
    }

    // ===== stockaSartu specific paths =====
    @Test
    void testStockaSartu_InvalidEAN() {
        testMenu("1\n1\ninvalid\n0\n", "Operazioa");
    }

    @Test
    void testStockaSartu_InvalidGelaxkaID() {
        testMenu("1\n1\n5449000000100\ninvalid\n0\n", "Operazioa");
    }

    @Test
    void testStockaSartu_InvalidQuantity_Negative() {
        testMenu("1\n1\n5449000000100\nA1-1\n-5\n0\n", "Operazioa");
    }

    @Test
    void testStockaSartu_NonIntegerQuantity() {
        testMenu("1\n1\n5449000000100\nA1-1\nabc\n0\n", "Errorea");
    }

    @Test
    void testStockaSartu_SuccessFull() {
        testMenu("1\n1\n5449000000100\nL1-1\n30\n0\n", "arrakastaz");
    }

    // ===== stockaAtera specific paths =====
    @Test
    void testStockaAtera_InvalidEAN() {
        testMenu("1\n2\ninvalid\n0\n", "Operazioa");
    }

    @Test
    void testStockaAtera_InvalidGelaxkaID() {
        testMenu("1\n2\n5449000000100\ninvalid\n0\n", "Operazioa");
    }

    @Test
    void testStockaAtera_ProductNotInGelaxka() {
        testMenu("1\n2\n9999999999999\n0\n", "Operazioa");
    }

    @Test
    void testStockaAtera_InvalidQuantity_Negative() {
        testMenu("1\n2\n5449000000100\nA1-1\n-5\n0\n", "Operazioa");
    }

    @Test
    void testStockaAtera_NonIntegerQuantity() {
        testMenu("1\n2\n5449000000100\nA1-1\nabc\n0\n", "Errorea");
    }

    @Test
    void testStockaAtera_SuccessPartial() {
        testMenu("1\n2\n5449000000100\nA1-1\n5\n0\n", "arrakastaz");
    }

    // ===== mugimenduakMenua = Menu 3 =====
    @Test
    void testMugimenduakMenua_Option1() {
        testMenu("3\n1\n5449000000100\nA1-1\nM1-1\n3\n0\n", "arrakastatsua");
    }

    @Test
    void testMugimenduakMenua_Option2_NotImplemented() {
        testMenu("3\n2\n0\n", "inplementatuta");
    }

    @Test
    void testMugimenduakMenua_InvalidOption() {
        testMenu("3\n99\n0\n", "Menu");
    }

    @Test
    void testMugimenduakMenua_NoOption_ReturnToMain() {
        testMenu("3\n3\n0\n", "Amaitu");
    }

    // ===== produktuMugimendua specific paths =====
    @Test
    void testProduktua_Mugitu_InvalidEAN() {
        testMenu("3\n1\ninvalid\n0\n", "Operazioa");
    }

    @Test
    void testProduktua_Mugitu_InvalidSourceGelaxka() {
        testMenu("3\n1\n5449000000100\ninvalid\n0\n", "Operazioa");
    }

    @Test
    void testProduktua_Mugitu_ProductNotInSource() {
        testMenu("3\n1\n9999999999999\nA1-1\n0\n", "Operazioa");
    }

    @Test
    void testProduktua_Mugitu_InvalidDestGelaxka() {
        testMenu("3\n1\n5449000000100\nA1-1\ninvalid\n0\n", "Operazioa");
    }

    @Test
    void testProduktua_Mugitu_InvalidQuantity_Negative() {
        testMenu("3\n1\n5449000000100\nA1-1\nN1-1\n-5\n0\n", "Operazioa");
    }

    @Test
    void testProduktua_Mugitu_NonIntegerQuantity() {
        testMenu("3\n1\n5449000000100\nA1-1\nN1-1\nabc\n0\n", "Errorea");
    }

    // ===== kontsultakMenua = Menu 2 =====
    @Test
    void testKontsultakMenua_Option1_GelaxkaKontsultatu() {
        testMenu("2\n1\nA1-1\n0\n", "Amaitu");
    }

    @Test
    void testKontsultakMenua_Option1_EmptyGelaxka() {
        testMenu("2\n1\nZ99-99\n0\n", "Amaitu");
    }

    @Test
    void testKontsultakMenua_Option2_ProduktuguztiakGelaxketan() {
        testMenu("2\n2\n0\n", "PRODUKTU");
    }

    @Test
    void testKontsultakMenua_Option3_InbentarioOsoa() {
        testMenu("2\n3\n0\n", "Amaitu");
    }

    @Test
    void testKontsultakMenua_Option4_NotImplemented() {
        testMenu("2\n4\n0\n", "inplementatuta");
    }

    @Test
    void testKontsultakMenua_InvalidOption() {
        testMenu("2\n99\n0\n", "Menu");
    }

    @Test
    void testKontsultakMenua_NoOption_ReturnToMain() {
        testMenu("2\n5\n0\n", "Amaitu");
    }

    // ===== Main menu edge cases =====
    @Test
    void testMain_InvalidMenuOption() {
        testMenu("99\n0\n", "desegokia");
    }

    @Test
    void testMain_NonIntegerMainMenu() {
        testMenu("xyz\n0\n", "Zenbaki");
    }

    // ===== Helper method =====
    private void testMenu(String input, String expectedOutput) {
        final PrintStream originalOut = System.out;
        final java.io.InputStream originalIn = System.in;

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(input + "\n".getBytes());
            System.setIn(in);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));

            App.main(new String[]{});

            String output = baos.toString();
            assertTrue(output.contains(expectedOutput), 
                "Expected output to contain '" + expectedOutput + "' but got:\n" + output);
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
    }

    // ===== Additional test for inbentarioOsoaErakutsi with direct access =====
    @Test
    void testInbentarioOsoa_DirectOutput() {
        testMenu("2\n3\n0\n", "Amaitu");
    }

    @Test
    void testProductuMugimendua_SuccessfulMovement() {
        testMenu("3\n1\n5449000000100\nA1-1\nB2-4\n5\n0\n", "arrakastatsua");
    }

    @Test
    void testErakutsiProduktuguztiakGelaxketan_Option3() {
        testMenu("2\n3\n0\n", "PRODUKTU");
    }
}
