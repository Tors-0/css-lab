package lab6;// Necessary imports for file i/o
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;             // Reads from stream
import java.io.IOException;           // This exception must be caught and handled when dealing with streams         
import java.io.PrintWriter;           // Writes to the stream


/**
 * Write a description of class Diary here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lab6_Diary {
    
    public static void main(String[] args) throws IOException {
      
        // Needed variables
        int mm = 0;
        int dd = 0;
        int yyyy = 0;
        String prose = "";   // Empty string to read prose
     
        PrintWriter output = null;
        Scanner keyboard = new Scanner(System.in);
      
        System.out.println("Enter the date as three integers separated by spaces (i.e mm dd yyyy) ");
      
        mm = keyboard.nextInt();
        dd = keyboard.nextInt();
        yyyy = keyboard.nextInt();
        keyboard.nextLine();
      
        System.out.println("Enter as many lines of prose you wish (for your to-do list or diary entry)");
      
        // START YOUR CODE HERE
        try {
            File outputFile = new File("diaryLog.txt");
            outputFile.createNewFile();
            output = new PrintWriter(new FileOutputStream(outputFile, true));
        } catch (Exception e) {
            System.out.println("Something went wrong while writing files...");
            e.printStackTrace();
            System.exit(0);
        }

        output.printf("Date: %2d/%2d/%4d %n", mm, dd, yyyy);
        do {
            prose = keyboard.nextLine();
            output.println(prose);
        } while (!"".equals(prose));

        output.close();
    }
}


