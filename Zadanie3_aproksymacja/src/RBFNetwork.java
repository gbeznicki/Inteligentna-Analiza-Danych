import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RBFNetwork {
    private static final double PRECISION = 0.01;
    private static final double STOP = 100000;

    private RadialLayer radialLayer;
    private IdentityLayer identityLayer;
    private int numberOfCentres;
    private List<Point> trainingPoints;
    private List<Point> testPoints;
    private double quality;

    public RBFNetwork(List<Point> trainingPoints, List<Point> testPoints, int numberOfCentres, double learningRate, double sigma) {
        this.numberOfCentres = numberOfCentres;
        this.trainingPoints = trainingPoints;
        this.testPoints = testPoints;
        radialLayer = new RadialLayer(numberOfCentres, generateCentres(), sigma);
        identityLayer = new IdentityLayer(numberOfCentres, learningRate);
    }

    private double[] generateCentres() {
        Random rand = new Random();
        double[] centres = new double[numberOfCentres];
        for (int i = 0; i < centres.length; i++) {
            int randomIndex = rand.nextInt(trainingPoints.size());
            centres[i] = trainingPoints.get(randomIndex).getX();
        }
        return centres;
    }

    private void iterate() {
        for (int i = 0; i < trainingPoints.size(); i++) {
            double input = trainingPoints.get(i).getX();
            double desiredOutput = trainingPoints.get(i).getY();

            // warstwa radialna
            radialLayer.feedData(input);
            radialLayer.calcOutputs();
            double[] radialLayerOutputs = radialLayer.getOutputs();

            // warstwa liniowa
            identityLayer.feedData(radialLayerOutputs, desiredOutput);
            identityLayer.calculateOutput();
            double output = identityLayer.getOutput();

            // obliczenie błędu w warstwie liniowej
            quality += 0.5 * (output - desiredOutput) * (output - desiredOutput);

            identityLayer.calculateError();
            identityLayer.calculatePartialDerivative();
        }
        identityLayer.updateWeights();
        identityLayer.resetDeltaWeights();
    }

    public void teach() {
        int i = 0;

        do {
            quality = 0;
            iterate();
            quality /= trainingPoints.size();
            System.out.println(quality);
            i++;
        } while (i < STOP && quality > PRECISION);
    }

    public void saveFiles(String fileName, double start, double end, double step) {
        savePointsToFile(fileName, calculateFunction(start, end, step));
//        for (int i = 0; i < radialLayer.getNeurons().length; i++) {
//            savePointsToFile("centre" + i + ".txt", calculateRadialFunction(i, start, end, step));
//        }
    }

    private void savePointsToFile(String fileName, List<Point> points) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int i = 0; i < points.size(); i++) {
                writer.println(points.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private List<Point> calculateRadialFunction(int index, double start, double end, double step) {
//        List<Point> points = new ArrayList<>();
//        RadialNeuron radialNeuron = radialLayer.getNeurons()[index];
//        for (double x = start; x <= end; x += step) {
//            radialNeuron.feedData(x);
//            radialNeuron.calcOutput();
//            double output = radialNeuron.getOutput();
//
//            points.add(new Point(x, output));
//        }
//        return points;
//    }

    private List<Point> calculateFunction(double start, double end, double step) {
        List<Point> points = new ArrayList<>();
        for (double x = start; x <= end; x += step) {
            radialLayer.feedData(x);
            radialLayer.calcOutputs();
            double[] radialLayerOutputs = radialLayer.getOutputs();

            // warstwa liniowa
            identityLayer.feedData(radialLayerOutputs, 0);
            identityLayer.calculateOutput();
            double output = identityLayer.getOutput();

            points.add(new Point(x, output));
        }
        return points;
    }
}
