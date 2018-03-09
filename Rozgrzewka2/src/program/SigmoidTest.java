package program;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SigmoidTest {

    @Test
    public void calcSigmoidForVector() {

    }

    @Test
    public void calcSigmoid() {
        double[] x = new double[1000];
        for (int i = 0; i < x.length; i++) {
            x[i] = new Random().nextDouble() * 100;
            assertEquals((1 / (1 + Math.exp(-x[i]))), Sigmoid.calcSigmoid(x[i]), 1e-10);
        }
    }
}