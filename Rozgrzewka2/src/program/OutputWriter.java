package program;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OutputWriter {

    private double[] weights;
    private String fileName;

    public OutputWriter(double[] weights, String fileName) {
        this.weights = weights;
        this.fileName = fileName;
    }

    public void saveToFile() {
        try {
            PrintWriter pw = new PrintWriter(fileName);
            for (int i = 0; i < weights.length; i++) {
                pw.println(weights[i]);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
