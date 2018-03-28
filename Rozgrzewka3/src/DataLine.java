import java.util.Arrays;

public class DataLine {
    private static final String SEPARATOR = ";";
    private String lineString;
    private double[] inputs;
    private double[] desiredOutputs;

    public DataLine(String lineString, int numberOfInputs, int numberOfOutputs) {
        this.lineString = lineString;
        inputs = new double[numberOfInputs];
        desiredOutputs = new double[numberOfOutputs];
    }

    public void parseData() {
        String[] lineParsed = lineString.split(SEPARATOR);
        double[] lineParsedDouble = new double[lineParsed.length + 1];
//        line = new double[lineParsed.length + 1];
        // wstawiamy x0 = 1 dla w0
        lineParsedDouble[0] = 1.0;
        for (int i = 1; i < lineParsedDouble.length; i++) {
            lineParsedDouble[i] = Double.parseDouble(lineParsed[i - 1]);
        }
        // wstawiamy x do tablicy inputs
        inputs = Arrays.copyOfRange(lineParsedDouble, 0, inputs.length);
        // wstawyamy y do tablicy desiredOutputs
        desiredOutputs = Arrays.copyOfRange(lineParsedDouble, inputs.length, lineParsedDouble.length);
    }

//    public double[] getLine() {
//        return line;
//    }

    public double[] getInputs() {
//        double[] result = new double[line.length - 1];
//        for (int i = 0; i < result.length; i++) {
//            result[i] = line[i];
//        }
//        return result;
        return inputs;
    }

    public double[] getDesiredOutputs() {
//        return line[line.length - 1];
        return desiredOutputs;
    }

    @Override
    public String toString() {
        String str = "";
//        for (int i = 0; i < line.length; i++) {
//            str += line[i] + "\t";
//        }
        str += "Inputs: ";
        for (int i = 0; i < inputs.length; i++) {
            str += inputs[i] + "\t";
        }
        str += "Outputs: ";
        for (int i = 0; i < desiredOutputs.length; i++) {
            str += desiredOutputs[i] + "\t";
        }
        return str;
    }
}
