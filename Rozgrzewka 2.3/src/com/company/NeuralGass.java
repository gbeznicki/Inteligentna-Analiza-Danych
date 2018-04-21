package com.company;

import java.util.ArrayList;
import java.util.List;

public class NeuralGass {

    private double radiusMinimal;
    private double radiusZero;

    public NeuralGass(double radius){
        this.radiusZero = radius;
        radiusMinimal = 1;
    }



    public double neighbourhoodFunc(List<Neuron> neurons, int winningNeuronIndex, int currentNeuronIndex, int iteration, int maxIteration) {

        double k = (double)iteration/(double)maxIteration;
        double currentRadius = radiusZero*Math.pow(radiusMinimal/radiusZero, k);
        double distance = Math.abs(winningNeuronIndex - currentNeuronIndex);
        if(distance <= currentRadius){
            double output = Math.exp(-(currentNeuronIndex) / currentRadius);
            return output;
        }
        else {
            return 0;
        }
    }
}
