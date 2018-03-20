public class LastLayer {

    private Neuron neuron;
    private double[] x;
    private double y;

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

    public Neuron getNeuron() {
        return neuron;
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
        neuron.setB((output - desired) * derivative);
    }

    // dla pojedynczego zestawu danych
    public void calculatePartialDerivative() {
        for (int i = 0; i < neuron.getDeltaWeights().length; i++) {
            double partialDerivative = neuron.getB() * x[i];
            neuron.addDeltaWeights(i, partialDerivative);
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
