import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
    private List<DataLine> dataLines;
    private File dataFile;
    private String fileName;

    public InputReader(String fileName) {
        dataFile = new File(fileName);
        this.fileName = fileName;
        dataLines = new ArrayList<>();
    }

    public void read() {
        if (dataFile.isFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    dataLines.add(new DataLine(line));
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
