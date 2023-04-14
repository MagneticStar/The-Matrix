import java.awt.Color;

import javax.swing.JPanel;
public class screenObject {
    private Coor position;
    private Color color;

    public screenObject() {
        color = Color.WHITE;
        position = new Coor(0, 0);
    }

    public screenObject(Color color, Coor position) {
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

    public Coor getPrintPos(JPanel panel) {
        Translation t = new Translation();
        t.setMat(panel.getWidth()/15.0, panel.getHeight()/15.0);
        int[] ans = t.translate(this.getPos().matrix());
        return new Coor(ans[0], ans[1]);
    }
}
