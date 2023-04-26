import java.awt.Color;

import javax.xml.crypto.Data;
class ScreenObject {
    private Coor position;
    private Color color;

    public ScreenObject() {
        color = Color.WHITE;
        position = new Coor(Database.worldSize/2, Database.worldSize/2);
    }

    public ScreenObject(Color color, Coor position) {
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