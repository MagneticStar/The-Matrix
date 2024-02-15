public class Sensor extends Neuron{

    public SensorMethod sensorMethod; 
    public static int numberOfSensorMethods = 8; // Update this when creating new Sensor methods
    public int methodID;

    public Sensor(int methodID) {
        super("Sensor");
        this.methodID = methodID%(numberOfSensorMethods);
        switch(this.methodID){
            case 0: this.sensorMethod = Sensor::nearestFoodDistance; break;
            case 1: this.sensorMethod = Sensor::detectFoodXDirection; break;
            case 2: this.sensorMethod = Sensor::detectFoodYDirection; break;
            case 3: this.sensorMethod = Sensor::nearestCreatureDistance; break;
            case 4: this.sensorMethod = Sensor::detectCreatureXDirection; break;
            case 5: this.sensorMethod = Sensor::detectCreatureYDirection; break;
            case 6: this.sensorMethod = Sensor::Oscillator; break;
            case 7: this.sensorMethod = Sensor::random; break;
        }
    }
    

    public interface SensorMethod {
        double invoke(Creature creature) throws Exception;
    }
    
    ////////////////////////////////////////////////////////
    // SENSOR METHODS // SENSOR METHODS // SENSOR METHODS //
    ////////////////////////////////////////////////////////

    public static double nearestFoodDistance(Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.foodLocations);
        double distance = distance(coorOfFoundObject, creature);
        // returns a value between 1 and 0
        return 1-(distance/Main.loaded.worldSize);
    }

    private static double detectFoodXDirection (Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.foodLocations);
        return directionX(coorOfFoundObject[0], creature);
    }

    private static double detectFoodYDirection (Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.foodLocations);
        return directionY(coorOfFoundObject[1], creature);
    }

    private static double nearestCreatureDistance(Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.creatureLocations);
        double distance = distance(coorOfFoundObject, creature);
              // returns a value between 1 and 0
        return 1-(distance/Main.loaded.worldSize);
    }

    private static double detectCreatureXDirection (Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.creatureLocations);
        return directionX(coorOfFoundObject[0], creature);
    }

    private static double detectCreatureYDirection (Creature creature) throws Exception{
        int[] indexOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.creatureLocations);
        return directionY(indexOfFoundObject[1], creature);
    }
    
    private static double Oscillator(Creature creature) {
        if(Main.loaded.currentGenerationTick%creature.getGenome().getOscillatorPeriod()==0){
            return 1;
        }
        else{
            return -1;
        }
    }

    private static double random(Creature creature){
        return Main.loaded.random.nextDouble(-1,1);
    }

    ////////////////////////////////////////////////////////
    // SENSOR METHOD ASSISTORS // SENSOR METHOD ASSISTORS // 
    ////////////////////////////////////////////////////////

    public static int[] findNearestObject(int centerX, int centerY, int[][] objectLocations) throws Exception{
        // Search logic
        if(objectLocations[centerX][centerY] > 0){ // Check Center
            return new int[]{centerX,centerY};
        }
        for(int i=0; i<Main.loaded.searchDepth; i++){
            if(objectLocations[centerX][(centerY+i)%Main.loaded.worldSize] > 0){ // Check Above
                return new int[]{centerX,(centerY+i)%Main.loaded.worldSize};
            }
            
            if(objectLocations[((centerX+i)%Main.loaded.worldSize)%Main.loaded.worldSize][centerY] > 0){ // Check Right
                return new int[]{(centerX+i)%Main.loaded.worldSize,centerY};
            }
            
            if(objectLocations[((centerX-i)+Main.loaded.worldSize)%Main.loaded.worldSize][centerY] > 0){ // Check Left
                return new int[]{((centerX-i)+Main.loaded.worldSize)%Main.loaded.worldSize,centerY};
            }
             
            if(objectLocations[centerX][((centerY-i)+Main.loaded.worldSize)%Main.loaded.worldSize] > 0){ // Check Below
                return new int[]{centerX,((centerY-i)+Main.loaded.worldSize)%Main.loaded.worldSize};
            }
            
            for(int j=1; j<i+1;j++){
                if(objectLocations[(centerX+j)%Main.loaded.worldSize][(centerY+i)%Main.loaded.worldSize] > 0){ // Check Above Right
                    return new int[]{(centerX+j)%Main.loaded.worldSize,(centerY+i)%Main.loaded.worldSize};
                }
                if(objectLocations[((centerX-j)+Main.loaded.worldSize)%Main.loaded.worldSize][(centerY+i)%Main.loaded.worldSize] > 0){ // Check Above Left
                    return new int[]{((centerX-j)+Main.loaded.worldSize)%Main.loaded.worldSize,(centerY+i)%Main.loaded.worldSize};
                }  
                if(objectLocations[(centerX+j)%Main.loaded.worldSize][((centerY-i)+Main.loaded.worldSize)%Main.loaded.worldSize] > 0){ // Check Below Right
                    return new int[]{(centerX+j)%Main.loaded.worldSize,((centerY-i)+Main.loaded.worldSize)%Main.loaded.worldSize};
                } 
                if(objectLocations[((centerX-j)+Main.loaded.worldSize)%Main.loaded.worldSize][((centerY-i)+Main.loaded.worldSize)%Main.loaded.worldSize] > 0){ // Check Below Left
                    return new int[]{((centerX-j)+Main.loaded.worldSize)%Main.loaded.worldSize,((centerY-i)+Main.loaded.worldSize)%Main.loaded.worldSize};
                }

                if(j < i){
                    if(objectLocations[(centerX+i)%Main.loaded.worldSize][(centerY+j)%Main.loaded.worldSize] > 0){ // Check Right Above
                        return new int[]{(centerX+i)%Main.loaded.worldSize,(centerY+j)%Main.loaded.worldSize};
                    } 
                    if(objectLocations[(centerX+i)%Main.loaded.worldSize][((centerY-j)+Main.loaded.worldSize)%Main.loaded.worldSize] > 0){ // Check Right Below
                        return new int[]{(centerX+i)%Main.loaded.worldSize,((centerY-j)+Main.loaded.worldSize)%Main.loaded.worldSize};
                    } 
                    if(objectLocations[((centerX-i)+Main.loaded.worldSize)%Main.loaded.worldSize][(centerY+j)%Main.loaded.worldSize] > 0){ // Check Left Above
                        return new int[]{((centerX-i)+Main.loaded.worldSize)%Main.loaded.worldSize,(centerY+j)%Main.loaded.worldSize};
                    }
                    if(objectLocations[((centerX-i)+Main.loaded.worldSize)%Main.loaded.worldSize][((centerY-j)+Main.loaded.worldSize)%Main.loaded.worldSize] > 0){ // Check Left Below
                        return new int[]{((centerX-i)+Main.loaded.worldSize)%Main.loaded.worldSize,((centerY-j)+Main.loaded.worldSize)%Main.loaded.worldSize};
                    }
                }
            }
        }
        Exception noObjectFound = new Exception("No Object was Found");
        throw noObjectFound;
    }

    public static double directionX(int posX, Creature creature) throws NullPointerException{
        // left
        if (posX < creature.getPosX()) {
            return -1.0;
        }
        // right
        else if (posX > creature.getPosX()) {
            return 1.0;
        }
        // same
        else {
            return 0.0;
        }
    }
    public static double directionY(int posY, Creature creature) throws NullPointerException {
        // down
        if (posY < creature.getPosY()) {
            return -1.0;
        }
        // up
        else if (posY > creature.getPosY()) {
            return 1.0;
        }
        // same
        else {
            return 0.0;
        }
    }
    // distance needs to be fixed for modulus
    
    public static double distance(int[] coor, Creature creature) throws NullPointerException {
        int x = creature.getPosX();
        int y = creature.getPosY();
        // find shortest distance along each axis
            int xDifference = Math.min(Math.abs(coor[0] - x), Math.abs(coor[0] - (x - Main.loaded.worldSize)));
            int yDifference = Math.min(Math.abs(coor[1] - y), Math.abs(coor[1] - (y - Main.loaded.worldSize)));
        // using Pyth theorem
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }
}