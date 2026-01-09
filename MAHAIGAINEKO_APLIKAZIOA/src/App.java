import java.util.List;
import java.util.Scanner;

/**
 * Aplikazio nagusia (Main). Erabiltzaile interfazea (kontsola bidezkoa)
 * kudeatzen du eta Biltegia klasearekin komunikatzen da.
 */
public class App {
    private static Biltegia biltegia = new Biltegia();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Programaren sarrera puntua.
     * @param args Komando lerroko argumentuak.
     */
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("IndiUsurbil WMS - Biltegi Kudeaketa (Hasi)");
        System.out.println("=========================================");

        menuNagusia();
    }

    /**
     * Menu nagusia erakusten du eta erabiltzailearen aukera kudeatzen du.
     */
    private static void menuNagusia() {
        int aukera;
        do {
            System.out.println("\n--- Menu Nagusia ---");
            System.out.println("1. Stocka Kudeatu");
            System.out.println("2. Kontsultak");
            System.out.println("3. Biltegiaren Mugimenduak");
            System.out.println("0. Amaitu");
            System.out.print("Aukeratu zenbaki bat: ");

            if (scanner.hasNextInt()) {
                aukera = scanner.nextInt();
                scanner.nextLine();

                switch (aukera) {
                    case 1:
                        stockaKudeatuMenua();
                        break;
                    case 2:
                        kontsultakMenua();
                        break;
                    case 3:
                        mugimenduakMenua();
                        break;
                    case 0:
                        System.out.println("Aplikazioa ixten... Agur!");
                        break;
                    default:
                        System.out.println("Aukera desegokia.");
                }
            } else {
                System.out.println("Zenbaki bat sartu behar duzu.");
                scanner.nextLine();
                aukera = -1;
            }
        } while (aukera != 0);
    }

    // ---------------------- 1. STOCKA KUDEATU FLUXUA ----------------------
    
    /**
     * Stock-a kudeatzeko azpimenua (Sarrera/Irteera).
     */
    private static void stockaKudeatuMenua() {
        System.out.println("\n--- Stocka Kudeatu (Sarrera / Irteera) ---");
        System.out.println("1. Produktua Sartu (Sarrera)");
        System.out.println("2. Produktua Atera (Irteera)");
        System.out.print("Aukeratu: ");

        int aukera = scanner.nextInt();
        scanner.nextLine();

        if (aukera == 1) {
            stockaSartu();
        } else if (aukera == 2) {
            stockaAtera();
        }
    }

    /**
     * Stock sarrera bat egiteko prozesua. Datuak eskatu eta biltegian sartu.
     */
    private static void stockaSartu() {
        biltegia.erakutsiProduktuguztiakGelaxketan();
        System.out.print("Sartu EAN-13 kodea: ");
        String ean13 = scanner.nextLine().trim();

        if (!biltegia.balidatuEAN13(ean13)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        System.out.print("Sartu Gelaxka IDa (Biltegian sartzeko): ");
        String gelaxkaID = scanner.nextLine().trim();

        if (!biltegia.balidatuGelaxkaID(gelaxkaID)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        System.out.print("Sartu sartu nahi den kantitatea: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Errorea: Kantitatea zenbaki bat izan behar da.");
            scanner.nextLine();
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }
        int kantitatea = scanner.nextInt();
        scanner.nextLine();

        if (!biltegia.balidatuKantitatea(kantitatea)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        if (biltegia.sartuStocka(ean13, gelaxkaID, kantitatea)) {
            System.out.println("Stocka arrakastaz sartu da.");
        } else {
            System.out.println("Errorea Stocka sartzerakoan.");
        }
    }

    /**
     * Stock irteera bat egiteko prozesua.
     */
    private static void stockaAtera() {
        biltegia.erakutsiProduktuguztiakGelaxketan();
        System.out.print("Sartu EAN-13 kodea: ");
        String ean13 = scanner.nextLine().trim();

        if (!biltegia.balidatuEAN13(ean13)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        System.out.print("Sartu Gelaxka IDa (Produktu hori duen Gelaxka): ");
        String gelaxkaID = scanner.nextLine().trim();

        if (!biltegia.balidatuGelaxkaID(gelaxkaID)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        if (!biltegia.balidatuGelaxkaProduktua(ean13, gelaxkaID)) {
            System.out.println("Produktua ez dago gelaxka horretan");
            return;
        }

        System.out.print("Sartu atera nahi den kantitatea: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Errorea: Kantitatea zenbaki bat izan behar da.");
            scanner.nextLine();
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }
        int kantitatea = scanner.nextInt();
        scanner.nextLine();

        if (!biltegia.balidatuKantitatea(kantitatea)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }else if (kantitatea == 0) {
            System.out.println("Kantitatea 0 baino handiagoa izan behar da.");
            return;
        }

        if (biltegia.ateraStocka(ean13, gelaxkaID, kantitatea)) {
            System.out.println("Stocka arrakastaz atera da.");
        } else {
            System.out.println("Errorea Stocka ateratzerakoan. Begiratu kantitatea.");
        }
    }

    // ---------------------- 2. BILTEGIAREN MUGIMENDUAK FLUXUA ----------------------
    
    /**
     * Mugimenduen azpimenua.
     */
    private static void mugimenduakMenua() {
        System.out.println("\n--- Biltegiaren Mugimenduak ---");
        System.out.println("1. Produktuaren Mugimendua");
        System.out.println("2. Gelaxken Edukia Transferitu");
        System.out.print("Aukeratu Mugimendu Mota:");

        int aukera = scanner.nextInt();
        scanner.nextLine();

        if (aukera == 1) {
            produktuMugimendua();
        } else if (aukera == 2) {
            System.out.println("Funtzionalitate hau ez dago inplementatuta oraindik.");
        }
    }

    /**
     * Produktu bat gelaxka batetik bestera mugitzeko prozesua.
     */
    private static void produktuMugimendua() {
        biltegia.erakutsiProduktuguztiakGelaxketan();
        System.out.println("\n--- Produktuaren Mugimendua ---");
        System.out.print("Sartu mugitu nahi den EAN-13 kodea: ");
        String ean13 = scanner.nextLine().trim();

        if (!biltegia.balidatuEAN13(ean13)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        System.out.print("Sartu Jatorrizko Gelaxka IDa: ");
        String jatorrizkoa = scanner.nextLine().trim();

        if (!biltegia.balidatuGelaxkaID(jatorrizkoa)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        if (!biltegia.balidatuGelaxkaProduktua(ean13, jatorrizkoa)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        System.out.print("Sartu Helmuga Gelaxka IDa: ");
        String helmuga = scanner.nextLine().trim();

        if (!biltegia.balidatuGelaxkaID(helmuga)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        System.out.print("Sartu mugitu nahi den kantitatea: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Errorea: Kantitatea zenbaki bat izan behar da.");
            scanner.nextLine();
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }
        int kantitatea = scanner.nextInt();
        scanner.nextLine();

        if (!biltegia.balidatuKantitatea(kantitatea)) {
            System.out.println("Operazioa bertan behera geratu da.");
            return;
        }

        if (biltegia.mugituProduktua(ean13, jatorrizkoa, helmuga, kantitatea)) {
            System.out.println("Mugimendua arrakastatsua.");
        } else {
            System.out.println("Mugimendua ez da burutu.");
        }
    }

    // ---------------------- 3. KONTSULTA ETA INFORMAZIO FLUXUA ----------------------
    
    /**
     * Kontsulten azpimenua.
     */
    private static void kontsultakMenua() {
        System.out.println("\n--- Kontsultak eta Informazioa ---");
        System.out.println("1. Gelaxkaren Kontsulta");
        System.out.println("2. Inbentario Osoa");
        System.out.println("3. Produktu Guztiak Gelaxketan");
        System.out.println("4. Produktuaren Kontsulta (EAN-13 edo kategoriaren arabera)");
        System.out.print("Aukeratu Kontsulta Mota:");

        int aukera = scanner.nextInt();
        scanner.nextLine();

        if (aukera == 1) {
            gelaxkaKontsultatu();
        } else if (aukera == 2) {
            inbentarioOsoaErakutsi();
        } else if (aukera == 3) {
            biltegia.erakutsiProduktuguztiakGelaxketan();
        } else if (aukera == 4) {
            System.out.println(
                    "Produktuaren kontsulta (EAN-13 edo kategoriaren arabera) ez dago inplementatuta oraindik.");
        }
    }

    /**
     * Gelaxka zehatz baten edukia bistaratzeko prozesua.
     */
    private static void gelaxkaKontsultatu() {
        System.out.print("Sartu Gelaxkaren kodea: ");
        String gelaxkaID = scanner.nextLine();

        List<GelaxkaStock> stockEdukia = biltegia.kontsultatuGelaxka(gelaxkaID);

        if (stockEdukia.isEmpty()) {
            System.out.println("Gelaxka hutsa dago edo ez da aurkitu.");
        } else {
            System.out.println("--- Gelaxka " + gelaxkaID + " Edukia ---");
            for (GelaxkaStock item : stockEdukia) {
                System.out.println("  EAN-13: " + item.getProduktuEAN13() + ", Kantitatea: " + item.getKantitatea());
            }
        }
        System.out.println("Amaitu: Kontsultaren emaitza pantailan erakutsi da.");
    }

    /**
     * Inbentario osoa lerroz lerro erakusten duen metodoa.
     */
    private static void inbentarioOsoaErakutsi() {
        System.out.println("\n--- Inbentario Osoaren Txostena ---");

        for (GelaxkaStock item : biltegia.stocka) {
            System.out.println(item);
        }
        System.out.println("Amaitu: Kontsultaren emaitza pantailan erakutsi da.");
    }
}