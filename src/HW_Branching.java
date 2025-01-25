/**
 * Created by Rae Johnston, 2025
 *
 * For the completion of the HW: Branching assignment
 */

import java.util.Scanner;

public class HW_Branching {
    // create a scanner that can be used by all methods
    static Scanner scanny = new Scanner(System.in);

    /**
     * Prints out a message asking the user for sales based on the store name
     * @param storeName the name of the store to collect sales from
     * @return the double value of the sales for {@code storeName}
     */
    public static double getStoreSales(String storeName) {
        System.out.println("Enter the sales " + storeName + ": ");
        double sales = scanny.nextDouble();
        scanny.nextLine();
        return sales;
    }

    public static void main(String[] args) {
        // these store names can be customized for extra functionality!
        String storeOneName = "Store 1";
        String storeTwoName = "Store 2";
        String storeThreeName = "Store 3";

        // collect sales data for all 3 stores
        double storeOneSales =
                getStoreSales("for " + storeOneName);
        boolean storeOneExceeds = false;
        double storeTwoSales =
                getStoreSales("for " + storeTwoName);
        boolean storeTwoExceeds = false;
        double storeThreeSales =
                getStoreSales("for " + storeThreeName);
        boolean storeThreeExceeds = false;

        double salesThreshold = getStoreSales("threshold");

        // check if each store exceeds the threshold and count how many do so
        int storesAboveThreshold = 0;
        if (storeOneSales > salesThreshold) {
            storesAboveThreshold++;
            storeOneExceeds = true;
        }
        if (storeTwoSales > salesThreshold) {
            storesAboveThreshold++;
            storeTwoExceeds = true;
        }
        if (storeThreeSales > salesThreshold) {
            storesAboveThreshold++;
            storeThreeExceeds = true;
        }

        // compile the report based on the number of stores over the threshold
        double averageSales = 0;
        if (storesAboveThreshold > 0) {
            double total = 0;
            // take the average of each store that exceeds the threshold
            total += storeOneExceeds ? storeOneSales : 0;
            total += storeTwoExceeds ? storeTwoSales : 0;
            total += storeThreeExceeds ? storeThreeSales : 0;
            averageSales = total / storesAboveThreshold;
        }

         /*
         * variables used later should be declared outside of branch statement
         * to ensure that devs know the variable can be used regardless of
         * the outcome of the branching statements
         */
        String celebrationReport;

        if (storesAboveThreshold == 0) {
            celebrationReport = "\nNo store met the threshold";
        } else {
            // add the names of each successful store to the celebration report
            celebrationReport = "\nStore(s) "
                    + (storeOneExceeds ? "\"" + storeOneName + "\", " : "")
                    + (storeTwoExceeds ? "\"" + storeTwoName + "\", " : "")
                    + (storeThreeExceeds ? "\"" + storeThreeName + "\"" : "")
                    + " did great!";
        }

        // print out the final report(s)
        System.out.println(celebrationReport);
        if (storesAboveThreshold > 0) {
            System.out.printf(
                    "\tThe average sales for exceeding stores: %.2f",
                    averageSales
            );
        }
    }
}
