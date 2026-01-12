import java.util.regex.Pattern;

/**
 * Biltegi-gelaxka baten stock-a adierazten duen klasea.
 * Gelaxka bakoitzak produktu zehatz baten kantitate bat gordetzen du.
 */
public class GelaxkaStock {
    private static final Pattern GELAXKA_PATTERN = Pattern.compile("^[A-Z]\\d+-\\d+$");
    private String gelaxkaID;
    private String produktuEAN13;
    private int kantitatea;

    /**
     * GelaxkaStock objektua sortzen du.
     * * @param gelaxkaID Gelaxkaren IDa (Formatua: LetraLarriaZenbakia-Zenbakia).
     * 
     * @param produktuEAN13 Produktuaren EAN kodea.
     * @param kantitatea    Produktu kopurua.
     * @throws IllegalArgumentException Gelaxka IDak formatu okerra badu.
     */
    public GelaxkaStock(String gelaxkaID, String produktuEAN13, int kantitatea) {
        if (!isValidGelaxkaID(gelaxkaID)) {
            throw new IllegalArgumentException("GelaxkaID invalid");
        }
        this.gelaxkaID = gelaxkaID;
        this.produktuEAN13 = produktuEAN13;
        this.kantitatea = kantitatea;
    }

    public String getGelaxkaID() {
        return gelaxkaID;
    }

    public String getProduktuEAN13() {
        return produktuEAN13;
    }

    public int getKantitatea() {
        return kantitatea;
    }

    public void setKantitatea(int kantitatea) {
        this.kantitatea = kantitatea;
    }

    /**
     * Gelaxka IDaren formatua balioztatzen du.
     * * @param id Egiaztatu beharreko IDa.
     * 
     * @return true formatua zuzena bada, false bestela.
     */
    private static boolean isValidGelaxkaID(String id) {
        return id != null && GELAXKA_PATTERN.matcher(id).matches();
    }

    @Override
    public String toString() {
        return "Gelaxka: " + gelaxkaID + ", EAN: " + produktuEAN13 + ", Kantitate: " + kantitatea;
    }
}