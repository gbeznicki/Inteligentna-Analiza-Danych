import java.util.Random;

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
        Random rand = new Random();

        if (neurons.length !=1){
            for (RadialNeuron neuron : neurons) {
                RadialNeuron closestNeuron = getClosestRadialNeuron(neuron);
                double x1 = neuron.getCentre();
                double x2 = closestNeuron.getCentre();
                double maxGauss = neuron.radialFunction(0);

                for (int i = 0; i < 100000; i++) {
                    neuron.setSigma(5.0 * rand.nextDouble());
                    double halfGauss = neuron.radialFunction(distance(x1, x2) / 2.0);
                    if (halfGauss >= 0.45 * maxGauss && halfGauss <= 0.55 * maxGauss) break;
                }
            }
        }
        else neurons[0].setSigma(1.0);
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
