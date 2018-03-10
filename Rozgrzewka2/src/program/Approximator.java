package program;

import java.util.List;
import java.util.Random;

public class Approximator {

    private List<DataLine> dataLines;
    private double[] weights;
    private double learningRate;
    private int n;
    private double precision;
    private double lastError;
    private int maxSteps;
    private double maxError;

    public Approximator(List<DataLine> dataLines) {
        this.dataLines = dataLines;
        // init weights
        n = dataLines.get(0).getVectorX().length;
        learningRate = 0.1;
        precision = 1e-5;
        lastError = 0.0;
        maxSteps = 1000;
        maxError = 0.2;
        weights = new double[n + 1];
        do {
            for (int i = 0; i < weights.length; i++) {
                weights[i] = new Random().nextDouble() * 2 - 1;
            }
        } while(overallSquareError() > maxError);
    }

    public double sigmoid(double[] x) {
        double z = weights[0];
        for (int i = 0; i < x.length; i++) {
            z += weights[i + 1] * x[i];
        }
        return 1 / (1 + Math.exp(-z));
    }

    public double sigmoidDerivative(double[] x) {
        return sigmoid(x) * (1 - sigmoid(x));
    }

    public double singleError(double output, double desired) {
        return output - desired;
    }

//    public double overallError() {
//        double errorSum = 0.0;
//        for (int i = 0; i < dataLines.size(); i++) {
//            double[] x = dataLines.get(i).getVectorX();
//            double y = dataLines.get(i).getY();
//            double output = sigmoid(x);
//            errorSum += singleError(output, y);
//        }
//        return errorSum;
//    }

    public double singleSquareError(double output, double desired) {
        return singleError(output, desired) * singleError(output, desired);
    }

    public double overallSquareError() {
        double errorSum = 0.0;
        for (int i = 0; i < dataLines.size(); i++) {
            double[] x = dataLines.get(i).getVectorX();
            double y = dataLines.get(i).getY();
            double output = sigmoid(x);
            errorSum += singleSquareError(output, y);
        }
        return errorSum / dataLines.size();
    }

    public double[] calcDeltaWeights() {
        double[] deltaWeights = new double[n + 1];
        for (int i = 0; i < weights.length; i++) {
            // sum - docelowo dE/dw
            double sum = 0.0;
            for (int j = 0; j < dataLines.size(); j++) {
                double[] x = dataLines.get(i).getVectorX();
                double y = dataLines.get(i).getY();
                double output = sigmoid(x);
                // dla przypadku gdy obliczamy dla w0, wtedy mnożymy przez 1
                if (i == 0) {
                    sum += -learningRate * 2 * singleError(output, y) * sigmoidDerivative(x);
                }
                // dla przypadku gdy obliczamy pozostałe w, wtedy mnożymy przez xi
                else {
                    sum += -learningRate * 2 * singleError(output, y) * sigmoidDerivative(x) * x[i - 1];
                }
            }
            deltaWeights[i] = sum / dataLines.size();
        }
        return deltaWeights;
    }

    public void updateWeights(double[] deltaWeights) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += deltaWeights[i];
        }
    }

    public void learn() {
        double error;
        double[] lastWeights = weights;
        int i = 0;
        do {
            double[] deltaWeights = calcDeltaWeights();
            // wyliczenie błędu przed zmianą wag
            lastError = overallSquareError();
            lastWeights = weights;
            updateWeights(deltaWeights);
            error = overallSquareError();
            if (error < lastError) {
                System.out.println(error);
            }
            i++;
        } while (error < lastError && i < maxSteps);
        // jeżeli zwiększył się błąd
        if (error < lastError) weights = lastWeights;
    }

    public double[] getWeights() {
        return weights;
    }
}
