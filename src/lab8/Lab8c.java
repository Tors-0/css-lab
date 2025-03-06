package lab8;// CSSSKL 142
// lab8.Lab8c.java
// Rae Johnston

import java.util.Scanner;

public class Lab8c {
    public static void main(String[] args) {
    
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Enter an integer, then press Enter");
    int size = keyboard.nextInt();
    
    // 1. Create your array dynamically here
    int[][] array = new int[size][size];


    // 2. Now, fill the 2D array with numbers
    int rotNum = 0;
    for (int[] iRow : array) {
        for (int i = 0; i < iRow.length; i++) {
            iRow[i] = i+1;
        }

        for (int i = 0; i < rotNum; i++) {
            rotateArrayToTheLeft(iRow);
        }
        rotNum++;
    }


    // 3. Print your 2D array
    for (int[] iRow : array) {
        for (int j = 0; j < iRow.length; j++) {
            System.out.print("+-");
        }
        System.out.println("+");

        for (int i : iRow) {
            System.out.print("|" + i);
        }
        System.out.println("|");
    }
    

    // 4. Print the lower border  

        for (int j = 0; j < array.length; j++) {
            System.out.print("+-");
        }
        System.out.println("+");

    }

    /**
     * Rotates each value in {@code nums} one to the right, rightmost element
     * is wrapped around to index 0
     *
     * @param nums array to be rotated, is modified
     */
    public static void rotateArrayToTheLeft(int[] nums) {
        if (nums.length == 0) return;

        int leftmostVal = nums[0];

        for (int i = 0; i < nums.length - 1; i++) {
            nums[i] = nums[i + 1];
        }

        nums[nums.length - 1] = leftmostVal;
    }
}
