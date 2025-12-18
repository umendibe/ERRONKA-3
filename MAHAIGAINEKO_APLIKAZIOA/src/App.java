import java.util.Scanner;

public class App {
    private static Biltegia biltegia = new Biltegia();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        menuNagusia();
    }

    private static void menuNagusia() {
        int aukera;
        do {
            System.out.println("\n1. Stocka Kudeatu\n2. Kontsultak\n3. Mugimenduak\n0. Amaitu");
            if (scanner.hasNextInt()) {
                aukera = scanner.nextInt();
                scanner.nextLine(); // Garbitu \n
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
                        System.out.println("Agur!");
                        break;
                    default:
                        System.out.println("Aukera okerra.");
                }
            } else {
                System.out.println("Zenbaki bat sartu behar duzu.");
                if (scanner.hasNextLine())
                    scanner.nextLine(); // Kontuz hemen: lerroa badago bakarrik irakurri
                aukera = -1;
            }
        } while (aukera != 0);
    }

    private static void stockaKudeatuMenua() {
        System.out.println("1. Sartu\n2. Atera");
        if (scanner.hasNextInt()) {
            int a = scanner.nextInt();
            scanner.nextLine();
            if (a == 1)
                stockaSartu();
            else if (a == 2)
                stockaAtera();
        } else {
            scanner.nextLine();
        }
    }

    private static void stockaSartu() {
        System.out.print("EAN: ");
        String e = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Kanti: ");
        if (!scanner.hasNextInt()) {
            if (scanner.hasNextLine())
                scanner.nextLine();
            return;
        }
        int k = scanner.nextInt();
        scanner.nextLine();
        biltegia.sartuStocka(e, id, k);
    }

    private static void stockaAtera() {
        System.out.print("EAN: ");
        String e = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        if (!biltegia.balidatuGelaxkaProduktua(e, id))
            return;
        System.out.print("Kanti: ");
        if (!scanner.hasNextInt()) {
            if (scanner.hasNextLine())
                scanner.nextLine();
            return;
        }
        int k = scanner.nextInt();
        scanner.nextLine();
        biltegia.ateraStocka(e, id, k);
    }

    private static void mugimenduakMenua() {
        System.out.println("1. Mugitu");
        if (scanner.hasNextInt()) {
            int a = scanner.nextInt();
            scanner.nextLine();
            if (a == 1) {
                System.out.print("EAN: ");
                String e = scanner.nextLine();
                System.out.print("Jat: ");
                String j = scanner.nextLine();
                System.out.print("Hel: ");
                String h = scanner.nextLine();
                System.out.print("Kanti: ");
                if (scanner.hasNextInt()) {
                    biltegia.mugituProduktua(e, j, h, scanner.nextInt());
                }
                if (scanner.hasNextLine())
                    scanner.nextLine();
            }
        } else {
            scanner.nextLine();
        }
    }

    private static void kontsultakMenua() {
        System.out.println("1. Gelaxka\n2. Inbentarioa\n3. Guztiak");
        if (scanner.hasNextInt()) {
            int a = scanner.nextInt();
            scanner.nextLine();
            if (a == 1) {
                System.out.print("ID: ");
                if (scanner.hasNextLine())
                    biltegia.kontsultatuGelaxka(scanner.nextLine());
            } else if (a == 2) {
                biltegia.kontsultatuInbentarioa().forEach(System.out::println);
            } else if (a == 3) {
                biltegia.erakutsiProduktuguztiakGelaxketan();
            }
        } else {
            scanner.nextLine();
        }
    }
}