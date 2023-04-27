import java.awt.*;
import java.util.ArrayList;

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
    public ArrayList<Creature> reproduce(){
        // Debug
        // System.out.println("Reproduced!");

        ArrayList<Creature> newCreatures = new ArrayList<Creature>();
        // Make this.foodeaten % Database.minimumFoodEaten creatures
        while(this.foodEaten>=Database.minimumFoodEaten){
            this.usedFood();

            Genome genome;
            Creature creature;

            // Mutate
            if(Database.random.nextDouble(0,1)<Database.mutationChance){
                // Debug
                int mutationCounter = 0;
                
                String newDNA = "";
                double currentMutationChance = Database.mutationChance;
                for(char bit : this.getGenome().getDNA().toCharArray()){
                    if(Database.random.nextDouble(0,1)<currentMutationChance){
                        // Mutate the DNA
                        newDNA+=(bit+1)%2;
                        currentMutationChance = Database.mutationChance;
                        mutationCounter++;
                    }
                    else{
                        // Don't mutate the DNA but increase the chance of mutation
                        newDNA+=bit;
                        currentMutationChance += Database.mutationChance;
                    }
                }

                // Debug
                // System.out.println("Old DNA: "+this.getGenome().getDNA());
                // System.out.println("New DNA"+this.getGenome().getDNA());
                System.out.println("Number of bits mutated: "+mutationCounter);

                // Mutate the DNA here (Needs Implemented)
                genome = new Genome(this,newDNA);

                // Create the new creature with the new genome 
                creature = new Creature(genome);

                // Let genome know it's creature
                genome.creature = creature;
            }
            // Don't Mutate
            else{
                creature = this;
            }
            newCreatures.add(creature);
        }
        return newCreatures;
    }
        

    // hunger
    public void ateFood() {
        this.foodEaten++;
    }

    public void usedFood(){
        this.foodEaten-=Database.minimumFoodEaten;
    }
}