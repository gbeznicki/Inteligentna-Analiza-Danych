public class IdentityFunction implements ActivationFunction {
    @Override
    public double calculate(double sum) {
        return sum;
    }

    @Override
    public double calculateDerivative(double sum) {
        return 1.0;
    }
}
