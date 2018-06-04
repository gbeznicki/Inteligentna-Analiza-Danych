import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length == 5) {
            String fileNameTrainingPoints = args[0];
            String fileNameTestPoints = args[1];
            int numberOfCentres = Integer.parseInt(args[2]);
            double learningRate = Double.parseDouble(args[3]);
            double sigma = Double.parseDouble(args[4]);

            InputReader readerTraining = new InputReader(fileNameTrainingPoints);
            readerTraining.read();
            List<Point> trainingPoints = readerTraining.getTrainingPoints();

            InputReader readerTest = new InputReader(fileNameTestPoints);
            readerTest.read();
            List<Point> testPoints = readerTest.getTrainingPoints();

            RBFNetwork network = new RBFNetwork(trainingPoints, testPoints, numberOfCentres, learningRate, sigma);
            network.teach();
            network.saveFunction("function.txt", -4, 4, 0.01);
        }
    }
}
