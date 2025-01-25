// Lab3B.java
// Part 2

public class Lab3B {
	public static void main(String[] args) {

		// greaterOfTwo tests
		System.out.println("\nTesting greaterOfTwo:");
		System.out.println(greaterOfTwo(-3, 6.2)); // 6.2
		System.out.println(greaterOfTwo(253, 3.2e+5)); // 320000
		System.out.println(greaterOfTwo(-325, 56)); // 56


		// smallestOfThree tests
		System.out.println("\nTesting smallestOfThree:");
		System.out.println(smallestOfThree(3, 17, 8)); // 3
		System.out.println(smallestOfThree(74, 0x11, 348)); // 17
		System.out.println(smallestOfThree(90, 23, 23)); // 23

	}

	// greaterOfTwo
	// Return the greater of two doubles without using Math.max or Math.min
	// num1: first value
	// num1: second value
	public static double greaterOfTwo(double num1, double num2) {
		return num1 > num2 ? num1 : num2;
	}


	// smallestOfThree
	// Return the smallest of three doubles without using Math.max or Math.min
	// num1: first value
	// num2: second value
	// num3: third value
	public static double smallestOfThree(double num1, double num2, double num3) {
		if (num1 <= num2 && num1 <= num3) {
			return num1;
		} else if (num2 <= num1 && num2 <= num3) {
			return num2;
		} else {
			return num3;
		}
	}
}
