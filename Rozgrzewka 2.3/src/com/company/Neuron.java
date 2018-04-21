package com.company;

public class Neuron implements Comparable<Neuron> {
    private double x;
    private double y;
    private double distance;

    public Neuron(double x, double y) {
        this.x = x;
        this.y = y;
        distance = Double.MAX_VALUE;
    }

    public Neuron(Neuron n) {
        this.x = n.x;
        this.y = n.y;
        distance = Double.MAX_VALUE;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    @Override
    public int compareTo(Neuron o) {
        return Double.compare(distance, o.distance);
    }
}
