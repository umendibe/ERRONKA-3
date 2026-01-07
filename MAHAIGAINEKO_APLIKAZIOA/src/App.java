import java.util.List;
import java.util.Scanner;

public class App {
    private static Biltegia biltegia = new Biltegia();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- BILTEGI KUDEAKETA SISTEMA ---");
        menuNagusia();
    }

    private static void menuNagusia() {
        int aukera;
        do {
            System.out.println("\n1. Stocka | 2. Kontsultak | 3. Mugimenduak | 0. Irten");
            System.out.print("Aukeratu: ");
            if (scanner.hasNextInt()) {
                aukera = scanner.nextInt();
                scanner.nextLine();
                switch (aukera) {
                    case 1: stockaKudeatuMenua(); break;
                    case 2: kontsultakMenua(); break;
                    case 3: mugimenduakMenua(); break;
                    case 0: System.out.println("Agur!"); break;
                    default: System.out.println("Aukera okerra.");
                }
            } else {
                System.out.println("ERROREA: Zenbaki bat sartu behar duzu.");
                scanner.nextLine();
                aukera = -1;
            }
        } while (aukera != 0);
    }

    private static void stockaKudeatuMenua() {
        System.out.println("\n1. Sarrera (Gehitu) | 2. Irteera (Kendu)");
        int auk = irakurriZenbakia();
        biltegia.erakutsiProduktuguztiakGelaxketan();

        System.out.print("EAN-13 kodea: ");
        String ean = scanner.nextLine();
        System.out.print("Gelaxka ID: ");
        String gel = scanner.nextLine();
        System.out.print("Kantitatea: ");
        int kan = irakurriZenbakia();

        if (auk == 1) {
            if (biltegia.sartuStocka(ean, gel, kan)) {
                System.out.println("ARRAKASTA: Produktua sartu da.");
            }
        } else if (auk == 2) {
            if (biltegia.ateraStocka(ean, gel, kan)) {
                System.out.println("ARRAKASTA: Produktua atera da.");
            }
        }
    }

    private static void kontsultakMenua() {
        System.out.println("\n1. Gelaxka ID bidez | 2. Inbentario osoa | 3. Bilatu Produktua");
        int auk = irakurriZenbakia();
        if (auk == 1) {
            System.out.print("ID: ");
            String id = scanner.nextLine();
            if (biltegia.balidatuGelaxkaID(id)) {
                List<GelaxkaStock> lista = biltegia.kontsultatuGelaxka(id);
                if (lista.isEmpty())
                    System.out.println("Hutsik.");
                else
                    lista.forEach(s -> System.out.println(
                            biltegia.billatuProduktua(s.getProduktuEAN13()) + " | Kant: " + s.getKantitatea()));
            }
        } else if (auk == 2) {
            biltegia.erakutsiProduktuguztiakGelaxketan();
        } else if (auk == 3) {
            System.out.print("EAN edo Kategoria: ");
            String t = scanner.nextLine();
            List<Produktua> prods = biltegia.bilatuProduktuaKatalogoan(t);
            if (prods.isEmpty())
                System.out.println("Ez da ezer aurkitu.");
            else
                prods.forEach(p -> {
                    System.out.println(p);
                    biltegia.kontsultatuInbentarioa().stream()
                            .filter(s -> s.getProduktuEAN13().equals(p.getEan13()))
                            .forEach(s -> System.out
                                    .println("  -> " + s.getGelaxkaID() + " [" + s.getKantitatea() + "]"));
                });
        }
    }

    private static void mugimenduakMenua() {
        System.out.println("\n1. Mugitu unitateak | 2. Gelaxka transferitu");
        int auk = irakurriZenbakia();

        if (auk == 1) {
            System.out.print("EAN: ");
            String ean = scanner.nextLine();
            System.out.print("Jatorria: ");
            String jat = scanner.nextLine();
            System.out.print("Helmuga: ");
            String hel = scanner.nextLine();
            System.out.print("Kantitatea: ");
            int kan = irakurriZenbakia();
            if (biltegia.mugituProduktua(ean, jat, hel, kan))
                System.out.println("ARRAKASTA: Mugitua.");
        } else if (auk == 2) {
            System.out.print("Jatorria: ");
            String jat = scanner.nextLine();
            System.out.print("Helmuga: ");
            String hel = scanner.nextLine();
            // Hemen ez da kantitaterik irakurtzen, transferentzia osoa delako
            if (biltegia.transferituGelaxka(jat, hel))
                System.out.println("ARRAKASTA: Gelaxka transferitua.");
        }
    }

    private static int irakurriZenbakia() {
        while (!scanner.hasNextInt()) {
            System.out.println("ERROREA: Zenbaki bat sartu behar duzu.");
            scanner.nextLine();
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }
}