import java.io.*;
import java.util.Scanner;

public class ICA_File_IO {
    public static void main(String[] args) {
        Scanner inputStream = null;
        PrintWriter outputStream = null;

        try {
            File newData = new File("newdata.txt");
            newData.createNewFile(); // create file if it doesn't exist

            inputStream =
                    new Scanner(new FileInputStream("data.txt"));
            outputStream =
                    new PrintWriter(new FileOutputStream(newData));
        } catch (Exception e) {
            System.out.println("Error opening or finding files:");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }

        int next, sum = 0;
        while (inputStream.hasNextInt()) {
            next = inputStream.nextInt();
            sum += next;

            outputStream.println(next);
        }

        inputStream.close();
        outputStream.close();

        System.out.println("The sum of the numbers is " + sum);
    }
}
