import java.util.List;

public class Main {

    public static void main(String[] args) {
        String inputFileName = args[0];
        int numberOfInputs = 4;
        int numberOfOutputs = 2;
        InputReader ir = new InputReader(inputFileName, numberOfInputs, numberOfOutputs);
        ir.read();
        ir.parseAllLines();
        List<DataLine> dataLines = ir.getDataLines();
        // wypisz
        for (DataLine dl : dataLines) {
            System.out.println(dl);
        }
//        MultiLayerPerceptron mlp = new MultiLayerPerceptron();
//        mlp.passData(dataLines);
//        mlp.process();
    }
}
