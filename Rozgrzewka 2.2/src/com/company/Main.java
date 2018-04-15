package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Point centre1 = new Point(-3, 0);
        Point centre2 = new Point(3, 0);

        int quantity = 100;
        int k = 10;

//        RandomPointGenerator randomPointGenerator = new RandomPointGenerator(quantity, centre1);
        PointFactory pointFactory = new PointFactory();
        List<Point> normalPointList = new ArrayList<>();
        List<Point> kPointList = new ArrayList<>();


        //Losowanie 1 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            normalPointList.add(pointFactory.generatePointsInsideCircle(2, centre1));
        }
        for (int i = 0; i < normalPointList.size(); i++) {
            System.out.println(normalPointList.get(i).getX() + " " + normalPointList.get(i).getY());
        }

        //Losowanie 2 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            normalPointList.add(pointFactory.generatePointsInsideCircle(2, centre2));
        }
        for (int i = 0; i < normalPointList.size(); i++) {
            System.out.println(normalPointList.get(i).getX() + " " + normalPointList.get(i).getY());
        }

        for (int i = 0; i < k; i++) {
            kPointList.add(pointFactory.generateRandomPoint(-10, 10, 0));
        }    }
}
