package program;

public class Sigmoid {

    private double[] x;
    private double[] weights;

    public Sigmoid(double[] x, double[] weights) {
        this.x = x;
        this.weights = weights;
    }

    public double calcSigmoidForVector() {
        double result = 0.0;
        for (int i = 0; i < x.length; i++) {
            result += calcSigmoid(x[i]);
        }
        return result;
    }

    public static double calcSigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
