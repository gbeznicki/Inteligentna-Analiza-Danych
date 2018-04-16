package com.company;

import static com.company.Point.calculateDistanceBetweenPoints;

public class WinnerTakesMost implements NeighbourhoodFunction{

    private double radius;

    public WinnerTakesMost(double radius){
        this.radius = radius;
    }

    @Override
    public double calculateTheta(Point winningNeuron, Point currentNeuron, int iteration) {
        // radius(t) = radius(0)*(1/t)
        double sigma = radius/iteration;
        double distance = calculateDistanceBetweenPoints(winningNeuron, currentNeuron);
        double output = Math.exp(-distance*distance/2*sigma*sigma);

        return output;
    }
}
