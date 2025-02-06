import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
//        int a = 865486;
//        int b = 658458;
//        System.out.println(add(a, b)); // == 1523944
//        System.out.println(a + b); // == 1523944

        // ICA Loops 3
        float totalFare = 0;
        int totalCustomers = 0;
        int totalRides = 0;

        int currentDriver = 1;

        Scanner driverConsole = new Scanner(System.in);

        boolean driversYetToBeServed = true;
        while (driversYetToBeServed) {
            System.out.println("\nNow serving Driver " + currentDriver);
            do {
                System.out.print("Enter fare amount for a ride: ");
                float fare = driverConsole.nextFloat();
                driverConsole.nextLine();

                System.out.print("Enter number customers for a ride: ");
                int customers = driverConsole.nextInt();
                driverConsole.nextLine();

                totalRides++;
                totalFare += Math.abs(fare);
                totalCustomers += Math.abs(customers);

                if (customers < 0) {
                    driversYetToBeServed = false;
                }
                if (fare < 0) {
                    break;
                }
                System.out.println();
            } while (true);
            currentDriver++;
        }

        System.out.println("\nTotal rides: " + totalRides);
        System.out.println("Total gross receipts: $" + totalFare);
        System.out.println("Average fare for a ride: $" +
                (totalFare / totalRides));
        System.out.println("Total number customers: " + totalCustomers);
    }
    public static int add(int a, int b) {
        byte bitSize = 31;
        int sum = 0;
        int carry = 0;
        for (byte i = 0; i < bitSize; i++) {
            int aI = (a & 1 << i);
            int bI = (b & 1 << i);
            int cI = (carry & 1 << i);
            int and1 = aI & bI;
            int xor1 = aI ^ bI;
            int xor2 = xor1 ^ cI;
            int and2 = xor1 & cI;
            int or = and1 | and2;
            sum |= xor2;
            carry |= or << 1;
        }
        return sum;
    }
    public static String caesarCipher(String text, int key, boolean decrypt) {
        String output = "";
        if (decrypt) key *= -1;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c += (char) key;
                if (c < 'a') c += 26;
                else if (c > 'z') c -= 26;
            } if (c >= 'A' && c <= 'Z') {
                c += (char) key;
                if (c < 'A') c += 26;
                else if (c > 'Z') c -= 26;
            }
            output += c;
        }
        return output;
    }
    public static void runCaeserCipher() {
        boolean decryptMode = true;
        String data = "Z X Z F Q Q D Y M J R T X Y H T R R T S Q J Y Y J W N X J G Z Y S T Y F Q B F D X ";
        int key = 5;
        System.out.println(caesarCipher(data, key, decryptMode));
    }
}
