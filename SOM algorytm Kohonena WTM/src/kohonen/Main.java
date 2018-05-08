package kohonen;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Point centre1 = new Point(-3, 0);
        Point centre2 = new Point(3, 0);

        int quantity = 200;
        int k = 10;

        PointFactory pointFactory = new PointFactory();
        List<Point> dataPoints = new ArrayList<>();
        List<Neuron> neuronsList = new ArrayList<>();


        //Losowanie 1 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            dataPoints.add(pointFactory.generatePointsInsideCircle(2, centre1));
        }

        //Losowanie 2 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            dataPoints.add(pointFactory.generatePointsInsideCircle(2, centre2));
        }

        //Losowanie neuronow
        for (int i = 0; i < k; i++) {
            Neuron neuron = pointFactory.generateRandomNeuron(-10, 10);
            neuronsList.add(neuron);
        }

        SOM som = new SOM(dataPoints, neuronsList);
        som.doSOM();
    }
}
