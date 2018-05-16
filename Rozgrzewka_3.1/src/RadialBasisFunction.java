import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RadialBasisFunction {
    private double[] centres;
    private double[] sigmas;
    private double[] weights;
    private Random rand;

    public RadialBasisFunction(int numberOfCentres) {
        centres = new double[numberOfCentres];
        sigmas = new double[numberOfCentres];
        weights = new double[numberOfCentres + 1];
        rand = new Random();
        initialize();
    }

    private void initialize() {
        // centres
        for (int i = 0; i < centres.length; i++) {
            centres[i] = rand.nextDouble() * 10;
        }

        // sigmas
        for (int i = 0; i < sigmas.length; i++) {
//            sigmas[i] = rand.nextDouble();
            sigmas[i] = 1.5;
        }

        // weights
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble() * 8 - 4;
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
        for (int i = 0; i < centres.length; i++) {
            List<Point> points = calculatePointsForCentre(start, end, step, i);
            savePointsToFile("centres" + i + ".txt", points);
        }

        List<Point> sum = calculatePointsSum(start, end, step);
        savePointsToFile("sum.txt", sum);
    }
}
