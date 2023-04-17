import java.awt.Color;
<<<<<<< HEAD
public class screenObject {
=======

import javax.swing.JPanel;
public abstract class screenObject {
>>>>>>> 429301190a3d5a229253084f5c82d739d3b7cef3
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
}
