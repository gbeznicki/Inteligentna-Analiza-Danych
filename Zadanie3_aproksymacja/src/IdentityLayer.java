public class IdentityLayer {
    private IdentityNeuron neuron;
    private double[] inputs;
    private double output;
    private double desiredOutput;

    public IdentityLayer(int numberOfInputs, double learningRate) {
        neuron = new IdentityNeuron(numberOfInputs, learningRate);
        inputs = new double[numberOfInputs + 1];
    }

    public void feedData(double[] inputs, double desiredOutput) {
        this.inputs = new double[inputs.length + 1];
        this.inputs[0] = 1;
        for (int i = 1; i < this.inputs.length; i++) {
            this.inputs[i] = inputs[i - 1];
        }
        this.desiredOutput = desiredOutput;
        neuron.feedData(inputs);
    }

    public void calculateError() {
        double output = neuron.getOutput();
        double desiredOutput = this.desiredOutput;
        double derivative = neuron.getOutputDerivative();
        double error = (output - desiredOutput) * derivative;
        neuron.setError(error);
    }

    public double getError() {
        return neuron.getError();
    }

    public void calculateOutput() {
        neuron.calcWeightedSum();
        output = neuron.getOutput();
    }

    public void updateWeights() {
        neuron.updateWeights();
    }

    public void resetDeltaWeights() {
        neuron.resetDeltaWeights();
    }

    public double getOutput() {
        return output;
    }

    public void calculatePartialDerivative() {
        for (int i = 0; i < neuron.getDeltaWeights().length; i++) {
            double partialDerivative = neuron.getError() * inputs[i];
            neuron.addDeltaWeights(i, partialDerivative);
        }
    }

    public IdentityNeuron getNeuron() {
        return neuron;
    }
}
