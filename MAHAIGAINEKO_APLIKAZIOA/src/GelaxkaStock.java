public class GelaxkaStock {
    private String gelaxkaID;
    private String produktuEAN13;
    private int kantitatea;

    public GelaxkaStock(String gelaxkaID, String produktuEAN13, int kantitatea) {
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

    @Override
    public String toString() {
        return "Gelaxka: " + gelaxkaID + ", EAN: " + produktuEAN13 + ", Kantitate: " + kantitatea;
    }
}