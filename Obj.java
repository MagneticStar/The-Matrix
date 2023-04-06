import java.awt.Color;
public class Obj {
    private Coor position;
    private Color color;

    public Obj() {
        color = Color.WHITE;
        position = new Coor(0, 0);
    }

    public Obj(Color color, Coor position) {
        this.position = position;
        this.color = color;
    }

    // setters 
    public void setPosX(int x) {
        position.setX(x);
    }

    public void setPosY(int y) {
        position.setY(y);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // getters
    public Coor getPos() {
        return position;
    }

    public int getPosY() {
        return position.y(); 
    }

    public int getPosX() {
        return position.x(); 
    }
    
    public Color getColor() {
        return color;
    }

    public Coor getPrintPos() {
        Translation t = new Translation();
        t.setMat(Frame.simPanel.getWidth()/10.0, Frame.simPanel.getHeight()/10.0);
        int[] ans = t.translate(this.getPos().matrix());
        return new Coor(ans[0], ans[1]);
    }
}
