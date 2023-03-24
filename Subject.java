import java.awt.*;

public class Subject{
    private Genome genome;
    private Coor position;
    private Color color;
    
    public Subject() {
        this.genome = new Genome();
        this.color = new Color(genome.getColor()[0]);
        // this.position = position; Random position from list of available positions
    }
    public Subject(Color color,Coor position){
        this.genome = new Genome();
        this.color = color;
        this.position = position;
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
    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    // getters
    public double getPosX() {
        return position.x();
    }
    public double getPosY() {
        return position.y();
    }
    public Color getColor() {
        return color;
    }
    public Genome getGenome() {
        return genome;
    }
    
}