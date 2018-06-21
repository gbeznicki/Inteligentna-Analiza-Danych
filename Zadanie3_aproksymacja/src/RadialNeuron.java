public class RadialNeuron {

    private double input;
    private double output;
    private double centre;
    private double sigma;

    // ustalenie sigmy
    public RadialNeuron(double centre) {
        this.centre = centre;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getCentre() {
        return centre;
    }

    public RadialNeuron(double centre, double sigma) {
        this.centre = centre;
        this.sigma = sigma;
    }

    public void feedData(double input) {
        this.input = input;
    }

    public void calcOutput() {
        double d = distance();
        output = radialFunction(d);
    }

    public double getOutput() {
        return output;
    }

    private double distance() {
        return Math.abs(centre - input);
    }

    public double radialFunction(double d) {
        return (1 / (Math.sqrt(2 * Math.PI) * sigma)) * Math.exp(-(d * d) / (2 * sigma * sigma));
    }

}
