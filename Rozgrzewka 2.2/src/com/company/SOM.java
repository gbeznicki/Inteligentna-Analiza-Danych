package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.company.Point.calculateDistanceBetweenPoints;

public class SOM {

    private List<Point> dataPoints;
    private List<Point> neurons;
    private List<Point> neuronsPrevious;
    private double initialLearningRate;
    private double actualLearningRate;
    private NeighbourhoodFunction func;
    private int iteration = 1;
    private double precision = 0.0001;
    private double actualError;

    public SOM(List<Point> dataPoints, List<Point> neurons, double initialLearningRate, NeighbourhoodFunction func) {
        this.dataPoints = dataPoints;
        this.neurons = neurons;
        this.initialLearningRate = initialLearningRate;
        this.func = func;
        neuronsPrevious = new ArrayList<>();
        for (int i = 0; i < neurons.size(); i++) {
            neuronsPrevious.add(new Point(neurons.get(i)));
        }
    }

    public Point getBestMatchingUnit(Point dataPoint) {
        double min = Double.MAX_VALUE;
        int index = 0;

        for (int j = 0; j < neurons.size(); j++) {
            double distance = calculateDistanceBetweenPoints(dataPoint, neurons.get(j));
            if (distance < min) {
                min = distance;
                index = j;
            }
        }

        return neurons.get(index);
    }

    public void updateLearningRate() {
        actualLearningRate = initialLearningRate / iteration;
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

    public boolean chceckStopCondition(List<Point> previous, List<Point> actual) {

        for (int i = 0; i < previous.size(); i++) {
            double x = Math.abs(previous.get(i).getX() - actual.get(i).getX());
            double y = Math.abs(previous.get(i).getY() - actual.get(i).getY());
            if (x > precision && y > precision) {
                return false;
            }
        }
        return true;
    }

    public void updatePreviousNeurons() {
        neuronsPrevious.clear();
        for (int i = 0; i < neurons.size(); i++) {
            neuronsPrevious.add(new Point(neurons.get(i)));
        }
    }

    public void calculateError() {
        for (int i = 0; i < dataPoints.size(); i++) {
            Point dataPoint = dataPoints.get(i);
            Point closestNeuron = getBestMatchingUnit(dataPoints.get(i));
            actualError += calculateDistanceBetweenPoints(dataPoint, closestNeuron);
        }
        actualError/=dataPoints.size();
    }

    public void doSOM() {
        do {
            iterate();
            updateLearningRate();
            updatePreviousNeurons();
            calculateError();
            System.out.println(actualError);
            actualError = 0;
            saveToFile(iteration);

            iteration++;
        } while (chceckStopCondition(neuronsPrevious, neurons) && iteration <= 1000);
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
