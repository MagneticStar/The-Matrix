import java.util.ArrayList;


public class Sensor extends Neuron{

    public SensorMethod sensorMethod; 
    private static int numberOfSensorMethods = 6; // Update this when creating new Sensor methods
    private static int searchDepth = 25;
    
    
    
    public Sensor(Creature s, int methodID) {
        super("Sensor");
        switch(methodID%(numberOfSensorMethods)){
            case 0: this.sensorMethod = Sensor::nearestFoodDistance; break;
            case 1: this.sensorMethod = Sensor::nearestWaterDistance; break;
            case 2: this.sensorMethod = Sensor::detectFoodXDirection; break;
            case 3: this.sensorMethod = Sensor::detectWaterXDirection; break;
            case 4: this.sensorMethod = Sensor::detectFoodYDirection; break;
            case 5: this.sensorMethod = Sensor::detectWaterYDirection; break;
        }
    }
    

    public interface SensorMethod{
        double invoke(Creature creature);
    }

    
    ////////////////////////////////////////////////////////
    // SENSOR METHODS // SENSOR METHODS // SENSOR METHODS //
    ////////////////////////////////////////////////////////

    public static double nearestFoodDistance(Creature creature) {
        for (int i=0; i<searchDepth; i++) {
            for (Coor coor : search(i, creature.getPos())) {
                    for (Food f: Database.foodsList) {
                        if (f.getPos().equals(coor)) {
                            double d = distance(f, creature);
                            if (d != -1.0) {
                                // System.out.println(d);
                                return d;
                            }
                    }
                }
            }
        }
        // -1.0 means no food nearby
        return -1.0;
    }

    private static double nearestWaterDistance(Creature creature){
        for (int i=0; i<searchDepth; i++) {
            for (Coor coor : search(i, creature.getPos())) {
                    for (Water w: Database.watersList) {
                        if (w.getPos().equals(coor)) {
                            double d = distance(w, creature);
                            if (d != -1.0) {
                                // System.out.println(d);
                                return d;
                            }
                    }
                }
            }
        } 
        // -1.0 means no water nearby
        return -1.0;
    }

    private static double detectFoodXDirection (Creature creature) {
        for (int i=0; i<searchDepth; i++) {
            for (Coor coor : search(i, creature.getPos())) {
                for (Food f: Database.foodsList) {
                    if (f.getPos().equals(coor)) {
                        return directionX(f, creature);
                    }
                }
            }
        }
        // if no food exist
        return 0.0;
    }

    private static double detectFoodYDirection (Creature creature) {
        for (int i=0; i<searchDepth; i++) {
            for (Coor coor : search(i, creature.getPos())) {
                for (Food f: Database.foodsList) {
                    if (f.getPos().equals(coor)) {
                        return directionY(f, creature);
                    }
                }
            }
        }
        // if no food exist
        return 0.0;
    }
    private static double detectWaterXDirection (Creature creature) {
        for (int i=0; i<searchDepth; i++) {
            for (Coor coor : search(i, creature.getPos())) {
                for (Water w: Database.watersList) {
                    if (w.getPos().equals(coor)) {
                        return directionX(w, creature);
                    }
                }
            }
        }
        // if no water exist
        return 0.0;
    }
    
    private static double detectWaterYDirection (Creature creature) {
<<<<<<< Updated upstream
        for (Coor coor : search(creature)) {
            for (Water w: Database.watersList) {
                if (w.getPos().equals(coor)) {
                    return directionY(w, creature);
=======
        for(int i=0; i<searchDepth; i++){
            for (Coor coor : search(i,creature.getPos())) {
                for (Water w: Database.watersList) {
                    if (w.getPos().equals(coor)) {
                        return directionY(w,creature);
                    }
>>>>>>> Stashed changes
                }
            }
        }
        // if no water exist
        return 0.0;
    }
    ////////////////////////////////////////////////////////
    // SENSOR METHOD ASSISTORS // SENSOR METHOD ASSISTORS // 
    ////////////////////////////////////////////////////////

    public static double directionX(ScreenObject obj, Creature creature) {
        // left
        if (obj.getPosX() < creature.getPosX()) {
            return -1.0;
        }
        // right
        else if (obj.getPosX() > creature.getPosX()) {
            return 1.0;
        }
        // same
        else {
            return 0.0;
        }
    }
    public static double directionY(ScreenObject obj, Creature creature) {
        // down
        if (obj.getPosY() < creature.getPosY()) {
            return -1.0;
        }
        // up
        else if (obj.getPosY() > creature.getPosY()) {
            return 1.0;
        }
        // same
        else {
            return 0.0;
        }
    }
    
    public static double distance(ScreenObject obj, Creature creature) {
        // using Pyth theorem
        try {
            return Math.sqrt(Math.pow(obj.getPos().x() - creature.getPos().x(), 2) + Math.pow(obj.getPos().y() - creature.getPos().y(), 2));
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
