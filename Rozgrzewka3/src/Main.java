import java.util.List;

public class Main {

    public static void main(String[] args) {
        // zmienne do konfiguracji sieci
        String inputFileName = args[0];
        int numberOfInputs = 4;
        int numberOfOutputs = 4;
        int numberOfHiddenLayerNeurons = 2;
        double learningRate = 0.1;
        double momentum = 0;
        boolean bias = false;
        ActivationFunction hiddenLayerFunction = new SigmoidalFunction();
        ActivationFunction outputLayerFunction = new SigmoidalFunction();

        // wczytanie danych treningowych z pliku
        InputReader trainingDataReader = new InputReader(inputFileName, numberOfInputs, numberOfOutputs, bias);
        trainingDataReader.read();
        trainingDataReader.parseAllLines();
        List<DataLine> trainigData = trainingDataReader.getDataLines();

        // stworzenie sieci
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(numberOfInputs, numberOfHiddenLayerNeurons, numberOfOutputs, learningRate, momentum, bias, hiddenLayerFunction, outputLayerFunction);

        // podanie danych treningowych i nauka
        mlp.passData(trainigData);
//        mlp.passTestData(testData);
        mlp.teachNetwork();
//        mlp.doSampling();
    }
}
