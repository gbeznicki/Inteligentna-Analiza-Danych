import javax.naming.ldap.ManageReferralControl;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPointGenerator {

    private int quantity;
    private Point centre;
    private int radius = 2;

    public RandomPointGenerator(int quantity, Point centre) {
        this.quantity = quantity;
        this.centre = centre;
    }

    public List<Point> generate(){

        List<Point> randomPoints = new ArrayList<Point>();
        double x = 0;
        double y = 0;
        Random random = new Random();

        //generating points
        for(int i = 0; i<quantity; i++){
            //
            x = Math.sqrt(random.nextDouble()*radius);
            x *= Math.cos(Math.toRadians(random.nextInt(361)));
            x+= centre.getX();
            y = Math.sqrt(random.nextDouble()*radius);
            y *= Math.cos(Math.toRadians(random.nextInt(361)));
            y += centre.getY();
            randomPoints.add(new Point(x, y));
        }

        return randomPoints;
    }
}
