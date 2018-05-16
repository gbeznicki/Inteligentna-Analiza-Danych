package com.company;

import java.util.List;

public class WinnerTakesMost implements NeighbourhoodFunction {

    private double radius;

    public WinnerTakesMost(double radius) {
        this.radius = radius;
    }

    // promien zostaje, distance distance zap omocą odleglosci indeksow output zostaje
    @Override
    public double calculateTheta(List<Point> neurons, int winningNeuronIndex, int currentNeuronIndex, int iteration) {
        // radius(t) = radius(0)*(1/t)
        radius /= iteration/10;
        double distance = Math.abs(winningNeuronIndex - currentNeuronIndex);

        if (distance < radius) {
            return Math.exp(-(distance * distance) / (2 * radius * radius));
        } else return 0;
    }
}