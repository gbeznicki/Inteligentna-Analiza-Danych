import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RadialBasisFunction {
    private double[] centres;
    private double[] sigmas;
    private double[] weights;
    private double[] weightDerivatives;
    private double learningRate;
    private List<Double> desiredOutputs;
    private List<Double> inputs;
    private List<Double> outputs;
    private Random rand;

    public RadialBasisFunction(int numberOfCentres, double learningRate, List<Double> desiredOutputs, List<Double> inputs) {
        centres = new double[numberOfCentres];
        sigmas = new double[numberOfCentres];
        weights = new double[numberOfCentres + 1];
        weightDerivatives = new double[weights.length];
        this.learningRate = learningRate;
        this.desiredOutputs = desiredOutputs;
        this.inputs = inputs;
        rand = new Random();
        initialize();
    }

    private void initialize() {
        // centres [-4, 4]
        for (int i = 0; i < centres.length; i++) {
            centres[i] = -4 + (i + 1) * (8 / 11);
        }

        // sigmas [1, 1.5]
        for (int i = 0; i < sigmas.length; i++) {
            sigmas[i] = 1;
        }

        // weights
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble() * 2 - 1;
        }
    }

    private double calculateDistance(double x, double c) {
        return Math.abs(x - c);
    }

    private double calculateRadialFunction(double d, double sigma) {
        return (1 / (Math.sqrt(2 * Math.PI) * sigma)) * Math.exp(-(d * d) / (2 * sigma * sigma));
    }

    private List<Point> calculatePointsForCentre(double start, double end, double step, int index) {
        double centre = centres[index];
        double sigma = sigmas[index];
        List<Point> results = new ArrayList<>();

        for (double x = start; x <= end; x += step) {
            double d = calculateDistance(x, centre);
            double k = calculateRadialFunction(d, sigma);
            double y = weights[index + 1] * k;
            results.add(new Point(x, y));
        }

        return results;
    }

    private List<Point> calculatePointsSum(double start, double end, double step) {
        List<Point> results = new ArrayList<>();

        for (double x = start; x <= end; x += step) {
            double sum = weights[0];
            for (int i = 0; i < centres.length; i++) {
                double d = calculateDistance(x, centres[i]);
                double k = calculateRadialFunction(d, sigmas[i]);
                sum += weights[i + 1] * k;
            }

            results.add(new Point(x, sum));
        }

        return results;
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

    public void run(double start, double end, double step) {
//        for (int i = 0; i < centres.length; i++) {
//            List<Point> points = calculatePointsForCentre(start, end, step, i);
//            savePointsToFile("centres" + i + ".txt", points);
//        }


        // punkty na początku
        List<Point> sum = calculatePointsSum(start, end, step);
        savePointsToFile("poczatek.txt", sum);

        int i = 0;
        do {
            System.out.println(calcQualityFunction());
            calcPartialDerivative();
            updateWeights();
            i++;
        } while (i < 100000);

        // punkty na końcu
        List<Point> sum2 = calculatePointsSum(start, end, step);
        savePointsToFile("koniec.txt", sum2);
    }

    private double calcQualityFunction() {
        double quality = 0;
        for (int i = 0; i < inputs.size(); i++) {
            double output = calcOutput(inputs.get(i));
            quality += (output - desiredOutputs.get(i)) * (output - desiredOutputs.get(i));
        }
        quality /= 2 * inputs.size();
        return quality;
    }

    private void calcPartialDerivative() {

        for (int i = 0; i < weights.length; i++) {
            double sum = 0;
            for (int j = 0; j < inputs.size(); j++) {
                sum += calcOutput(inputs.get(j)) - desiredOutputs.get(j);
            }
            if (i != 0) sum *= weights[i];
            sum /= inputs.size();
            weightDerivatives[i] = sum;
        }
    }

    private double calcOutput(double x) {
        double output = weights[0];
        for (int i = 0; i < centres.length; i++) {
            double d = Math.abs(x - centres[i]);
            double k = calculateRadialFunction(d, sigmas[i]);
            output += weights[i + 1] * k;
        }
        return output;
    }

    private void updateWeights() {
        double[] previousWeights = Arrays.copyOf(weights, weights.length);
        for (int i = 0; i < weights.length; i++) {
            weights[i] = previousWeights[i] - (learningRate * weightDerivatives[i]);
        }
    }

}
