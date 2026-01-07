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
        System.out.println("\n1. Sarrera | 2. Irteera");
        int auk = irakurriZenbakia();
        biltegia.erakutsiProduktuguztiakGelaxketan();
        System.out.print("EAN: "); String ean = scanner.nextLine();
        System.out.print("Gelaxka: "); String gel = scanner.nextLine();
        System.out.print("Kant: "); int kan = irakurriZenbakia();

        if (auk == 1) biltegia.sartuStocka(ean, gel, kan);
        else if (auk == 2) biltegia.ateraStocka(ean, gel, kan);
    }

    private static void kontsultakMenua() {
        System.out.println("\n1. Gelaxka | 2. Inbentarioa | 3. Bilatu");
        int auk = irakurriZenbakia();
        if (auk == 1) {
            System.out.print("ID: "); String id = scanner.nextLine();
            biltegia.kontsultatuGelaxka(id).forEach(System.out::println);
        } else if (auk == 2) biltegia.erakutsiProduktuguztiakGelaxketan();
        else if (auk == 3) {
            System.out.print("Bilatu: "); String t = scanner.nextLine();
            biltegia.bilatuProduktuaKatalogoan(t).forEach(System.out::println);
        }
    }

    private static void mugimenduakMenua() {
        System.out.println("\n1. Mugitu | 2. Transferitu");
        int auk = irakurriZenbakia();
        if (auk == 1) {
            System.out.print("EAN: "); String ean = scanner.nextLine();
            System.out.print("Jat: "); String jat = scanner.nextLine();
            System.out.print("Hel: "); String hel = scanner.nextLine();
            int kan = irakurriZenbakia();
            biltegia.mugituProduktua(ean, jat, hel, kan);
        } else if (auk == 2) {
            System.out.print("Jat: "); String jat = scanner.nextLine();
            System.out.print("Hel: "); String hel = scanner.nextLine();
            biltegia.transferituGelaxka(jat, hel);
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