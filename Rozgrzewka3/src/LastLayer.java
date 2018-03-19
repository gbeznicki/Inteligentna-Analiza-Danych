import java.util.ArrayList;
import java.util.List;

public class LastLayer extends Layer {
    private Neuron neuron;
//    private double[] b;
    private double[] deltaWeights;
    private List<double[]> listOfX;
    private List<Double> listOfV;

    public LastLayer(int numberOfNeurons, int numberOfInputs) {
//        super(numberOfNeurons, numberOfInputs);
        super();
//        b = new double[numberOfInputs];
        listOfX = new ArrayList<>();
    }

    public void calculateB() {
        // dla każdej wagi neuronu
        for (int i = 0; i < neuron.getWeights().length; i++) {
            // dla każdego zestawu argumentów
            for (int j = 0; j < listOfX.size(); j++) {
                double y = neuron.activate(listOfX.get(j));
                double v = listOfV.get(j);
                double derivative = y * (1 - y);
                double x = listOfX.get(j)[i];
                double partialDerivative = (y - v) * derivative * x;
                deltaWeights[i] += partialDerivative;
//                b[i] += (y - v) * derivative;
            }
            deltaWeights[i] *= -neuron.getLearningRate();
        }
    }

    public void calculateDeltaWeights() {
        for (int i = 0; i < neuron.getWeights().length; i++) {

        }
    }
}
