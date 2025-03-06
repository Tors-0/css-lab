package lab3;// lab3.Lab3A.java
// Part 1
// compiled and tested in Java 21

import java.util.Scanner;

public class Lab3A {
	public static void main(String[] args) {
		// oddEvenChecker tests
		System.out.println("\nmisc.Testing oddEvenChecker:");
		oddEvenChecker(3); // Should print "3 is an odd number"
		oddEvenChecker(8); // Should print "8 is an even number"
		oddEvenChecker(174);
		oddEvenChecker(-145);

		// multipleOfChecker tests
		System.out.println("\nmisc.Testing multipleOfChecker:");
		// Should print "16 is a multiple of 4"
		multipleOfChecker(16, 4);
		// Should print "11 is not a multiple of 3"
		multipleOfChecker(11, 3);
		multipleOfChecker(100000001, 17);
		multipleOfChecker(4096, 64);

		// sqrtSumBucketer tests
		System.out.println("\nmisc.Testing sqrtSumBucketer:");
		sqrtSumBucketer();
		sqrtSumBucketer();
	}

	// oddEvenChecker
	// Determine if num is odd or even.
	// num: the integer to check
	public static void oddEvenChecker(int num) {
		System.out.println(num + " is an " +
				(num % 2 == 0 ? "even" : "odd") + " number");
	}

	// multipleOfChecker
	// Determine if num is a multiple of base.
	// num: the integer to check
	// base: the integer base against which to check
	public static void multipleOfChecker(int num, int base) {
		System.out.println(num + (num % base == 0 ? " is" : " is not") +
				" a multiple of " + base);
	}

	// sumSqrtBucketer
	// Sums the square roots of two doubles (input from user) and
	// sorts results into bucket, printing result
	public static void sqrtSumBucketer() {
		Scanner scanny = new Scanner(System.in);
		System.out.println("Please enter two double values, " +
				"separated by spaces");
		double num1 = scanny.nextDouble();
		double num2 = scanny.nextDouble();
		scanny.nextLine();

		if (num1 < 0 || num2 < 0) {
			System.out.println("Negative numbers are not allowed!");
			return;
		}

		double total = Math.sqrt(num1) + Math.sqrt(num2);
		int bucket = (int)total / 10;

		switch (bucket) {
			case 0 ->
				System.out.println("Less than 10");
			case 1 ->
				System.out.println("Between 10 and 20");
			case 2 ->
				System.out.println("Between 21 and 30");
			default ->
				System.out.println("Greater than 30");
		}
	}
}
