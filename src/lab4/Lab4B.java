package lab4;// lab4.Lab4B.java
// CSSSKL 142
// <Your Name>

import java.util.Scanner;
import java.lang.*;

public class    Lab4B {
    public static void main(String[] args) {

        // Call all methods here
        System.out.println("For size 3:");
        straightLineBox(3);
        System.out.println("For size 5:");
        straightLineBox(5);

        System.out.println("misc.Testing boxmaker:");
        boxMaker();

        System.out.println("misc.Testing triangle(6):");
        triangle(6);
        System.out.println("misc.Testing triangle(1):");
        triangle(1);

        System.out.println("testing xPattern(3):");
        xPattern(3);
        System.out.println("testing xPattern(0):");
        xPattern(0);
        System.out.println("testing xPattern(1):");
        xPattern(1);
    }

	// straightLineBox
	// Produces and displays a box of straight lines using asterisks
	// size: the length of the sides of the box
    public static void straightLineBox(int size) {
        for (int iRow = 0; iRow < size; iRow++) {
            for (int iColumn = 0; iColumn < size; iColumn++) {
                System.out.print('*');
            }
            System.out.println();
        }
    }

	// boxMaker
	// Ask the user for an integer size, and print the box using asterisks
    public static void boxMaker() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter size (int): ");
        int size = userInput.nextInt();
        userInput.nextLine();
        for (int iRow = 0; iRow < size; iRow++) {
            for (int iColumn = 0; iColumn < size; iColumn++) {
                if (iColumn == 0 || iRow == 0 || iColumn == size - 1 ||
                        iRow == size - 1) {
                    System.out.print('*');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

	// triangle
	// Print a shape that is a triangular pattern with sides of 6 asterisks
	// number: the width and length of the triangle
    public static void triangle(int number) {
        for (int iRow = 0; iRow < number; iRow++) {
            for (int iColumn = 0; iColumn < number; iColumn++) {
                if (iRow - iColumn > 0) {
                    System.out.print(" .");
                } else {
                    System.out.print(" *");
                }
            }
            System.out.println();
        }
    }

	// xPattern
	// Print a shape that is an "X" pattern with arms of 3 asterisks each
	// number: the length of the arms of the "X"
    public static void xPattern(int number) {
        for (int iRow = 0; iRow < number * 2 + 1; iRow++) {
            for (int iColumn = 0; iColumn < number * 2 + 1; iColumn++) {
                if (iRow == iColumn || iRow + iColumn == number * 2) {
                    System.out.print(" *");
                } else {
                    System.out.print(" .");
                }
            }
            System.out.println();
        }
    }
}