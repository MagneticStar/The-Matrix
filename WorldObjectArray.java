import java.security.InvalidParameterException;
import java.awt.Color; 

public class WorldObjectArray {
    /**
     * Contains all creature
     */
    private Creature[] creatures; 
    /**
     * Contains the number of creatures at any given location
     */
    private int[][] creatureLocations;  
    /**
     * The number of creatures current in creatures
     */
    private int creaturesLength;
    
    /**
     * Contains all the locations for each food
     */
    private Coor[] food;
    /**
     * Whether or not a food is at any given location
     */
    private int[][] foodLocations;
    /**
     * How much food is there currently 
     */
    private int foodLength;

    /**
     * [tickNumber][x][y] gives the color to print at any given position on any given tick
     */
    private Color[][][] printColorsForAllTicks;
    /**
     * [tickNumber][x][y] whether or not any given position has a food on any given tick
     */
    private boolean[][][] isFoodBooleansForAllTicks;
    /**
     * [tickNumber] the number of food on any given tick
     */
    private int[] foodCountForAllTicks;

     /**
     * Contains all objects that are contained in the simulation world
     * @param worldSize The size of the simulation world
     * @param generationLength How many ticks in a single generation
     * @param generationSize How many creatures are in each generation
     * @param startingFoodCount How much food is there at the start of each generation
     */
    public WorldObjectArray(int generationSize, int startingFoodCount, int worldSize, int generationLength){
        this.creatures = new Creature[generationSize];
        this.creatureLocations = new int[worldSize][worldSize];
        this.creaturesLength = 0;

        this.food = new Coor[startingFoodCount];
        this.foodLocations = new int[worldSize][worldSize];
        this.foodLength = 0;

        this.printColorsForAllTicks = new Color[generationLength][worldSize][worldSize];
        this.isFoodBooleansForAllTicks = new boolean[generationLength][worldSize][worldSize];
        this.foodCountForAllTicks = new int[generationLength];
    }

    /////////////////////////////////////////////////////
    // Mutators //  Mutators //  Mutators //  Mutators //
    /////////////////////////////////////////////////////

    /**
     * Saves the current food and creature lists for replay
     */
    public void save(){
        int tick = Main.loaded.currentGenerationTick;

        for(Creature creature : this.creatures){
            Coor position = creature.getPos();
            this.printColorsForAllTicks[tick][position.x()][position.y()] = creature.getColor(); 
            this.isFoodBooleansForAllTicks[tick][position.x()][position.y()] = false;
        }

        for(Coor position : food){
            // Only save the food position if there is no creature there
            if(this.printColorsForAllTicks[tick][position.x()][position.y()] == null){
                this.printColorsForAllTicks[tick][position.x()][position.y()] = Main.loaded.FOOD_COLOR; 
                this.isFoodBooleansForAllTicks[tick][position.x()][position.y()] = true;   
            } 
        }

        this.foodCountForAllTicks[tick] = foodLength;
    }

    // Mutators

    /**
     * Adds the specified screenObject to the worldSpace
     * @param screenObject A screenObject that can be shown in the simulation world
     * @param type Either "creature" or "food"
     * @throws InvalidParameterException If the type parameter is invalid
     */
    public void add(ScreenObject screenObject, String type){
        Coor position = screenObject.getPos();
        switch (type.toLowerCase()) {
            case "creature": 
            creatures[creaturesLength++] = (Creature) screenObject; // Increments creaturesLength after using it for the index
            creatureLocations[position.x()][position.y()]++; 
            break;

            case "food": 
            food[foodLength++] = position; // Increments foodLength after using it for the index
            foodLocations[position.x()][position.y()]++;
            break;

            default: throw new InvalidParameterException();
        }
    }

    /**
     * Removes the specified food from the world
     * @param location The position of the food to be removed
     */
    public void removeFood(Coor location){
        // No Food at location
        if(foodLocations[location.x()][location.y()] == 0){
            return;
        }

        int index = -1;

        for(int i=0; i<foodLength; i++){
            if(food[i] == location){
                index = i;
                break;
            }
        }

        for(int i=index; i<foodLength-1; i++){
            food[i] = food[i+1];
        }

        foodLength--;
    }

    /**
     * Removes all food and creatures from this WorldObjectArray
     */
    public void removeAll(){
        this.creatures = new Creature[this.creatures.length];
        this.creatureLocations = new int[this.creatureLocations.length][this.creatureLocations[0].length];
        this.creaturesLength = 0;

        this.food = new Coor[this.food.length];
        this.foodLocations = new int[this.foodLocations.length][this.foodLocations[0].length];
        this.foodLength = 0;

        this.printColorsForAllTicks = new Color[this.printColorsForAllTicks.length][this.printColorsForAllTicks[0].length][this.printColorsForAllTicks[0][0].length];
        this.isFoodBooleansForAllTicks = new boolean[this.isFoodBooleansForAllTicks.length][this.isFoodBooleansForAllTicks[0].length][this.isFoodBooleansForAllTicks[0][0].length];
        this.foodCountForAllTicks = new int[this.foodCountForAllTicks.length];
    }

    /**
     * @param from The Coor to move the creature from
     * @param to The Coor to move the creature to
     */
    public void moveCreature(Coor from, Coor to){
        creatureLocations[from.x()][from.y()]--;
        creatureLocations[to.x()][to.y()]++;
    }

    //////////////////////////////////////////////
    // Getters // Getters // Getters // Getters //
    //////////////////////////////////////////////

    /**
     * @param index The index of the Creature in the Creature array
     * @return The Creature at the specified index or null if the index is invalid
     */
    public Creature getCreature(int index){
        if(index >= creaturesLength || index < 0){
            return null;
        }

        return this.creatures[index];
    }

    /**
     * @param index The index of the Creature in the Creature array
     * @return The number of Creatures at the specified index
     */
    public int getCreature(Coor position){
        return this.creatureLocations[position.x()][position.y()];
    }

    /**
     * @return A copy of the Creature locations array
     */
    public int[][] getCreatureLocationsArrayCopy(){
        return this.creatureLocations.clone();
    }

    /**
     * @return The current amount of food in the simulation space
     */
    public int getFoodCount(){
        return this.foodLength;
    }

    /**
     * @return The current amount of food in the simulation space at a certain tick
     * @param tick The to check
     */
    public int getFoodCountAtTick(int tick){
        return this.foodCountForAllTicks[tick];
    }

    /**
     * @return The Color to print
     * @param tick The tick to check
     * @param position The position to check at
     */
    public Color getPrintColor(int tick, Coor position){
        return this.printColorsForAllTicks[tick][position.x()][position.y()];
    }

    /**
     * @return Whether or not it is a food
     * @param tick The tick to check
     * @param position The position to check at
     */
    public boolean isFoodAtTick(int tick, Coor position){
        return this.isFoodBooleansForAllTicks[tick][position.x()][position.y()];
    }

    /**
     * @param location The Coor to check
     * @return Whether a food is at location
     */
    public boolean isFoodAtLocation(Coor location){
        return this.foodLocations[location.x()][location.y()] == 1;
    }

    /**
     * @param index The index of the Food in the Food array
     * @return The Food at the specified index
     * @throws IndexOutOfBoundsException If the given index is invalid
     */
    public Coor getFood(int index){
        if(index >= foodLength){
            throw new IndexOutOfBoundsException();
        }

        return this.food[index];
    }

    /**
     * @return A copy of the Food locations array
     */
    public int[][] getFoodLocationsArrayCopy(){
        return this.foodLocations.clone();
    }
}