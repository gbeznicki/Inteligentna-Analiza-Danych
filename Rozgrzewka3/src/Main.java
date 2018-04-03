import java.util.List;

public class Main {

    public static void main(String[] args) {
        // zmienne do konfiguracji sieci
        String inputFileName = args[0];
        int numberOfInputs = 1;
        int numberOfOutputs = 1;
        int numberOfHiddenLayerNeurons = 20;
        double learningRate = 0.001;
        double momentum = 0;
        boolean bias = true;

        InputReader ir = new InputReader(inputFileName, numberOfInputs, numberOfOutputs, bias);
        ir.read();
        ir.parseAllLines();
        List<DataLine> dataLines = ir.getDataLines();

//        for (DataLine dl : dataLines) {
//            System.out.println(dl);
//        }

        MultiLayerPerceptron mlp = new MultiLayerPerceptron(numberOfInputs, numberOfHiddenLayerNeurons, numberOfOutputs, learningRate, momentum, bias);

        mlp.passData(dataLines);
        mlp.teachNetwork();
        mlp.doSampling();
//        System.out.println(mlp);
    }
}
