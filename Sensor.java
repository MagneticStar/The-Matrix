public class Sensor extends Neuron{

    public SensorMethod sensorMethod; 
    public static int numberOfSensorMethods = 6; // Update this when creating new Sensor methods
    public int methodID;
    private static int searchDepth = 10;

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
            // case 6: this.sensorMethod = Sensor::Oscillator; break;
            // case 7: this.sensorMethod = Sensor::random; break;
        }
    }
    

    public interface SensorMethod {
        double invoke(Creature creature) throws Exception;
    }
    
    ////////////////////////////////////////////////////////
    // SENSOR METHODS // SENSOR METHODS // SENSOR METHODS //
    ////////////////////////////////////////////////////////

    public static double nearestFoodDistance(Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Database.foodLocations);
        double distance = distance(coorOfFoundObject, creature);
        // returns a value between 1 and 0
        return 1-(distance/Database.worldSize);
    }

    private static double detectFoodXDirection (Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Database.foodLocations);
        return directionX(coorOfFoundObject[0], creature);
    }

    private static double detectFoodYDirection (Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Database.foodLocations);
        return directionY(coorOfFoundObject[1], creature);
    }

    private static double nearestCreatureDistance(Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Database.creatureLocations);
        double distance = distance(coorOfFoundObject, creature);
              // returns a value between 1 and 0
        return 1-(distance/Database.worldSize);
    }

    private static double detectCreatureXDirection (Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Database.creatureLocations);
        return directionX(coorOfFoundObject[0], creature);
    }

    private static double detectCreatureYDirection (Creature creature) throws Exception{
        int[] indexOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Database.creatureLocations);
        return directionY(indexOfFoundObject[1], creature);
    }
    
    private static double Oscillator(Creature creature) {
        if(Database.currentGenerationTick%creature.getGenome().getOscillatorPeriod()==0){
            return 1;
        }
        else{
            return -1;
        }
    }

    private static double random(Creature creature){
        return Database.random.nextDouble(-1,1);
    }

    ////////////////////////////////////////////////////////
    // SENSOR METHOD ASSISTORS // SENSOR METHOD ASSISTORS // 
    ////////////////////////////////////////////////////////

    public static int[] findNearestObject(int centerX, int centerY, int[][] objectLocations) throws Exception{
        // Search logic
        if(objectLocations[centerX][centerY] > 0){ // Check Center
            return new int[]{centerX,centerY};
        }
        for(int i=0; i<searchDepth; i++){
            if(objectLocations[centerX][(centerY+i)%Database.worldSize] > 0){ // Check Above
                return new int[]{centerX,(centerY+i)%Database.worldSize};
            }
            
            if(objectLocations[((centerX+i)%Database.worldSize)%Database.worldSize][centerY] > 0){ // Check Right
                return new int[]{(centerX+i)%Database.worldSize,centerY};
            }
            
            if(objectLocations[((centerX-i)+Database.worldSize)%Database.worldSize][centerY] > 0){ // Check Left
                return new int[]{((centerX-i)+Database.worldSize)%Database.worldSize,centerY};
            }
             
            if(objectLocations[centerX][((centerY-i)+Database.worldSize)%Database.worldSize] > 0){ // Check Below
                return new int[]{centerX,((centerY-i)+Database.worldSize)%Database.worldSize};
            }
            
            for(int j=1; j<i+1;j++){
                if(objectLocations[(centerX+j)%Database.worldSize][(centerY+i)%Database.worldSize] > 0){ // Check Above Right
                    return new int[]{(centerX+j)%Database.worldSize,(centerY+i)%Database.worldSize};
                }
                if(objectLocations[((centerX-j)+Database.worldSize)%Database.worldSize][(centerY+i)%Database.worldSize] > 0){ // Check Above Left
                    return new int[]{((centerX-j)+Database.worldSize)%Database.worldSize,(centerY+i)%Database.worldSize};
                }  
                if(objectLocations[(centerX+j)%Database.worldSize][((centerY-i)+Database.worldSize)%Database.worldSize] > 0){ // Check Below Right
                    return new int[]{(centerX+j)%Database.worldSize,((centerY-i)+Database.worldSize)%Database.worldSize};
                } 
                if(objectLocations[((centerX-j)+Database.worldSize)%Database.worldSize][((centerY-i)+Database.worldSize)%Database.worldSize] > 0){ // Check Below Left
                    return new int[]{((centerX-j)+Database.worldSize)%Database.worldSize,((centerY-i)+Database.worldSize)%Database.worldSize};
                }

                if(j < i){
                    if(objectLocations[(centerX+i)%Database.worldSize][(centerY+j)%Database.worldSize] > 0){ // Check Right Above
                        return new int[]{(centerX+i)%Database.worldSize,(centerY+j)%Database.worldSize};
                    } 
                    if(objectLocations[(centerX+i)%Database.worldSize][((centerY-j)+Database.worldSize)%Database.worldSize] > 0){ // Check Right Below
                        return new int[]{(centerX+i)%Database.worldSize,((centerY-j)+Database.worldSize)%Database.worldSize};
                    } 
                    if(objectLocations[((centerX-i)+Database.worldSize)%Database.worldSize][(centerY+j)%Database.worldSize] > 0){ // Check Left Above
                        return new int[]{((centerX-i)+Database.worldSize)%Database.worldSize,(centerY+j)%Database.worldSize};
                    }
                    if(objectLocations[((centerX-i)+Database.worldSize)%Database.worldSize][((centerY-j)+Database.worldSize)%Database.worldSize] > 0){ // Check Left Below
                        return new int[]{((centerX-i)+Database.worldSize)%Database.worldSize,((centerY-j)+Database.worldSize)%Database.worldSize};
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
            int xDifference = Math.min(Math.abs(coor[0] - x), Math.abs(coor[0] - (x - Database.worldSize)));
            int yDifference = Math.min(Math.abs(coor[1] - y), Math.abs(coor[1] - (y - Database.worldSize)));
        // using Pyth theorem
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }
}