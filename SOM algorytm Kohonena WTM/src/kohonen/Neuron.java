package kohonen;

public class Neuron implements Comparable<Neuron> {
    private double x;
    private double y;
    private double distance;
    private int punishment;
    private int id;

    public Neuron(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        distance = Double.MAX_VALUE;
        punishment = 0;
    }

    public Neuron(Neuron n) {
        this.x = n.x;
        this.y = n.y;
        distance = Double.MAX_VALUE;
        punishment = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPunishment(int punishment) {
        this.punishment = punishment;
    }

    public int getId(){
        return id;
    }

    public void decrementPunishment(){
        if(punishment!=0){
            punishment--;
        }
    }

    public int getPunishment() {
        return punishment;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    @Override
    public int compareTo(Neuron o) {
        return Double.compare(distance, o.distance);
    }
}
