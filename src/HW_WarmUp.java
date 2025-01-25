import java.util.Scanner;

public class HW_WarmUp {
    //Driver for if statement warm-up

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        // section 1
        System.out.println("Enter the number");
        int number = keyboard.nextInt();
        keyboard.nextLine();
        if (number < 0) {
            System.out.println("The number is negative");
        }

        // section 2
        System.out.println("Enter a second number");
        number = keyboard.nextInt();
        keyboard.nextLine();

        if (number == 0) {
            System.out.println("The number is zero");
        }

        // section 3
        System.out.println("Enter a third number, a double, for the class avg");
        double average = keyboard.nextDouble();
        keyboard.nextLine();

        System.out.println((average >= 65 ? "A" : "Not a") + " passing grade");

        // section 4 & 5
        System.out.println("Is the answer reported true? Enter a boolean val.");
        boolean answer = keyboard.nextBoolean();
        keyboard.nextLine();

        // simplified 4 & 5 into one line without conditional
        System.out.println("The answer is " + answer);

        // section 6
        System.out.println("Enter a number");
        number = keyboard.nextInt();
        keyboard.nextLine();

        if (number % 2 == 0) {
            System.out.println("The number is even");
        } else {
            System.out.println("The number is odd");
        }

        // section 7
        System.out.println("Enter a floating-point grade value");
        float grade = keyboard.nextFloat();
        keyboard.nextLine();

        if (grade >= 90) {
            System.out.println("The grade is above or equal to 90%");
        }

        // section 8
        else if (grade >= 80) {
            System.out.println("The grade is above or equal to 80%");
        }

        grade = 0;

        // section 9
        System.out.println("Please enter an int value for temperature");
        int temperature = keyboard.nextInt();
        keyboard.nextLine();

        if (temperature > 78) {
            System.out.println("The temperature is greater than 78");
        } else {
            System.out.println("The temperature is less than or equal to 78");
        }

        // section 10

        if ((temperature >= 0 && Math.abs(temperature % 2) == 1) ||
                        (temperature == 0 && temperature % 2 == 0)
        ) {
            System.out.println("yes; positive and odd or zero and even");
        } else {
            System.out.println("no; not positive and odd or zero and even");
        }

        // section 11
        System.out.println("Please enter a letter grade (A-D | F)");
        char letterGrade = keyboard.nextLine().charAt(0);
        letterGrade = ("" + letterGrade).toUpperCase().charAt(0);

        if (letterGrade == 'A') {
            System.out.println("The grade is an " + letterGrade);
        } else if (letterGrade == 'B') {
            System.out.println("The grade is a " + letterGrade);
        } else if (letterGrade == 'C') {
            System.out.println("The grade is a " + letterGrade);
        } else if (letterGrade == 'D') {
            System.out.println("The grade is a " + letterGrade);
        } else if (letterGrade == 'F') {
            System.out.println("The grade is an " + letterGrade);
        } else {
            System.out.println("The entered character is not a letter grade.");
        }

        // section 12 done

        // section 13

        System.out.print("Enter two integers, seperated by spaces: ");
        int number1 = keyboard.nextInt();
        int number2 = keyboard.nextInt();
        keyboard.nextLine();

        int max = max(number1, number2);
        System.out.println("Largest is " + max);
        System.out.println("Smallest is " +
                (number1 == max ? number2 : number1));

    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }
}
