// Lab3C.java
// Part 3
// compiled and tested with Java 21

import java.util.Scanner;

public class Lab3C {
	static Scanner scanny = new Scanner(System.in);
	public static void main(String[] args) {
		boolean isGameTied;
		do {
			// TODO: Get user choice
			String userChoiceString = "";
			while (!"0".equals(userChoiceString) &&
					!"1".equals(userChoiceString) &&
					!"2".equals(userChoiceString)) {
				System.out.print(
						"Please choose rock (0), paper (1), or scissors (2): "
				);
				userChoiceString = scanny.nextLine();
			}
			int userChoice = Integer.parseInt(userChoiceString);

			// TODO: Generate random computer choice
			int computerChoice = (int) (Math.random() * 3);

			// TODO: Print choices
			System.out.println("You chose " + intToWord(userChoice));
			System.out.println("I chose " + intToWord(computerChoice));
			// TODO: Determine winner
			System.out.println(findWinner(userChoice, computerChoice));
			if (userChoice == computerChoice) {
				System.out.println("\nGame is tied, rematching...\n");
				isGameTied = true;
			} else {
				isGameTied = false;
			}
		} while (isGameTied);
	}

	// intToWord
	// Convert from an integer to the relevant string, using a switch
	// choice: the integer to convert
	public static String intToWord(int choice) {
		switch (choice) {
            case 0 -> {
                return "Rock";
            }
            case 1 -> {
				return "Paper";
			}
			case 2 -> {
				return "Scissors";
			}
			default -> {
				System.out.println("Invalid choice");
				return "Error";
			}
		}
	}


	// findWinner
	// Determine the winner from two choices
	// userChoice: the user's integer
	// compChoice: the computer's integer
	public static String findWinner(int userChoice, int compChoice) {
		if (userChoice == compChoice) {
			return "Tie.";
		}

		// since we represent the choices with integers, we can use a clever
		// branching statement rather than using one if statement for
		// every possible case
		int diff = userChoice - compChoice;
		if (diff == 1 || diff == -2) {
			return intToWord(userChoice) + " beats " + intToWord(compChoice) +
					". You win.";
		} else {
			return intToWord(compChoice) + " beats " + intToWord(userChoice) +
					". I win.";
		}
	}

	
	// chooseString
	// prints "<player> chose <string value of choice>."
	// player: a string that is either "You" or "I"
	// choice: the player's integer
	public static void chooseString(String player, String choice) {
		System.out.printf("%s chose %s", player, choice);
	}
}
