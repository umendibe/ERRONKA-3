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
    }

    public boolean sartuStocka(String ean13, String gelaxkaID, int kantitatea) {
        // Internal validations to ensure callers cannot bypass rules
        if (!balidatuEAN13(ean13) || !balidatuGelaxkaID(gelaxkaID) || !balidatuKantitatea(kantitatea))
            return false;

        // Gelaxkaren kapazitate totala kalkulatu
        int gelaxkaKapazitatea = kalkulatuGelaxkaKapazitatea(gelaxkaID);

        // Egiazta gelaxka kapazitate limitea gainditu ez duen
        if (gelaxkaKapazitatea + kantitatea > 100) {
            System.out.println("Errorea: Gelaxka " + gelaxkaID + " -en kapazitate limitea gainditu daiteke.");
            System.out.println("Unean: " + gelaxkaKapazitatea + " produktu, gehitu nahi: " + kantitatea);
            System.out.println("Gehienez: " + (100 - gelaxkaKapazitatea) + " produktu sartzen dira.");
            return false;
        }

        if (gelaxkaBetetaLista.contains(gelaxkaID)) {
            System.out.println("Errorea: Gelaxka 'beteta' gisa markatuta dago. Ezin da sartu.");
            return false;
        }

        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID) && item.getProduktuEAN13().equals(ean13)) {
                item.setKantitatea(item.getKantitatea() + kantitatea);
                if (item.getKantitatea() >= 100) {
                    gelaxkaBetetaLista.add(gelaxkaID);
                    System.out.println("Abisua: Gelaxka " + gelaxkaID + " 'beteta' gisa markatuta dago.");
                }
                return true;
            }
        }

        // Sarrera berri bat sortu
        stocka.add(new GelaxkaStock(gelaxkaID, ean13, kantitatea));
        if (gelaxkaKapazitatea + kantitatea >= 100) {
            gelaxkaBetetaLista.add(gelaxkaID);
            System.out.println("Abisua: Gelaxka " + gelaxkaID + " 'beteta' gisa markatuta dago.");
        }
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
                        if (item.getKantitatea() == 0) {
                            System.out.println("Abisua: Gelaxka " + gelaxkaID + " hutsik dago.");
                            kenduGelaxkaHutsik(gelaxkaID);
                            return true;
                        }
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

        // Helmugako gelaxkaren kapazitate totala kalkulatu
        int helmugaKapazitatea = kalkulatuGelaxkaKapazitatea(helmugaGelaxkaID);

        // Egiazta helmugako gelaxka kapazitate limitea gainditu ez duen
        if (helmugaKapazitatea + kantitatea > 100) {
            System.out.println(
                    "Errorea: Helmugako gelaxka " + helmugaGelaxkaID + " -en kapazitate limitea gainditu daiteke.");
            System.out.println("Unean: " + helmugaKapazitatea + " produktu, gehitu nahi: " + kantitatea);
            System.out.println("Gehienez: " + (100 - helmugaKapazitatea) + " produktu mugitu daitezke.");
            return false;
        }

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

    // Gelaxka kapazitate totala kalkulatu (produktu guztien kantitate batuta)
    private int kalkulatuGelaxkaKapazitatea(String gelaxkaID) {
        int kapazitatea = 0;
        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID)) {
                kapazitatea += item.getKantitatea();
            }
        }
        return kapazitatea;
    }

    // Gelaxka guztien kapazitate totala erakutsi (produktuak eta lokalizazioak)
    public void erakutsiProduktuguztiakGelaxketan() {
        System.out.println("\n========================================");
        System.out.println("PRODUKTU GUZTIAK GELAXKETAN");
        System.out.println("========================================");

        if (stocka.isEmpty()) {
            System.out.println("Biltegia hutsa dago. Ez da produkturik aurkitu.");
            return;
        }

        // Gelaxka bakarraren arabera taldekatzea
        java.util.Map<String, List<GelaxkaStock>> gelaxkaMap = new java.util.LinkedHashMap<>();

        for (GelaxkaStock item : stocka) {
            gelaxkaMap.computeIfAbsent(item.getGelaxkaID(), k -> new ArrayList<>()).add(item);
        }

        int totalProduktua = 0;

        // Gelaxka bakoitzaren informazioa erakutsi
        for (String gelaxkaID : gelaxkaMap.keySet()) {
            List<GelaxkaStock> gelaxkaProduktua = gelaxkaMap.get(gelaxkaID);
            int gelaxkaKapazitatea = gelaxkaProduktua.stream()
                    .mapToInt(GelaxkaStock::getKantitatea)
                    .sum();

            System.out.println("\n--- Gelaxka: " + gelaxkaID + " ---");
            System.out.println("Kapazitate Totala: " + gelaxkaKapazitatea + "/100");
            System.out.println("Egoera: " + (gelaxkaKapazitatea >= 100 ? "BETETA" : "LIBRE"));

            for (GelaxkaStock item : gelaxkaProduktua) {
                // Produktuaren izena bilatu
                Produktua prod = billatuProduktua(item.getProduktuEAN13());
                String produktuIzena = prod != null ? prod.getIzena() : "Ezezaguna";

                System.out.println("  - " + produktuIzena +
                        " [EAN: " + item.getProduktuEAN13() + "]" +
                        " | Kantitatea: " + item.getKantitatea());
                totalProduktua += item.getKantitatea();
            }
        }

        System.out.println("\n========================================");
        System.out.println("TOTALA: " + totalProduktua + " produktu");
        System.out.println("========================================\n");
    }

    // Produktua EAN-13 bidez bilatu
    private Produktua billatuProduktua(String ean13) {
        for (Produktua prod : produktuKatalogoa) {
            if (prod.getEan13().equals(ean13)) {
                return prod;
            }
        }
        return null;
    }

    // Gelaxka hutsik badago, stocketik kendu
    private void kenduGelaxkaHutsik(String gelaxkaID) {
        stocka.removeIf(item -> item.getGelaxkaID().equals(gelaxkaID) && item.getKantitatea() == 0);
    }

    // EAN-13 balidatu
    public boolean balidatuEAN13(String ean13) {
        if (ean13 == null || ean13.trim().isEmpty()) {
            System.out.println("Errorea: EAN-13 kodea ezin da hutsik egon.");
            return false;
        }

        if (!ean13.matches("\\d{13}")) {
            System.out.println("Errorea: EAN-13 kodeak 13 digitu eduki behar ditu (zenbaki osoak).");
            return false;
        }

        // EAN-13 katalogoan dagoen egiazta
        if (billatuProduktua(ean13) == null) {
            System.out.println("Errorea: EAN-13 kodea ez da katalogoan aurkitu.");
            System.out.println("Katalogoan dauden EAN-13 kodeak:");
            for (Produktua prod : produktuKatalogoa) {
                System.out.println("  - " + prod.getEan13() + ": " + prod.getIzena());
            }
            return false;
        }

        return true;
    }

    // Gelaxka ID balidatu
    public boolean balidatuGelaxkaID(String gelaxkaID) {
        if (gelaxkaID == null || gelaxkaID.trim().isEmpty()) {
            System.out.println("Errorea: Gelaxka IDa ezin da hutsik egon.");
            return false;
        }

        // Expected format: single uppercase letter, digits, dash, digits (e.g. A1-1)
        if (!gelaxkaID.matches("[A-Z]\\d+-\\d+")) {
            System.out.println("Errorea: Gelaxka IDaren formatua okerra da. Adibidez: A1-1, B2-4");
            return false;
        }

        return true;
    }

    // Kantitate balidatu
    public boolean balidatuKantitatea(int kantitatea) {
        if (kantitatea <= 0) {
            System.out.println("Errorea: Kantitatea 0 baino handiagoa izan behar da.");
            return false;
        }

        if (kantitatea > 1000) {
            System.out.println("Errorea: Kantitatea gehiegiz handia da (max 1000).");
            return false;
        }

        return true;
    }

    // Gelaxka eta produktua existitzen dauden egiazta (Irteera/Mugimendua)
    public boolean balidatuGelaxkaProduktua(String ean13, String gelaxkaID) {
        boolean aurkitu = false;
        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID) && item.getProduktuEAN13().equals(ean13)) {
                aurkitu = true;
                break;
            }
        }

        if (!aurkitu) {
            System.out.println("Errorea: Produktu hori ez da gelaxka horretan aurkitu.");
            return false;
        }

        return true;
    }
}