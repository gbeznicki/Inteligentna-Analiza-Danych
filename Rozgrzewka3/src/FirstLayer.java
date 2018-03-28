public class FirstLayer {

    private Neuron[] neurons;
    private double[] inputs;
    private double[] outputs;

    public FirstLayer(int numberOfNeurons, int numberOfInputs) {
        inputs = new double[numberOfInputs];
        neurons = new Neuron[numberOfNeurons];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(numberOfInputs);
        }
        outputs = new double[numberOfNeurons];
    }

    public void passData(double[] inputs) {
        this.inputs = inputs;
    }

    public void calculateOutputs() {
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].activate(inputs);
        }
    }

    public double[] getOutputs() {
        return outputs;
    }

    public void process() {
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].activate(inputs);
        }
    }

    // upper layer - w0; w1; w2
    // this layer - w1; w2
    public void calculateB(double[] bFromUpperLayer, double[][] weightsFromUpperLayer) {
        // dla każdego neuronu z obecnej warstwy
        for (int i = 0; i < neurons.length; i++) {
            double b = 0.0;
            double output = outputs[i];
            double derivative = output * (1 - output);
            // dla każdego neuronu z wyższej warstwy
            for (int j = 0; j < weightsFromUpperLayer.length; j++) {
                // odpowiadająca waga z neuronu z wyższej warstwy
                double w = weightsFromUpperLayer[j][i];
                b += bFromUpperLayer[j] * w;
            }
            b *= derivative;
            neurons[i].setB(b);
        }
    }


//    public void calculateB(double bUpperLayer, double[] weightsUpperLayer) {
//        for (int i = 0; i < neurons.length; i++) {
//            double output = neurons[i].activate(inputs);
//            double derivative = output * (1 - output);
//            neurons[i].setB(bUpperLayer * weightsUpperLayer[i] * derivative);
//        }
//    }

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

    public Neuron[] getNeurons() {
        return neurons;
    }

    //    public void printWeights() {
//        for (int i = 0; i < neurons.length; i++) {
//            for (int j = 0; j < neurons[i].getWeights().length; j++) {
//                System.out.print(neurons[i].getWeights()[j] + "\t");
//            }
//            System.out.println("");
//        }
//    }
}
