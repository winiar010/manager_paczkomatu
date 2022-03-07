package components;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static int menu() {
        System.out.println();
        System.out.println("****************************************");
        System.out.println("*           MENAGER PACZEK 2.0         *");
        System.out.println("****************************************");
        System.out.println("1. Utwórz paczkę");
        System.out.println("2. Wyświetl stworzone paczki");
        System.out.println("3. Opłać paczkę");
        System.out.println("4. Nadaj paczkę w paczkomacie");
        System.out.println("5. Odbierz paczkę z paczkomatu");
        System.out.println("6. Wyświetl flow paczki");
        System.out.println("7. Sprawdź status paczki");
        System.out.println("0. WYJŚCIE");
        System.out.println("Wybierz opcję od 1 do 7 lub wyjdź z programu");
        System.out.print("> ");

        Scanner in = new Scanner(System.in);
        int w = in.nextInt();
        return w;
    }

    public static int serviceMenu() {
        System.out.println();
        System.out.println("*****************************************");
        System.out.println("* MENAGER PACZEK 2.0-tryb kurier/serwis *");
        System.out.println("*****************************************");
        System.out.println("1. Zmień status paczki");
        System.out.println("2. Odbierz paczkę z paczkomatu i przekaż paczkę sortowni nadawczej");
        System.out.println("3. Posortuj paczkę w sortowni nadawczej");
        System.out.println("4. Dostarcz paczkę do sortowni odbiorczej");
        System.out.println("5. Posortuj paczkę w sortowni odbiorczej");
        System.out.println("6. Dostarcz paczkę do paczkomatu");
        System.out.println("7. Umieść paczkę w paczkomacie");
        System.out.println("0. WYJŚCIE");
        System.out.println("Wybierz opcję od 1 do 6 lub wyjdź z programu");
        System.out.print("> ");

        Scanner in = new Scanner(System.in);
        int w = in.nextInt();
        return w;
    }

    public static void main(String[] args) throws IOException {

        int wybor = menu();
        Scanner in = new Scanner(System.in);
        while (wybor != 0) {
            switch (wybor) {
                case 1:
                    String packNumber = Paczka.CreateBoxmachineParcel();
                    System.out.println("Paczka o numerze: " + packNumber + " została utworzona poprawnie.");
                    break;
                case 2:
                    Paczka.getAllPacks();
                    break;
                case 3:
                    System.out.println(Paczka.setCustomerDelivering());
                    break;
                case 4:
                    System.out.println(Paczka.setCustomerStored());
                    break;
                case 5:
                    System.out.println(Paczka.setDelivered());
                    break;
                case 6:
                    Paczka.getPackFlowList();
                    break;
                case 7:
                    System.out.println(Paczka.checkStatus());
                    break;
                case 99:
                    int choice = serviceMenu();
                    while (choice != 0) {
                        switch (choice) {
                            case 1:
                                System.out.println(Kurier.changeParcelStatus());
                                break;
                            case 2:
                                System.out.println(Kurier.setCustomerSent());
                                break;
                            case 3:
                                System.out.println(Sortownia.setPosortowanaWSortowaniNadawczej());
                                break;
                            case 4:
                                System.out.println(Kurier.setInTransit());
                                break;
                            case 5:
                                System.out.println(Sortownia.setPosortowanaWSortowaniOdbiorczej());
                                break;
                            case 6:
                                System.out.println(Sortownia.setDoDoreczenia());
                                break;
                            case 7:
                                System.out.println(Kurier.setStored());
                                break;
                            default:
                                System.out.println("Nieprawidłowy wybór - wybierz liczbę od 1 do 7 lub wyjdź");
                        }
                        System.out.print("\nWciśnij Enter, aby kontynuować...");
                        System.in.read();
                        choice = serviceMenu();
                    }
                    System.out.println("****************************************");
                    System.out.println("\nWyszedłeś ze specjalnego trybu\n");
                    break;

                default:
                    System.out.println("Nieprawidłowy wybór - wybierz liczbę od 1 do 6 lub wyjdź");
            }
            System.out.print("\nWciśnij Enter, aby kontynuować...");
            System.in.read();
            wybor = menu();
        }
        System.out.println("****************************************");
        System.out.println("\nKoniec programu\n");

    }
}