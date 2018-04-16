package com.company;

import java.util.List;

public class WinnerTakesAll implements NeighbourhoodFunction {

    @Override
    public double calculateTheta(Point winningNeuron, Point currentNeuron, int iteration) {
        if (winningNeuron == currentNeuron) {
            return 1;
        } else return 0;
    }
}
