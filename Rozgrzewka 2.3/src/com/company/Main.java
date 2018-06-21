package com.company;

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

        //Losowanie 2 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            dataPoints.add(pointFactory.generatePointsInsideCircle(2, centre2));
        }


        //Losowanie neuronow
        for (int i = 0; i < k; i++) {
            Point p = pointFactory.generateRandomPoint(-10, 10);
            Neuron n = new Neuron(p.getX(), p.getY(),0);
            neuronsList.add(n);
        }

        SOM som = new SOM(dataPoints, 2, -10, 10);
        som.doSOM();
        som.savePointGroups();
//        int trials = 100;
//        double min = -10;
//        double max = 10;
//
//        NeuralGassWrapper neuralGassWrapper = new NeuralGassWrapper(dataPoints, trials, k, min, max);
//        neuralGassWrapper.run();
//
//        System.out.println("średni błąd: " + neuralGassWrapper.calculateAverageError());
//        System.out.println("odchylenie błędu: " + neuralGassWrapper.calculateErrorDeviation());
//        System.out.println("minimalny błąd: " + neuralGassWrapper.getMinimumError());
//        System.out.println("średnia liczba nieaktywnych centrów: " + neuralGassWrapper.calculateAverageInactiveNeurons());
//        System.out.println("odchylenie nieaktywnych centrów: " + neuralGassWrapper.calculateInactiveNeuronsDeviation());

//        som.savePointGroups();
    }
}
