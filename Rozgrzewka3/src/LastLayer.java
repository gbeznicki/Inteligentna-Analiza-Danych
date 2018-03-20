public class LastLayer {

    public Neuron neuron;
    public double[] x;
    public double y;

    public LastLayer() {
        neuron = new Neuron(3);
        x = new double[3];
    }

    // przyjmujemy x jako 2 argumenty z wcześniejszej warstwy, należy jeszcze dodać x0 = 1
    public void passData(double[] x, double y) {
        this.x[0] = 1;
        this.x[1] = x[0];
        this.x[2] = x[1];
        this.y = y;
    }

    // zwracamy wyjście sieci
    public double generateOutput() {
        return neuron.activate(x);
    }

    // dla pojedynczego zestawu danych
    public void calculateB() {
        double output = neuron.activate(x);
        double desired = y;
        double derivative = output * (1 - output);
        neuron.b = (output - desired) * derivative;
    }

    // dla pojedynczego zestawu danych
    public void calculatePartialDerivative() {
        for (int i = 0; i < neuron.deltaWeights.length; i++) {
            double partialDerivative = neuron.b * x[i];
            neuron.deltaWeights[i] += partialDerivative;
        }
    }

    // wywołujemy kiedy wszystkie pochodne cząstkowe są dodane
    public void updateWeights() {
        neuron.updateWeights();
    }

    public void resetDeltaWeights() {
        neuron.resetDeltaWeights();
    }
}
