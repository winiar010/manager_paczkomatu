package components;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class Sortownia {

    private static String fileLocation = "src/files/pack.txt";

    public static String setPosortowanaWSortowaniNadawczej() throws IOException {
        List<List<String>> packLists = Paczka.getParcelsFromFile();
        List<String> searchedPacks = Paczka.searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, którą chcesz posortować:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode) && innerlist.get(1).equals("CustomerSent")) {
                    innerlist.set(1, "PosortowanaWSortowaniNadawczej");

                    Paczka.deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Paczka o numerze: " + packCode + " została poprawnie posortowana w sortowni nadawczej.";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(1).equals("CustomerSent")) {
                    return "Paczka znajduje się w statusie " + innerlist.get(1) + " i nie może zostać posortowana. Paczka powinna być w statusie CustomerSent.";
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }

    public static String setPosortowanaWSortowaniOdbiorczej() throws IOException {
        List<List<String>> packLists = Paczka.getParcelsFromFile();
        List<String> searchedPacks = Paczka.searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, którą chcesz posortować:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode) && innerlist.get(1).equals("InTransit")) {
                    innerlist.set(1, "PosortowanaWSortowaniOdbiorczej");

                    Paczka.deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Paczka o numerze: " + packCode + " została poprawnie posortowana w sortowni odbiorczej.";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(1).equals("InTransit")) {
                    return "Paczka znajduje się w statusie " + innerlist.get(1) + " i nie może zostać posortowana. Paczka powinna być w statusie InTransit.";
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }

    public static String setDoDoreczenia() throws IOException {
        List<List<String>> packLists = Paczka.getParcelsFromFile();
        List<String> searchedPacks = Paczka.searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, którą chcesz wydać do doręczenia:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode) && innerlist.get(1).equals("PosortowanaWSortowaniOdbiorczej")) {
                    innerlist.set(1, "DoDoreczenia");

                    Paczka.deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Paczka o numerze: " + packCode + " została poprawnie wydana do doręczenia.";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(1).equals("PosortowanaWSortowaniOdbiorczej")) {
                    return "Paczka znajduje się w statusie " + innerlist.get(1) + " i nie może zostać wydana do doręczenia. Paczka powinna być w statusie PosortowanaWSortowaniOdbiorczej.";
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }
}