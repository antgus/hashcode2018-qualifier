package com.antgus;

public class Point {
    public final int x;
    public final int y;

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return new Point(x,y);
    }

    public int distTo(Point other) {
        return Distance.dist(this, other);
    }
}
