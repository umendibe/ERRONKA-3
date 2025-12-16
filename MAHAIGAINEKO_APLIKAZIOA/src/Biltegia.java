import java.util.ArrayList;
import java.util.List;

public class Biltegia {
    private List<Produktua> produktuKatalogoa;
    protected List<GelaxkaStock> stocka;
    private List<String> gelaxkaBetetaLista;

    public Biltegia() {
        produktuKatalogoa = new ArrayList<>();
        stocka = new ArrayList<>();
        gelaxkaBetetaLista = new ArrayList<>();
        kargatuHasierakoDatuak();
    }

    private void kargatuHasierakoDatuak() {
        // Produktu motak
        produktuKatalogoa.add(new Produktua("5449000000100", "Kamiseta Zubieta", "Kamisetak"));
        produktuKatalogoa.add(new Produktua("8437008888001", "Kirol Jertse Gorria", "Kirol-jertseak"));
        produktuKatalogoa.add(new Produktua("1112223334445", "Galtza Bakeroak Unisex", "Galtzak"));

        // Hasierako stocka gelaxketan
        stocka.add(new GelaxkaStock("A1-1", "5449000000100", 15));
        stocka.add(new GelaxkaStock("A1-2", "1112223334445", 15));
        stocka.add(new GelaxkaStock("B2-4", "8437008888001", 15));

        gelaxkaBetetaLista.add("B2-4");
    }

    public boolean sartuStocka(String ean13, String gelaxkaID, int kantitatea) {
        if (kantitatea <= 0)
            return false;

        if (gelaxkaBetetaLista.contains(gelaxkaID)) {
            System.out.println("Errorea: Gelaxka 'beteta' gisa markatuta dago. Ezin da sartu.");
            return false;
        }

        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID) && item.getProduktuEAN13().equals(ean13)) {
                item.setKantitatea(item.getKantitatea() + kantitatea);
                if (item.getKantitatea() > 100) {
                    gelaxkaBetetaLista.add(gelaxkaID);
                    System.out.println("Abisua: Gelaxka " + gelaxkaID + " 'beteta' gisa markatuta dago.");
                }
                return true;
            }
        }

        // Sarrera berri bat sortu
        stocka.add(new GelaxkaStock(gelaxkaID, ean13, kantitatea));
        return true;
    }

    public boolean ateraStocka(String ean13, String gelaxkaID, int kantitatea) {
        if (kantitatea <= 0)
            return false;

        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID) && item.getProduktuEAN13().equals(ean13)) {
                if (item.getKantitatea() >= kantitatea) {
                    item.setKantitatea(item.getKantitatea() - kantitatea);
                    if (item.getKantitatea() < 100 && gelaxkaBetetaLista.contains(gelaxkaID)) {
                        gelaxkaBetetaLista.remove(gelaxkaID);
                        System.out.println("Abisua: Gelaxka " + gelaxkaID + " 'beteta' egoeratik atera da.");
                    }
                    return true;
                } else {
                    System.out.println("Errorea: Ez dago nahikoa stock gelaxka horretan.");
                    return false;
                }
            }
        }
        System.out.println("Errorea: Produktu hori ez da aurkitu gelaxka horretan.");
        return false;
    }

    public boolean mugituProduktua(String ean13, String jatorrizkoGelaxkaID, String helmugaGelaxkaID, int kantitatea) {
        if (kantitatea <= 0)
            return false;

        boolean ateraDa = ateraStocka(ean13, jatorrizkoGelaxkaID, kantitatea);

        if (!ateraDa)
            return false;

        boolean sartuDa = sartuStocka(ean13, helmugaGelaxkaID, kantitatea);

        if (sartuDa) {
            return true;
        } else {
            System.err.println("Mugimendua ezin izan da osatu. Jatorrizko kokalekua berreskuratzen.");
            sartuStocka(ean13, jatorrizkoGelaxkaID, kantitatea);
            return false;
        }
    }

    public List<GelaxkaStock> kontsultatuGelaxka(String gelaxkaID) {
        List<GelaxkaStock> emaitzak = new ArrayList<>();
        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID)) {
                emaitzak.add(item);
            }
        }
        return emaitzak;
    }

    public List<GelaxkaStock> kontsultatuInbentarioa() {
        return stocka;
    }
}