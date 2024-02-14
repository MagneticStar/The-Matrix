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
    public Creature[] reproduce() {
        try {
            int howManyRepoduce = 1;
        Creature[] copies = new Creature[howManyRepoduce];
        for (int i = 0; i < howManyRepoduce; i++) {
            copies[i] = (Creature)this.clone();
        }
        return copies;
        } catch (Exception e) {
            System.out.println(e);
            return null;
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