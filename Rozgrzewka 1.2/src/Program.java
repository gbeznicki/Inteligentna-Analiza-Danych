import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        // generate training points
        Point centre1 = new Point(0, 0);
        Point centre2 = new Point(3, 0);

        int numberOfPoints = 1000;
        PointFactory pointFactory = new PointFactory();
        List<Point> trainingPoints = new ArrayList<>();

        for (int j = 0; j < numberOfPoints; j++) {
            trainingPoints.add(pointFactory.generatePointsInsideCircle(2, centre1));
        }

//        for (int j = 0; j < numberOfPoints; j++) {
//            trainingPoints.add(pointFactory.generatePointsInsideCircle(2, centre2));
//        }

        // zapis punktów treningowych do pliku
        try (PrintWriter printWriter = new PrintWriter("punktyDane.txt")) {
            for (int i = 0; i < trainingPoints.size(); i++) {
                printWriter.println(trainingPoints.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int trials = 100;
        int centroids = 2;
        double min = -10;
        double max = 10;

        KMeansAlgorithm kma = new KMeansAlgorithm(trainingPoints, centroids, min, max);
        kma.runKMeansAlgorithm();

        kma.savePointsWithGroups();

//        KMeansWrapper kMeansWrapper = new KMeansWrapper(trainingPoints, trials, centroids, min, max);
//        kMeansWrapper.run();
//
//        System.out.println("średni błąd: " + kMeansWrapper.calculateAverageError());
//        System.out.println("odchylenie błędu: " + kMeansWrapper.calculateErrorDeviation());
//        System.out.println("minimalny błąd: " + kMeansWrapper.getMinimumError());
//        System.out.println("średnia liczba nieaktywnych centrów: " + kMeansWrapper.calculateAverageInactiveCentroids());
//        System.out.println("odchylenie nieaktywnych centrów: " + kMeansWrapper.calculateInactiveCentroidsDeviation());
    }
}
