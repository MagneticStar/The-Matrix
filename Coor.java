public class Coor {
    private int x,y;

    // constructors
    public Coor() {
        
    }
    public Coor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // setters
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    // getters
    public int x() {
        return this.x;
    }
    public int y() {
        return this.y;
    }
    public int[] matrix() {
        return new int[]{x,y};
    }
    @Override
    public boolean equals(Object o) {
 
        // If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of Coor or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Coor)) {
            return false;
        }
         
        // typecast o to Coor so that we can compare data members 
        Coor other = (Coor) o;
         
        // Compare the data members and return accordingly 
        return this.x == other.x && this.y == other.y;
    }
    public String toString() {
        return x + ", " + y;
    }
}