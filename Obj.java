import java.awt.Color;
public class Obj {
    private Coor position;
    private Color color;

    public Obj() {
        color = Color.WHITE;
        position = new Coor(0.0, 0.0);
    }

    public Obj(Color color, Coor position) {
        this.position = position;
        this.color = color;
    }

    // setters 
    public void setPosX(double x) {
        position.setX(x);
    }

    public void setPosY(double y) {
        position.setY(y);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // getters
    public Coor getPos() {
        return position;
    }

    public double getPosY() {
        return position.y(); 
    }

    public double getPosX() {
        return position.x(); 
    }
    
    public Color getColor() {
        return color;
    }

    public Coor getPrintPos() {
        Translation t = new Translation();
        t.setMat(Main.frame.getWidth()/1000.0, Main.frame.getHeight()/1000.0);
        double[] ans = t.translate(this.getPos().matrix());
        return new Coor(ans[0], ans[1]);
    }



}
