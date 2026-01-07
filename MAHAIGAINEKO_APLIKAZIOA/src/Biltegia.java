import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Biltegia {
    private List<Produktua> produktuKatalogoa;
    protected List<GelaxkaStock> stocka;
    protected List<String> gelaxkaBetetaLista;

    public Biltegia() {
        produktuKatalogoa = new ArrayList<>();
        stocka = new ArrayList<>();
        gelaxkaBetetaLista = new ArrayList<>();
        kargatuHasierakoDatuak();
    }

    protected void kargatuHasierakoDatuak() {
        produktuKatalogoa.add(new Produktua("5449000000100", "Kamiseta Zubieta", "Kamisetak"));
        produktuKatalogoa.add(new Produktua("8437008888001", "Kirol Jertse Gorria", "Kirol-jertseak"));
        produktuKatalogoa.add(new Produktua("1112223334445", "Galtza Bakeroak Unisex", "Galtzak"));

        stocka.add(new GelaxkaStock("A1-1", "5449000000100", 15));
        stocka.add(new GelaxkaStock("A1-2", "1112223334445", 15));
        stocka.add(new GelaxkaStock("B2-4", "8437008888001", 15));
    }

    public boolean sartuStocka(String ean13, String gelaxkaID, int kantitatea) {
        if (!balidatuEAN13(ean13) || !balidatuGelaxkaID(gelaxkaID) || !balidatuKantitatea(kantitatea)) return false;

        int gelaxkaKapazitatea = kalkulatuGelaxkaKapazitatea(gelaxkaID);
        
        if (gelaxkaKapazitatea + kantitatea > 100) {
            System.out.println("ERROREA: " + gelaxkaID + " gelaxkak ez du leku nahikorik (Max 100, unean: " + gelaxkaKapazitatea + ").");
            return false;
        }

        if (gelaxkaBetetaLista.contains(gelaxkaID)) {
            System.out.println("ERROREA: " + gelaxkaID + " gelaxka BETETA gisa markatuta dago.");
            return false;
        }

        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID) && item.getProduktuEAN13().equals(ean13)) {
                item.setKantitatea(item.getKantitatea() + kantitatea);
                if (item.getKantitatea() >= 100) gelaxkaBetetaLista.add(gelaxkaID);
                return true;
            }
        }

        stocka.add(new GelaxkaStock(gelaxkaID, ean13, kantitatea));
        if (gelaxkaKapazitatea + kantitatea >= 100) gelaxkaBetetaLista.add(gelaxkaID);
        return true;
    }

    public boolean ateraStocka(String ean13, String gelaxkaID, int kantitatea) {
        if (!balidatuGelaxkaProduktua(ean13, gelaxkaID)) return false;

        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID) && item.getProduktuEAN13().equals(ean13)) {
                if (item.getKantitatea() >= kantitatea) {
                    item.setKantitatea(item.getKantitatea() - kantitatea);
                    if (item.getKantitatea() < 100) gelaxkaBetetaLista.remove(gelaxkaID);
                    if (item.getKantitatea() == 0) kenduGelaxkaHutsik(gelaxkaID);
                    return true;
                } else {
                    System.out.println("ERROREA: Ez dago nahikoa unitate (stockean: " + item.getKantitatea() + ").");
                }
            }
        }
        return false;
    }

    public boolean mugituProduktua(String ean13, String jat, String hel, int kan) {
        if (!balidatuGelaxkaID(hel)) return false;
        if (ateraStocka(ean13, jat, kan)) {
            if (sartuStocka(ean13, hel, kan)) return true;
            sartuStocka(ean13, jat, kan); // Rollback
        }
        return false;
    }

    public boolean transferituGelaxka(String jat, String hel) {
        if (!balidatuGelaxkaID(hel) || !balidatuGelaxkaID(jat)) return false;
        
        // Jatorrizko gelaxkan dagoenaren kopia lortu (ConcurrentModificationException ekiditeko)
        List<GelaxkaStock> jatorrizkoStockKopia = new ArrayList<>();
        for (GelaxkaStock s : stocka) {
            if (s.getGelaxkaID().equals(jat)) {
                jatorrizkoStockKopia.add(new GelaxkaStock(s.getGelaxkaID(), s.getProduktuEAN13(), s.getKantitatea()));
            }
        }

        if (jatorrizkoStockKopia.isEmpty()) {
            System.out.println("ERROREA: Jatorrizko gelaxka (" + jat + ") hutsik dago.");
            return false;
        }

        int guztiraMugitu = jatorrizkoStockKopia.stream().mapToInt(GelaxkaStock::getKantitatea).sum();
        int helmugaLekua = 100 - kalkulatuGelaxkaKapazitatea(hel);

        if (guztiraMugitu > helmugaLekua) {
            System.out.println("ERROREA: Helmugan ez dago leku nahikorik den dena mugitzeko.");
            return false;
        }

        for (GelaxkaStock item : jatorrizkoStockKopia) {
            String ean = item.getProduktuEAN13();
            int kant = item.getKantitatea();
            if (ateraStocka(ean, jat, kant)) {
                if (!sartuStocka(ean, hel, kant)) {
                    sartuStocka(ean, jat, kant); // Rollback errore baten kasuan
                }
            }
        }
        return true;
    }

    public boolean balidatuEAN13(String ean13) {
        if (ean13 == null || !ean13.matches("\\d{13}")) {
            System.out.println("ERROREA: EAN-13 kodea 13 digituko zenbakia izan behar da.");
            return false;
        }
        if (billatuProduktua(ean13) == null) {
            System.out.println("ERROREA: " + ean13 + " kodea ez dago katalogoan.");
            return false;
        }
        return true;
    }

    public boolean balidatuGelaxkaID(String id) {
        if (id == null || !id.matches("[A-Z]\\d+-\\d+")) {
            System.out.println("ERROREA: GelaxkaID formatu okerra. Adibidez: A1-1, B12-3.");
            return false;
        }
        return true;
    }

    public boolean balidatuKantitatea(int k) {
        if (k <= 0) {
            System.out.println("ERROREA: Kantitatea 0 baino handiagoa izan behar da.");
            return false;
        }
        return true;
    }

    public boolean balidatuGelaxkaProduktua(String ean, String id) {
        if (!balidatuGelaxkaID(id)) return false;
        boolean aurkitua = stocka.stream().anyMatch(i -> i.getGelaxkaID().equals(id) && i.getProduktuEAN13().equals(ean));
        if (!aurkitua) {
            System.out.println("ERROREA: " + id + " gelaxkan ez dago " + ean + " produktua.");
        }
        return aurkitua;
    }

    public Produktua billatuProduktua(String ean13) {
        for (Produktua p : produktuKatalogoa) if (p.getEan13().equals(ean13)) return p;
        return null;
    }

    public List<GelaxkaStock> kontsultatuGelaxka(String id) {
        List<GelaxkaStock> res = new ArrayList<>();
        for (GelaxkaStock s : stocka) if (s.getGelaxkaID().equals(id)) res.add(s);
        return res;
    }

    public void erakutsiProduktuguztiakGelaxketan() {
        if (stocka.isEmpty()) { System.out.println("Biltegia hutsik."); return; }
        Map<String, List<GelaxkaStock>> map = new LinkedHashMap<>();
        for (GelaxkaStock s : stocka) map.computeIfAbsent(s.getGelaxkaID(), k -> new ArrayList<>()).add(s);
        map.forEach((id, edukiak) -> {
            int kap = edukiak.stream().mapToInt(GelaxkaStock::getKantitatea).sum();
            System.out.println("Gelaxka: " + id + " [" + kap + "/100] " + (gelaxkaBetetaLista.contains(id) ? "(BETETA)" : ""));
            for (GelaxkaStock s : edukiak) System.out.println("  -> " + billatuProduktua(s.getProduktuEAN13()) + " (" + s.getKantitatea() + ")");
        });
    }

    public List<Produktua> bilatuProduktuaKatalogoan(String t) {
        List<Produktua> res = new ArrayList<>();
        for (Produktua p : produktuKatalogoa) if (p.getEan13().equals(t) || p.getKategoria().equalsIgnoreCase(t)) res.add(p);
        return res;
    }

    private int kalkulatuGelaxkaKapazitatea(String id) {
        return stocka.stream().filter(i -> i.getGelaxkaID().equals(id)).mapToInt(GelaxkaStock::getKantitatea).sum();
    }

    private void kenduGelaxkaHutsik(String id) { stocka.removeIf(i -> i.getGelaxkaID().equals(id) && i.getKantitatea() == 0); }
    public List<GelaxkaStock> kontsultatuInbentarioa() { return stocka; }
}