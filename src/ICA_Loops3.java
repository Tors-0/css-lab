import java.util.Scanner;

public class ICA_Loops3 {
    public static void main(String[] args) {
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
}
