import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
    private List<DataLine> dataLines;
    private File dataFile;
    private String fileName;
    private int numberOfInputs;
    private int numberOfOutputs;
    private boolean bias;

    public InputReader(String fileName, int numberOfInputs, int numberOfOutputs, boolean bias) {
        dataFile = new File(fileName);
        this.fileName = fileName;
        dataLines = new ArrayList<>();
        this.numberOfInputs = numberOfInputs;
        this.numberOfOutputs = numberOfOutputs;
        this.bias = bias;
    }

    public void read() {
        if (dataFile.isFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    dataLines.add(new DataLine(line, numberOfInputs, numberOfOutputs, bias));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseAllLines() {
        for (DataLine dl : dataLines) {
            dl.parseData();
        }
    }

    public List<DataLine> getDataLines() {
        return dataLines;
    }

}
