import java.awt.*;

public class Creature extends ScreenObject{
    private Genome genome;
    private int hungerCounter;

    // constructors
    public Creature() {
        super();
        this.genome = new Genome(this);
        this.hungerCounter = Database.hungerCounter;
    }
    public Creature(Genome genome){
        super();
        this.genome = genome;
        this.hungerCounter = Database.hungerCounter;
    }
    public Creature(Color color,Coor position){
        super(color, position);
        this.genome = new Genome(this);
        this.hungerCounter = Database.hungerCounter;
    }

    // setters
    public void setGenome(Genome genome) {
        this.genome = genome;
    }
    public void setHunger(int hunger) {
        hungerCounter = hunger;
    }

    // getters
    public Genome getGenome() {
        return genome;
    }
    public int getHunger() {
        return hungerCounter;
    }
    public Coor getPrintPos() {
        Screens.SimulationWorldToScreen.setWorld(Screens.simulationPanel.getWidth(), Screens.simulationPanel.getHeight());
        int[] ans = Screens.SimulationWorldToScreen.translate(this.getPos().matrix());
        return new Coor(ans[0], ans[1]);
    }
    // hunger
    public int incrementHunger() {
        hungerCounter++;
        return hungerCounter;
    }
    public int decrementHunger() {
        hungerCounter--;
        return hungerCounter;
    }
}