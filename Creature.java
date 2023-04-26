import java.awt.*;

public class Creature extends ScreenObject{
    private Genome genome;
    private int foodEaten;

    // constructors
    public Creature() {
        super();
        this.genome = new Genome(this);
    }
    public Creature(Genome genome){
        super();
        this.genome = genome;
    }
    public Creature(Color color,Coor position){
        super(color, position);
        this.genome = new Genome(this);
    }

    // setters
    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    // getters
    public Genome getGenome() {
        return this.genome;
    }
    public Coor getPrintPos() {
        Screens.SimulationWorldToScreen.setWorld(Screens.simulationPanel.getWidth(), Screens.simulationPanel.getHeight());
        int[] ans = Screens.SimulationWorldToScreen.translate(this.getPos().matrix());
        return new Coor(ans[0], ans[1]);
    }
    public int getFoodCount(){
        return this.foodEaten;
    }

    // Methods
    public Creature reproduce(){
        // Debug
        System.out.println("Reproduced!");
        
        Genome genome;
        Creature creature;

        // Mutate
        if(Database.random.nextDouble(0,1)<Database.mutationChance){
            String newDNA = this.getGenome().getDNA();
            // Mutate the DNA here (Needs Implemented)
            genome = new Genome(this,newDNA);

            // Create the new creature with the new genome 
            creature = new Creature(genome);

            // Let genome now it's creature
            genome.creature = creature;
        }
        // Don't Mutate
        else{
            creature = this;
        }

        return creature;
    }

    // hunger
    public void ateFood() {
        this.foodEaten++;
    }
}