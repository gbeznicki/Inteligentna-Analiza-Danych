package kohonen;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Point centre1 = new Point(-3, 0);
        Point centre2 = new Point(3, 0);

        int quantity = 500;
        int k = 2;

        PointFactory pointFactory = new PointFactory();
        List<Point> dataPoints = new ArrayList<>();
        List<Neuron> neuronsList = new ArrayList<>();


        //Losowanie 1 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            dataPoints.add(pointFactory.generatePointsInsideCircle(2, centre1));
        }

//        //Losowanie 2 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            dataPoints.add(pointFactory.generatePointsInsideCircle(2, centre2));
        }

//        //Losowanie neuronow
        for (int i = 0; i < k; i++) {
            Neuron neuron = pointFactory.generateRandomNeuron(-10, 10, i);
            neuronsList.add(neuron);
        }

        int trials = 100;
        double min = -20;
        double max = 20;

//        KohonenWrapper kohonenWrapper = new KohonenWrapper(dataPoints, trials, k, min, max);
//        kohonenWrapper.run();

//        System.out.println("średni błąd: " + kohonenWrapper.calculateAverageError());
//        System.out.println("odchylenie błędu: " + kohonenWrapper.calculateErrorDeviation());
//        System.out.println("minimalny błąd: " + kohonenWrapper.getMinimumError());
//        System.out.println("średnia liczba nieaktywnych centrów: " + kohonenWrapper.calculateAverageInactiveNeurons());
//        System.out.println("odchylenie nieaktywnych centrów: " + kohonenWrapper.calculateInactiveNeuronsDeviation());

<<<<<<< Updated upstream
//        som.savePointGroups();
=======
        SOM som = new SOM(dataPoints, k, -10, 10);
        som.doSOM();
        som.savePointGroups();
>>>>>>> Stashed changes
    }
}
