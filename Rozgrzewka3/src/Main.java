import java.util.List;

public class Main {

    public static void main(String[] args) {
        // zmienne do konfiguracji sieci
        String inputFileName = args[0];
        int numberOfInputs = 1;
        int numberOfOutputs = 1;
        int numberOfHiddenLayerNeurons = 5;
        double learningRate = 0.001;
        double momentum = 0;
        boolean bias = true;
        ActivationFunction hiddenLayerFunction = new SigmoidalFunction();
        ActivationFunction outputLayerFunction = new IdentityFunction();

        // wczytanie danych z pliku
        InputReader ir = new InputReader(inputFileName, numberOfInputs, numberOfOutputs, bias);
        ir.read();
        ir.parseAllLines();
        List<DataLine> dataLines = ir.getDataLines();

        // stworzenie sieci
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(numberOfInputs, numberOfHiddenLayerNeurons, numberOfOutputs, learningRate, momentum, bias, hiddenLayerFunction, outputLayerFunction);

        // podanie danych treningowych i nauka
        mlp.passData(dataLines);
        mlp.teachNetwork();
        mlp.doSampling();
    }
}
