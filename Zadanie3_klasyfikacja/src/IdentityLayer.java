public class IdentityLayer {
    private IdentityNeuron[] neurons;
    private double[] inputs;
    private double[] outputs;
    private double[] desiredOutputs;

    public IdentityLayer(int numberOfInputs, int numberOfOutputs, double learningRate) {
        neurons = new IdentityNeuron[numberOfOutputs];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new IdentityNeuron(numberOfInputs, learningRate);
        }
        inputs = new double[numberOfInputs + 1];
        outputs = new double[numberOfOutputs];
        desiredOutputs = new double[numberOfOutputs];
    }

    public void feedData(double[] inputs, double[] desiredOutputs) {
        this.inputs = new double[inputs.length + 1];
        this.inputs[0] = 1;
        for (int i = 1; i < this.inputs.length; i++) {
            this.inputs[i] = inputs[i - 1];
        }
        this.desiredOutputs = desiredOutputs;
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].feedData(inputs);
        }
    }

    public void calculateError() {
        for (int i = 0; i < neurons.length; i++) {
            double output = neurons[i].getOutput();
            double desiredOutput = this.desiredOutputs[i];
            double derivative = neurons[i].getOutputDerivative();
            double error = (output - desiredOutput) * derivative;
            neurons[i].setError(error);
        }
    }

    public double[] getError() {
        double[] errors = new double[neurons.length];
        for (int i = 0; i < errors.length; i++) {
            errors[i] = neurons[i].getError();
        }
        return errors;
    }

    public void calculateOutput() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].calcWeightedSum();
            outputs[i] = neurons[i].getOutput();
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

    public double[] getOutputs() {
        return outputs;
    }

    public void calculatePartialDerivative() {
        for (int i = 0; i < neurons.length; i++) {

            // dla każdej wagi w neuronie
            for (int j = 0; j < neurons[i].getDeltaWeights().length; j++) {
                double partialDerivative = neurons[i].getError() * inputs[j];
                neurons[i].addDeltaWeights(j, partialDerivative);
            }
        }
    }

    public IdentityNeuron[] getNeurons() {
        return neurons;
    }
}
