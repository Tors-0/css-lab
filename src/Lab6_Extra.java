import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Lab6_Extra {
    public static void main(String[] args) {
        PrintWriter fileWriter = null;
        try {
            fileWriter = new PrintWriter(
                    new FileOutputStream("fileIn.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 10_000; i++) {
            fileWriter.println(
                    randomInRange(Integer.MIN_VALUE, Integer.MAX_VALUE)
            );
        }

        fileWriter.close();
    }

    public static double randomSource() {
        return Math.random();
    }

    public static double randomInRange(int min, int max) {
        return randomSource() * ((long )max - min) + min;
    }
}
