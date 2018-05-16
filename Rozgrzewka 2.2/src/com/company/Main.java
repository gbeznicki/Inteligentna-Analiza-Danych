package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Point centre1 = new Point(-3, 0);
        Point centre2 = new Point(3, 0);

        int quantity = 100;
        int k = 10;
        double learningRate = 0.5;
        double radius = 10;

//        RandomPointGenerator randomPointGenerator = new RandomPointGenerator(quantity, centre1);
        PointFactory pointFactory = new PointFactory();
        List<Point> dataPoints = new ArrayList<>();
        List<Point> neuronsList = new ArrayList<>();


        //Losowanie 1 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            dataPoints.add(pointFactory.generatePointsInsideCircle(2, centre1));
        }
        for (int i = 0; i < dataPoints.size(); i++) {
            System.out.println(dataPoints.get(i).getX() + " " + dataPoints.get(i).getY());
        }

        //Losowanie 2 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            dataPoints.add(pointFactory.generatePointsInsideCircle(2, centre2));
        }
        for (int i = 0; i < dataPoints.size(); i++) {
            System.out.println(dataPoints.get(i).getX() + " " + dataPoints.get(i).getY());
        }

        //Losowanie neuronow
        for (int i = 0; i < k; i++) {
            neuronsList.add(pointFactory.generateRandomPoint(-10, 10, 0));
        }

//        SOM somWTA = new SOM(dataPoints, neuronsList, learningRate, new WinnerTakesAll());
//
//        somWTA.doSOM();

        SOM somWTM = new SOM(dataPoints, neuronsList, learningRate, new WinnerTakesMost(radius));

        somWTM.doSOM();
    }
}
