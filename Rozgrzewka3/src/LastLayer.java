import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LastLayer extends Layer {
    private Neuron neuron;
    //    private double[] b;
    private double[] deltaWeights;
    private double[] x;
    private double y;
//    private double[] b;

    public LastLayer() {
//        super(numberOfNeurons, numberOfInputs);
        super();
//        b = new double[numberOfInputs];
        neuron = new Neuron(3);
        deltaWeights = new double[neuron.getNumberOfInputs()];
//        listOfX = new ArrayList<>();
//        listOfB = new ArrayList<>();
    }

    public void passData(double[] x, double y) {
        this.x = x;
        this.y = y;
    }

//    public double[] getB() {
//        return b;
//    }

    public double[] calculateB() {
        // dla każdej wagi neuronu
        double[] b = new double[neuron.getNumberOfInputs()];
        for (int i = 0; i < neuron.getWeights().length; i++) {
            double output = neuron.activate(x);
            double desired = y;
            double derivative = output * (1 - output);
            b[i] = (output - desired) * derivative;
        }
        return b;
    }

    public void calculatePartialDerivative() {
        double[] b = calculateB();
        for (int i = 0; i < b.length; i++) {
            double partialDerivative = b[i] * x[i];
            deltaWeights[i] += partialDerivative;
        }
    }

    public void updateWeights() {
        for (int i = 0; i < deltaWeights.length; i++) {
            deltaWeights[i] *= -neuron.getLearningRate();
        }
        neuron.updateWeights(deltaWeights);
    }

    public void resetDeltaWeights() {
        Arrays.fill(deltaWeights, 0);
    }
}
