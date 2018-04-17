package com.company;

import java.util.List;

public class WinnerTakesAll implements NeighbourhoodFunction {

    @Override
    public double calculateTheta(List<Point> neurons, int winningNeuronIndex,int currentNeuronIndex, int iteration) {
        if (neurons.get(winningNeuronIndex) == neurons.get(currentNeuronIndex)) {
            return 1;
        } else return 0;
    }
}
