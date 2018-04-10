import java.sql.SQLOutput;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        // TODO 2 okręgi, RPG, file printer, kmeansAlgorithm

        Point centre1 = new Point(-3, 0);
        Point centre2 = new Point(3, 0);

        int quantity = 10000;

        RandomPointGenerator randomPointGenerator = new RandomPointGenerator(quantity, centre1);
        List<Point> pointList = randomPointGenerator.generate();

        for(int i =0; i<quantity; i++){

            System.out.println(pointList.get(i).getX()+ " "+ pointList.get(i).getY());
        }

    }
}
