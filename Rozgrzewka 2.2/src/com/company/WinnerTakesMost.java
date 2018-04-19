package com.company;

import java.util.List;

import static com.company.Point.calculateDistanceBetweenPoints;

public class WinnerTakesMost implements NeighbourhoodFunction {

    private double radius;

    public WinnerTakesMost(double radius) {
        this.radius = radius;
    }

    // promien zostaje, distance distance zap omocą odleglosci indeksow output zostaje
    @Override
    public double calculateTheta(List<Point> neurons, int winningNeuronIndex, int currentNeuronIndex, int iteration) {
        // radius(t) = radius(0)*(1/t)
        double sigma = radius / iteration;
        double distance = Math.abs(winningNeuronIndex - currentNeuronIndex);
        double output = Math.exp(-(distance * distance) / (2 * sigma * sigma));

        return output;
    }
}