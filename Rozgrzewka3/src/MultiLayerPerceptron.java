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
    private double previousQuality;
    private double quality;
    private double precision;

    public MultiLayerPerceptron() {
        lastLayer = new LastLayer();
        firstLayer = new FirstLayer();
        trainingX = new ArrayList<>();
        trainingY = new ArrayList<>();
        previousQuality = Double.MAX_VALUE;
        quality = Double.MAX_VALUE;
        precision = 0.001;
    }

    public void passData(List<DataLine> dataLines) {
        for (DataLine data : dataLines) {
            trainingX.add(data.getVectorX());
            trainingY.add(data.getY());
        }
    }

    public void learningIteration() {
        for (int i = 0; i < trainingX.size(); i++) {
            // pierwsza warstwa
            double[] x = trainingX.get(i);
            double y = trainingY.get(i);
            firstLayer.passData(x);
            firstLayer.process();
            double[] firstLayerOutputs = firstLayer.outputs;
            // ostatnia warstwa
            lastLayer.passData(firstLayerOutputs, y);
            double output = lastLayer.generateOutput();
            // obliczenie błędu dla pojedynczego zestawu danych
            quality += 0.5 * (output - y) * (output - y);
            lastLayer.calculateB();
            lastLayer.calculatePartialDerivative();
            // wracamy do pierwszej warstwy
            double bFromUpperLayer = lastLayer.neuron.b;
            double[] weightsFromUpperLayer = new double[2];
            weightsFromUpperLayer[0] = lastLayer.neuron.weights[1];
            weightsFromUpperLayer[1] = lastLayer.neuron.weights[2];
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
        do {
            previousQuality = quality;
            // zerujemy quality żeby móc zliczać sumę
            quality = 0;
            learningIteration();
            // sumę błędów z poszczególnych iteracji dzielimy przez ilość zestawów traningowych
            quality /= trainingX.size();
            printQualityFunction();
        } while (quality > 0.0001);
//        } while (previousQuality > quality);
    }

    public void printQualityFunction() {
        System.out.println(quality);
    }
}
