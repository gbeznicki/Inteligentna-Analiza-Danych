package program;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SigmoidTest {

    @Test
    public void calcSigmoidForVector() {
        // x - od x1 do x5
        // weights - wagi od w0 do w5
        int n = 5;
        double[] x = new double[n];
        double[] weights = new double[n + 1];
        for (int i = 0; i < x.length; i++) {
            x[i] = new Random().nextDouble() * 10 - 5;
        }
        for (int i = 0; i < weights.length; i++) {
            weights[i] = new Random().nextDouble() * 2 - 1;
        }
        Sigmoid s = new Sigmoid(x, weights);
        double sigmoidVector = s.calcSigmoidForVector();
        double sigmoid = 0.0;
        for (int i = 0; i < x.length; i++) {
            sigmoid += Sigmoid.calcSigmoid(x[i]);
        }
        assertEquals(sigmoid, sigmoidVector, 0);
    }

    @Test
    public void calcSigmoid() {
        double[] x = new double[1000];
        for (int i = 0; i < x.length; i++) {
            x[i] = new Random().nextDouble() * 100;
            assertEquals((1 / (1 + Math.exp(-x[i]))), Sigmoid.calcSigmoid(x[i]), 0);
        }
    }
}