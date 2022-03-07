package components;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Paczka {

    private static String fileLocation = "src/files/pack.txt";

    public static String generateInt() {
        int digits = 19;

        StringBuilder str = new StringBuilder();
        //paczka paczkomatowa, wiec zaczyna się od 6
        str.append(63055);
        Random random = new Random();
        for (int i = 0; i < digits; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public static String openCode() {
        int digits = 6;

        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < digits; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public static String CreateBoxmachineParcel() {
        String status = "Created";

        List<Object> PackList = new ArrayList<Object>();
        PackList.add(generateInt());
        PackList.add(status);

        //Paczka paczkomatowa
        System.out.println("Tworzysz paczkę paczkomatową, podaj proszę poniższe dane: ");
        Scanner scanInt = new Scanner(System.in);
        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź adres email nadawcy:\n> ");
        String senderEmail = scanString.nextLine();
        PackList.add(senderEmail);

        System.out.print("Wprowadź numer telefonu nadawcy (9 cyfrowy):\n> ");
        int senderPhoneNumber = scanInt.nextInt();
        PackList.add(senderPhoneNumber);

        System.out.print("Wprowadź adres email odbiorcy:\n> ");
        String receiverEmail = scanString.nextLine();
        PackList.add(receiverEmail);

        System.out.print("Wprowadź numer telefonu odbiorcy (9 cyfrowy):\n> ");
        int receiverPhoneNumber = scanInt.nextInt();
        PackList.add(receiverPhoneNumber);

        System.out.print("Wprowadź gabaryt paczki (A, B, C):\n> ");
        String packSize = scanString.nextLine();
        PackList.add(packSize);
        switch (packSize) {
            case "A":
                PackList.add("12.99");
                break;
            case "B":
                PackList.add("13.99");
                break;
            case "C":
                PackList.add("15.49");
                break;
        }

        System.out.print("Wprowadź nazwę paczkomatu odbiorczego:\n> ");
        String bmName = scanString.nextLine();
        PackList.add(bmName);
        PackList.add(openCode());
        String packNumber = (String) PackList.get(0);

        try {
            StringBuffer sb = new StringBuffer();
//            int elements = PackList.size();

            for (Object o : PackList) {
                if (sb.length() > 0) sb.append(",");
                sb.append(o);
            }
            String packs = sb.toString();
//            Files.writeString(Paths.get(fileLocation), packs + "\n", StandardOpenOption.CREATE);
            Files.writeString(Paths.get(fileLocation), packs + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packNumber;
    }

    public static List<List<String>> getParcelsFromFile() throws IOException {

        try {
            List<String> parcels = Files.readAllLines(Path.of(fileLocation));
            List<List<String>> packLists = new ArrayList<>();

            for (String parcel : parcels) {
                List<String> packList = new ArrayList<String>();
                for (String i : parcel.split(",")) {
                    packList.add(i);
                }
                packLists.add(packList);
            }
            return packLists;

        } catch (IOException e) {
            System.out.println("Incapable to get parcels from file: " + fileLocation + e.getMessage());
        }
        return null;
    }

    public static void getAllPacks() throws IOException {
        List<List<String>> packLists = getParcelsFromFile();

        for (List innerlist : packLists) {
            for (Object i : innerlist) {
                System.out.print(i + " ");
            }
            System.out.println("");
        }
    }

    public static void deleteCreateFile() throws IOException {
        try {
            File file = new File(fileLocation);
            file.delete();
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> searchForPackcode() throws IOException {
        List<List<String>> packLists = getParcelsFromFile();
        List<String> searchedPacks = new ArrayList<>();

        for (List innerlist : packLists) {
            searchedPacks.add((String) innerlist.get(0));
        }
        return searchedPacks;
    }

    public static String setCustomerDelivering() throws IOException {

        List<List<String>> packLists = getParcelsFromFile();
        List<String> searchedPacks = searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, którą chcesz opłacić:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode) && innerlist.get(1).equals("Created")) {
                    innerlist.set(1, "CustomerDelivering");

                    deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Paczka o numerze: " + packCode + " została poprawnie opłacona.";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(1).equals("Created")) {
                    return "Paczka znajduje się w statusie " + innerlist.get(1) + " i nie może zostać opłacona. Paczka powinna być w statusie Created.";
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }

    public static String setCustomerStored() throws IOException {
        List<List<String>> packLists = getParcelsFromFile();
        List<String> searchedPacks = searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, którą chcesz nadać:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode) && innerlist.get(1).equals("CustomerDelivering")) {
                    innerlist.set(1, "CustomerStored");

                    deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Paczka o numerze: " + packCode + " została poprawnie nadana w paczkomacie. Możesz śledzić jej status w naszej aplikacji!";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(1).equals("CustomerDelivering")) {
                    return "Paczka znajduje się w statusie " + innerlist.get(1) + " i nie może zostać nadana. Paczka powinna być w statusie CustomerDelivering.";
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }

    public static String setDelivered() throws IOException {
        List<List<String>> packLists = getParcelsFromFile();
        List<String> searchedPacks = searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, którą chcesz odebrać:\n> ");
        String packCode = scanString.nextLine();
        System.out.print("Wprowadź kod odbioru paczki:\n> ");
        String openCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode) && innerlist.get(1).equals("Stored") && innerlist.get(9).equals(openCode)) {
                    innerlist.set(1, "Delivered");

                    deleteCreateFile();

                    for (List tmpList : packLists) {
                        String arrayToString = tmpList.toString().replace("[", "").replace("]", "").replace(" ", "");
                        Files.writeString(Paths.get(fileLocation), arrayToString + "\n", StandardOpenOption.APPEND);
                    }
                    return "Paczka o numerze: " + packCode + " została poprawnie odebrana. Dziękujemy za skorzystanie z naszych usług!";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(1).equals("Stored")) {
                    return "Paczka znajduje się w statusie " + innerlist.get(1) + " i nie może zostać odebrana. Skontaktuj się z naszą infolinią.";
                } else if (innerlist.get(0).equals(packCode) && !innerlist.get(9).equals(openCode)) {
                    return "Podany kod odbioru jest nieprawidłowy.";
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }

    public static List<String> createPackFlowList() {
        List<String> packFlow = new ArrayList<String>();

        packFlow.add("Prawidłowe flow paczki paczkomatowej wygląda następująco:");
        packFlow.add("Created");
        packFlow.add("CustomerDelivering");
        packFlow.add("CustomerStored");
        packFlow.add("CustomerSent");
        packFlow.add("PosortowanaWSortowaniNadawczej");
        packFlow.add("InTransit");
        packFlow.add("PosortowanaWSortowaniOdbiorczej");
        packFlow.add("DoDoreczenia");
        packFlow.add("Stored");
        packFlow.add("Delivered");

        return packFlow;
    }

    public static void getPackFlowList() {
        List<String> packFlow = createPackFlowList();
        for (String status : packFlow)
            System.out.println(status);
    }


    public static String checkStatus() throws IOException {
        List<List<String>> packLists = getParcelsFromFile();
        List<String> searchedPacks = searchForPackcode();

        Scanner scanString = new Scanner(System.in);
        System.out.print("Wprowadź numer paczki, której status chcesz sprawdzić:\n> ");
        String packCode = scanString.nextLine();

        if (searchedPacks.contains(packCode)) {
            for (List innerlist : packLists) {
                if (innerlist.get(0).equals(packCode)) {
                    return "Paczka o numerze: " + packCode + " znajduje się aktualnie w statusie: " + innerlist.get(1);
                }
            }
        } else {
            return "Podana paczka nie istnieje w bazie!";
        }
        return null;
    }


}