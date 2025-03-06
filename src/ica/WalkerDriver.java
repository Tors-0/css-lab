package ica;

import static ica.Walker.clearScreen;

public class WalkerDriver {
    public static void main(String[] args) {
        Walker myWalker = new Walker(0, 5, 250);

        myWalker.moveRight(10);

        myWalker.setWait(15); // by the magic of the druids, i increase my speed

        myWalker.moveRight(10);
        myWalker.moveDown(5);
        myWalker.moveLeft(10);
        myWalker.moveUp(5);

        myWalker.setWait(25);

        myWalker.moveUp(30);
        myWalker.queueY(-350);
        myWalker.moveRight(350);
    }
    public static void testDrawing(Walker walker) {

        walker.waitMillis(walker.getWait()); // wait some time

        walker.tick(); // update walker position

        clearScreen(); // clear screen
        walker.drawWalker(); // draw new position
    }
}
