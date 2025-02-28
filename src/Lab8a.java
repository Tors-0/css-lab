// CSSSKL 142
// Lab8a.java
// < Add Your Name Here>

import java.util.Arrays;
import java.util.List;

public class Lab8a {
    public static void main(String[] args) {
        //for exercises 1, 2
        double[] oddSet = {0.5, 3.1415, 7.6, 42, 799.4};
        double[] evenSet = {0.5, 2.2, 3.1415, 7.6, 42, 799.4};
        double[] notSorted = {3.4, 0.01, 8.7, 2.3};
        
        System.out.println("Exercise 1: ");
        System.out.println(findMedian(oddSet));
        System.out.println(findMedian(evenSet));
        System.out.println();
        
        System.out.println("Exercise 2: ");
        System.out.println(isSorted(evenSet));
        System.out.println(isSorted(notSorted));
        System.out.println();
        
        //for exercises 3, 4, 5
        int[] array1 =
                {3, 8, 5, 6, 5, 8, 9, 2};
        int[] array2 =
                {5, 15, 4, 6, 7, 3, 9, 11, 9, 3, 12, 13, 14, 9, 5, 3, 13};
        int[] common = new int[array1.length];

        System.out.println("Exercise 3:");
        findCommonValues(array1, array2, common);
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));
        System.out.println(Arrays.toString(common));
        System.out.println();

        System.out.println("Exercise 4:");
        int[] nums = {3, 8, 19, 7};
        rotateArrayToTheRight(nums);
        System.out.println(Arrays.toString(nums));
        rotateArrayToTheRight(nums);
        System.out.println(Arrays.toString(nums));
        rotateArrayToTheRight(nums);
        System.out.println(Arrays.toString(nums));
        System.out.println();

        System.out.println("Exercise 5:");
        System.out.println("Count 3s in array2: " + countTarget(array2, 3));
        System.out.println("Count 5s in array1: " + countTarget(array1, 5));
        System.out.println();

        // TODO: Add tests for exercise 6

    }
    
    /**
     * Finds the median value for the given array.
     * Arrays of type int, float, or long, will be converted to double
     * 
     * @param array array to find median of
     * @return the median value of the array
     */
    public static double findMedian(double[] array) {
        double median;

        if (array.length % 2 == 0) {
            if (array.length >= 2) {
                median = array[array.length / 2] +
                        array[(array.length / 2) - 1];
                median /= 2d;
            } else {
                return -1; // array length is 0
            }
        } else {
            median = array[array.length / 2];
        }
        return median;
    }

    /**
     * Iteratively checks the array without modifying any values in it.
     * 
     * @param array sorted or unsorted array
     * @return true if array is sorted in ascending order
     */
    public static boolean isSorted(double[] array) {
        if (array.length < 1) return true;

        double prevVal = array[0];
        for (double val : array) {
            // early return if any value is out of order
            if (val < prevVal) return false;
            prevVal = val;
        }

        return true;
    }


    /**
     * Finds common elements between {@code array1} and {@code array2},
     * adds unique values to array {@code common}
     * 
     * @param array1 not modified, does not need to be same size as array2
     * @param array2 not modified, does not need to be same size as array 1
     * @param common modified, must be at least as large as the smallest of
     *               the other 2 arrays
     */
    public static void findCommonValues(
            int[] array1,
            int[] array2,
            int[] common
    ) {
        List<Integer> list1 = Arrays.stream(array1).boxed().toList();
        List<Integer> list2 = Arrays.stream(array2).boxed().toList();

        int nextCommonIndex = 0;
        for (Integer number : list1) {

            if (
                // first check if both input arrays have the given number
                    list2.contains(number) &&
                // then check if the output array doesn't have that number
                    Arrays.stream(common).noneMatch(i -> i == number)
            ) {
                common[nextCommonIndex] = number;
                nextCommonIndex++;
            }
        }
    }

    /**
     * Rotates each value in {@code nums} one to the right, rightmost element
     * is wrapped around to index 0
     * 
     * @param nums array to be rotated, is modified
     */
    public static void rotateArrayToTheRight(int[] nums) {
        if (nums.length == 0) return;

        int rightmostVal = nums[nums.length - 1];

        for (int i = nums.length - 1; i > 0; i--) {
            nums[i] = nums[i - 1];
        }

        nums[0] = rightmostVal;
    }

    /**
     * Counts the number of occurrences of {@code target} within {@code nums}
     * 
     * @param nums array to check, not modified
     * @param target number to search for in {@code nums}
     */
    public static int countTarget(int[] nums, int target) {
        int targetCount = 0;

        for (int i : nums) {
            if (i == target) targetCount++;
        }

        return targetCount;
    }

    /**
     * Write a description of the method here
     * 
     * @param array
     */
    public static int[] stretchArray(int[] array) {
        int[] finalArray = new int[array.length * 2];



        return null; // Remove this later
    }
}