import java.util.List;

public class Main {

    public static void main(String[] args) {
	    String fileName = "in3.txt";
	    InputReader ir = new InputReader(fileName);
	    ir.read();
	    ir.parseAllLines();
        List<DataLine> dataLines = ir.getDataLines();
        for (DataLine dl : dataLines) {
            System.out.println(dl);
        }
    }
}
