package ica;

import java.util.concurrent.TimeUnit;

public class Walker {
    public final char walker = '@';
    private int x;
    private int y;
    private int dx = 0;
    private int dy = 0;

    public final int limitX = 20;
    public final int limitY = 20;

    public final int wait;

    public Walker(int initX, int initY, int wait) {
        x = initX;
        y = initY;
        this.wait = wait;
    }

    public void moveUp(int dist) {
        dy += dist;
    }
    public void moveDown(int dist) {
        dy -= dist;
    }
    public void moveLeft(int dist) {
        dx -= dist;
    }
    public void moveRight(int dist) {
        dx += dist;
    }
    public Vector2i getCurrentPos() {
        return new Vector2i((this.x + dx) % limitX, (this.y + dy) % limitY);
    }
    public Vector2i getInitPos() {
        return new Vector2i(this.x, this.y);
    }
    public void tick() {
        this.x = (this.x + dx) % limitX;
        this.y = (this.y + dy) % limitY;
    }
    public void waitMillis(int numMilliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(numMilliseconds);
        }
        catch (InterruptedException e) {
            System.out.println("Premature awakening");
        }
    }
    public void drawWalker() {
        for (int i = 0; i < this.y; i++) {
            System.out.println();
        }
        for (int i = 0; i < this.x; i++) {
            System.out.print(' ');
        }
        System.out.print(this.walker);
    }
}
