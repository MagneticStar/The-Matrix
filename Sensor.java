public class Sensor extends Neuron{

    public SensorMethod sensorMethod; 
    public static int numberOfSensorMethods = 8; // Update this when creating new Sensor methods
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
            case 1: this.sensorMethod = Sensor::detectFoodXDirection; break;
            case 2: this.sensorMethod = Sensor::detectFoodYDirection; break;
            case 3: this.sensorMethod = Sensor::nearestCreatureDistance; break;
            case 4: this.sensorMethod = Sensor::detectCreatureXDirection; break;
            case 5: this.sensorMethod = Sensor::detectCreatureYDirection; break;
            case 6: this.sensorMethod = Sensor::Oscillator; break;
            case 7: this.sensorMethod = Sensor::random; break;
            default: System.out.println("numberOfSensorMethods needs to be updated to the correct number"); 
                     System.exit(0);
        }
    }
    

    public interface SensorMethod {
        double invoke(Creature creature) throws Exception;
    }
    
    ////////////////////////////////////////////////////////
    // SENSOR METHODS // SENSOR METHODS // SENSOR METHODS //
    ////////////////////////////////////////////////////////

    public static double nearestFoodDistance(Creature creature){
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Simulation.simulation.worldObjects.getFoodLocationsArrayCopy());
        double distance = distance(coorOfFoundObject, creature);
        // returns a value between 1 and 0
        return 1-(distance/Simulation.simulation.worldSize);
    }

    private static double detectFoodXDirection (Creature creature){
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Simulation.simulation.worldObjects.getFoodLocationsArrayCopy());
        return directionX(coorOfFoundObject[0], creature);
    }

    private static double detectFoodYDirection (Creature creature){
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Simulation.simulation.worldObjects.getFoodLocationsArrayCopy());
        return directionY(coorOfFoundObject[1], creature);
    }

    private static double nearestCreatureDistance(Creature creature){
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Simulation.simulation.worldObjects.getCreatureLocationsArrayCopy());
        double distance = distance(coorOfFoundObject, creature);
              // returns a value between 1 and 0
        return 1-(distance/Simulation.simulation.worldSize);
    }

    private static double detectCreatureXDirection (Creature creature){
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Simulation.simulation.worldObjects.getCreatureLocationsArrayCopy());
        return directionX(coorOfFoundObject[0], creature);
    }

    private static double detectCreatureYDirection (Creature creature){
        int[] indexOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Simulation.simulation.worldObjects.getCreatureLocationsArrayCopy());
        return directionY(indexOfFoundObject[1], creature);
    }
    
    private static double Oscillator(Creature creature) {
        if(Simulation.simulation.currentGenerationTick%creature.getGenome().getOscillatorPeriod()==0){
            return 1;
        }
        else{
            return -1;
        }
    }

    private static double random(Creature creature){
        return Simulation.simulation.random.nextDouble(-1,1);
    }

     ////////////////////////////////////////////////////////
    // SENSOR METHOD ASSISTORS // SENSOR METHOD ASSISTORS // 
    ////////////////////////////////////////////////////////

    public static int[] findNearestObject(int centerX, int centerY, int[][] objectLocations){
        // Search logic
        if(objectLocations[centerX][centerY] > 0){ // Check Center
            return new int[]{centerX,centerY};
        }
        for(int i=0; i<searchDepth; i++){
            if(objectLocations[centerX][(centerY+i)%Simulation.simulation.worldSize] > 0){ // Check Above
                return new int[]{centerX,(centerY+i)%Simulation.simulation.worldSize};
            }
            
            if(objectLocations[((centerX+i)%Simulation.simulation.worldSize)%Simulation.simulation.worldSize][centerY] > 0){ // Check Right
                return new int[]{(centerX+i)%Simulation.simulation.worldSize,centerY};
            }
            
            if(objectLocations[((centerX-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize][centerY] > 0){ // Check Left
                return new int[]{((centerX-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize,centerY};
            }
             
            if(objectLocations[centerX][((centerY-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize] > 0){ // Check Below
                return new int[]{centerX,((centerY-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize};
            }
            
            for(int j=1; j<i+1;j++){
                if(objectLocations[(centerX+j)%Simulation.simulation.worldSize][(centerY+i)%Simulation.simulation.worldSize] > 0){ // Check Above Right
                    return new int[]{(centerX+j)%Simulation.simulation.worldSize,(centerY+i)%Simulation.simulation.worldSize};
                }
                if(objectLocations[((centerX-j)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize][(centerY+i)%Simulation.simulation.worldSize] > 0){ // Check Above Left
                    return new int[]{((centerX-j)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize,(centerY+i)%Simulation.simulation.worldSize};
                }  
                if(objectLocations[(centerX+j)%Simulation.simulation.worldSize][((centerY-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize] > 0){ // Check Below Right
                    return new int[]{(centerX+j)%Simulation.simulation.worldSize,((centerY-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize};
                } 
                if(objectLocations[((centerX-j)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize][((centerY-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize] > 0){ // Check Below Left
                    return new int[]{((centerX-j)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize,((centerY-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize};
                }


                if(j < i){
                    if(objectLocations[(centerX+i)%Simulation.simulation.worldSize][(centerY+j)%Simulation.simulation.worldSize] > 0){ // Check Right Above
                        return new int[]{(centerX+i)%Simulation.simulation.worldSize,(centerY+j)%Simulation.simulation.worldSize};
                    } 
                    if(objectLocations[(centerX+i)%Simulation.simulation.worldSize][((centerY-j)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize] > 0){ // Check Right Below
                        return new int[]{(centerX+i)%Simulation.simulation.worldSize,((centerY-j)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize};
                    } 
                    if(objectLocations[((centerX-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize][(centerY+j)%Simulation.simulation.worldSize] > 0){ // Check Left Above
                        return new int[]{((centerX-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize,(centerY+j)%Simulation.simulation.worldSize};
                    }
                    if(objectLocations[((centerX-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize][((centerY-j)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize] > 0){ // Check Left Below
                        return new int[]{((centerX-i)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize,((centerY-j)+Simulation.simulation.worldSize)%Simulation.simulation.worldSize};
                    }
                }
            }
        }
        return null;
    }

    public static double directionX(int posX, Creature creature){
        // left
        if (posX < creature.getPosX()) {
        if (posX < creature.getPosX()) {
            return -1.0;
        }
        // right
        else if (posX > creature.getPosX()) {
        else if (posX > creature.getPosX()) {
            return 1.0;
        }
        // same
        else {
            return 0.0;
        }
    }
    public static double directionY(int posY, Creature creature) {
        // down
        if (posY < creature.getPosY()) {
        if (posY < creature.getPosY()) {
            return -1.0;
        }
        // up
        else if (posY > creature.getPosY()) {
        else if (posY > creature.getPosY()) {
            return 1.0;
        }
        // same
        else {
            return 0.0;
        }
    }
    // distance needs to be fixed for modulus
    // distance needs to be fixed for modulus
    
    public static double distance(int[] coor, Creature creature) {
        int x = creature.getPosX();
        int y = creature.getPosY();
        // find shortest distance along each axis
            int xDifference = Math.min(Math.abs(coor[0] - x), Math.abs(coor[0] - (x - Simulation.simulation.worldSize)));
            int yDifference = Math.min(Math.abs(coor[1] - y), Math.abs(coor[1] - (y - Simulation.simulation.worldSize)));
        // using Pyth theorem
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }
}