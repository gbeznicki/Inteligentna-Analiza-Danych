public class RadialNeuron {

    private double[] inputs;
    private double output;
    private Point centre;
    private double sigma;

    public RadialNeuron(Point centre, double sigma, int numberOfInputs) {
        inputs = new double[numberOfInputs];
        this.centre = centre;
        this.sigma = sigma;
    }

    public void feedData(double[] inputs) {
        this.inputs = inputs;
    }

    public void calcOutput() {
        double d = distance();
        output = radialFunction(d);
    }

    public double getOutput() {
        return output;
    }

    private double distance() {
        double distance = 0;
        for (int i = 0; i < inputs.length; i++) {
            distance += (inputs[i] - centre.getX()[i]) * (inputs[i] - centre.getX()[i]);
        }
        distance = Math.sqrt(distance);
        return distance;
    }

    private double radialFunction(double d) {
        return (1 / (Math.sqrt(2 * Math.PI) * sigma)) * Math.exp(-(d * d) / (2 * sigma * sigma));
    }

}
