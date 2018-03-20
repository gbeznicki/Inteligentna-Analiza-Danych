import java.util.List;

public class Main {

    public static void main(String[] args) {
        String inputFileName = args[0];
        InputReader ir = new InputReader(inputFileName);
        ir.read();
        ir.parseAllLines();
        List<DataLine> dataLines = ir.getDataLines();
        MultiLayerPerceptron mlp = new MultiLayerPerceptron();
        mlp.passData(dataLines);
        mlp.process();
    }
}
