import java.util.ArrayList;
import java.util.List;

public class MultiLayerPerceptron {
    // dla każdego zestawu danych
    // najpierw podajemy dane do pierwszej warstwy
    // przekazujemy rezultat do warstwy ostatniej
    // tam liczymy b i delty
    // podajemy do warstwy wcześniejszej
    // liczymy w 1 warstwie b i delty
    // po skończonym całym cyklu obliczamy wartość funkcji oceniającej
    // aktualizujemy wagi

    private LastLayer lastLayer;
    private FirstLayer firstLayer;
    private List<double[]> trainingX;
    private List<Double> trainingY;
    private double quality;
    private double precision;
    private int stop;

    public MultiLayerPerceptron() {
        lastLayer = new LastLayer();
        firstLayer = new FirstLayer();
        trainingX = new ArrayList<>();
        trainingY = new ArrayList<>();
        quality = Double.MAX_VALUE;
        precision = 0.0001;
        stop = 100000;
    }

    public void passData(List<DataLine> dataLines) {
        for (DataLine data : dataLines) {
            trainingX.add(data.getInputs());
            trainingY.add(data.getDesiredOutputs());
        }
    }

    public void learningIteration() {
        for (int i = 0; i < trainingX.size(); i++) {
            // pierwsza warstwa
            double[] x = trainingX.get(i);
            double y = trainingY.get(i);
            firstLayer.passData(x);
            firstLayer.process();
            double[] firstLayerOutputs = firstLayer.getOutputs();
            // ostatnia warstwa
            lastLayer.passData(firstLayerOutputs, y);
            double output = lastLayer.generateOutput();
            // obliczenie błędu dla pojedynczego zestawu danych
            quality += 0.5 * (output - y) * (output - y);
            lastLayer.calculateB();
            lastLayer.calculatePartialDerivative();
            // wracamy do pierwszej warstwy
            double bFromUpperLayer = lastLayer.getNeuron().getB();
            double[] weightsFromUpperLayer = new double[2];
            weightsFromUpperLayer[0] = lastLayer.getNeuron().getWeights()[1];
            weightsFromUpperLayer[1] = lastLayer.getNeuron().getWeights()[2];
            firstLayer.calculateB(bFromUpperLayer, weightsFromUpperLayer);
            firstLayer.calculatePartialDerivative();
        }
        // po przejściu przez wszystkie zestawy treningowe aktualizujemy wagi i zerujemy odpowiednie tablice
        // aktualizzacja wag w ostatniej warstwie
        lastLayer.updateWeights();
        lastLayer.resetDeltaWeights();
        // aktualizacja wag w pierwszej warstwie
        firstLayer.updateWeights();
        firstLayer.resetDeltaWeights();
    }

    // główna metoda sterująca przebiegiem uczenia sieci
    public void process() {
        int i = 0;
        do {
            // zerujemy quality żeby móc zliczać sumę
            quality = 0;
            learningIteration();
            // sumę błędów z poszczególnych iteracji dzielimy przez ilość zestawów traningowych
            quality /= trainingX.size();
            printQualityFunction();
            i++;
        } while (i < stop && quality > precision);
//        lastLayer.printWeights();
        doSampling();
    }

    public void printQualityFunction() {
        System.out.println(quality);
    }

    public void doSampling() {
        List<double[]> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
        for (double i = -5; i < 10; i += 0.1) {
            double[] xArray = new double[2];
            xArray[0] = 1;
            xArray[1] = i;
            xs.add(xArray);
        }
        // podajemy dane do sieci
        for (int i = 0; i < xs.size(); i++) {
            firstLayer.passData(xs.get(i));
            firstLayer.process();
            double[] firstLayerOutputs = firstLayer.getOutputs();
            // ostatnia warstwa
            lastLayer.passData(firstLayerOutputs, 0.5);
            double output = lastLayer.generateOutput();
            ys.add(output);
        }
        // wypisanie na konsole
        for (int i = 0; i < xs.size(); i++) {
            System.out.println(xs.get(i)[1] + "\t" + ys.get(i));
        }
    }
}
