import java.util.List;

public class Main {

    public static void main(String[] args) {
        // zmienne do konfiguracji sieci
        String inputFileName = args[0];
        int numberOfInputs = 1;
        int numberOfOutputs = 1;
        int numberOfHiddenLayerNeurons = 1;
        double learningRate = 0.0001;
        double momentum = 0;
        boolean bias = true;
        ActivationFunction hiddenLayerFunction = new SigmoidalFunction();
        ActivationFunction outputLayerFunction = new IdentityFunction();

        // wczytanie danych treningowych z pliku
        InputReader trainingDataReader = new InputReader(inputFileName, numberOfInputs, numberOfOutputs, bias);
        trainingDataReader.read();
        trainingDataReader.parseAllLines();
        List<DataLine> trainigData = trainingDataReader.getDataLines();

        // wczytanie danych testowych z pliku
        InputReader testDataReader = new InputReader("atest.txt", numberOfInputs, numberOfOutputs, bias);
        testDataReader.read();
        testDataReader.parseAllLines();
        List<DataLine> testData = testDataReader.getDataLines();

        // stworzenie sieci
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(numberOfInputs, numberOfHiddenLayerNeurons, numberOfOutputs, learningRate, momentum, bias, hiddenLayerFunction, outputLayerFunction);

        // podanie danych treningowych i nauka
        mlp.passData(trainigData);
        mlp.passTestData(testData);
        mlp.teachNetwork();
        mlp.doSampling();
//        System.out.println(mlp.getIt());
    }
}
