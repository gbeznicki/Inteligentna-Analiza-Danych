package kohonen;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

public class KohonenWrapper {
    private List<Point> points;
//    private List<Neuron> neurons;
    private int numberOfTrials, numberOfNeurons;
    private double[] errors;
    private int[] inactiveNeurons;
    private double min;
    private double max;

    public KohonenWrapper(List<Point> points, int numberOfTrials, int numberOfNeurons, double min, double max) {
        this.points = points;
        this.numberOfTrials = numberOfTrials;
        this.numberOfNeurons = numberOfNeurons;
        this.min = min;
        this.max = max;
        errors = new double[numberOfTrials];
        inactiveNeurons = new int[numberOfTrials];
    }

    public void run() {
        // TODO: 08.05.2018 implement getInactiveCentresNumber method
        for (int i = 0; i < numberOfTrials; i++) {
            SOM som = new SOM(points, numberOfNeurons, min, max);
            som.doSOM();
            errors[i] = som.getActualError();
            inactiveNeurons[i] = som.getInactiveNeuronsNumber();

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

    public double calculateAverageInactiveNeurons() {
        OptionalDouble averageInactiveNeurons = Arrays.stream(inactiveNeurons).average();
        if (averageInactiveNeurons.isPresent()) return averageInactiveNeurons.getAsDouble();
        else return 0.0;
    }

    public double calculateInactiveNeuronsDeviation() {
        double averageInactiveNeurons = calculateAverageInactiveNeurons();
        double inactiveNeuronsDeviation = 0.0;
        for (int i = 0; i < inactiveNeurons.length; i++) {
            inactiveNeuronsDeviation += (inactiveNeurons[i] - averageInactiveNeurons) * (inactiveNeurons[i] - averageInactiveNeurons);
        }
        inactiveNeuronsDeviation /= inactiveNeurons.length;
        return Math.sqrt(inactiveNeuronsDeviation);
    }
}
