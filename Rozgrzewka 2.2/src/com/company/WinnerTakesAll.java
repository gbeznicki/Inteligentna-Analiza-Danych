package com.company;

public class WinnerTakesAll implements NeighbourhoodFunction{

    @Override
    public double calculateTheta(Point winningNeuron, Point currentNeuron, int iteration) {
        if(winningNeuron.equals(currentNeuron)){
            return 1;
        }
        else return 0;
    }
}
