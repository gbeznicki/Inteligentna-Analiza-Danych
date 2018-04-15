package com.company;

import java.util.List;

public class SOM {

    private List<Point> dataPoints;
    private List<Point> neurons;
    private double initialLearningRate;
    private double actualLearningRate;
    private NeighbourhoodFunction func;

    public SOM(List<Point> dataPoints, List<Point> neurons, double initialLearningRate, NeighbourhoodFunction func) {
        this.dataPoints = dataPoints;
        this.neurons = neurons;
        this.initialLearningRate = initialLearningRate;
        this.func = func;
    }

    public double calculateDistanceBetweenPoints(Point p1, Point p2) {
        double x1 = p1.getX();
        double x2 = p2.getX();
        double y1 = p1.getY();
        double y2 = p2.getY();
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public Point getBestMatchingUnit() {
        double min;
        int index = 0;
        for (int i = 0; i < dataPoints.size(); i++) {
            min = Double.MAX_VALUE;
            for (int j = 0; j < neurons.size(); j++) {
                double distance = calculateDistanceBetweenPoints(dataPoints.get(i), neurons.get(j));
                if (distance < min) {
                    min = distance;
                    index = j;
                }
            }
        }
        return neurons.get(index);
    }

    public void updateLearningRate(int iteration){
        actualLearningRate = initialLearningRate/iteration;
    }



}
