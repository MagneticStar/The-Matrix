import java.util.BitSet;

public class Creature extends ScreenObject implements Cloneable{
    private Genome genome;
    private int foodEaten;
    private int foodEatenAll;
    private int moveCount;

    // constructors
    public Creature() {
        super();
        this.genome = new Genome();
    }
    public Creature(Genome genome){
        super();
        this.genome = genome;
    }
    public Creature(BitSet DNA) {
        super();
        this.genome = new Genome(DNA);
    }

    // setters
    public void setGenome(Genome genome) {
        this.genome = genome;
    }
    public void moved() {
        moveCount++;
    }

    // getters
    public Genome getGenome() {
        return this.genome;
    }
    
    public int getFoodCount(){
        return foodEaten;
    }
    public int getFoodCountAll() {
        return foodEatenAll;
    }
    public int getMoveCount() {
        return moveCount;
    }

    // Methods
    public ArrayList<Creature> reproduce(){
        // Debug
        // System.out.println("Reproduced!");

        ArrayList<Creature> newCreatures = new ArrayList<Creature>();
        // Make this.foodeaten % Simulation.simulation.minimumFoodEaten creatures
        while(this.foodEaten>=Simulation.simulation.minimumFoodEaten){
            this.foodEaten-=Simulation.simulation.minimumFoodEaten;

            Genome genome;
            Creature creature;

            // Mutate
            if(Simulation.simulation.random.nextDouble(0,1)<Simulation.simulation.mutationChance){

                String newDNA = this.getGenome().getDNA();
                int dnaLength = this.getGenome().getDNA().length();
                // Debug
                int bitMutationTotal = (int) (dnaLength/Simulation.simulation.bitMutationAverage);


                while(bitMutationTotal>0){
                    int randomBit = Simulation.simulation.random.nextInt(dnaLength);
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
        }
    }
        

    // hunger
    public void ateFood() {
        foodEaten++;
        foodEatenAll++;
    }

    public void clearFood(){
        foodEaten = 0;
    }
    @Override
    public String toString() {
        return genome.getDNA().toString();
    }
    public Object clone() {
        try {
            Creature clone = (Creature)super.clone();
        clone.genome = (Genome)genome.clone();
        clone.foodEaten = 0;
        clone.foodEatenAll = this.foodEatenAll;
        clone.moveCount = 0;
        return clone;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}