import java.util.List;

public class Main {

    public static void main(String[] args) {
        String fileName = args[0];
        int numberOfCentres = 10;
        double learningRate = 0.0005;
        InputReader reader = new InputReader(fileName);
        reader.read();
        reader.parseAllLines();

        List<DataLine> dataLines = reader.getDataLines();



        RadialBasisFunction function = new RadialBasisFunction(numberOfCentres, learningRate, reader.getDesiredOutputs(), reader.getInputs());
	    function.run(-4, 4, 0.01);
    }
}
