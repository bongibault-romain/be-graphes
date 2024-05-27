package org.insa.graphs.algorithm.utils;

public class Vector2D {

    private final double x;

    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double norm() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D normalize() {
        double norm = norm();

        return new Vector2D(x / norm, y / norm);
    }

    public Vector2D dot(Vector2D other) {
        return new Vector2D(x * other.x, y * other.y);
    }

    public double angle(Vector2D other) {
        // oriented angle between this and other between 0 and 2PI
        double dot = dot(other).x();
        double det = x * other.y - y * other.x;

        // atan2 returns a value between -PI and PI
        double angle = Math.atan2(det, dot);

        if (angle < 0) {
            angle += 2 * Math.PI;
        }

        return angle;
    }
}
