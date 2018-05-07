import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

public class KMeansWrapper {
    private List<Point> points;
    private int numberOfTrials, numberOfCentroids;
    private double[] errors;
    private int[] inactiveCentroids;
    private double min;
    private double max;

    public KMeansWrapper(List<Point> points, int numberOfTrials, int numberOfCentroids, double min, double max) {
        this.points = points;
        this.numberOfTrials = numberOfTrials;
        this.numberOfCentroids = numberOfCentroids;
        this.min = min;
        this.max = max;
        errors = new double[numberOfTrials];
        inactiveCentroids = new int[numberOfTrials];
    }

    public void run() {
        for (int i = 0; i < numberOfTrials; i++) {
            KMeansAlgorithm kMeans = new KMeansAlgorithm(points, numberOfCentroids, min, max);
            kMeans.runKMeansAlgorithm();

            errors[i] = kMeans.getActualKError();
            inactiveCentroids[i] = kMeans.getInactiveCentersNumber();

            System.out.println("Done " + (i + 1) + " trial");
        }
    }

    public double calculateAverageError() {
        OptionalDouble avgError = Arrays.stream(errors).average();
        if (avgError.isPresent()) return avgError.getAsDouble();
        else return 0.0;
    }

    public double calculateErrorDeviation() {
        double averageError = calculateAverageError();
        double errorDeviation = 0.0;
        for (int i = 0; i < errors.length; i++) {
            errorDeviation += (errors[i] - averageError) * (errors[i] - averageError);
        }
        errorDeviation /= errors.length;
        return Math.sqrt(errorDeviation);
    }

    public double getMinimumError() {
        OptionalDouble minimumError = Arrays.stream(errors).min();
        if (minimumError.isPresent()) return minimumError.getAsDouble();
        else return 0.0;
    }

    public double calculateAverageInactiveCentroids() {
        OptionalDouble averageInactiveCentroids = Arrays.stream(inactiveCentroids).average();
        if (averageInactiveCentroids.isPresent()) return averageInactiveCentroids.getAsDouble();
        else return 0.0;
    }

    public double calculateInactiveCentroidsDeviation() {
        double averageInactiveCentroids = calculateAverageInactiveCentroids();
        double inactiveCentroidsDeviation = 0.0;
        for (int i = 0; i < inactiveCentroids.length; i++) {
            inactiveCentroidsDeviation += (inactiveCentroids[i] - averageInactiveCentroids) * (inactiveCentroids[i] - averageInactiveCentroids);
        }
        inactiveCentroidsDeviation /= inactiveCentroids.length;
        return Math.sqrt(inactiveCentroidsDeviation);
    }
}
