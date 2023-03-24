public class Coor {
    private double[][] coor = new double[1][2];

    public Coor(double x, double y) {
        coor[0][0] = x;
        coor[0][1] = y;
    }

    // setters
    public void setX(double x) {
        coor[0][0] = x;
    }
    public void setY(double y) {
        coor[0][0] = y;
    }

    // getters
    public double x() {
        return coor[0][0];
    }
    public double y() {
        return coor[0][1];
    }
    public double[][] matrix() {
        return coor;
    }
}
