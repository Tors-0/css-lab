// CSSSKL 142
// Lab5.java
// < Add Your Name Here>

import java.util.Arrays;
import java.util.Scanner;

public class Lab5 {
    static Scanner keyboardInput = new Scanner(System.in);
    static int[] compositeBooleans;
    /**
     * We use x >> 6 (equal to x/64) to get the index of the int x is within. Then use bitwise operators to divide x by 2, take the mod of
     * x and 32, which gives us the index within the int at x/64. Then place a 1 at that index
     * @param x the index in our "boolean" array
     */
    private static void markComposite(long x) {
        if (x % 2 == 0) return;
        compositeBooleans[(int) (x >> 6)] |= (1 << ((x >> 1) & 31));
    }
    /**
     * We use x >> 6 to get the index of the int x is within. Then use bitwise operators to divide x by 2, take the mod of
     * x and 32, which gives us the index within the int at x/64. Then bitwise AND the binary at that bit with 1 and
     * compare that output with 0 (the binary representation of {@link Boolean#FALSE})
     * @param x the index in the "boolean" array
     * @return true if x is prime, false if composite
     */
    private static boolean checkIfPrime(long x) {
        if (x % 2 == 0) return false;
        return (compositeBooleans[(int) (x >> 6)] & (1 << ((x >> 1) & 31))) == 0;
    }

    public static void main(String[] args)  {
        // Call methods here to test
//        getRichQuick();
//         isPrime(179424673);
//         palindromeCheck();

        // Finally only call programSelector
         programSelector(true);
    }
    
    //getRichQuick
    //Calculates days to earn $1 million
    public static void getRichQuick() {
        double totalMoney = 1;
        long day = 1;
        double halfTotal;
        double newTotal;
        float moneyPerDay = 1;

        System.out.println("Day 1: $1");
        while (totalMoney < 1_000_000) {
            day++;
            halfTotal = totalMoney / 2;
            newTotal = totalMoney + (moneyPerDay + halfTotal);

            // printf in a loop eats lots of memory, best to avoid in the future
            System.out.printf("Day %s: $%,.2f + ($1 + %,.2f) = $%,.2f %n",
                    day,
                    totalMoney,
                    halfTotal,
                    newTotal);

            totalMoney = newTotal;
        }
    }
    
    //isPrime
    //Prints a message if a number is prime or not 
    public static void isPrime() {
        System.out.println("Please enter a number to check if it is prime:");

        long n = keyboardInput.nextLong();
        keyboardInput.nextLine();

        if (n % 2 == 1) {
            /*
         * We're using each individual bit of an int (32 bit) array as a boolean, and only storing odd numbers, so we can
         * cut the array size down to n/64. +1 for IndexOutOfBoundsException protection
         */
            compositeBooleans = new int[(int) (n / 64) + 1];
            Arrays.fill(compositeBooleans, 0x00000000); // fill array with zero

            long nextProgressMarker = 9;
            System.out.print("Thinking");

            for (long currentNum = 3; currentNum * currentNum <= n; currentNum += 2) {
                if (currentNum == nextProgressMarker) {
                    System.out.print(".");
                    nextProgressMarker = nextProgressMarker * 3;
                }
                if (checkIfPrime(currentNum)) {
                    for (long j = currentNum * currentNum, k = currentNum << 1; j <= n; j += k) {
                        markComposite(j);
                    }
                }
            }
        }

        System.out.println();
        if (checkIfPrime(n)) {
            System.out.println(n + " is a prime number");
        } else {
            System.out.println(n + " is NOT a prime number");
        }
    }
    
	//palindromeCheck 
    //This program reads words, identifies, counts and writes all the palindromes and the total
    //palindrome count.
    //hint 1: Using keybord.next() will only return what comes before a space.
    //hint 2: Using keybord.nextLine() automatically reads the entire current line.
    public static void palindromeCheck(){

        String userInput = "";		// Stores words read from user input
        int palindromeCount = 0;	// Keeps track of palindrome words only 
        int totalWords = 0;			// Counts the total number of lines read from the given text input

        System.out.println("Enter some words separated by white space ");    // Ask for user input
      
        Scanner keyboard = new Scanner(System.in);	// Declare your Scanner object here

        while (keyboard.hasNext() && keyboard.hasNextLine()) {		// for each word user enters
            userInput = keyboard.next();	// Store each word in a string variable and then do your operations

            if ("stop".equals(userInput)) break; // break the loop if the user enters "stop"

            totalWords++;					// Increment number of words as you read each one
            
            System.out.println("  " + totalWords + " " + userInput); // Testing

            if (checkIfPalindrome(userInput)) {
                System.out.println(userInput + " is a palindrome.");
                palindromeCount++;
            } else {
                System.out.println(userInput + " is not a palindrome.");
            }
        }

        System.out.printf("There are %s palindromes out of %s words provided by user",
                palindromeCount,
                totalWords);
    }

    public static boolean checkIfPalindrome(String word) {
        String wordBackwards = "";
        for (int i = word.length() - 1; i >= 0; i--) {
            wordBackwards += word.charAt(i);
        }
        return word.equals(wordBackwards);
    }

    public static void programSelector(boolean keepLooping){
        System.out.println(" Welcome to Lab5!\n" +
                "    Enter 1 to check how long it takes to get rich on a magic dollar coin.\n" +
                "    Enter 2 to check for prime numbers.\n" +
                "    Enter 3 to enter palindrome words.\n" +
                "    Enter 4 to re-print the menu.\n" +
                "    Enter 0 to exit.");

        while (keepLooping) {
            System.out.println("What is your choice?");
            int choice = keyboardInput.nextInt();
            keyboardInput.nextLine();

            switch (choice) {
                case 1 -> {
                    getRichQuick();
                }
                case 2 -> {
                    isPrime();
                }
                case 3 -> {
                    palindromeCheck();
                }
                case 4 -> {
                    programSelector(keepLooping);
                    keepLooping = false;
                }
                default -> {
                    keepLooping = false;
                }
            }
        }
    }
}