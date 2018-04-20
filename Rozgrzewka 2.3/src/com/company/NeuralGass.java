package com.company;

import java.util.ArrayList;
import java.util.List;

public class NeuralGass {

    private double radiusMinimal;
    private double radiusZero;

    public NeuralGass(double radius){
        this.radiusZero = radius;
    };



    public double neighbourhoodFunc(List<Neuron> neurons, int winningNeuronIndex, int currentNeuronIndex, int iteration, int maxIteration) {

        double currentRadius = radiusZero*Math.pow(radiusMinimal/radiusZero, iteration/maxIteration);
        double distance = Math.abs(winningNeuronIndex - currentNeuronIndex);
        if(distance <= currentRadius){
            double output = Math.exp(-(winningNeuronIndex) / currentRadius);
            return output;
        }
        else {
            return 0;
        }
    }
}
