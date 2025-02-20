import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Lab6_Advice {
    public static void main(String[] args) {
        File advice = new File("advice.txt");
        File adviceLog = new File("adviceLog.txt");

        PrintWriter adviceWriter = null;
        PrintWriter adviceLogWriter = null;
        Scanner adviceReader = null;

        try {
            advice.createNewFile();
            adviceLog.createNewFile();

            adviceWriter = new PrintWriter(new FileOutputStream(advice));
            adviceLogWriter = new PrintWriter(new FileOutputStream(adviceLog, true));
            adviceReader = new Scanner(new FileInputStream(advice));
        } catch (Exception e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Here's some advice!");
        while (adviceReader.hasNextLine()) {
            System.out.println(adviceReader.nextLine());
        }

        // get the user's advice
        // detect end of advice stream
        // write advice to advice.txt and adviceLog.txt
    }
}
