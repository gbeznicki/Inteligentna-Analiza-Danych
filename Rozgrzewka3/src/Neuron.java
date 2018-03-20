import java.util.Arrays;
import java.util.Random;

public class Neuron {

    public double[] deltaWeights;
    public double[] weights;
    public double b;
    public int numberOfInputs;
    public double learningRate;

    public Neuron(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
        deltaWeights = new double[numberOfInputs];
        weights = new double[numberOfInputs];
        learningRate = 0.1;
        initializeWeights();
    }

    public void initializeWeights() {
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

    public void updateWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += -learningRate * deltaWeights[i];
        }
    }

    public void resetDeltaWeights() {
        Arrays.fill(deltaWeights, 0);
    }
}
