import java.util.ArrayList;

public class Sensor extends Neuron{

    public SensorMethod sensorMethod; 
    public static int numberOfSensorMethods = 9; // Update this when creating new Sensor methods
    public int methodID;
    private static int searchDepth = 10;

    public Sensor(Creature s, int methodID) {
        super("Sensor");
        this.methodID = methodID%(numberOfSensorMethods);
        switch(this.methodID){
            case 0: this.sensorMethod = Sensor::nearestFoodDistance; break;
            case 1: this.sensorMethod = Sensor::nearestWaterDistance; break;
            case 2: this.sensorMethod = Sensor::detectFoodXDirection; break;
            case 3: this.sensorMethod = Sensor::detectWaterXDirection; break;
            case 4: this.sensorMethod = Sensor::detectFoodYDirection; break;
            case 5: this.sensorMethod = Sensor::detectWaterYDirection; break;
            case 6: this.sensorMethod = Sensor::Oscillator; break;
            case 7: this.sensorMethod = Sensor::nearestCreatureDistance; break;
            case 8: this.sensorMethod = Sensor::random; break;
        }
    }
    

    public interface SensorMethod{
        double invoke(Creature creature);
    }

    
    ////////////////////////////////////////////////////////
    // SENSOR METHODS // SENSOR METHODS // SENSOR METHODS //
    ////////////////////////////////////////////////////////

<<<<<<< Updated upstream
    public static double nearestFoodDistance(Creature creature) {
        int indexOfFoundObject = findNearestObject(creature.getPos(), Database.foodCoordinates);

        if(indexOfFoundObject != -1){
            double distance = distance(Database.foodCoordinates.get(indexOfFoundObject), creature);
            if (distance != -1.0) {
                // System.out.println("Distance: "+d+" Adjusted: "+(1-(d/Database.worldSize)));
                return 1-(distance/Database.worldSize);
            }
        }

        // No object found
        return -1;
    }

    private static double nearestWaterDistance(Creature creature){
        int indexOfFoundObject = findNearestObject(creature.getPos(), Database.waterCoordinates);

        if(indexOfFoundObject != -1){
            double distance = distance(Database.waterCoordinates.get(indexOfFoundObject), creature);
            if (distance != -1.0) {
                // System.out.println("Distance: "+d+" Adjusted: "+(1-(d/Database.worldSize)));
                return 1-(distance/Database.worldSize);
            }
        }

        // No object found
        return -1;
    }

    private static double nearestCreatureDistance(Creature creature) {
        int indexOfFoundObject = findNearestObject(creature.getPos(), Database.creatureCoordinates);

        if(indexOfFoundObject != -1){
            double distance = distance(Database.creatureCoordinates.get(indexOfFoundObject), creature);
            if (distance != -1.0) {
                // System.out.println("Distance: "+d+" Adjusted: "+(1-(d/Database.worldSize)));
                return 1-(distance/Database.worldSize);
            }
        }

        // No object found
        return -1;
    }

    private static double detectFoodXDirection (Creature creature) {
        int indexOfFoundObject = findNearestObject(creature.getPos(), Database.foodCoordinates);

        if(indexOfFoundObject != -1){
            return directionX(Database.foodCoordinates.get(indexOfFoundObject), creature);
        }

        // No object found
        return 0;
    }

    private static double detectFoodYDirection (Creature creature) {
        int indexOfFoundObject = findNearestObject(creature.getPos(), Database.foodCoordinates);

        if(indexOfFoundObject != -1){
            return directionY(Database.foodCoordinates.get(indexOfFoundObject), creature);
        }

        // No object found
        return 0;
    }
    private static double detectWaterXDirection (Creature creature) {
        int indexOfFoundObject = findNearestObject(creature.getPos(), Database.waterCoordinates);

        if(indexOfFoundObject != -1){
            return directionX(Database.waterCoordinates.get(indexOfFoundObject), creature);
        }

        // No object found
        return 0;
=======
    public static double nearestFoodDistance(Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.worldObjects.getFoodLocationsArrayCopy());
        double distance = distance(coorOfFoundObject, creature);
        // returns a value between 1 and 0
        return 1-(distance/Main.loaded.worldSize);
    }

