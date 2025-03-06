package ica;

import java.util.concurrent.TimeUnit;

public class Walker {
    public final char walker = '@';
    private int x;
    private int y;
    private int dx = 0;
    private int dy = 0;

    public final int limitX = 80;
    public final int limitY = 20;

    private int wait;

    public Walker(int initX, int initY, int wait) {
        x = initX;
        y = initY;
        this.wait = wait;
    }

    public void queueX(int num) {
        dx += num;
    }
    public void queueY(int num) {
        dy -= num;
    }
    public void moveUp(int dist) {
        dy -= dist;
        while (dy != 0) {
            this.waitMillis(this.wait);
            this.tick();
            clearScreen();
            this.drawWalker();
        }
    }
    public void moveDown(int dist) {
        dy += dist;
        while (dy != 0) {
            this.waitMillis(this.wait);
            this.tick();
            clearScreen();
            this.drawWalker();
        }
    }
    public void moveLeft(int dist) {
        dx -= dist;
        while (dx != 0) {
            this.waitMillis(this.wait);
            this.tick();
            clearScreen();
            this.drawWalker();
        }
    }
    public void moveRight(int dist) {
        dx += dist;
        while (dx != 0) {
            this.waitMillis(this.wait);
            this.tick();
            clearScreen();
            this.drawWalker();
        }
    }
    public Vector2i getCurrentPos() {
        return new Vector2i((this.x + dx) % limitX, (this.y + dy) % limitY);
    }
    public Vector2i getInitPos() {
        return new Vector2i(this.x, this.y);
    }
    public void tick() {
        // move the walker one space in the specified directions
        if (dx != 0)
            this.x = (this.x + dx / Math.abs(dx));
        if (dy != 0)
            this.y = (this.y + dy / Math.abs(dy));

        if (x > limitX) {
            x %= limitX;
        } else if (x < 0) {
            x += limitX;
        }
        if (y > limitY) {
            y %= limitY;
        } else if (y < 0) {
            y += limitY;
        }

        // remove one from each direction
        if (dx > 0) dx--;
        else if (dx < 0) dx++;
        if (dy > 0) dy--;
        else if (dy < 0) dy++;
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
            System.out.print("\n");
        }
        for (int i = 0; i < this.x; i++) {
            System.out.print(' ');
        }
        System.out.print(this.walker);
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int getWait() {
        return wait;
    }
    public void setWait(int wait) {
        this.wait = wait;
    }
}
