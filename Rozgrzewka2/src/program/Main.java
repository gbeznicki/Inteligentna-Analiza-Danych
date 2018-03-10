package program;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    String inputFileName = args[0];
	    String outputFileName = args[1];
	    InputReader ir = new InputReader(inputFileName);
	    ir.read();
	    ir.parseAllLines();
        List<DataLine> dataLines = ir.getDataLines();
        for (DataLine dl : dataLines) {
            System.out.println(dl);
        }
        Approximator a = new Approximator(dataLines);
        double[] weights = a.getWeights();
        for (int i = 0; i < weights.length; i++) {
            System.out.println("w" + i + " = " + weights[i]);
        }
        a.learn();
        weights = a.getWeights();
        for (int i = 0; i < weights.length; i++) {
            System.out.println("w" + i + " = " + weights[i]);
        }
        OutputWriter ow = new OutputWriter(weights, outputFileName);
        ow.saveToFile();
    }
}
