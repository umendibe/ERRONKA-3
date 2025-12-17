import java.util.regex.Pattern;

public class GelaxkaStock {
    private static final Pattern GELAXKA_PATTERN = Pattern.compile("^[A-Z]\\d+-\\d+$");

    private String gelaxkaID;
    private String produktuEAN13;
    private int kantitatea;

    public GelaxkaStock(String gelaxkaID, String produktuEAN13, int kantitatea) {
        if (!isValidGelaxkaID(gelaxkaID)) {
            throw new IllegalArgumentException("GelaxkaID invalid: " + gelaxkaID + ". Expected format: LetterNumber-Number (e.g. A1-1)");
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

    private static boolean isValidGelaxkaID(String id) {
        return id != null && GELAXKA_PATTERN.matcher(id).matches();
    }

    @Override
    public String toString() {
        return "Gelaxka: " + gelaxkaID + ", EAN: " + produktuEAN13 + ", Kantitate: " + kantitatea;
    }
}