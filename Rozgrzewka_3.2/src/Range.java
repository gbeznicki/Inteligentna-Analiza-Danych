import java.util.List;

public class Range {
    public static double findMin(List<Point> numbers) {
        return numbers.stream().mapToDouble(p -> p.getX()).min().getAsDouble();
    }

    public static double findMax(List<Point> numbers) {
        return numbers.stream().mapToDouble(p -> p.getX()).max().getAsDouble();
    }

    public static double divide(double start, double end, int parts) {
        return (end - start) / (parts + 1);
    }

    public static double length(double start, double end) {
        return end - start;
    }
}
