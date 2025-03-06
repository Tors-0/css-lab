package lab1;

public class Lab1B {
    public static void main(String[] args) {
        for (int numBottles = 5; numBottles > 0;) {
            onWall(numBottles);
            System.out.print(numBottles);
            botBeer(numBottles);
            takeOneDown(numBottles);
            numBottles--; // Update numBottles after taking one down
            onWall(numBottles);
            System.out.println(); // Blank line
        }
    }

    /**
     * Print the line "<numBottles> bottle(s) of beer on the wall"
     * @param bottles number of bottles
     */
    public static void onWall(int bottles) {
        if (bottles != 0) {
            System.out.println( bottles + " bottle" + (bottles == 1 ? "" : "s") + " of root beer on the wall");
        } else {
            System.out.println("No more bottles of root beer on the wall");
        }
    }
    /**
     * Prints the line "<numBottles> bottle(s) of beer" according to number of bottles
     * @param bottles number of bottles
     */
    public static void botBeer(int bottles) {
        System.out.println(" bottle" + (bottles == 1 ? "" : "s") + " of root beer");
    }

    /**
     * Prints the third line of each verse, accounting for the differences in verse 4 and the proper pluralization in verse 5
     * @param bottles number of bottles
     */
    public static void takeOneDown(int bottles) {
        // prints the third line of each verse
        if (bottles > 2) {
            System.out.println("Take one down and pass it around");
        } else if (bottles == 2) {
            System.out.println("If one of those bottles should happen to fall");
        } else if (bottles == 1) {
            System.out.println("Take it down and pass it around");
        }
    }
}