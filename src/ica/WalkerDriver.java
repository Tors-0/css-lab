package ica;

public class WalkerDriver {
    public static void main(String[] args) {
        Walker myWalker = new Walker(5, 5, 50);

        testDrawing(myWalker);
    }
    public static void testDrawing(Walker walker) {

        walker.waitMillis(walker.wait); // wait some time
        System.out.print('\u000C'); // clear screen

        walker.tick();
        walker.drawWalker();

    }
}
