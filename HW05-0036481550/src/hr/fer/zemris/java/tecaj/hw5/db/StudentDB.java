package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code StudentDB} is class used to start this application.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class StudentDB {

    /**
     * Starting point of a program.
     * 
     * @param args
     *            Command-line argument
     * @throws IOException
     *             signals that an error has been reached unexpectedly while
     *             opening records file.
     */
    public static void main(String[] args) throws IOException {
        List<String> records = Files
                .readAllLines(Paths.get(".\\db\\db1.txt"), StandardCharsets.UTF_8);
        StudentDatabase db = new StudentDatabase(records);

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.print(">");

            String query = scan.nextLine();

            if (query.toLowerCase().equals("exit")) {
                break;
            }

            QueryFilter filter;
            try {
                filter = new QueryFilter(query.trim());
            } catch (IllegalArgumentException e) {
                System.err.println("Query is of invalid type!");
                continue;
            }

            List<StudentRecord> list = new ArrayList<>();

            if (query.toLowerCase().startsWith("indexquery")) {
                String regex = "indexquery\\s+jmbag\\s*=\\s*\\\"([0-9]{10})\\\"";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                Matcher matcher = pattern.matcher(query);

                if (matcher.find()) {
                    list.add(db.forJMBAG(matcher.group(1)));
                } else {
                    list = null;
                }
            } else {
                list = db.filter(filter);
            }

            printList(list);
        }

        scan.close();
    }

    /**
     * Prints out a list of student records.
     * 
     * @param records
     *            list of student records
     */
    private static void printList(List<StudentRecord> records) {
        if (records.size() != 0) {
            int biggestLastName = records
                    .stream()
                    .sorted((e1, e2) -> e1.getLastName().length() > e2.getLastName().length() ? -1 : 1)
                    .findFirst()
                    .get()
                    .getLastName()
                    .length();

            int biggestFirstName = records
                    .stream()
                    .sorted((e1, e2) -> e1.getFirstName().length() > e2.getFirstName().length() ? -1 : 1)
                    .findFirst()
                    .get()
                    .getFirstName()
                    .length();

            printLine(biggestLastName, biggestFirstName);

            for (StudentRecord rec : records) {
                System.out.print("| ");
                System.out.print(rec.getJMBAG());

                System.out.print(" | ");
                System.out.print(rec.getLastName());
                printStringNTimes(" ", biggestLastName - rec.getLastName().length());

                System.out.print(" | ");
                System.out.print(rec.getFirstName());
                printStringNTimes(" ", biggestFirstName - rec.getFirstName().length());

                System.out.print(" | ");
                System.out.print(rec.getFinalGrade());
                System.out.print(" |");

                System.out.println();
            }

            printLine(biggestLastName, biggestFirstName);
        }

        System.out.println("Records selected: " + records.size());
        System.out.println();
    }

    /**
     * Prints given string n times.
     * 
     * @param str
     *            string
     * @param num
     *            number of times print will be performed
     */
    private static void printStringNTimes(String str, int num) {
        while (num != 0) {
            System.out.print(str);
            num--;
        }
    }

    /**
     * Prints out a start or end of a table.
     * 
     * @param biggestLastName
     *            length of a biggest last name in table.
     * @param biggestFirstName
     *            length of a biggest last name in table.
     */
    private static void printLine(int biggestLastName, int biggestFirstName) {
        System.out.print("+============+=");
        printStringNTimes("=", biggestLastName);
        System.out.print("=+=");
        printStringNTimes("=", biggestFirstName);
        System.out.print("=+===+");

        System.out.println();
    }
}
