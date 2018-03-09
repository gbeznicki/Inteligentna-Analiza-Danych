package program;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    String inputFileName = args[0];
	    String outputFileName = args[1];
	    InputReader ir = new InputReader(inputFileName);
	    ir.read();
	    ir.parseAllLines();
        List<DataLine> dataLines = ir.getDataLines();
        for (DataLine dl : dataLines) {
            System.out.println(dl);
        }
    }
}
