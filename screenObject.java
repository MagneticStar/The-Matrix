import java.awt.Color;

// default class for all objects that can be drawn to the screen
class ScreenObject {
    private Coor position;
    private Color color;

    public ScreenObject() {
        position = new Coor(Main.loadedDatabase.worldSize/2, Main.loadedDatabase.worldSize/2);
    }

    public ScreenObject(Color color){
        this.color = color;
        position = new Coor(Main.loadedDatabase.worldSize/2, Main.loadedDatabase.worldSize/2);
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

    public static Coor getPrintPos(int x, int y) {
        Screens.SimulationWorldToScreen.setWorld(Screens.simulationPanel.getWidth(), Screens.simulationPanel.getHeight());
        int[] ans = Screens.SimulationWorldToScreen.translate(new int[]{x,y});
        return new Coor(ans[0], ans[1]);
    }
}