package lab4;// lab4.Lab4A.java
// CSSSKL 142
// <Your Name>

import java.math.BigInteger;
import java.lang.*;

public class Lab4A {
    public static void main(String[] args) {
        BigInteger totalGrains = new BigInteger("0");
        // Call all methods here
        for (int i = 0; i < 64; i++) {
            totalGrains = countGrains(i, totalGrains);
        }

        numBackward(123456789);
        numBackward(123454321);
    }

	// countGrains
	// Calculates the number of grains and prints out the result per lab instructions
    public static BigInteger countGrains(int day, BigInteger totalGrains) {
        // use a `BigInteger` to hold values larger than `long`
        BigInteger todaysGrain = twoToThePowerOf(day);
        totalGrains = totalGrains.add(todaysGrain);
        System.out.printf(
                "Day %2s and you got %s grains of rice for a total of %s grain(s).%n",
                day + 1,
                todaysGrain,
                totalGrains);
        return totalGrains;
    }

	// twoToThePowerOf
	// Calculates 2 to the power of a given exponent and prints the result
	// exponent: an integer to raise 2 to the power of
    public static BigInteger twoToThePowerOf(int exponent) {
        if (exponent == 0) return BigInteger.valueOf(1);
        BigInteger total = new BigInteger("1");
        BigInteger multiple = new BigInteger("2");
        for (int i = 0; i < exponent; i++) {
            total = total.multiply(multiple);
        }
        return total;
    }

	// numBackward
	// Reverses the digits of a given integer; e.g., 12345 would become 54321
	// number: the integer to be reversed
    public static void numBackward(int number) {
        String num = "" + number;
        String out = "";
        for (int i = num.length() -1; i >= 0; i--) {
            out += num.charAt(i);
        }

        System.out.printf("backward: %s, %spalindrome%n",
                out,
                out.equals(num) ? "" : "not "
        );
    }
}