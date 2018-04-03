import java.util.List;

public class HiddenLayer {

    private Neuron[] neurons;
    private double[] inputs;
    private double[] outputs;

    public HiddenLayer(int numberOfNeurons, int numberOfInputs, double learningRate, double momentum, ActivationFunction function) {
        inputs = new double[numberOfInputs];
        neurons = new Neuron[numberOfNeurons];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(numberOfInputs, learningRate, momentum, function);
        }
        outputs = new double[numberOfNeurons];
    }

    public void passData(double[] inputs) {
        this.inputs = inputs;
    }

    public void calculateOutputs() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].calculateWeightedSum(inputs);
            outputs[i] = neurons[i].activate();
        }
    }

    public double[] getOutputs() {
        return outputs;
    }

    public void calculateB(double[] bFromUpperLayer, List<double[]> weightsFromUpperLayer, boolean bias) {
        // dla każdego neuronu z tej warstwy
        for (int i = 0; i < neurons.length; i++) {
            double b = 0.0;
            double derivative = neurons[i].getActivationDerivative();

            // dla każdego neuronu z wyższej warstwy
            for (int j = 0; j < weightsFromUpperLayer.size(); j++) {
                if (bias) {
                    // wybieramy i+1-szą wagę z j-tego neuronu warstwy wyższej (bo stosujemy bias, dlatego pomijamy w0)
                    double w = weightsFromUpperLayer.get(j)[i + 1];
                    b += bFromUpperLayer[j] * w;
                }
                else {
                    // wybieramy i-tą wagę z j-tego neuronu warstwy wyższej
                    double w = weightsFromUpperLayer.get(j)[i];
                    b += bFromUpperLayer[j] * w;
                }
            }
            b *= derivative;
            neurons[i].setB(b);
        }
    }

    public void calculatePartialDerivative() {
        // dla każdego neuronu w warstwie
        for (int i = 0; i < neurons.length; i++) {

            // dla każdej wagi w neuronie
            for (int j = 0; j < neurons[i].getWeights().length; j++) {
                double partialDerivative = neurons[i].getB() * inputs[j];
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

    public String toString() {
        String s = "Hidden layer\n";
        for (int i = 0; i < neurons.length; i++) {
            s += i + " neuron's weights: ";
            for (int j = 0; j < neurons[i].getWeights().length; j++) {
                s += neurons[i].getWeights()[j] + "\t";
            }
            s += "\n";
        }
        return s;
    }
}
