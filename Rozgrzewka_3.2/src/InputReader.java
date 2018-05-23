import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
    private static final String SEPARATOR = " ";

    private List<Point> trainingPoints;
    private File dataFile;
    private String fileName;

    public InputReader(String fileName) {
        dataFile = new File(fileName);
        this.fileName = fileName;
        trainingPoints = new ArrayList<>();
    }

    public void read() {
        if (dataFile.isFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(SEPARATOR);
                    double x = Double.parseDouble(values[0]);
                    double y = Double.parseDouble(values[1]);

                    trainingPoints.add(new Point(x, y));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Point> getTrainingPoints() {
        return trainingPoints;
    }
}
