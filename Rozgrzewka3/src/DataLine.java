public class DataLine {
    private double[] line;
    private String lineString;

    public DataLine(String lineString) {
        this.lineString = lineString;
    }

    public void parseData() {
        String[] lineParsed = lineString.split(";");
        line = new double[lineParsed.length + 1];
        line[0] = 1.0;
        for (int i = 1; i < line.length; i++) {
            line[i] = Double.parseDouble(lineParsed[i - 1]);
        }
    }

    public double[] getLine() {
        return line;
    }

    public double[] getVectorX() {
        double[] result = new double[line.length - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = line[i];
        }
        return result;
    }

    public double getY() {
        return line[line.length - 1];
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < line.length; i++) {
            str += line[i] + "\t";
        }
        return str;
    }
}
