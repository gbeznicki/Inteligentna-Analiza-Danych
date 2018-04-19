import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class KMeansAlgorithm {

    private List<Point> normalPoints;
    private List<Point> kPoints;
    private double precision = 1e-10;
    private double previousKError;
    private double actualKError;

    public KMeansAlgorithm(List<Point> normalPoints, List<Point> kPoints) {
        this.normalPoints = normalPoints;
        this.kPoints = kPoints;
    }

    public double calculateDistanceBetweenPoints(Point p1, Point p2) {
        double x1 = p1.getX();
        double x2 = p2.getX();
        double y1 = p1.getY();
        double y2 = p2.getY();
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public void assignToGroup() {
        int index;
        double min;
        for (int i = 0; i < normalPoints.size(); i++) {
            index = 0;
            min = Double.MAX_VALUE;
            for (int j = 0; j < kPoints.size(); j++) {
                double distance = calculateDistanceBetweenPoints(normalPoints.get(i), kPoints.get(j));
                if (distance < min) {
                    index = j;
                    min = distance;
                }
            }
            normalPoints.get(i).setGroup(index);
        }
    }

    public double mediumKError() {
        double sum = 0;
        for (int i = 0; i < normalPoints.size(); i++) {
            Point normalPoint = normalPoints.get(i);
            int group = normalPoint.getGroup();
            Point kPoint = kPoints.get(group);
            sum += calculateDistanceBetweenPoints(normalPoint, kPoint);
        }
        return sum / normalPoints.size();
    }

    public void updateKPointsPosition() {

        double avgX = 0;
        double avgY = 0;
        int counter;

        for (int i = 0; i < kPoints.size(); i++) {
            Point kPoint = kPoints.get(i);
            counter = 0;
            for (int j = 0; j < normalPoints.size(); j++) {
                Point normalPoint = normalPoints.get(j);
                if (normalPoint.getGroup() == i) {
                    avgX += normalPoint.getX();
                    avgY += normalPoint.getY();
                    counter++;
                }
            }
            if (counter > 0) {
                avgX /= counter;
                avgY /= counter;
                kPoint.setX(avgX);
                kPoint.setY(avgY);
            }
        }
    }

    // single algorithm iteration
    public void iterate() {

        //assigning normalPoints to groups
        assignToGroup();
        //calculate medium kError
        previousKError = actualKError;
        actualKError = mediumKError();
        //calculate centroids and update kPoints position
        updateKPointsPosition();
    }

    public boolean shouldEnd() {
        return ((previousKError - actualKError) / actualKError < precision);
    }

    public void runKMeansAlgorithm() {
        actualKError = Double.MAX_VALUE;
        int i = 0;
        do {
            iterate();
            saveToFile(i);
            System.out.println(actualKError);
            i++;
        } while (!shouldEnd());
    }

    private void saveToFile(int index) {

        try (PrintWriter printWriter = new PrintWriter("punkty" + index + ".txt")) {
            for (int i = 0; i < kPoints.size(); i++) {
                printWriter.println(kPoints.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
