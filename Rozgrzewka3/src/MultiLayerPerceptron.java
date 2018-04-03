import java.util.ArrayList;
import java.util.List;

public class MultiLayerPerceptron {

    private OutputLayer outputLayer;
    private HiddenLayer hiddenLayer;
    private List<double[]> trainingInputs;
    private List<double[]> trainingDesiredOutputs;
    private double quality;
    private double precision;
    private int stop;
    private boolean bias;

    public MultiLayerPerceptron(int numberOfInputs, int numberOfHiddenLayerNeurons, int numberOfOutputs, double learningRate, double momentum, boolean bias, ActivationFunction hiddenLayerFunction, ActivationFunction outputLayerFunction) {
        int hiddenLayerInputs = numberOfInputs;
        int outputLayerInputs = numberOfHiddenLayerNeurons;
        if (bias) {
            hiddenLayerInputs++;
            outputLayerInputs++;
        }
        outputLayer = new OutputLayer(numberOfOutputs, outputLayerInputs, learningRate, momentum, outputLayerFunction);
        hiddenLayer = new HiddenLayer(numberOfHiddenLayerNeurons, hiddenLayerInputs, learningRate, momentum, hiddenLayerFunction);
        trainingInputs = new ArrayList<>();
        trainingDesiredOutputs = new ArrayList<>();
        quality = Double.MAX_VALUE;
        precision = 1e-5;
        stop = 100000;
        this.bias = bias;
    }

    public void passData(List<DataLine> dataLines) {
        for (DataLine data : dataLines) {
            trainingInputs.add(data.getInputs());
            trainingDesiredOutputs.add(data.getDesiredOutputs());
        }
    }

    public void learningIteration() {
        for (int i = 0; i < trainingInputs.size(); i++) {
            // pobranie jednego zestawu danych
            double[] inputs = trainingInputs.get(i);
            double[] desiredOutputs = trainingDesiredOutputs.get(i);

            // warstwa ukryta
            hiddenLayer.passData(inputs);
            hiddenLayer.calculateOutputs();
            double[] hiddenLayerOutputs = hiddenLayer.getOutputs();

            // jeżeli korzystamy z biasu, to dodajemy x0 = 1
            if (bias) {
                hiddenLayerOutputs = addBiasToArray(hiddenLayerOutputs);
            }

            // warstwa wyjściowa
            outputLayer.passTrainingData(hiddenLayerOutputs, desiredOutputs);
            outputLayer.calculateOutputs();
            double[] outputs = outputLayer.getOutputs();

            // dla każdego neuronu w warstwie wyjściowej obliczamy wartość funkcji oceniającej
            for (int j = 0; j < outputs.length; j++) {
                quality += 0.5 * (outputs[j] - desiredOutputs[j]) * (outputs[j] - desiredOutputs[j]);
            }

            // obliczenie błędów w warstwie wyjściowej
            outputLayer.calculateB();
            outputLayer.calculatePartialDerivative();

            // wsteczna propagacja, wracamy do warstwy ukrytej
            double[] bFromOutputLayer = outputLayer.getB();

            // pobierz wagi z warstwy wyjściowej
            List<double[]> weightsFromOutputLayer = new ArrayList<>();
            for (int j = 0; j < outputLayer.getNeurons().length; j++) {
                double[] w = outputLayer.getNeurons()[j].getWeights();
                weightsFromOutputLayer.add(w);
            }

            // obliczenie błędów w warstwie ukrytej
            hiddenLayer.calculateB(bFromOutputLayer, weightsFromOutputLayer, bias);
            hiddenLayer.calculatePartialDerivative();
        }
        // po przejściu przez wszystkie zestawy treningowe aktualizujemy wagi i zerujemy odpowiednie tablice

        // aktualizacja wag w warstwie wyjściowej
        outputLayer.updateWeights();
        outputLayer.resetDeltaWeights();

        // aktualizacja wag w warstwie ukrytej
        hiddenLayer.updateWeights();
        hiddenLayer.resetDeltaWeights();
    }

    // główna metoda sterująca przebiegiem uczenia sieci
    public void teachNetwork() {
        int i = 0;
        do {
            // zerujemy quality żeby móc zliczać sumę

            quality = 0;
            learningIteration();

            // sumę błędów z poszczególnych iteracji dzielimy przez ilość zestawów traningowych
            quality /= trainingInputs.size();
            printQualityFunction();

            i++;
        } while (i < stop && quality > precision);
    }

    private double[] addBiasToArray(double[] array) {
        double[] arrayWithBias = new double[array.length + 1];

        // dodaj x0 = 1 jako bias
        arrayWithBias[0] = 1.0;

        // przekopiuj resztę wartości wejściowych do tablicy
        for (int i = 1; i < arrayWithBias.length; i++) {
            arrayWithBias[i] = array[i - 1];
        }
        return arrayWithBias;
    }

    public void printQualityFunction() {
        System.out.println(quality);
    }

    public void doSampling() {
        List<double[]> inputs = new ArrayList<>();
        List<double[]> outputs = new ArrayList<>();

//        inputs.add(new double[] {1, 1, 0, 0, 0});
//        inputs.add(new double[] {1, 0, 1, 0, 0});
//        inputs.add(new double[] {1, 0, 0, 1, 0});
//        inputs.add(new double[] {1, 0, 0, 0, 1});

        for (double i = -4.0; i < 4.0; i += 0.1) {
            inputs.add(new double[] {1, i});
        }

        // podajemy dane do sieci
        for (int i = 0; i < inputs.size(); i++) {
            hiddenLayer.passData(inputs.get(i));
            hiddenLayer.calculateOutputs();
            double[] hiddenLayerOutputs = hiddenLayer.getOutputs();

            if (bias) {
                hiddenLayerOutputs = addBiasToArray(hiddenLayerOutputs);
            }

            // ostatnia warstwa
            outputLayer.passData(hiddenLayerOutputs);
            outputLayer.calculateOutputs();

            // wyjścia sieci
            double[] output = outputLayer.getOutputs();

//            System.out.print("Wejście: ");
            for (int j = 1; j < inputs.get(i).length; j++) {
                System.out.print(inputs.get(i)[j] + "\t");
            }
//            System.out.print("\tWyjście: ");
            for (int j = 0; j < output.length; j++) {
                System.out.print(output[j] + "\t");
            }
            System.out.println("");

            outputs.add(output);
        }
        // wypisanie na konsole
//        for (int i = 0; i < inputs.size(); i++) {
//            System.out.print("Wejście: ");
//            for (int j = 0; j < inputs.get(i).length; j++) {
//                System.out.print(inputs.get(i)[j] + "\t");
//            }
//            System.out.print("\tWyjście: ");
//            for (int j = 0; j < inputs.get(i).length; j++) {
//                System.out.print(outputs.get(i)[j] + "\t");
//            }
//            System.out.println("");
//        }
    }

    public String toString() {
        return hiddenLayer + "\n" + outputLayer + "\n";
    }
}
