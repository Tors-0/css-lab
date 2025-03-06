package hw;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Assumes the situation is taking place on or near the earth's surface, and
 * the general composition of the atmosphere has not significantly changed
 * in density since September 2014, and that gravity has not changed
 * significantly.
 *
 * @author Rae Johnston
 */
public class HW_Arrays {
    static final double AIR_DENSITY = 1.14;
    static final double GRAV_ACCEL = 9.81;

    static double[] timesData;
    static double[] velocityData;

    public static void main(String[] args) {
        Scanner userIn = new Scanner(System.in);

        calculateSkydiveData(userIn);
    }

    private static void calculateSkydiveData(Scanner userIn) {
        System.out.print("Please enter the mass of the skydiver (kg): ");
        final double diverMass = userIn.nextDouble();
        userIn.nextLine();
        System.out.print("Please enter the cross-sectional area of the skydiver (m^2): ");
        final double diverCrossArea = userIn.nextDouble();
        userIn.nextLine();
        System.out.print("Please enter the drag coefficient of the skydiver: ");
        final double diverDragCoeff = userIn.nextDouble();
        userIn.nextLine();
        System.out.print("Please enter the simulation ending time (sec): ");
        final double endingTime = userIn.nextDouble();
        userIn.nextLine();
        System.out.print("Please enter the time step (sec): ");
        final double deltaTime = userIn.nextDouble();
        userIn.nextLine();
        System.out.print("Please enter the output file name: ");
        final String outputFilename = userIn.nextLine();

        int timeSteps = (int) (endingTime / deltaTime) + 1;
        timesData = new double[timeSteps];
        velocityData = new double[timeSteps];

        double velocity;
        timesData[0] = 0;
        velocityData[0] = 0;

        int ticker = 1;
        for (double time = deltaTime; time <= endingTime; time += deltaTime) {
            // calculate new velocity for this time step, based on prior factors
            velocity = calcNewVelocity(ticker, diverDragCoeff, diverCrossArea, diverMass, deltaTime);

            // write this data to arrays
            timesData[ticker] = time;
            velocityData[ticker] = velocity;

            // increase the ticker since it isn't handled by the loop
            ticker++;
        }

        PrintWriter fileOutput = null;
        try {
            File outputLoc = new File(outputFilename);
            // create the output file if it does not exist
            outputLoc.createNewFile();
            fileOutput = new PrintWriter(new FileOutputStream(outputLoc));
        } catch (Exception e) {
            System.out.println("File writing error " + e.getMessage());
            System.exit(0);
            return;
        }

        System.out.println("Writing out file, here are the first few lines:");
        for (int lineNum = 0; lineNum < timeSteps; lineNum++) {
            fileOutput.printf("%1.3f, %1.4f%n", timesData[lineNum], velocityData[lineNum]);

            // print only first few lines of file to console
            if (lineNum < 10) {
                System.out.printf("%1.3f, %1.4f%n", timesData[lineNum], velocityData[lineNum]);
            }
        }

        fileOutput.close();
    }

    /**
     * Calculates the next velocity of a skydiver with given parameters
     *
     * @param ticker counter, used for accessing previous calculation values
     * @param diverDragCoeff unitless
     * @param diverCrossArea meters squared
     * @param diverMass kilograms
     * @param deltaTime seconds since previous velocity
     * @return the double value for the current velocity
     */
    private static double calcNewVelocity(int ticker, double diverDragCoeff, double diverCrossArea, double diverMass, double deltaTime) {
        return velocityData[ticker - 1] +
                ((GRAV_ACCEL - ((diverDragCoeff * AIR_DENSITY * diverCrossArea) / (2 * diverMass))
                        * Math.pow(velocityData[ticker - 1], 2.0)) * deltaTime);
        // grabbing velocity at `ticker - 1` gives the previous velocity
    }
}
