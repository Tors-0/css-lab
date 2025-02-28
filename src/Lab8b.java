// CSSSKL 142
// Lab8b.java
// < Add Your Name Here>

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



    }

    /**
     * Write a description of the method here
     * 
     * @param N
     * @param start
     * @param end
     * @return
     */
    public static int[][] randomMatrix(int N, int start, int end) {
       // TODO
        return new int[1][1];
    }

    /**
     * Helper method for randomMatrix to generate a random number
     * 
     * @param end
     * @param start
     * @return
     */
    public static int randomGenerator(int end, int start) {
        // TODO
        return 0;
    }

    /**
     * Write a description of the method here
     * 
     * @param array
     * @param row
     * @return
     */
    public static int rowSum(int[][] array, int row) {
        // TODO
        return 0;

    }

    /**
     * Write a description of the method here
     * 
     * @param array
     * @param col
     * @return
     */
    public static int colSum(int[][] array, int col) {
        // TODO
        return 0;
    }

    /**
     * Write a description of the method here
     * 
     * @param array
     * @return
     */
    public static boolean isSquare(int[][] array) {
        // TODO
        return true;
    }

    /**
     * Write a description of the method here
     * 
     * @param array
     * @return
     */
    public static boolean isLatin(int[][] array) {
        // TODO
        return true;
    }
}