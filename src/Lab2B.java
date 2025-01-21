//Lab2B.java

import java.util.*;

public class Lab2B {
	/**
	 *
	 * @param args value of {@code args}
	 */
	public static void main(String[] args){
		// TODO: Import and declare a Scanner
		Scanner userInput = new Scanner(System.in);
		// TODO: Request and parse user input
		System.out.println("Please enter you name followed by three numbers (space separated):");
		String name = userInput.next();
		double num1 = Double.parseDouble(userInput.next());
		double num2 = Double.parseDouble(userInput.next());
		double num3 = Double.parseDouble(userInput.next());
		// TODO: Sort values and display
		double maxNum = Math.max(Math.max(num1, num2), num3);
		double minNum = Math.min(Math.min(num1, num2), num3);
		Double[] numArray = new Double[]{num1, num2, num3};
		List<Double> numList = Arrays.asList(numArray);
		Collections.sort(numList); // we checked, this method doesn't use any conditionals :D
		System.out.printf("Hi there, %s! Here are the numbers you entered in descending order: %n %s %s and %s%n",
				name, maxNum, numList.get(1), minNum);
		// TODO: Thank user
		System.out.println("Thank you for using the three-number-sorting system! Good-bye.");
	}

}
