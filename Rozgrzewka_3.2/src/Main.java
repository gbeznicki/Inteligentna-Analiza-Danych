import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length == 3) {
            String fileName = args[0];
            int numberOfCentres = Integer.parseInt(args[1]);
            double learningRate = Double.parseDouble(args[2]);

            double step = 0.01;

            InputReader reader = new InputReader(fileName);
            reader.read();

            List<Point> trainingPoints = reader.getTrainingPoints();

            double min = Range.findMin(trainingPoints);
            double max = Range.findMax(trainingPoints);

            RadialBasisFunction rbf = new RadialBasisFunction(min, max, numberOfCentres, learningRate, trainingPoints);

            rbf.run(step);
        }
    }
}
