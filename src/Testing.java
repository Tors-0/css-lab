import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
//        int a = 865486;
//        int b = 658458;
//        System.out.println(add(a, b)); // == 1523944
//        System.out.println(a + b); // == 1523944

        double[] array = new double[15];
        Arrays.fill(array, 9.0);

        char[][] a = new char[5][];
        a[0] = "once upon".toCharArray();
        a[1] = "a time".toCharArray();
        a[2] = "there were".toCharArray();
        a[3] = "three little".toCharArray();
        a[4] = "programmers".toCharArray();

        a[1][3] = 'e';
        a[1][4] = 'r';
        a[1][5] = 'm';

        char[][] arrA = new char[5][12];
        Arrays.fill(arrA[0], 'A');
        Arrays.fill(arrA, arrA[0]);

        System.out.println(Arrays.deepToString(arrA));

        int[] data1 = new int[6];
        int[] data2 = new int[6];

        for (int i = 0; i < 6; i++) {
            data1[i] = 1;
            data2[i] = 2;
        }

        for (int i = 0; i < 6; i++) {
            data2[i] += data1[i];
        }

        System.out.println(Arrays.toString(data1));
        System.out.println(Arrays.toString(data2));

    }


















    public static int listRange(int... array) {
        if (array.length == 0) {
            return 0;
        } else if (array.length == 2) {
            return Math.abs(array[0] - array[1]);
        } else {
            int max = array[0];
            int min = array[1];

            for (int val : array) {
                max = Math.max(max, val);
                min = Math.min(min, val);
            }

            return max - min;
        }
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
