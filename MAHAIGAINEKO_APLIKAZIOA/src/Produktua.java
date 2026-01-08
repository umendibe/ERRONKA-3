/**
 * Biltegian kudeatzen den produktu baten informazioa gordetzen duen klasea.
 * EAN-13 kodea, izena eta kategoria ditu.
 */
public class Produktua {
    private String ean13;
    private String izena;
    private String kategoria;

    /**
     * Produktu berri bat sortzen du.
     *
     * @param ean13     Produktuaren EAN-13 kodea.
     * @param izena     Produktuaren izena.
     * @param kategoria Produktuaren kategoria.
     */
    public Produktua(String ean13, String izena, String kategoria) {
        this.ean13 = ean13;
        this.izena = izena;
        this.kategoria = kategoria;
    }

    /**
     * Produktuaren EAN-13 kodea itzultzen du.
     * @return EAN-13 kodea.
     */
    public String getEan13() { return ean13; }

    /**
     * Produktuaren izena itzultzen du.
     * @return Izena.
     */
    public String getIzena() { return izena; }

    /**
     * Produktuaren kategoria itzultzen du.
     * @return Kategoria.
     */
    public String getKategoria() { return kategoria; }
    
    /**
     * Produktuaren informazioa testu formatuan itzultzen du.
     * @return String formatua: [EAN] Izena (Kategoria)
     */
    @Override
    public String toString() {
        return "[" + ean13 + "] " + izena + " (" + kategoria + ")";
    }
}