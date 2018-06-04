public class RadialLayer {
    private RadialNeuron[] neurons;
    private double[] outputs;
//    private double[] inputs;

    public RadialLayer(int numberOfNeurons, Point[] centres, double sigma, int numberOfInputs) {
        neurons = new RadialNeuron[numberOfNeurons];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new RadialNeuron(centres[i], sigma, numberOfInputs);
        }
        outputs = new double[numberOfNeurons];
//        inputs = new double[numberOfInputs];
    }

    public void feedData(double[] inputs) {
//        this.inputs = inputs;
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].feedData(inputs);
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
