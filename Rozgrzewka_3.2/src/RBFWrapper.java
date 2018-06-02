import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

public class RBFWrapper {
    private List<Point> trainingPoints;
    private int numberOfTrials, numberOfCentres;
    private double[] trainingErrors;
    private double[] testErrors;
    private double learningRate;

    public RBFWrapper(int numberOfCentres, double learningRate, List<Point> trainingPoints, int numberOfTrials) {
        this.trainingPoints = trainingPoints;
        this.numberOfTrials = numberOfTrials;
        this.numberOfCentres = numberOfCentres;
        this.learningRate = learningRate;
        trainingErrors = new double[numberOfTrials];
        testErrors = new double[numberOfTrials];
    }

    public void run() {
        for (int i = 0; i < numberOfTrials; i++) {
            RadialBasisFunction rbf = new RadialBasisFunction(numberOfCentres, learningRate, trainingPoints);
            rbf.run();

            trainingErrors[i] = rbf.getFinalTrainingError();
            testErrors[i] = rbf.getFinalTestError();

            System.out.println("Done " + (i + 1) + " trial");
        }
    }

    public double calculateAverageTrainingError() {
        OptionalDouble avgError = Arrays.stream(trainingErrors).average();
        if (avgError.isPresent()) return avgError.getAsDouble();
        else return -1;
    }

    public double calculateAverageTestError() {
        OptionalDouble avgError = Arrays.stream(testErrors).average();
        if (avgError.isPresent()) return avgError.getAsDouble();
        else return -1;
    }

    public double calculateTrainingErrorDeviation() {
        double averageError = calculateAverageTrainingError();
        double errorDeviation = 0.0;
        for (int i = 0; i < trainingErrors.length; i++) {
            errorDeviation += (trainingErrors[i] - averageError) * (trainingErrors[i] - averageError);
        }
        errorDeviation /= trainingErrors.length;
        return Math.sqrt(errorDeviation);
    }

    public double calculateTestErrorDeviation() {
        double averageError = calculateAverageTestError();
        double errorDeviation = 0.0;
        for (int i = 0; i < testErrors.length; i++) {
            errorDeviation += (testErrors[i] - averageError) * (testErrors[i] - averageError);
        }
        errorDeviation /= testErrors.length;
        return Math.sqrt(errorDeviation);
    }
}

