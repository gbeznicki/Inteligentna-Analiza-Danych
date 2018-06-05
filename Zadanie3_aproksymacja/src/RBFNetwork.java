import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RBFNetwork {
    private static final double PRECISION = 0.001;
    private static final double STOP = 100000;

    private RadialLayer radialLayer;
    private IdentityLayer identityLayer;
    private int numberOfCentres;
    private List<Point> trainingPoints;
    private List<Point> testPoints;
    private double quality;

    private List<Double> error;

    public RBFNetwork(List<Point> trainingPoints, List<Point> testPoints, int numberOfCentres, double learningRate, double sigma) {
        this.numberOfCentres = numberOfCentres;
        this.trainingPoints = trainingPoints;
        this.testPoints = testPoints;
        radialLayer = new RadialLayer(numberOfCentres, generateCentres(), sigma);
        identityLayer = new IdentityLayer(numberOfCentres, learningRate);
        error = new ArrayList<>();
    }

    public double getFinalTrainingError() {
        return quality;
    }

    public double getFinalTestError() {
        double error = 0;
        for (int i = 0; i < testPoints.size(); i++) {
            double testInput = testPoints.get(i).getX();
            double desiredTrainingOutput = testPoints.get(i).getY();

            // warstwa radialna
            radialLayer.feedData(testInput);
            radialLayer.calcOutputs();
            double[] radialLayerOutputs = radialLayer.getOutputs();

            // warstwa liniowa
            identityLayer.feedData(radialLayerOutputs, desiredTrainingOutput);
            identityLayer.calculateOutput();
            double output = identityLayer.getOutput();

            // obliczenie błędu w warstwie liniowej
            error += 0.5 * (output - desiredTrainingOutput) * (output - desiredTrainingOutput);
        }
        return error / testPoints.size();
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
            double trainingInput = trainingPoints.get(i).getX();
            double desiredTrainingOutput = trainingPoints.get(i).getY();

            // warstwa radialna
            radialLayer.feedData(trainingInput);
            radialLayer.calcOutputs();
            double[] radialLayerOutputs = radialLayer.getOutputs();

            // warstwa liniowa
            identityLayer.feedData(radialLayerOutputs, desiredTrainingOutput);
            identityLayer.calculateOutput();
            double output = identityLayer.getOutput();

            // obliczenie błędu w warstwie liniowej
            quality += 0.5 * (output - desiredTrainingOutput) * (output - desiredTrainingOutput);

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
            error.add(quality);
            System.out.println(quality);
            i++;
        } while (i < STOP && quality > PRECISION);
    }

    public void saveFiles(String fileName, double start, double end, double step) {
        savePointsToFile(fileName, calculateFunction(start, end, step));
        for (int i = 0; i < radialLayer.getNeurons().length; i++) {
            savePointsToFile("centre" + i + ".txt", calculateRadialFunction(i, start, end, step));
        }
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

    private List<Point> calculateRadialFunction(int index, double start, double end, double step) {
        List<Point> points = new ArrayList<>();
        RadialNeuron radialNeuron = radialLayer.getNeurons()[index];
        double weight = identityLayer.getNeuron().getWeights()[index + 1];
        for (double x = start; x <= end; x += step) {
            radialNeuron.feedData(x);
            radialNeuron.calcOutput();
            double output = weight * radialNeuron.getOutput();

            points.add(new Point(x, output));
        }
        return points;
    }

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

    public void saveError() {
        try (PrintWriter writer = new PrintWriter("error.txt")) {
            for (int i = 0; i < error.size(); i++) {
                writer.println(error.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
