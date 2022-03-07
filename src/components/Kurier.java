package components;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class Kurier {

    private static String fileLocation = "src/files/pack.txt";

    public static String changeParcelStatus() throws IOException {
        List<List<String>> packLists = Paczka.getParcelsFromFile();
        List<String> searchedPacks = Paczka.searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, której status chcesz zmienić:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode)) {
                    System.out.print("Podaj status jaki chcesz ustawić:\n> ");
                    String status = scanString.nextLine();
                    innerlist.set(1, status);

                    Paczka.deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Status paczki o numerze: " + packCode + " został poprawnie zmieniony na: " + status;
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }

    public static String setCustomerSent() throws IOException {
        List<List<String>> packLists = Paczka.getParcelsFromFile();
        List<String> searchedPacks = Paczka.searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Kurierze, wprowadź numer paczki, którą chcesz odebrać z paczkomatu:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode) && innerlist.get(1).equals("CustomerStored")) {
                    innerlist.set(1, "CustomerSent");


                    Paczka.deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Paczka o numerze: " + packCode + " została odebrana przez kuriera i wyruszyła w dalszą drogę.";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(1).equals("CustomerStored")) {
                    return "Paczka znajduje się w statusie " + innerlist.get(1) + " i nie może zostać odebrana. Paczka powinna być w statusie CustomerStored.";
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }

    public static String setInTransit() throws IOException {
        List<List<String>> packLists = Paczka.getParcelsFromFile();
        List<String> searchedPacks = Paczka.searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, którą chcesz przekazać sortowni nadawczej:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode) && innerlist.get(1).equals("PosortowanaWSortowaniNadawczej")) {
                    innerlist.set(1, "InTransit");

                    Paczka.deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Paczka o numerze: " + packCode + " została poprawnie przekazana do sortowni odbiorczej.";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(1).equals("PosortowanaWSortowaniNadawczej")) {
                    return "Paczka znajduje się w statusie " + innerlist.get(1) + " i nie może zostać przekazana do sortowni nadawczej. Paczka powinna być w statusie PosortowanaWSortowaniNadawczej.";
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }

    public static String setStored() throws IOException {
        List<List<String>> packLists = Paczka.getParcelsFromFile();
        List<String> searchedPacks = Paczka.searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, którą chcesz dostarczyć do paczkomatu:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode) && innerlist.get(1).equals("DoDoreczenia")) {
                    innerlist.set(1, "Stored");

                    Paczka.deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Paczka o numerze: " + packCode + " została poprawnie umieszczona w paczkomacie przez kuriera.";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(1).equals("DoDoreczenia")) {
                    return "Paczka znajduje się w statusie " + innerlist.get(1) + " i nie może zostać umieszczona w paczkomacie. Paczka powinna być w statusie DoDoreczenia.";
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }

}