    private static double detectFoodXDirection (Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.worldObjects.getFoodLocationsArrayCopy());
        return directionX(coorOfFoundObject[0], creature);
    }

    private static double detectFoodYDirection (Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.worldObjects.getFoodLocationsArrayCopy());
        return directionY(coorOfFoundObject[1], creature);
    }

    private static double nearestCreatureDistance(Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.worldObjects.getCreatureLocationsArrayCopy());
        double distance = distance(coorOfFoundObject, creature);
              // returns a value between 1 and 0
        return 1-(distance/Main.loaded.worldSize);
    }

    private static double detectCreatureXDirection (Creature creature) throws Exception{
        int[] coorOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.worldObjects.getCreatureLocationsArrayCopy());
        return directionX(coorOfFoundObject[0], creature);
    }

    private static double detectCreatureYDirection (Creature creature) throws Exception{
        int[] indexOfFoundObject = findNearestObject(creature.getPosX(), creature.getPosY(), Main.loaded.worldObjects.getCreatureLocationsArrayCopy());
        return directionY(indexOfFoundObject[1], creature);
>>>>>>> Stashed changes
    }
    
    private static double detectWaterYDirection (Creature creature) {
        int indexOfFoundObject = findNearestObject(creature.getPos(), Database.waterCoordinates);

        if(indexOfFoundObject != -1){
            return directionY(Database.waterCoordinates.get(indexOfFoundObject), creature);
        }

        // No object found
        return 0;
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

    public static int findNearestObject(Coor center, ArrayList<Coor> coordinatesToCheck){
        for(int i=0; i<searchDepth; i++){
            Coor check = new Coor();

            // Search logic
            check.setX(center.x());check.setY(center.y()+i); // Above
            if(coordinatesToCheck.contains(check)){
                return coordinatesToCheck.indexOf(check);
            }
            check.setX(center.x()+i);check.setY(center.y()); // Right
            if(coordinatesToCheck.contains(check)){
                return coordinatesToCheck.indexOf(check);
            }
            check.setX(center.x()-i);check.setY(center.y()); // Left
            if(coordinatesToCheck.contains(check)){
                return coordinatesToCheck.indexOf(check);
            }  
            check.setX(center.x());check.setY(center.y()-i); // Below
            
            for(int j=1; j<i+1;j++){
                check.setX(center.x()+j);check.setY(center.y()+i); // Above right
                if(coordinatesToCheck.contains(check)){
                    return coordinatesToCheck.indexOf(check);
                }  
                check.setX(center.x()-j);check.setY(center.y()+i); // Above Left
                if(coordinatesToCheck.contains(check)){
                    return coordinatesToCheck.indexOf(check);
                }  
                check.setX(center.x()+j);check.setY(center.y()-i); // Below Right
                if(coordinatesToCheck.contains(check)){
                    return coordinatesToCheck.indexOf(check);
                }  
                check.setX(center.x()-j);check.setY(center.y()-i); // Below Left
                if(coordinatesToCheck.contains(check)){
                    return coordinatesToCheck.indexOf(check);
                }
                if(j < i){
                    check.setX(center.x()+i);check.setY(center.y()+j); // Right Above
                    if(coordinatesToCheck.contains(check)){
                        return coordinatesToCheck.indexOf(check);
                    }  
                    check.setX(center.x()+i);check.setY(center.y()-j); // Right Below
                    if(coordinatesToCheck.contains(check)){
                        return coordinatesToCheck.indexOf(check);
                    }  
                    check.setX(center.x()-i);check.setY(center.y()+j); // Left Above
                    if(coordinatesToCheck.contains(check)){
                        return coordinatesToCheck.indexOf(check);
                    } 
                    check.setX(center.x()-i);check.setY(center.y()-j); // Left Below
                    if(coordinatesToCheck.contains(check)){
                        return coordinatesToCheck.indexOf(check);
                    }
                }
            }
        }
        return -1;
    }

    public static double directionX(Coor coor, Creature creature) {
        // left
        if (coor.x() < creature.getPosX()) {
            return -1.0;
        }
        // right
        else if (coor.y() > creature.getPosX()) {
            return 1.0;
        }
        // same
        else {
            return 0.0;
        }
    }
    public static double directionY(Coor coor, Creature creature) {
        // down
        if (coor.y() < creature.getPosY()) {
            return -1.0;
        }
        // up
        else if (coor.x() > creature.getPosY()) {
            return 1.0;
        }
        // same
        else {
            return 0.0;
        }
    }
    
<<<<<<< Updated upstream
    public static double distance(Coor coor, Creature creature) {
=======
    public static double distance(int[] coor, Creature creature) throws NullPointerException {
        int x = creature.getPosX();
        int y = creature.getPosY();
        // find shortest distance along each axis
        int xDifference = Math.min(Math.abs(coor[0] - x), Math.abs(coor[0] - (x - Main.loaded.worldSize)));
        int yDifference = Math.min(Math.abs(coor[1] - y), Math.abs(coor[1] - (y - Main.loaded.worldSize)));
>>>>>>> Stashed changes
        // using Pyth theorem
        try {
            return Math.sqrt(Math.pow(coor.x() - creature.getPos().x(), 2) + Math.pow(coor.y() - creature.getPos().y(), 2));
        } catch (NullPointerException e) {
            return -1.0;
        }    
    } 
}