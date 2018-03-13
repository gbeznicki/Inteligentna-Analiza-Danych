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
        Approximator a = new Approximator(dataLines);
        double[] weights = a.getWeights();
        a.learn();
        weights = a.getWeights();
        OutputWriter ow = new OutputWriter(weights, outputFileName);
        ow.saveToFile();
    }
}
