public class Neuron {

    double[] weights;
    int numberOfInputs;

    public Neuron(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
        weights = new double[numberOfInputs];
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
}
