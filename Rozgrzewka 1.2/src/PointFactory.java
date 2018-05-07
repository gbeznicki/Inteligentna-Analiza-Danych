import java.util.Random;

public class PointFactory {

    private Random random;

    public PointFactory() {
        random = new Random();
    }

    public Point generatePointsInsideCircle(int radius, Point centre) {
        double r = Math.sqrt(random.nextDouble()) * radius;
        double theta = random.nextDouble() * 2 * Math.PI;

        double x = r * Math.cos(theta) + centre.getX();
        double y = r * Math.sin(theta) + centre.getY();

        return new Point(x, y);
    }

    public Point generateRandomPoint(double min, double max, int group) {
        double x = random.nextDouble() * (max - min) + min;
        double y = random.nextDouble() * (max - min) + min;

        return new Point(x, y, group);
    }

}
