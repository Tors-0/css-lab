package ica;

public class Vector2i {
    public int x;
    public int y;

    public static final Vector2i ZERO = new Vector2i(0,0);

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i copy() {
        return new Vector2i(this.x, this.y);
    }
    public void add(Vector2i other) {
        this.x += other.x;
        this.y += other.y;
    }
    public void sub(Vector2i other) {
        this.x -= other.x;
        this.y -= other.y;
    }
    public void mult(Vector2i other) {
        this.x *= other.x;
        this.y *= other.y;
    }
    public void div(Vector2i other) {
        this.x = (int) Math.round((double) this.x / other.x);
        this.y = (int) Math.round((double) this.y / other.y);
    }
}
