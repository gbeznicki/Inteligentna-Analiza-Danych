public class SigmoidalFunction implements ActivationFunction {
    @Override
    public double calculate(double sum) {
        return 1 / (1 + Math.exp(-sum));
    }

    @Override
    public double calculateDerivative(double sum) {
        return calculate(sum) * (1 - calculate(sum));
    }
}
