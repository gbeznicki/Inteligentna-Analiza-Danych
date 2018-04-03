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


        double[] testQuality = new double[100];
        double[] trainQuality = new double[100];
        MultiLayerPerceptron[] mlps = new MultiLayerPerceptron[100];

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

        // dla 100
        for (int i = 0; i < mlps.length; i++) {
            mlps[i] = new MultiLayerPerceptron(numberOfInputs, numberOfHiddenLayerNeurons, numberOfOutputs, learningRate, momentum, bias, hiddenLayerFunction, outputLayerFunction);
            mlps[i].passData(trainigData);
            mlps[i].passTestData(testData);
            mlps[i].teachNetwork();

            testQuality[i] = mlps[i].testQuality();
            trainQuality[i] = mlps[i].getTrainQuality();

            System.out.println(i);
        }

        // obliczenie średnich
        double avgErrorTest = 0.0;
        for (int i = 0; i < testQuality.length; i++) {
            avgErrorTest += testQuality[i];
        }
        avgErrorTest /= testQuality.length;

        System.out.println("sredni blad testowy: " + avgErrorTest);

        double avgErrorTrain = 0.0;
        for (int i = 0; i < trainQuality.length; i++) {
            avgErrorTrain += trainQuality[i];
        }
        avgErrorTrain /= trainQuality.length;

        System.out.println("sredni blad treningowy: " + avgErrorTrain);

        // odchylenia standardowe
        double odchylenieTest = 0.0;
        for (int i = 0; i < testQuality.length; i++) {
            odchylenieTest += (testQuality[i] - avgErrorTest) * (testQuality[i] - avgErrorTest);
        }

        odchylenieTest /= (testQuality.length - 1);
        odchylenieTest = Math.sqrt(odchylenieTest);

        System.out.println("odchylenie zbior testowy: " + odchylenieTest);

        double odchylenieTrening = 0.0;
        for (int i = 0; i < trainQuality.length; i++) {
            odchylenieTrening += (trainQuality[i] - avgErrorTrain) * (trainQuality[i] - avgErrorTrain);
        }

        odchylenieTrening /= (trainQuality.length - 1);
        odchylenieTrening = Math.sqrt(odchylenieTrening);

        System.out.println("odchylenie zbior treningowy: " + odchylenieTrening);

        // stworzenie sieci
//        MultiLayerPerceptron mlp = new MultiLayerPerceptron(numberOfInputs, numberOfHiddenLayerNeurons, numberOfOutputs, learningRate, momentum, bias, hiddenLayerFunction, outputLayerFunction);

        // podanie danych treningowych i nauka
//        mlp.passData(trainigData);
//        mlp.passTestData(testData);
//        mlp.teachNetwork();


//        mlp.doSampling();
//        System.out.println(mlp.getIt());
    }
}
