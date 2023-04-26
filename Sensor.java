import java.util.ArrayList;

// search is very slow.

public class Sensor extends Neuron{

    public SensorMethod sensorMethod; 
    private static int numberOfSensorMethods = 9; // Update this when creating new Sensor methods
    private static int searchDepth = 10;

    public Sensor(Creature s, int methodID) {
        super("Sensor");
        switch(methodID%(numberOfSensorMethods)){
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

    public static double nearestFoodDistance(Creature creature) {
        // Debug
        System.out.println("New Search");

        for (int i = 0; i < searchDepth; i++) {
            for (Coor coor : search(i,creature.getPos())) {
                // Debug
                System.out.println(coor.toString());
                
                if (Database.foodCoordinates.contains(coor)) {
                    double d = distance(coor, creature);
                    if (d != -1.0) {
                        // System.out.println("Distance: "+d+" Adjusted: "+(1-(d/Database.worldSize)));
                        return 1-(d/Database.worldSize);
                    }
                }
            }
        }
        return -1.0;
    }

    private static double nearestWaterDistance(Creature creature){
        // Debug
        System.out.println("New Search");

        for (int i = 0; i < searchDepth; i++) {
            for (Coor coor : search(i,creature.getPos())) {
                // Debug
                System.out.println(coor.toString());
                
                if (Database.waterCoordinates.contains(coor)) {
                    double d = distance(coor, creature);
                    if (d != -1.0) {
                        // System.out.println("Distance: "+d+" Adjusted: "+(1-(d/Database.worldSize)));
                        return 1-(d/Database.worldSize);
                    }
                }
            }
        } 
        // -1.0 means no water nearby
        return -1.0;
    }

    private static double nearestCreatureDistance(Creature creature) {
        // Debug
        System.out.println("New Search");

        for (int i = 0; i < searchDepth; i++) {
            for (Coor coor : search(i,creature.getPos())) {
                // Debug
                System.out.println(coor.toString());
                
                if (Database.creatureCoordinates.contains(coor)) {
                    double d = distance(coor, creature);
                    if (d != -1.0) {
                        // System.out.println("Distance: "+d+" Adjusted: "+(1-(d/Database.worldSize)));
                        return 1-(d/Database.worldSize);
                    }
                }
            }
        }
        return -1.0;
    }

    private static double detectFoodXDirection (Creature creature) {
        // Debug
        System.out.println("New Search");

        for (int i = 0; i < searchDepth; i++) {
            for (Coor coor : search(i,creature.getPos())) {
                // Debug
                System.out.println(coor.toString());
                
                if(Database.foodCoordinates.contains(coor)){
                    return directionX(coor, creature);
                }
            }
        }
        return 0.0;
    }

    private static double detectFoodYDirection (Creature creature) {
        // Debug
        System.out.println("New Search");

        for (int i = 0; i < searchDepth; i++) {
            for (Coor coor : search(i,creature.getPos())) {
                // Debug
                System.out.println(coor.toString());
                
                if(Database.foodCoordinates.contains(coor)){
                    return directionY(coor, creature);
                }
            }
        }
        return 0.0;
    }
    private static double detectWaterXDirection (Creature creature) {
        // Debug
        System.out.println("New Search");

        for(int i=0; i<searchDepth; i++){
            for (Coor coor: search(i,creature.getPos())) {
                // Debug
                System.out.println(coor.toString());
                
                if(Database.waterCoordinates.contains(coor)){
                    return directionX(coor, creature);
                }
            }
        }
        return 0.0;
    }
    
    private static double detectWaterYDirection (Creature creature) {
        // Debug
        System.out.println("New Search");

        for(int i=0; i<searchDepth; i++){
            for (Coor coor : search(i,creature.getPos())) {
                // Debug
                System.out.println(coor.toString());
                
                if(Database.waterCoordinates.contains(coor)){
                    return directionY(coor, creature);
                }
            }
        }
        return 0.0;
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

    // public static Coor findNearestObject(){
    //     for(int i=0; i<searchDepth; i++){

    //     }
    // }

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
    
    public static double distance(Coor coor, Creature creature) {
        // using Pyth theorem
        try {
            return Math.sqrt(Math.pow(coor.x() - creature.getPos().x(), 2) + Math.pow(coor.y() - creature.getPos().y(), 2));
        } catch (NullPointerException e) {
            return -1.0;
        }    
    }

    public static ArrayList<Coor> search(int i, Coor center) {
        Coor temp = new Coor();
        ArrayList<Coor> arr= new ArrayList<Coor>();

        // Search logic
        temp.setX(center.x());temp.setY(center.y()+i); // Above
        arr.add(temp); 
        temp.setX(center.x()+i);temp.setY(center.y()); // Right
        arr.add(temp);
        temp.setX(center.x()-i);temp.setY(center.y()); // Left
        arr.add(temp);  
        temp.setX(center.x());temp.setY(center.y()-i); // Below
        
        for(int j=1; j<i+1;j++){
            temp.setX(center.x()+j);temp.setY(center.y()+i); // Above right
            arr.add(temp);  
            temp.setX(center.x()-j);temp.setY(center.y()+i); // Above Left
            arr.add(temp);  
            temp.setX(center.x()+j);temp.setY(center.y()-i); // Below Right
            arr.add(temp);  
            temp.setX(center.x()-j);temp.setY(center.y()-i); // Below Left
            arr.add(temp);
            if(j < i){
                temp.setX(center.x()+i);temp.setY(center.y()+j); // Right Above
                arr.add(temp);  
                temp.setX(center.x()+i);temp.setY(center.y()-j); // Right Below
                arr.add(temp);  
                temp.setX(center.x()-i);temp.setY(center.y()+j); // Left Above
                arr.add(temp);  
                temp.setX(center.x()-i);temp.setY(center.y()-j); // Left Below
                arr.add(temp); 
            }
        }
      return arr;
    } 
}