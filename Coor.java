public class Coor {
    private int[] coor = new int[2];

    // constructors
    public Coor() {
        
    }
    public Coor(int x, int y) {
        coor[0] = x;
        coor[1] = y;
    }

    // setters
    public void setX(int x) {
        coor[0] = x;
    }
    public void setY(int y) {
        coor[1] = y;
    }

    // getters
    public int x() {
        return coor[0];
    }
    public int y() {
        return coor[1];
    }
    public int[] matrix() {
        return coor;
    }
    @Override
    public boolean equals(Object obj) {
        return ((Coor) obj).x() == coor[0] && ((Coor) obj).y() == coor[1];
    }
    public String toString() {
        return coor[0] + ", " + coor[1];
    }
}