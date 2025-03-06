package lab7;// CSSSKL 142
// lab5.Lab5.java
// < Add Your Name Here>

import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Lab7 {
    public static void main(String[] args) {
        // TODO: Implement user input
        Scanner scanny = new Scanner(System.in);
        System.out.println("Please input a sentence:");
        String userInput = scanny.nextLine();


        capitalizeFirstLetters(userInput);
        getNameInitials(userInput);
        getLexLargest(userInput);
        printCharIndexTable(userInput);
    }  
    
    public static void capitalizeFirstLetters(String input) {
        // TODO: Parse the user's input for words and capitalize each one.
        String[] words = input.split(" "); // split the words apart
        String output = "";
        for (String w : words) {
            if (!w.isEmpty()) {
                // capitalize and add the first letter
                output += w.substring(0, 1).toUpperCase(Locale.ROOT);
                // add the remaining letters (unchanged)
                output += w.substring(1);
                // add back the space removed by the split algorithm
                output += " ";
            }
        }
        System.out.println(output);
    }

    public static void getNameInitials(String fullName) {
        // TODO: Parse the user's full name and returns a String of the name's initials.
        String[] names = fullName.split(" "); // split string into names
        String initials = ""; // keep track of initials
        for (String name : names) {
            // ensure the split algorithm hasn't given us an empty string
            if (!name.isEmpty()) {
                // add the first letter (capitalized) of each name
                initials += name.substring(0,1).toUpperCase(Locale.ROOT) + ".";
            }
        }
        System.out.println(initials);
    }

    public static void getLexLargest(String input) {
        // TODO: Parse the user's input and return the largest word in lexicographical order (The one that would appear latest in the dictionary).
        String[] words = input.split(" "); // split string into words
        if (words.length > 0) {
            String lexLargest = words[0];
            for (String word : words) {
                if (word.compareTo(lexLargest) > 0) {
                    lexLargest = word;
                }
            }
            System.out.println(lexLargest);
        } else {
            System.out.println("No words provided (err)");
        }
    }

    public static void printCharIndexTable(String input){
        // TODO: Parse the user's input and print a table of 26 alphabet characters and their index in the input.
        HashMap<Character, Integer> charMap = new HashMap<>();
        for (char c = 'a'; c <= 'z'; c++) {
            charMap.put(c, input.indexOf(c));
        }

        for (char d = 'a'; d <= 'z'; d++) {
            System.out.printf("%2s ", d);
        }
        System.out.println();
        for (char e = 'a'; e <= 'z'; e++) {
            System.out.print("===");
        }
        System.out.println();
        for (char f = 'a'; f <= 'z'; f++) {
            if (charMap.get(f).toString().length() > 2)
                System.out.printf("%3d", charMap.get(f));
            else
                System.out.printf("%2d ", charMap.get(f));
        }
    }
}  
