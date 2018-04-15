package com.company;

public class Point {
    private double x;
    private double y;
    private int group;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, int group){
        this.x = x;
        this.y = y;
        this.group = group;
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
    public String toString(){
        return x+" "+y;
    }
}
