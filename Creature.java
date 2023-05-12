import java.awt.*;
import java.util.BitSet;

public class Creature extends ScreenObject{
    private Genome genome;
    private int foodEaten;
    private int foodEatenAll;
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
        return foodEaten;
    }
    public int getFoodCountAll() {
        return foodEatenAll;
    }

    // Methods
    public Creature[] reproduce(){

        Creature[] newCreatures = new Creature[Database.generationSize];
        int index = 0;
        while(this.foodEaten>=Database.minimumFoodEaten && index < Database.generationSize - 1){
            foodEaten--;
            for (int i = 0; i < Database.repoductionPerCreature; i++) {
                Genome newGenome;
                Creature newCreature;

                // Mutate
                if(Database.random.nextDouble(0,1)<Database.mutationChance){

                    BitSet newDNA = getGenome().getDNA();
                    int dnaLength = getGenome().getDNA().length();
                    // Debug
                    int bitMutationTotal = (int) (dnaLength/Database.bitMutationAverage);


                    // while(bitMutationTotal>0){
                    //     int randomBit = Database.random.nextInt(dnaLength);
                    //     newDNA = newDNA.substring(0, randomBit) + ((Integer.parseInt(newDNA.substring(randomBit, randomBit+1))+1)%2) + newDNA.substring(randomBit+1);
                    //     bitMutationTotal--;
                    // }

                    // Create the new genome with the newDNA
                    newGenome = new Genome(this,newDNA);

                    // Create the new creature with the new genome 
                    newCreature = new Creature(genome);

                    // Let genome know it's creature
                    newGenome.creature = newCreature;
                }
                // Don't Mutate
                else{
                    newCreature = new Creature(genome);
                }
                newCreatures[index] = newCreature;
                index++;
            }
        }
        return newCreatures;
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
}