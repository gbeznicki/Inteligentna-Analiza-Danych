import java.util.Arrays;

public class DataLine {
    private static final String SEPARATOR = " ";
    private String lineString;
    private double input;
    private double desiredOutput;

    public DataLine(String lineString) {
        this.lineString = lineString;
    }

    public void parseData() {
        String[] lineParsed = lineString.split(SEPARATOR);
        input = Double.parseDouble(lineParsed[0]);
        desiredOutput = Double.parseDouble(lineParsed[1]);
    }

    public double getInput() {
        return input;
    }

    public double getDesiredOutput() {
        return desiredOutput;
    }

    @Override
    public String toString() {
        return "input = " + input + " desiredOutput = " + desiredOutput;
    }
}
