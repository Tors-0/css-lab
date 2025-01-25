import java.util.Scanner;

public class HWMethods {
    public static void main(String[] args) {
        programA();
        System.out.println("\n\n\n");
        programB();
    }
    public static void programA() {
        System.out.println("mario board 1:");
        segment1("");
        segment2("");
        segment2("");
        segment2("");
        segment1("");
        segment2("");
        segment3("", "");

        System.out.println("\n\nmario board 2");
        segment2("");
        segment1("");
        segment1("");
        segment2("");
        segment3("", "");
    }
    public static void programB() {
        Scanner scanny = new Scanner(System.in);
        System.out.print("What does mario look like: ");
        String mario = scanny.nextLine();
        System.out.print("What does a coin look like: ");
        String coin = scanny.nextLine();

        System.out.println("mario board 1:");
        segment1("");
        segment2("");
        segment2(coin);
        segment2(coin);
        segment1("");
        segment2("");
        segment3("", mario);
    }


    public static void segment1(String coin) {
        System.out.println(
                "||\n" +
                "||" + coin + "\n" +
                "||\n" +
                "||" + coin + "\n" +
                "||\n" +
                "||");
    }
    public static void segment2(String coin) {
        System.out.println(
                "||\n" +
                "||    |?|" + coin + "\n" +
                "||\n" +
                "||    |?|\n" +
                "||\n" +
                "||");
    }
    public static void segment3(String coin, String mario) {
        System.out.println(
                "||\n" +
                "||||\n" +
                "||||||\n" +
                "||||||||\n" +
                "||||||||||\n" +
                "||||||||||||" + mario + "\n" +
                "||\n" +
                "||              ^\n" +
                "|----------------");
    }
}
