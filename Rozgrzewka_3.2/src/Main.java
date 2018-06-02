import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length == 4) {
            String fileNameTrainingPoints = args[0];
            String fileNameTestPoints = args[1];
            int numberOfCentres = Integer.parseInt(args[2]);
            double learningRate = Double.parseDouble(args[3]);

            double step = 0.01;

            InputReader readerTraining = new InputReader(fileNameTrainingPoints);
            readerTraining.read();

            List<Point> trainingPoints = readerTraining.getTrainingPoints();

            InputReader readerTest = new InputReader(fileNameTestPoints);
            readerTest.read();

            List<Point> testPoints = readerTest.getTrainingPoints();

            double min = Range.findMin(trainingPoints);
            double max = Range.findMax(trainingPoints);

            RadialBasisFunction rbf = new RadialBasisFunction(min, max, numberOfCentres, learningRate, trainingPoints, testPoints);

//            rbf.run(step);
            RBFWrapper wrapper = new RBFWrapper(numberOfCentres, learningRate, trainingPoints, 100, testPoints);
            wrapper.run();
        }
    }
}
