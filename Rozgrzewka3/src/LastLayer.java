public class LastLayer {

    private Neuron[] neurons;
    private double[] inputs;
    private double[] desiredOutputs;
    private double[] outputs;

    public LastLayer(int numberOfNeurons, int numberOfInputs) {
        neurons = new Neuron[numberOfNeurons];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(numberOfInputs);
        }
        inputs = new double[numberOfInputs];
        outputs = new double[numberOfNeurons];
        desiredOutputs = new double[numberOfNeurons];
    }

    // jako inputs[0] wpisujemy 1 (potrzebne do biasu), resztę xów przesuwamy o jeden indeks w górę
    public void passData(double[] inputs, double[] desiredOutputs) {
        this.inputs[0] = 1;
        for (int i = 1; i < this.inputs.length; i++) {
            this.inputs[i] = inputs[i - 1];
        }
        for (int i = 0; i < this.desiredOutputs.length; i++) {
            this.desiredOutputs[i] = desiredOutputs[i];
        }

//        this.inputs[0] = 1;
//        this.inputs[1] = inputs[0];
//        this.inputs[2] = inputs[1];
//        this.desiredOutputs = desiredOutputs;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    // obliczamy wyjście sieci
    public void generateOutput() {
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].activate(inputs);
        }
    }

    // dla pojedynczego zestawu danych
    public void calculateB() {
        for (int i = 0; i < neurons.length; i++) {
            double output = outputs[i];
            double desiredOutput = desiredOutputs[i];
            double derivative = output * (1 - output);
            double b = (output - desiredOutput) * derivative;
            neurons[i].setB(b);
        }


//        double output = neuron.activate(inputs);
//        double desired = desiredOutputs;
//        double derivative = output * (1 - output);
//        neuron.setB((output - desired) * derivative);
    }

    // dla pojedynczego zestawu danych
    public void calculatePartialDerivative() {
        for (int i = 0; i < neurons.length; i++) {
            for (int j = 0; j < neurons[i].getDeltaWeights().length; j++) {
                double partialDerivative = neurons[i].getB() * inputs[j];
                neurons[i].addDeltaWeights(j, partialDerivative);
            }
        }

//        for (int i = 0; i < neuron.getDeltaWeights().length; i++) {
//            double partialDerivative = neuron.getB() * inputs[i];
//            neuron.addDeltaWeights(i, partialDerivative);
//        }
    }

    // wywołujemy kiedy wszystkie pochodne cząstkowe są dodane
    public void updateWeights() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].updateWeights();
        }

//        neuron.updateWeights();
    }

    public void resetDeltaWeights() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].resetDeltaWeights();
        }

//        neuron.resetDeltaWeights();
    }

//    public void printWeights() {
//        for (int i = 0; i < neuron.getWeights().length; i++) {
//            System.out.print(neuron.getWeights()[i] + "\t");
//        }
//    }
}
