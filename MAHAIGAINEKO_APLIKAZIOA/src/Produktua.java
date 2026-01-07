public class Produktua {
    private String ean13;
    private String izena;
    private String kategoria;

    public Produktua(String ean13, String izena, String kategoria) {
        this.ean13 = ean13;
        this.izena = izena;
        this.kategoria = kategoria;
    }

    public String getEan13() { return ean13; }
    public String getIzena() { return izena; }
    public String getKategoria() { return kategoria; }

    @Override
    public String toString() {
        return "[" + ean13 + "] " + izena + " (" + kategoria + ")";
    }
}