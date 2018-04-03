import java.util.Arrays;
import java.util.Random;

public class Neuron {

    private double[] deltaWeights;
    private double[] previousDeltaWeights;
    private double[] weights;
    private double b;
    private double learningRate;
    private double momentum;
    private double weightedSum;
    private ActivationFunction function;

    public Neuron(int numberOfInputs, double learningRate, double momentum, ActivationFunction function) {
        deltaWeights = new double[numberOfInputs];
        previousDeltaWeights = new double[numberOfInputs];
        weights = new double[numberOfInputs];
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.function = function;
        initializeWeights();
    }

    public double[] getDeltaWeights() {
        return deltaWeights;
    }

    public void addDeltaWeights(int i, double value) {
        deltaWeights[i] += value;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void initializeWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = new Random().nextDouble() * 2 - 1;
        }
    }

    public void calculateWeightedSum(double[] x) {
        weightedSum = 0.0;
        for (int i = 0; i < x.length; i++) {
            weightedSum += x[i] * weights[i];
        }
    }

    public double activate() {
        return function.calculate(weightedSum);
    }

    public double getActivationDerivative() {
        return function.calculateDerivative(weightedSum);
    }

    public void updateWeights() {
        for (int i = 0; i < weights.length; i++) {
            deltaWeights[i] *= -learningRate;

            // aktualizacja wagi z uwzględnieniem momentum
            weights[i] += deltaWeights[i] + momentum * previousDeltaWeights[i];
        }
    }

    public void resetDeltaWeights() {
        previousDeltaWeights = Arrays.copyOf(deltaWeights, deltaWeights.length);
        Arrays.fill(deltaWeights, 0);
    }
}
