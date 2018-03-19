import java.util.Random;

public class Neuron {

    private double[] weights;
    private int numberOfInputs;
    private double learningRate;

    public Neuron(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
        weights = new double[numberOfInputs];
        learningRate = 0.1;
        initializeWeights();
    }

    private void initializeWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = new Random().nextDouble() * 2 - 1;
        }
    }

    public double activate(double[] x) {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * weights[i];
        }
        return 1 / (1 + Math.exp(-sum));
    }

    public void updateWeights(double[] deltaWeights) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += deltaWeights[i];
        }
    }

    public double[] getWeights() {
        return weights;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }
}
