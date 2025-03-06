package lab8;// CSSSKL 142
// lab8.Lab8b.java
// < Add Your Name Here>

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lab8b {
    public static void main(String [] args) {
        // TODO: Test random matrix of integers on each method
        int[][] array = randomMatrix (7, 20, 100);
        
        // Example arrays for testing
        int[][] allNeg = {{-10,-12,-3}, {-4,-5,-6,-8}, {-7,-8}};
        int[][] latin = {{1,2,3}, {2,3,1}, {3,1,2}};
        int[][] notLatin = {{2,1,3}, {2,3,1}, {3,1,2}};
        int[][] nonSquare = {{1,2,3}, {4,5}, {6,7,8,9}};
        int[][] notSquare = {{10, 12, 3, 17}, {4, 5, 16, 18}, {7, 9, 10, 45}};
        int[][] perfectSquare = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};

        // TODO: Add at least 2 tests per method using the arrays above (or your own)
        // Try to generate different results (e.g. one that results true and another false)
        System.out.println("Testing rowSum");
        System.out.println(rowSum(perfectSquare, 0));
        System.out.println(rowSum(array, 4));
        System.out.println(rowSum(array, 6));

        System.out.println("Testing colSum");
        System.out.println(colSum(array, 1));
        System.out.println(colSum(allNeg, 0));
        System.out.println(colSum(perfectSquare, 0));

        System.out.println("Testing isSquare");
        System.out.println(isSquare(array));
        System.out.println(isSquare(nonSquare));
        System.out.println(isSquare(notSquare));

        System.out.println("Testing isLatin");
        System.out.println(isLatin(latin));
        System.out.println(isLatin(notLatin));
        System.out.println(isLatin(array));
    }

    /**
     * Creates a square array full of random values ranging from {@code start}
     * to {@code end}
     * 
     * @param N the size of the square array
     * @param start the smallest value possibly present in the array, inclusive
     * @param end the largest value possible present in the array, exclusive
     * @return the generated array
     */
    public static int[][] randomMatrix(int N, int start, int end) {
       int[][] out = new int[N][N];

       for (int i = 0; i < N; i++) {
           for (int j = 0; j < N; j++) {
               out[i][j] = randomGenerator(end, start);
           }
       }

        return out;
    }

    /**
     * Helper method for randomMatrix to generate a random number
     * 
     * @param end largest value, exclusive
     * @param start smallest value, inclusive
     * @return a single randomly generated number
     */
    public static int randomGenerator(int end, int start) {
        return (int) (( Math.random() * ( end - start )) + start );
    }

    /**
     * Write a description of the method here
     * 
     * @param array
     * @param row
     * @return
     */
    public static int rowSum(int[][] array, int row) {
        int total = 0;
        for (int i : array[row]) {
            total += i;
        }
        return total;
    }

    /**
     * Write a description of the method here
     *
     * @param array
     * @param col
     * @return
     */
    public static int colSum(int[][] array, int col) {
        int total = 0;
        for (int[] iRow : array) {
            total += iRow[col];
        }
        return total;
    }

    /**
     * Write a description of the method here
     * 
     * @param array
     * @return
     */
    public static boolean isSquare(int[][] array) {
        int horiSize = array.length;
        for (int[] iRow : array) {
            // check each row length to catch jagged arrays
            if (iRow.length != horiSize) {
                return false;
            }
        }
        return true;
    }

    /**
     * Write a description of the method here
     * 
     * @param array
     * @return
     */
    public static boolean isLatin(int[][] array) {
        if (!isSquare(array)) {
            // early return if the array is not square
            return false;
        }
        List<Integer> currList = new ArrayList<>();

        for (int[] iRow : array) {
            currList.clear();

            // collect and sort the row
            for (int i : iRow) currList.add(i);

            if (listIsntConsecutiveVals(currList)) return false;
        }

        // for each column number
        for (int i = 0; i < array.length; i++) {
            currList.clear();

            // collect nums from that column
            for (int[] iRow : array) {
                currList.add(iRow[i]);
            }

            if (listIsntConsecutiveVals(currList)) return false;
        }

        return true;
    }

    private static boolean listIsntConsecutiveVals(List<Integer> currList) {
        Collections.sort(currList);

        // make sure the row starts with 1
        if (currList.get(0) != 1) return true;

        // ensure that each value is one more than the last
        for (int j = 1; j < currList.size(); j++) {
            if (currList.get(j) != currList.get(j - 1) + 1) return true;
        }
        return false;
    }
}