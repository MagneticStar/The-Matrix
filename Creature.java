import java.awt.*;

public class Creature extends ScreenObject{
    private Genome genome;
    
    public Creature() {
        super();
        this.genome = new Genome(this);
    }
    
    public Creature(Color color,Coor position){
        super(color, position);
        this.genome = new Genome(this);
    }

    public Creature(Genome genome){
        super();
        this.genome = genome;
    }

    // setters
    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    // getters
    public Genome getGenome() {
        return genome;
    }
    public Coor getPrintPos() {
        Screens.SimulationWorldToScreen.setWorld(Screens.simulationPanel.getWidth(), Screens.simulationPanel.getHeight());
        int[] ans = Screens.SimulationWorldToScreen.translate(this.getPos().matrix());
        return new Coor(ans[0], ans[1]);
    }
}