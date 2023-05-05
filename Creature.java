import java.awt.*;
import java.util.ArrayList;

public class Creature extends ScreenObject{
    private Genome genome;
    private int foodEaten;
    public int repo;
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
        while(this.foodEaten*200>=Database.minimumFoodEaten){
            repo++;
            // this.foodEaten-=Database.minimumFoodEaten;
            this.foodEaten--;
            Genome genome;
            Creature creature;

            // Mutate
            if(Database.random.nextDouble(0,1)<Database.mutationChance){

                String newDNA = this.getGenome().getDNA();
                int dnaLength = this.getGenome().getDNA().length();
                // Debug
                int bitMutationTotal = (int) (dnaLength/Database.bitMutationAverage);


                while(bitMutationTotal>0){
                    int randomBit = Database.random.nextInt(dnaLength);
                    newDNA = newDNA.substring(0, randomBit) + ((Integer.parseInt(newDNA.substring(randomBit, randomBit+1))+1)%2) + newDNA.substring(randomBit+1);
                    bitMutationTotal--;
                }

                // Create the new genome with the newDNA
                genome = new Genome(this,newDNA);

                // Create the new creature with the new genome 
                creature = new Creature(genome);

                // Let genome know it's creature
                genome.creature = creature;
            }
            // Don't Mutate
            else{
                creature = this;
                creature.clearFood();
            }
            newCreatures.add(creature);
            if (newCreatures.size()>Database.generationSize) {
                newCreatures.remove(0);
            }
        }
        return newCreatures;
    }
        

    // hunger
    public void ateFood() {
        this.foodEaten++;
    }

    public void clearFood(){
        this.foodEaten = 0;
    }
}