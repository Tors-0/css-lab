import java.io.*;
import java.util.Scanner;

public class MakeErrors {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please type the filepath (name included) relative to this file, of the file you would like to create errors in:");
        String filepath = userInput.nextLine();

        Scanner inputStream = null;
        PrintWriter outputStream = null;

        try {
            inputStream = new Scanner(new FileInputStream(filepath));
            outputStream = new PrintWriter(new FileOutputStream(filepath));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening files");
            System.exit(0);
        }


    }
}
