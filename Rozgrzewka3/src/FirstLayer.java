public class FirstLayer {

    private Neuron[] neurons;
    private double[] x;
    private double[] outputs;

    public FirstLayer() {
        x = new double[3];
        neurons = new Neuron[2];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(3);
        }
        outputs = new double[2];
    }

    public void passData(double[] x) {
        this.x = x;
    }

    public double[] getOutputs() {
        return outputs;
    }

    public void process() {
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].activate(x);
        }
    }

    // upper layer - w0; w1; w2
    // this layer - w1; w2
    public void calculateB(double bUpperLayer, double[] weightsUpperLayer) {
        for (int i = 0; i < neurons.length; i++) {
            double output = neurons[i].activate(x);
            double derivative = output * (1 - output);
            neurons[i].setB(bUpperLayer * weightsUpperLayer[i] * derivative);
        }
    }

    public void calculatePartialDerivative() {
        // dla każdego neuronu w warstwie
        for (int i = 0; i < neurons.length; i++) {
            // dla każdej wagi w neuronie
            for (int j = 0; j < neurons[i].getWeights().length; j++) {
                double partialDerivative = neurons[i].getB() * x[j];
                neurons[i].addDeltaWeights(j, partialDerivative);
            }
        }
    }

    public void updateWeights() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].updateWeights();
        }
    }

    public void resetDeltaWeights() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].resetDeltaWeights();
        }
    }
}
