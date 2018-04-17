package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.Point.calculateDistanceBetweenPoints;

public class SOM {

    private static final double PRECISION = 1e-3;
    private static final double LEARNING_RATE_RATIO = 0.9;

    private List<Point> dataPoints;
    private List<Point> neurons;
    private List<Point> previousNeurons;
    private double initialLearningRate;
    private double actualLearningRate;
    private NeighbourhoodFunction func;
    private int iteration = 1;
    private double actualError;

    public SOM(List<Point> dataPoints, List<Point> neurons, double initialLearningRate, NeighbourhoodFunction func) {
        this.dataPoints = dataPoints;
        this.neurons = neurons;
        this.initialLearningRate = initialLearningRate;
        actualLearningRate = initialLearningRate;
        this.func = func;
        previousNeurons = new ArrayList<>();
        for (int i = 0; i < neurons.size(); i++) {
            previousNeurons.add(new Point(neurons.get(i)));
        }
    }

    public Point getBestMatchingUnit(Point dataPoint) {
        double min = Double.MAX_VALUE;
        int index = 0;

        for (int i = 0; i < neurons.size(); i++) {
            double distance = calculateDistanceBetweenPoints(dataPoint, neurons.get(i));
            if (distance < min) {
                min = distance;
                index = i;
            }
        }

        return neurons.get(index);
    }

    public void updateLearningRate() {
//        actualLearningRate = initialLearningRate / iteration;
        actualLearningRate *= LEARNING_RATE_RATIO;
    }

    public void iterate() {
        Random random = new Random();

        Point randomDataPoint = dataPoints.get(random.nextInt(dataPoints.size()));

        Point bmu = getBestMatchingUnit(randomDataPoint);

        for (int i = 0; i < neurons.size(); i++) {
            double x = neurons.get(i).getX();
            double y = neurons.get(i).getY();
            x += actualLearningRate * func.calculateTheta(bmu, neurons.get(i), iteration) * (randomDataPoint.getX() - bmu.getX());
            y += actualLearningRate * func.calculateTheta(bmu, neurons.get(i), iteration) * (randomDataPoint.getY() - bmu.getY());
            neurons.get(i).setX(x);
            neurons.get(i).setY(y);
        }
    }

    public boolean checkStopCondition() {

        for (int i = 0; i < neurons.size(); i++) {
            double diffX = Math.abs(previousNeurons.get(i).getX() - neurons.get(i).getX());
            double diffY = Math.abs(previousNeurons.get(i).getY() - neurons.get(i).getY());

            if (diffX > PRECISION || diffY > PRECISION) {
                return false;
            }
        }
        return true;
    }

    public void updatePreviousNeurons() {
        previousNeurons.clear();
        for (int i = 0; i < neurons.size(); i++) {

            previousNeurons.add(new Point(neurons.get(i)));
        }
    }

    public void calculateError() {
        for (int i = 0; i < dataPoints.size(); i++) {
            Point dataPoint = dataPoints.get(i);
            Point closestNeuron = getBestMatchingUnit(dataPoints.get(i));
            actualError += calculateDistanceBetweenPoints(dataPoint, closestNeuron);
        }
        actualError /= dataPoints.size();
    }

    public void doSOM() {
        do {
            actualError = 0;
            updatePreviousNeurons();
            iterate();
            updateLearningRate();
            calculateError();
            System.out.println(actualError);
            saveToFile(iteration);

            iteration++;
        } while (!checkStopCondition());

        System.out.println(iteration);
    }

    private void saveToFile(int index) {

        try (PrintWriter printWriter = new PrintWriter("punkty" + index + ".txt")) {
            for (int i = 0; i < neurons.size(); i++) {
                printWriter.println(neurons.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
