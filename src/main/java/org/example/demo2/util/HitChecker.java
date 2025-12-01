package org.example.demo2.util;



public final class HitChecker {

    private HitChecker() {}

    public static boolean hit(double x, double y, double r) {
        boolean rect = (x >= -r / 2 && x <= 0 && y >= 0 && y <= r);

        boolean circle = (x >= 0 && y <= 0 && (x * x) + (y * y) <= r * r);

        boolean tri = (x >= -r / 2 && x <= 0 && y <= 0 && y >= -r && y >= -2 * x - r);

        return rect || circle || tri;
    }
}
