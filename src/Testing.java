import java.io.PrintStream;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
//        int a = 865486;
//        int b = 658458;
//        System.out.println(add(a, b)); // == 1523944
//        System.out.println(a + b); // == 1523944

        System.out.println(System.out);
        PrintStream out = System.out;
        System.out.println(System.out);
        System.out.println(out);
        out.println("hi");
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
