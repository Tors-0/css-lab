package hw;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileInputStream;

// Authors: Rae

public class HW_Loops_Files {
    static double programWeight;
    static double midtermWeight;
    static double finalWeight;

    public static void main(String[] args) {
        int courseNumber;            // Number of the course
        Scanner inputFile = null;    // File containing data (p. 297
        //     in Savitch discusses null)

        // ... code assigning inputFile in a try/catch statement
        try {
            inputFile = new Scanner(new FileInputStream("courseData.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace(System.out);
        }

        // ... code for any stuff you need to do one time ...
        programWeight = inputFile.nextInt();
        midtermWeight = inputFile.nextInt();
        finalWeight = inputFile.nextInt();

        //Per class, print a table of ID numbers, grades, weighted average
        //    per student, and a Pass or Fail programs grade.  The class
        //    average is also printed.
        for (;true;)
        {
            // Read class number, print class number title, headings.
            courseNumber = inputFile.nextInt();


            // initialization


            // Loop to handle one class:
            //     For each student in the class, get and print their
            //     information, compute their avg, and sum the avgs.
            while (true)
            {
                break;
            }

            // compute and print class average
            break;
        }
    }
}