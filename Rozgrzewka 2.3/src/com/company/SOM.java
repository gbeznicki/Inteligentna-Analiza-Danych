package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SOM {
    private static final double PRECISION = 1e-3;

    private static final double INITIAL_LEARNING_RATE = 0.8;
    private static final double MINIMAL_LEARNING_RATE = 0.0001;

    private static final double INITIAL_RADIUS = 10.0;
    private static final double MINIMAL_RADIUS = 0.1;
    private static final double MAX_ITERATIONS = 100;

    private List<Point> dataPoints;
    private List<Neuron> neurons;
    private List<Neuron> previousNeurons;
    private double actualLearningRate;
    private double actualRadius;
    private int iteration;
    private double actualError;
    private Random random;

    public SOM(List<Point> dataPoints, List<Neuron> neurons) {
        this.dataPoints = dataPoints;
        this.neurons = neurons;
        random = new Random();

        actualLearningRate = INITIAL_LEARNING_RATE;
        actualRadius = INITIAL_RADIUS;

        previousNeurons = new ArrayList<>();
        for (int i = 0; i < neurons.size(); i++) {
            previousNeurons.add(new Neuron(neurons.get(i)));
        }
    }

    private double calculateDistance(Point p, Neuron n) {
        double x1 = p.getX();
        double x2 = n.getX();
        double y1 = p.getY();
        double y2 = n.getY();
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public int getBestMatchingUnitIndex(Point dataPoint) {
        double min = Double.MAX_VALUE;
        int index = 0;

        for (int i = 0; i < neurons.size(); i++) {
            double distance = calculateDistance(dataPoint, neurons.get(i));
            if (distance < min) {
                min = distance;
                index = i;
            }
        }

        return index;
    }

    public void updateLearningRate() {
        double iterationRatio = iteration / MAX_ITERATIONS;
        actualLearningRate = INITIAL_LEARNING_RATE * Math.pow(MINIMAL_LEARNING_RATE / INITIAL_LEARNING_RATE, iterationRatio);
    }

    public void updateRadius() {
        double iterationRatio = iteration / MAX_ITERATIONS;
        actualRadius = INITIAL_RADIUS * Math.pow(MINIMAL_RADIUS / INITIAL_RADIUS, iterationRatio);
    }

    public void iterate() {
        // wybierz losowy punkt z danymi
        Point randomDataPoint = dataPoints.get(random.nextInt(dataPoints.size()));

        // oblicz odległości neuronów od tego punktu
        for (int i = 0; i < neurons.size(); i++) {
            double distance = calculateDistance(randomDataPoint, neurons.get(i));
            neurons.get(i).setDistance(distance);
        }

        // posortuj listę neuronów względem ich odległości od punktu z danymi
        Collections.sort(neurons);

        // oblicz promień sąsiedztwa i współczynnik nauki dla danej iteracji
        updateLearningRate();
        updateRadius();

        // zaktualizuj wagi neuronów zgodnie z algorytmem gazu neuronowego
        for (int i = 0; i < neurons.size(); i++) {
            double previousX = neurons.get(i).getX();
            double previousY = neurons.get(i).getY();

            double deltaX = actualLearningRate * Math.exp(-i / actualRadius) * (randomDataPoint.getX() - neurons.get(i).getX());
            double deltaY = actualLearningRate * Math.exp(-i / actualRadius) * (randomDataPoint.getY() - neurons.get(i).getY());

            double newX = previousX + deltaX;
            double newY = previousY + deltaY;

            neurons.get(i).setX(newX);
            neurons.get(i).setY(newY);
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
            previousNeurons.add(new Neuron(neurons.get(i)));
        }
    }

    public void calculateError() {
        for (int i = 0; i < dataPoints.size(); i++) {
            Point dataPoint = dataPoints.get(i);
            int closestNeuronIndex = getBestMatchingUnitIndex(dataPoints.get(i));
            Neuron closestNeuron = neurons.get(closestNeuronIndex);
            actualError += calculateDistance(dataPoint, closestNeuron);
        }
        actualError /= dataPoints.size();
    }

    public void doSOM() {
        iteration = 0;

        // zapis do pliku początkowych wag neuronów
        saveToFile(iteration);

        do {
            actualError = 0;
            updatePreviousNeurons();
            iterate();
            calculateError();
            System.out.println(actualError);

            iteration++;
            // zapis do pliku wag neuronów
            saveToFile(iteration);
        } while (!checkStopCondition() && iteration < MAX_ITERATIONS);

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
