package com.company;

public class Point {
    private double x;
    private double y;
    private int group;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }


    public static double calculateDistanceBetweenPoints(Point p1, Point p2) {
        double x1 = p1.getX();
        double x2 = p2.getX();
        double y1 = p1.getY();
        double y2 = p2.getY();
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

}
