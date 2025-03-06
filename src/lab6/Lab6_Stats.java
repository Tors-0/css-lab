package lab6;// Necessary imports for file i/o
import java.io.FileInputStream;       // "turns" the file into a read stream
import java.util.Scanner;             // Reads from stream
import java.io.IOException;           // This exception must be caught and handled when dealing with streams
import java.io.FileOutputStream;      // "turns" the file into a write stream
import java.io.PrintWriter;           // Writes to the stream


/**
 * Write a description of class Stats here.
 * 
 * @author Rae Johnston
 * @version 1.0.0
 */
public class Lab6_Stats {
    public static void main(String[] args) throws IOException {  
        // Scanner and PrintWriter must be declared outside the try block
        // otherwise their scope will be limited to within the block 
        Scanner input = null;
        
        double inputNumber = 0.0;
        
        int negNum = 0;
        int btw0and100 = 0;
        int geq100 = 0;
        
        int lineCounter = 0;
        double grandTotal = 0.0;
        
        double min = 0.0;
        double max = 0.0;
        double average = 0.0;
        
        try {            
           input = new Scanner(new FileInputStream("fileIn.txt"));
            // TODO: calculate the required statistics here

            while (input.hasNextDouble()) {
                inputNumber = input.nextDouble();
                lineCounter++;
                grandTotal += inputNumber;

                min = Math.min(min, inputNumber);
                max = Math.max(max, inputNumber);

                if (inputNumber < 0) negNum++;
                if (inputNumber >= 0 && inputNumber < 100) btw0and100++;
                if (inputNumber >= 100) geq100++;
            }

            average = grandTotal / lineCounter;
           
        } catch (IOException e) {
           System.out.println("File not found.");
           System.exit(0);
        } 
 
        input.close();   
        display(average, max, min, lineCounter, negNum, btw0and100, geq100);
    }

    public static void display(
            double average,
            double max,
            double min,
            double lineCounter,
            int negNum,
            int btw0and100,
            int geq100
    ) {
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileOutputStream("fileOut.txt"));
            // TODO: generate output to fileOut.txt
            output.println("Statistics for the numbers in fileIn.txt:");
            output.println("average: " + average);
            output.println("max: " + max);
            output.println("min: " + min);

            output.printf("There are %d negative numbers, %d numbers between " +
                    "0 (inclusive) and 100 (exclusive), and %d numbers that " +
                    "are greater than or equal to 100",
                    negNum, btw0and100, geq100);

        } catch (IOException e) {
            System.out.println(" Sorry, we cannot locate the file!");
            System.exit(0);
        }  
        output.close();
    }
}
