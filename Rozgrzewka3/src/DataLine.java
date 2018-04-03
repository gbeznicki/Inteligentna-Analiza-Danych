import java.util.Arrays;

public class DataLine {
    private static final String SEPARATOR = " ";
    private String lineString;
    private double[] inputs;
    private double[] desiredOutputs;
    boolean bias;

    public DataLine(String lineString, int numberOfInputs, int numberOfOutputs, boolean bias) {
        this.lineString = lineString;
        this.bias = bias;
        // jeżeli stosujemy bias, potrzebujemy o 1 miejsce w tablicy więcej na x0 = 1
        if (this.bias) {
            inputs = new double[numberOfInputs + 1];
        }
        else {
            inputs = new double[numberOfInputs];
        }
        desiredOutputs = new double[numberOfOutputs];
    }

    public void parseData() {
        String[] lineParsed = lineString.split(SEPARATOR);
        double[] lineParsedDouble;

        // jeżeli stosujemy bias, dopisujemy na początku tablicy inputs x0 = 1
        if (bias) {
            lineParsedDouble = new double[lineParsed.length + 1];
            lineParsedDouble[0] = 1.0;

            // wpisujemy do tablicy wartości przekazane w linijce danych z pliku
            for (int i = 1; i < lineParsedDouble.length; i++) {
                lineParsedDouble[i] = Double.parseDouble(lineParsed[i - 1]);
            }
        }
        else {
            lineParsedDouble = new double[lineParsed.length];

            // wpisujemy do tablicy wartości przekazane w linijce danych z pliku
            for (int i = 0; i < lineParsedDouble.length; i++) {
                lineParsedDouble[i] = Double.parseDouble(lineParsed[i]);
            }
        }

        // wstawiamy x do tablicy inputs
        inputs = Arrays.copyOfRange(lineParsedDouble, 0, inputs.length);

        // wstawyamy y do tablicy desiredOutputs
        desiredOutputs = Arrays.copyOfRange(lineParsedDouble, inputs.length, lineParsedDouble.length);
    }

    public double[] getInputs() {
        return inputs;
    }

    public double[] getDesiredOutputs() {
        return desiredOutputs;
    }

    @Override
    public String toString() {
        String str = "";
        str += "Inputs: ";
        for (int i = 0; i < inputs.length; i++) {
            str += inputs[i] + "\t";
        }
        str += "\tOutputs: ";
        for (int i = 0; i < desiredOutputs.length; i++) {
            str += desiredOutputs[i] + "\t";
        }
        return str;
    }
}
