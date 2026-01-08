import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GelaxkaStockPrivateTest {

    @Test
    void testIsValidGelaxkaID_ValidInputs() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        assertTrue((Boolean) m.invoke(null, "A1-1"));
        assertTrue((Boolean) m.invoke(null, "B2-10"));
        assertTrue((Boolean) m.invoke(null, "Z9-99"));
        assertTrue((Boolean) m.invoke(null, "C5-5"));
        assertTrue((Boolean) m.invoke(null, "X1-100"));
        assertTrue((Boolean) m.invoke(null, "Y2-3"));
        assertTrue((Boolean) m.invoke(null, "D1-1"));
        assertTrue((Boolean) m.invoke(null, "E2-2"));
        assertTrue((Boolean) m.invoke(null, "F3-3"));
        assertTrue((Boolean) m.invoke(null, "G4-4"));
        assertTrue((Boolean) m.invoke(null, "H5-5"));
        assertTrue((Boolean) m.invoke(null, "I6-6"));
        assertTrue((Boolean) m.invoke(null, "J7-7"));
        assertTrue((Boolean) m.invoke(null, "K8-8"));
        assertTrue((Boolean) m.invoke(null, "L9-9"));
    }

    @Test
    void testIsValidGelaxkaID_InvalidInputs_Lowercase() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        assertFalse((Boolean) m.invoke(null, "a1-1"));
        assertFalse((Boolean) m.invoke(null, "b2-10"));
        assertFalse((Boolean) m.invoke(null, "z9-99"));
    }

    @Test
    void testIsValidGelaxkaID_InvalidInputs_WrongFormat() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        assertFalse((Boolean) m.invoke(null, "11-A"));        // digit-digit-dash-letter
        assertFalse((Boolean) m.invoke(null, "A11"));         // missing dash
        assertFalse((Boolean) m.invoke(null, "AA1-1"));       // double letter
        assertFalse((Boolean) m.invoke(null, "1A-1"));        // digit-letter instead
        assertFalse((Boolean) m.invoke(null, "A-1"));         // missing middle digits
        assertFalse((Boolean) m.invoke(null, "A1-"));         // missing final digits
        assertFalse((Boolean) m.invoke(null, "-1-1"));        // starts with dash
        assertFalse((Boolean) m.invoke(null, "A1-1-1"));      // extra dash
    }

    @Test
    void testIsValidGelaxkaID_InvalidInputs_Whitespace() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        assertFalse((Boolean) m.invoke(null, "A1 1"));        // space instead of dash
        assertFalse((Boolean) m.invoke(null, "A 1-1"));       // space after letter
        assertFalse((Boolean) m.invoke(null, "A1-1 "));       // trailing space
        assertFalse((Boolean) m.invoke(null, " A1-1"));       // leading space
    }

    @Test
    void testIsValidGelaxkaID_InvalidInputs_SpecialChars() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        assertFalse((Boolean) m.invoke(null, "A1@1"));
        assertFalse((Boolean) m.invoke(null, "A#1-1"));
        assertFalse((Boolean) m.invoke(null, "A1$-1"));
        assertFalse((Boolean) m.invoke(null, "A1%-1"));
    }

    @Test
    void testIsValidGelaxkaID_EmptyString() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        assertFalse((Boolean) m.invoke(null, ""));
    }

    @Test
    void testIsValidGelaxkaID_NullInput() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        assertFalse((Boolean) m.invoke(null, new Object[]{null}));
    }

    @Test
    void testIsValidGelaxkaID_BoundaryDigits() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        // Valid: single to many digits
        assertTrue((Boolean) m.invoke(null, "A0-0"));
        assertTrue((Boolean) m.invoke(null, "M99-999"));
        assertTrue((Boolean) m.invoke(null, "Z999-9999"));
    }

    @Test
    void testIsValidGelaxkaID_AllLetters() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        // Valid: test all uppercase letters
        for (char c = 'A'; c <= 'Z'; c++) {
            String id = c + "1-1";
            assertTrue((Boolean) m.invoke(null, id), "Failed for: " + id);
        }
    }

    @Test
    void testIsValidGelaxkaID_TooManyLeadingLetters() throws Exception {
        Method m = GelaxkaStock.class.getDeclaredMethod("isValidGelaxkaID", String.class);
        m.setAccessible(true);

        assertFalse((Boolean) m.invoke(null, "AB1-1"));
        assertFalse((Boolean) m.invoke(null, "ABC1-1"));
    }
}

