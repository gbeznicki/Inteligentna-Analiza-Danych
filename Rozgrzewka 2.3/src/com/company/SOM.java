package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SOM {
    private static final double PRECISION = 1e-5;

    private static final double INITIAL_LEARNING_RATE = 0.8;
    private static final double MINIMAL_LEARNING_RATE = 0.001;

    private static final double INITIAL_RADIUS = 10.0;
    private static final double MINIMAL_RADIUS = 0.1;
    private static final double MAX_ITERATIONS = 1000;
    private static final int PUNISHMENT = 5;


    private List<Point> dataPoints;
    private List<Neuron> neurons;
    private List<Neuron> previousNeurons;
    private List<Neuron> initialNeurons;
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

    public SOM(List<Point> dataPoints, int numberOfNeurons, double min, double max) {
        this.dataPoints = dataPoints;
        random = new Random();

        actualLearningRate = INITIAL_LEARNING_RATE;
        actualRadius = INITIAL_RADIUS;

        //create neurons
        neurons = new ArrayList<>();
        PointFactory pointFactory = new PointFactory();

        for (int j = 0; j < numberOfNeurons; j++) {
            neurons.add(pointFactory.generateRandomNeuron(min, max));
        }

        //copy initial neurons values to initialNeurons List
        initialNeurons = new ArrayList<>();
        for (int i = 0; i < neurons.size(); i++) {
            initialNeurons.add(new Neuron(neurons.get(i)));
        }

        previousNeurons = new ArrayList<>();
        for (int i = 0; i < neurons.size(); i++) {
            previousNeurons.add(new Neuron(neurons.get(i)));
        }

//        initialNeurons = new ArrayList<>(neurons);
//        previousNeurons = new ArrayList<>(neurons);
    }

    public double getActualError() {
        return actualError;
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
        actualLearningRate = INITIAL_LEARNING_RATE *
                Math.pow(MINIMAL_LEARNING_RATE / INITIAL_LEARNING_RATE, iterationRatio);
    }

    public void updateRadius() {
        double iterationRatio = iteration / MAX_ITERATIONS;
        actualRadius = INITIAL_RADIUS * Math.pow(MINIMAL_RADIUS / INITIAL_RADIUS, iterationRatio);
    }

    public void iterate() {
        // wybierz losowy punkt z danymi
        if (iteration % 10 == 0) {
            Collections.shuffle(dataPoints);
        }

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

        //zdekrementuj karę
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).decrementPunishment();
        }

        // zaktualizuj wagi neuronów zgodnie z algorytmem gazu neuronowego, dla uczonych neuronów ustaw karę
        for (int i = 0; i < neurons.size(); i++) {
            if (i <= actualRadius && neurons.get(i).getPunishment() == 0) {
                double previousX = neurons.get(i).getX();
                double previousY = neurons.get(i).getY();

                double deltaX = actualLearningRate * Math.exp(-i / actualRadius) *
                        (randomDataPoint.getX() - previousX);
                double deltaY = actualLearningRate * Math.exp(-i / actualRadius) *
                        (randomDataPoint.getY() - previousY);

                double newX = previousX + deltaX;
                double newY = previousY + deltaY;

                neurons.get(i).setX(newX);
                neurons.get(i).setY(newY);

            }
        }

        if (neurons.get(0).getPunishment() == 0) {
            neurons.get(0).setPunishment(PUNISHMENT);
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

        boolean punishmentFlag = true;
        for (int j = 0; j < neurons.size(); j++) {
            if (neurons.get(j).getPunishment() == 0) {
                punishmentFlag = false;
            }
        }

        if (punishmentFlag) {
            return false;
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

//        saveDataPointsToFile();

        // zapis do pliku początkowych wag neuronów
//        saveNeuronsToFile(iteration);

        do {
            actualError = 0;
            updatePreviousNeurons();
            iterate();
            calculateError();
//            System.out.println(actualError);

            iteration++;
            // zapis do pliku wag neuronów
//            saveNeuronsToFile(iteration);
        } while (!checkStopCondition() && iteration < MAX_ITERATIONS);

//        System.out.println(iteration);

//        createGnuplotStript();
    }

    private void saveDataPointsToFile() {
        try (PrintWriter printWriter = new PrintWriter("data.txt")) {
            for (int i = 0; i < dataPoints.size(); i++) {
                printWriter.println(dataPoints.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveNeuronsToFile(int index) {

        try (PrintWriter printWriter = new PrintWriter("punkty" + index + ".txt")) {
            for (int i = 0; i < neurons.size(); i++) {
                printWriter.println(neurons.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createGnuplotStript() {
        try (PrintWriter printWriter = new PrintWriter("script.gnu")) {
            printWriter.println("set key off");
            printWriter.println("set xrange [-10:10]");
            printWriter.println("set yrange [-10:10]");
            printWriter.println("set size ratio -1");
            printWriter.print("do for [i=0:" + iteration + "] { set terminal png size 600,600;" +
                    " set output sprintf('D:\\Pulpit\\output\\img%i.png', i); " +
                    "plot 'D:\\Studia\\IAD\\Repozytorium\\Rozgrzewka 2.3\\data.txt' pt 7 lc \"black\"," +
                    "sprintf('D:\\Studia\\IAD\\Repozytorium\\Rozgrzewka 2.3\\punkty%i.txt', i) pt 7 lc \"red\"}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getInactiveNeuronsNumber() {
        int inactiveNeurons = 0;
        Neuron currentNeuron, initialNeuron;
        double currentX, currentY, initialX, initialY;
        for (int i = 0; i < neurons.size(); i++) {
            currentNeuron = neurons.get(i);
            currentX = currentNeuron.getX();
            currentY = currentNeuron.getY();
            for (int j = 0; j < initialNeurons.size(); j++) {
                initialNeuron = initialNeurons.get(i);
                initialX = initialNeuron.getX();
                initialY = initialNeuron.getY();
                if (currentX == initialX && currentY == initialY) {
                    inactiveNeurons++;
                }
            }
//            if (neurons.get(i).getX() == initialNeurons.get(i).getX() && neurons.get(i).getY() == initialNeurons.get(i).getY()) {
//                inactiveNeurons++;
//            }
        }
        return inactiveNeurons;
    }

}
