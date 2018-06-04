public class RadialLayer {
    private RadialNeuron[] neurons;
    private double[] outputs;
    private double input;

    public RadialLayer(int numberOfNeurons, double[] centres, double sigma) {
        neurons = new RadialNeuron[numberOfNeurons];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new RadialNeuron(centres[i], sigma);
        }
        outputs = new double[numberOfNeurons];
    }

    public void feedData(double input) {
        this.input = input;
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].feedData(input);
        }
    }

    public void calcOutputs() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].calcOutput();
            outputs[i] = neurons[i].getOutput();
        }
    }

    public double[] getOutputs() {
        return outputs;
    }

    public RadialNeuron[] getNeurons() {
        return neurons;
    }
}
