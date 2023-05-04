// search is very slow.

public class Sensor extends Neuron{

    public SensorMethod sensorMethod; 
    private static int numberOfSensorMethods = 6; // Update this when creating new Sensor methods
    private static int searchDepth = 0;

    public Sensor(Creature s, int methodID) {
        super("Sensor");
        switch(methodID%(numberOfSensorMethods)){
            case 0: this.sensorMethod = Sensor::nearestFoodDistance; break;
            case 1: this.sensorMethod = Sensor::detectFoodXDirection; break;
            case 2: this.sensorMethod = Sensor::detectFoodYDirection; break;
            case 3: this.sensorMethod = Sensor::Oscillator; break;
            case 4: this.sensorMethod = Sensor::nearestCreatureDistance; break;
            case 5: this.sensorMethod = Sensor::random; break;
        }
    }
    
    public interface SensorMethod{
        double invoke(Creature creature);
    }
    
    ////////////////////////////////////////////////////////
    // SENSOR METHODS // SENSOR METHODS // SENSOR METHODS //
    ////////////////////////////////////////////////////////

    public static double nearestFoodDistance(Creature creature) {
        for (int i = 0; i < searchDepth; i++) {
            Coor near = search(i, creature.getPos(), "food");
            for (Food f: Database.foodsList) {
                if (f.getPos().equals(near)) {
                    double d = distance(f, creature);
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
        for (int i = 0; i < searchDepth; i++) {
            Coor near = search(i, creature.getPos(), "food");
            for (Food f: Database.foodsList) {
                if (f.getPos().equals(near)) {
                    return directionX(f, creature);
                }
            }
        }
        return 0.0;
    }

    private static double detectFoodYDirection (Creature creature) {
        for (int i = 0; i < searchDepth; i++) {
            Coor near = search(i, creature.getPos(), "food");
            for (Food f: Database.foodsList) {
                if (f.getPos().equals(near)) {
                    return directionY(f, creature);
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
    
    private static double nearestCreatureDistance(Creature creature) {
        for (int i = 0; i < searchDepth; i++) {
            Coor near = search(i,creature.getPos(), "creature");
            for (Creature otherCreature: Database.creaturesList) {
                if (otherCreature.getPos().equals(near)) {
                    double d = distance(otherCreature, creature);
                    if (d != -1.0) {
                        // System.out.println(d);
                        return d/Database.worldSize;
                    }
                }
            }
        }
        return -1.0;
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
    // distance needs to be fixed for modulus
    
    public static double distance(ScreenObject obj, Creature creature) {
        // using Pyth theorem
        try {
            return Math.sqrt(Math.pow(obj.getPos().x() - creature.getPos().x(), 2) + Math.pow(obj.getPos().y() - creature.getPos().y(), 2));
        } catch (NullPointerException e) {
            return -1.0;
        }    
    }

    private static boolean checkLocation(String type, Coor temp) {
        switch (type) {
            case "creature": 
                for (Creature creature : Database.creaturesList) {
                    if (creature.getPos().equals(temp)) {
                        return true;
                    }
                }
                return false;
            
            case "food": 
                for (Food food: Database.foodsList) {
                    if (food.getPos().equals(temp)) {
                        return true;
                    }
                }
                return false;
            // should never happen
            default: System.out.println("ERROR IN Genome.checkLocation");return false;
        }
    }

    public static Coor search(int i, Coor center, String type) {
        Coor temp = new Coor();
        
        // Search logic
        temp.setX(center.x() % Database.worldSize);temp.setY(center.y()+i % Database.worldSize); // Above
        if (checkLocation(type, temp)) {
            return temp;
        }
        temp.setX(center.x()+i % Database.worldSize);temp.setY(center.y() % Database.worldSize); // Right
        if (checkLocation(type, temp)) {
            return temp;
        }
        temp.setX(center.x()-i % Database.worldSize);temp.setY(center.y() % Database.worldSize); // Left
        if (checkLocation(type, temp)) {
            return temp;
        }
        temp.setX(center.x() % Database.worldSize);temp.setY(center.y()-i % Database.worldSize); // Below
        if (checkLocation(type, temp)) {
            return temp;
        }
        for(int j=1; j<i+1;j++){
            temp.setX(center.x()+j % Database.worldSize);temp.setY(center.y()+i % Database.worldSize); // Above right
            if (checkLocation(type, temp)) {
                return temp;
            } 
            temp.setX(center.x()-j % Database.worldSize);temp.setY(center.y()+i % Database.worldSize); // Above Left
            if (checkLocation(type, temp)) {
                return temp;
            } 
            temp.setX(center.x()+j % Database.worldSize);temp.setY(center.y()-i % Database.worldSize); // Below Right
            if (checkLocation(type, temp)) {
                return temp;
            } 
            temp.setX(center.x()-j % Database.worldSize);temp.setY(center.y()-i % Database.worldSize); // Below Left
            if (checkLocation(type, temp)) {
                return temp;
            }
            if(j < i){
                temp.setX(center.x()+i % Database.worldSize);temp.setY(center.y()+j % Database.worldSize); // Right Above
                if (checkLocation(type, temp)) {
                    return temp;
                } 
                temp.setX(center.x()+i % Database.worldSize);temp.setY(center.y()-j % Database.worldSize); // Right Below
                if (checkLocation(type, temp)) {
                    return temp;
                } 
                temp.setX(center.x()-i % Database.worldSize);temp.setY(center.y()+j % Database.worldSize); // Left Above
                if (checkLocation(type, temp)) {
                    return temp;
                }  
                temp.setX(center.x()-i % Database.worldSize);temp.setY(center.y()-j % Database.worldSize); // Left Below
                if (checkLocation(type, temp)) {
                    return temp;
                }
            }
        }
      return center;
    } 
}