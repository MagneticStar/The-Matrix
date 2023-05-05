import java.awt.*;

public class Creature extends ScreenObject{
    private Genome genome;
    private int foodEaten;
    public int repo;
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

    // Methods
    public Creature reproduce(){
        Genome genome;
        Creature creature;

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

        // Create the new creature with the new genome 
        creature = new Creature(genome);
        // Let genome now it's creature
        genome.creature = creature;

        return creature;
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