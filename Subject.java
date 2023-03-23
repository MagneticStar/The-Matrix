import java.awt.*;

public class Subject{
    private Genome genome;
    private int positionx;
    private int positiony;
    private Color color;

    public Subject(Genome genome, int positionx, int positiony) {
        this.genome = genome;
        this.positionx = positionx;
        this.positiony = positiony;
    }

    
    public int getPosX() {
        return positionx;
    }
    public int getPosY() {
        return positiony;
    }
    


}