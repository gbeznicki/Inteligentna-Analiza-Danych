import java.util.Arrays;

public class OutputLayer {

    private Neuron[] neurons;
    private double[] inputs;
    private double[] desiredOutputs;
    private double[] outputs;
    private double[] b;

    public OutputLayer(int numberOfNeurons, int numberOfInputs, double learningRate, double momentum) {
        neurons = new Neuron[numberOfNeurons];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new Neuron(numberOfInputs, learningRate, momentum);
        }
        inputs = new double[numberOfInputs];
        outputs = new double[numberOfNeurons];
        desiredOutputs = new double[numberOfNeurons];
        b = new double[numberOfNeurons];
    }

    // jeżeli korzystamy z biasu, to x0 = 1 jest już dodane w klasie MultiLayerPerceptron
    public void passTrainingData(double[] inputs, double[] desiredOutputs) {
        this.inputs = inputs;
        this.desiredOutputs = desiredOutputs;
    }

    // nie podajemy spodziewanych danych wyjściowych, stosujemy w przypadku gdy sieć już jest nauczona
    // i chcemy poznać odpowiedź sieci na zadane dane wejściowe
    public void passData(double[] inputs) {
        this.inputs = inputs;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    // obliczamy wyjście sieci
    public void calculateOutputs() {
        for (int i = 0; i < neurons.length; i++) {
//            neurons[i].calculateWeightedSum(inputs);
            outputs[i] = neurons[i].activateIdentity(inputs);
        }
    }

    public double[] getOutputs() {
        return outputs;
    }

    // dla pojedynczego zestawu danych
    public void calculateB() {
        for (int i = 0; i < neurons.length; i++) {
            double output = neurons[i].activateIdentity(inputs);
            double desiredOutput = desiredOutputs[i];
            double derivative = 1.0;
            double b = (output - desiredOutput) * derivative;
            neurons[i].setB(b);
            this.b[i] = b;
        }
    }

    public double[] getB() {
        return b;
    }

    public void calculatePartialDerivative() {
        // dla każdego neuronu w warstwie
        for (int i = 0; i < neurons.length; i++) {

            // dla każdej wagi w neuronie
            for (int j = 0; j < neurons[i].getDeltaWeights().length; j++) {
                double partialDerivative = neurons[i].getB() * inputs[j];
                neurons[i].addDeltaWeights(j, partialDerivative);
            }
        }
    }

    // wywołujemy kiedy wszystkie pochodne cząstkowe są dodane
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
        String s = "Output layer\n";
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
