package com.company;

public class Neuron implements Comparable{
    private double x;
    private double y;
    private double distance = Double.MAX_VALUE;

    public Neuron(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Neuron(Neuron n1){
        this.x = n1.x;
        this.y = n1.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistance() {
        return distance;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Object o) {
        Neuron neuron = (Neuron) o;
        return Double.compare(distance, neuron.distance);
    }

    public static double calculateDistanceBetweenPoints(Point point, Neuron neuron) {
        double x1 = point.getX();
        double x2 = neuron.getX();
        double y1 = point.getY();
        double y2 = neuron.getY();
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
