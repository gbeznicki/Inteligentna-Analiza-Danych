import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Program {
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

        //Losowanie 2 koła czarnych punktów
        for (int i = 0; i < quantity; i++) {
            normalPointList.add(pointFactory.generatePointsInsideCircle(2, centre2));
        }

        for (int i = 0; i < k; i++) {
            kPointList.add(pointFactory.generateRandomPoint(-10, 10, 0));
        }

        KMeansAlgorithm kMeansAlgorithmDoer = new KMeansAlgorithm(normalPointList, kPointList);

        kMeansAlgorithmDoer.runKMeansAlgorithm();

    }
}
