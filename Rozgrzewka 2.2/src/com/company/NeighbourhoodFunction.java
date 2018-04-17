package com.company;

import java.util.List;

public interface NeighbourhoodFunction {
    double calculateTheta(List<Point> neurons, int winningNeuronIndex, int currentNeuronIndex, int iteration);
}
