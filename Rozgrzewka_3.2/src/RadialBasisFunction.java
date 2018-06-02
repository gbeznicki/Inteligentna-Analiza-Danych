import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class RadialBasisFunction {
    private static final double PRECISION = 0.01;
    private static final double STOP = 100000;

    private List<Point> trainingPoints;

    private double[] centres;
    private double[] sigmas;
    private double[] weights;
    private double learningRate;
    private double start;
    private double end;
    private Random rand;

    public RadialBasisFunction(double start, double end, int numberOfCentres, double learningRate, List<Point> trainingPoints) {
        this.trainingPoints = trainingPoints;
        centres = new double[numberOfCentres];
        sigmas = new double[numberOfCentres];
        weights = new double[numberOfCentres + 1];
        this.learningRate = learningRate;
        this.start = start;
        this.end = end;
        rand = new Random();
        initialize();
    }

    private void initialize() {
        // pozycje centrów ustalane są w taki sposób, aby podzielić badany przedział na równe części (z niewielkimi losowymi odchyleniami)
        for (int i = 0; i < centres.length; i++) {
            int randomIndex = rand.nextInt(trainingPoints.size());
            centres[i] = trainingPoints.get(randomIndex).getX();
        }

        // sigmy losowane zprzedziału [1, 1.5]
        for (int i = 0; i < sigmas.length; i++) {
            sigmas[i] = 0.05;
        }

        // wagi losowane z przedziału [-1, 1]
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble() * 2 - 1;
        }
    }

    private double distance(double x, double c) {
        return Math.abs(x - c);
    }

    private double radialFunction(double d, double sigma) {
        return (1 / (Math.sqrt(2 * Math.PI) * sigma)) * Math.exp(-(d * d) / (2 * sigma * sigma));
    }

    private List<Point> calculateFunctionForCentre(double step, int index) {
        double centre = centres[index];
        double sigma = sigmas[index];
        List<Point> results = new ArrayList<>();

        for (double x = start; x <= end; x += step) {
            double d = distance(x, centre);
            double k = radialFunction(d, sigma);
            double y = weights[index + 1] * k;
            results.add(new Point(x, y));
        }

        return results;
    }

    private List<Point> calculateFunction(double step) {
        List<Point> results = new ArrayList<>();

        for (double x = start; x <= end; x += step) {
            double sum = weights[0];
            for (int i = 0; i < centres.length; i++) {
                double d = distance(x, centres[i]);
                double k = radialFunction(d, sigmas[i]);
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

    public void run(double step) {
        int i = 0;
        do {
            doNextEpoch();
            System.out.println(qualityFunction());
            i++;
        } while (qualityFunction() > PRECISION && i < STOP);

        // zapisz funkcję realizowaną przez sieć
        List<Point> function = calculateFunction(step);
        savePointsToFile("function.txt", function);

        // zapisz funkcje realizowane przez poszczególne centra
        for (i = 0; i < centres.length; i++) {
            List<Point> points = calculateFunctionForCentre(step, i);
            savePointsToFile("centres" + i + ".txt", points);
        }
    }

    private void doIteration(Point point) {
        double input = point.getX();
        double output = generateOutput(input);
        double desiredOutput = point.getY();
        double[] previousWeights = Arrays.copyOf(weights, weights.length);
        double[] partialDerivatives = new double[weights.length];

        // oblicz pochodne cząstkowe
        for (int i = 0; i < weights.length; i++) {
            double derivative;

            if (i != 0) {
                double d = distance(input, centres[i - 1]);
                double sigma = sigmas[i - 1];
                double k = radialFunction(d, sigma);
                derivative = (output - desiredOutput) * k;
            } else {
                derivative = output - desiredOutput;
            }

            partialDerivatives[i] = derivative;
        }

        // zaktualizuj wagi
        for (int i = 0; i < weights.length; i++) {
            weights[i] = previousWeights[i] - learningRate * partialDerivatives[i];
        }

    }

    private void doNextEpoch() {
        // ustaw punkty treningowe w losowej kolejności
        Collections.shuffle(trainingPoints);

        for (int i = 0; i < trainingPoints.size(); i++) {
            doIteration(trainingPoints.get(i));
        }
    }

    private double generateOutput(double x) {
        double output = weights[0];

        for (int i = 0; i < centres.length; i++) {
            double d = distance(x, centres[i]);
            double k = radialFunction(d, sigmas[i]);
            output += weights[i + 1] * k;
        }

        return output;
    }

    private double qualityFunction() {
        double quality = 0;

        for (int i = 0; i < trainingPoints.size(); i++) {
            double input = trainingPoints.get(i).getX();
            double output = generateOutput(input);
            double desiredOutput = trainingPoints.get(i).getY();

            quality += (output - desiredOutput) * (output - desiredOutput);
        }
        quality /= 2 * trainingPoints.size();

        return quality;
    }
}
