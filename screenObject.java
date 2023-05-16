import java.awt.Color;

class ScreenObject {
    private Coor position;
    private Color color;

    public ScreenObject() {
        position = new Coor(Main.loaded.worldSize/2, Main.loaded.worldSize/2);
    }

    public ScreenObject(Color color){
        this.color = color;
        position = new Coor(Main.loaded.worldSize/2, Main.loaded.worldSize/2);
    }

    public ScreenObject(Coor position){
        this.position = position;
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
    public void setPos(Coor coor){
        this.position = coor; 
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