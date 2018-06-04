import java.util.Arrays;
import java.util.Random;

public class IdentityNeuron {
    private double[] weights;
    private double[] deltaWeights;
    private double learningRate;
    private double[] inputs;
    private double error;
    private double weightedSum;

    public IdentityNeuron(int numberOfInputs, double learningRate) {
        weights = new double[numberOfInputs + 1];
        deltaWeights = new double[weights.length];
        this.learningRate = learningRate;
        initializeWeights();
    }

    private void initializeWeights() {
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble() * 2 - 1;
        }
    }

    public void feedData(double[] inputs) {
        this.inputs = inputs;

    }

    public double[] getDeltaWeights() {
        return deltaWeights;
    }

    public void addDeltaWeights(int i, double value) {
        deltaWeights[i] += value;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public void calcWeightedSum() {
        weightedSum = weights[0];
        for (int i = 0; i < inputs.length; i++) {
            weightedSum += inputs[i] * weights[i + 1];
        }
    }

    public double getOutput() {
        return weightedSum;
    }

    public double getOutputDerivative() {
        return 1;
    }

    public void updateWeights() {
        for (int i = 0; i < weights.length; i++) {
            deltaWeights[i] *= -learningRate;
            weights[i] += deltaWeights[i];
        }
    }

    public void resetDeltaWeights() {
        Arrays.fill(deltaWeights, 0);
    }

    public double[] getWeights() {
        return weights;
    }
}
