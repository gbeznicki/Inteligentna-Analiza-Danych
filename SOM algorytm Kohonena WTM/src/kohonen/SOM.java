package kohonen;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SOM {

    private static final double PRECISION = 1e-5;

    private static final double INITIAL_LEARNING_RATE = 0.5;
    private static final double MINIMAL_LEARNING_RATE = 0.001;

    private static final double INITIAL_RADIUS = 2.0;
    private static final double MINIMAL_RADIUS = 0.01;

    private static final double MAX_ITERATIONS = 10000;

    private static final int PUNISHMENT = 0;

    private List<Point> dataPoints;
    private List<Neuron> neurons;
    private List<Neuron> previousNeurons;
    private List<Neuron> initialNeurons;
    private double actualLearningRate;
    private int iteration = 1;

    public double getActualError() {
        return actualError;
    }

    private double actualError;
    private double actualRadius;
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

    // TODO: 08.05.2018 implement constructor
    public SOM(List<Point> dataPoints, int numberOfNeurons, double min, double max) {
        this.dataPoints = dataPoints;
        random = new Random();

        actualLearningRate = INITIAL_LEARNING_RATE;
        actualRadius = INITIAL_RADIUS;

        //create neurons
        neurons = new ArrayList<>();
//        List<Neuron> minNeurons = new ArrayList<>();
        PointFactory pointFactory = new PointFactory();

//        double minError = Double.MAX_VALUE;

<<<<<<< Updated upstream
            for (int j = 0; j < numberOfNeurons; j++) {
                neurons.add(pointFactory.generateRandomNeuron(min, max, j));
            }
=======
        for (int j = 0; j < numberOfNeurons; j++) {
            neurons.add(pointFactory.generateRandomNeuron(min, max, j));
        }
>>>>>>> Stashed changes
//            calculateError();
//
//            if (actualError < minError) {
//                minError = actualError;
//                minNeurons = neurons;
//            }

//        neurons = minNeurons;

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

        //znajdź zwycięzcę
        int bmuIndex = getBestMatchingUnitIndex(randomDataPoint);

        // oblicz i ustaw odległości dla wszystkich neuronów względem zwycięzcy
        for (int i = 0; i < neurons.size(); i++) {
            double distance = Math.abs(bmuIndex - i);
            neurons.get(i).setDistance(distance);
        }

        updateLearningRate();
        updateRadius();

        //zdekrementuj karę
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).decrementPunishment();
        }

        for (int i = 0; i < neurons.size(); i++) {
            if (neurons.get(i).getDistance() <= actualRadius && neurons.get(i).getPunishment() == 0) {
                double previousX = neurons.get(i).getX();
                double previousY = neurons.get(i).getY();

                double distance = neurons.get(i).getDistance();

                double deltaX = actualLearningRate * Math.exp(-(distance * distance) / (2 * actualRadius * actualRadius)) *
                        (randomDataPoint.getX() - previousX);
                double deltaY = actualLearningRate * Math.exp(-(distance * distance) / (2 * actualRadius * actualRadius)) *
                        (randomDataPoint.getY() - previousY);

                double newX = previousX + deltaX;
                double newY = previousY + deltaY;

                neurons.get(i).setX(newX);
                neurons.get(i).setY(newY);

//                x += actualLearningRate * func.calculateTheta(neurons, neurons.indexOf(bmu), i, iteration) *
//                        (randomDataPoint.getX() - neurons.get(i).getX());
//                y += actualLearningRate * func.calculateTheta(neurons, neurons.indexOf(bmu), i, iteration) *
//                        (randomDataPoint.getY() - neurons.get(i).getY());
//                neurons.get(i).setX(x);
//                neurons.get(i).setY(y);
            }
        }

        if (neurons.get(bmuIndex).getPunishment() == 0) {
            neurons.get(bmuIndex).setPunishment(PUNISHMENT);
        }

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
            System.out.println(actualError);

            iteration++;
            // zapis do pliku wag neuronów
//            saveNeuronsToFile(iteration);
//            saveToVoronoi();
        } while (!checkStopCondition() && iteration < MAX_ITERATIONS);

//        System.out.println(iteration);
        System.out.println(neurons.get(0));
        System.out.println(neurons.get(1));

//        createGnuplotStript();
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

    private void saveDataPointsToFile() {
        try (PrintWriter printWriter = new PrintWriter("data.txt")) {
            for (int i = 0; i < dataPoints.size(); i++) {
                printWriter.println(dataPoints.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveNeuronsToFile(int index) {

        try (PrintWriter printWriter = new PrintWriter("punkty" + index + ".txt")) {
            for (int i = 0; i < neurons.size(); i++) {
                printWriter.println(neurons.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void savePointGroups() {
        for (int i = 0; i < neurons.size(); i++) {
            try (PrintWriter writer = new PrintWriter("group" + i + ".txt")) {
                // sprawdź które punkty mają ten neuron za najbliższy
                for (int j = 0; j < dataPoints.size(); j++) {
                    int bmuIndex = getBestMatchingUnitIndex(dataPoints.get(j));
                    if (neurons.get(bmuIndex) == neurons.get(i)) {
                        writer.println(dataPoints.get(j));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Neuron getNeuron(int id) {
        for (int i = 0; i < neurons.size(); i++) {
            if (neurons.get(i).getId() == id) return neurons.get(i);
        }
        return null;
    }

    public void saveToVoronoi() {
        try (FileWriter fw = new FileWriter("neurony.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter printWriter = new PrintWriter(bw)) {
            for (int i = 0; i < neurons.size(); i++) {
                Neuron n = getNeuron(i);
                printWriter.print(n + " ");
            }
            printWriter.println("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
                    "plot 'D:\\Studia\\IAD\\Repozytorium\\SOM algorytm Kohonena WTM\\data.txt' pt 7 lc \"black\"," +
                    "sprintf('D:\\Studia\\IAD\\Repozytorium\\SOM algorytm Kohonena WTM\\punkty%i.txt', i) pt 7 lc \"red\"}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getInactiveNeuronsNumber() {
        int inactiveNeurons = 0;
        for (int i = 0; i < neurons.size(); i++) {
            if (neurons.get(i).getX() == initialNeurons.get(i).getX() && neurons.get(i).getY() == initialNeurons.get(i).getY()) {
                inactiveNeurons++;
            }
        }
        return inactiveNeurons;
    }
}
