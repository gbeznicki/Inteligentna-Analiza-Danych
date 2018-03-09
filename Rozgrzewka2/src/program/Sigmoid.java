package program;

public class Sigmoid {

    private DataLine dataLine;

    public Sigmoid(DataLine dataLine) {
        this.dataLine = dataLine;
    }

    public double calcSigmoidForVector() {
        double result = 0.0;
        double[] xVector = dataLine.getX();
        for (int i = 0; i < xVector.length; i++) {
            result += calcSigmoid(xVector[i]);
        }
        return result;
    }

    public double calcSigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
