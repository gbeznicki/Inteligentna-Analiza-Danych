package program;

public class SigmoidDerivative {

    private Sigmoid sigmoid;

    public SigmoidDerivative(Sigmoid sigmoid) {
        this.sigmoid = sigmoid;
    }

    public double calcSigmoidDerivative() {
        return sigmoid.calcSigmoidForVector() * (1 - sigmoid.calcSigmoidForVector());
    }
}
