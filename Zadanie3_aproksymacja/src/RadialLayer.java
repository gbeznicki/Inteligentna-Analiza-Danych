public class RadialLayer {
    private RadialNeuron[] neurons;
    private double[] outputs;
    private double input;

    // ustalenie sigmy
    private RadialNeuron getClosestRadialNeuron(RadialNeuron neuron) {
        double minDistance = Double.MAX_VALUE;
        RadialNeuron closestNeuron = null;

        for (RadialNeuron otherNeuron : neurons) {
            if (otherNeuron != neuron) {
                double distance = distance(neuron.getCentre(), otherNeuron.getCentre());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestNeuron = otherNeuron;
                }
            }
        }

        return closestNeuron;
    }

    private void initializeSigmas() {
        for (RadialNeuron neuron : neurons) {
            RadialNeuron closestNeuron = getClosestRadialNeuron(neuron);
            double x1 = neuron.getCentre();
            double x2 = closestNeuron.getCentre();
            double sigma = distance(x1, x2) / 2.0;
            neuron.setSigma(sigma);
        }
    }

    private double distance(double x1, double x2) {
        return Math.abs(x1 - x2);
    }

    public RadialLayer(int numberOfNeurons, double[] centres, double sigma) {
        neurons = new RadialNeuron[numberOfNeurons];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new RadialNeuron(centres[i]);
        }

        initializeSigmas();

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
