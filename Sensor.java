import java.util.ArrayList;

public class Sensor extends Neuron{

<<<<<<< Updated upstream
    private static Subject subject;
    public SensorMethod sensorMethod; 
    private static int numberOfSensorMethods = 2; // Update this when creating new Sensor methods
=======
    public SensorMethod sensorMethod; 
    private static int numberOfSensorMethods = 6; // Update this when creating new Sensor methods
>>>>>>> Stashed changes
    private Coor temp = new Coor();
    private ArrayList<Double> arr= new ArrayList<Double>();
    private static int i;
    private static int j;
    
    public Sensor(Subject subject, int methodID) {
        super("Sensor");
<<<<<<< Updated upstream
        this.subject = subject;
        switch(methodID%numberOfSensorMethods){
=======
        switch(methodID%(numberOfSensorMethods)){
>>>>>>> Stashed changes
            case 0: this.sensorMethod = Sensor::detectFood; break;
            case 1: this.sensorMethod = Sensor::nearestWater; break;
        }
    }

    public interface SensorMethod{
        double invoke(Creature creature);
    }

    ////////////////////////////////////////////////////////
    // SENSOR METHODS // SENSOR METHODS // SENSOR METHODS //
    ////////////////////////////////////////////////////////

<<<<<<< Updated upstream
    public static double detectFood(Coor coor) {
        for (Food f: Main.foods) {
            if (f.getPos().equals(coor)) {
=======
    public static double detectFood(Creature creature) {
        for (Food f: Database.foodsList) {
            if (f.getPos().equals(creature.getPos())) {
>>>>>>> Stashed changes
                double d = distance(f);
                if (d != -1.0) {
                    // System.out.println(d);
                    return d;
                }
            }
        }
        // -1.0 means null 
        return -1.0;
    }

<<<<<<< Updated upstream
    private static double nearestWater(Coor coor){
        for (Water w: Main.waters) {
            if (w.getPos().equals(coor)) {
=======
    private static double nearestWater(Creature creature){
        for (Water w: Database.watersList) {
            if (w.getPos().equals(creature.getPos())) {
>>>>>>> Stashed changes
                double d = distance(w);
                if (d != -1.0) {
                    // System.out.println(d);
                    return d;
                }
            }
        } 
        return -1.0;
    }

<<<<<<< Updated upstream
=======
    private static double detectFoodXDirection (Creature creature) {
        for (Food f: Database.foodsList) {
            if (f.getPos().equals(creature.getPos())) {
                return directionX(f);
            }
        }
        // if no food exist or none nearby
        return 0.0;
    }

    private static double detectFoodYDirection (Creature creature) {
        for (Food f: Database.foodsList) {
            if (f.getPos().equals(creature.getPos())) {
                return directionY(f);
            }
        }
        // if no food exist or none nearby
        return 0.0;
    }
    private static double detectWaterXDirection (Creature creature) {
        for (Water w: Database.watersList) {
            if (w.getPos().equals(creature.getPos())) {
                return directionX(w);
            }
        }
        // if no water exist or none nearby
        return 0.0;
    }
    private static double detectWaterYDirection (Creature creature) {
        for (Water w: Database.watersList) {
            if (w.getPos().equals(creature.getPos())) {
                return directionY(w);
            }
        }
        // if no water exist or none nearby
        return 0.0;
    }
    private static double oscillator(Creature creature){
        return Main.stepCount%creature.getGenome().getOscillatorPeriod();
    }
>>>>>>> Stashed changes
    ////////////////////////////////////////////////////////
    // SENSOR METHOD ASSISTORS // SENSOR METHOD ASSISTORS // 
    ////////////////////////////////////////////////////////


    
    public static double distance(screenObject obj) {
        // using Pyth theorem
        try {
            return Math.sqrt(Math.pow(obj.getPos().x() - subject.getPos().x(), 2) + Math.pow(obj.getPos().y() - subject.getPos().y(), 2));
        } catch (NullPointerException e) {
            return -1.0;
        }    
    }

    public void findAdj() {

        Coor tempPos = new Coor();

        for (int i = subject.getPos().x() - 1; i <= subject.getPos().x() + 1; i++) {
            for (int j = subject.getPos().y() - 1; j <= subject.getPos().y() + 1; j++) {
                tempPos.setX(i);
                tempPos.setY(j);
                detectFood(tempPos);
            }
        }
    }

    public double search(Creature creature) {
        // Search logic
    for(i=1;i<10;i++){

        temp.setX(creature.getPos().x());temp.setY(creature.getPos().y()+i); // Above
        arr.add(sensorMethod.invoke(temp)); 
        temp.setX(creature.getPos().x()+i);temp.setY(creature.getPos().y()); // Right
        arr.add(sensorMethod.invoke(temp));
        temp.setX(creature.getPos().x()-i);temp.setY(creature.getPos().y()); // Left
        arr.add(sensorMethod.invoke(temp));  
        temp.setX(creature.getPos().x());temp.setY(creature.getPos().y()-i); // Below
        
        j = 1;
        while(abNext(creature) || rlNext(creature)){
          j++;
        }
      }
      for (double num : arr) {
        if (num != -1.0) {
            return num;
        }
      }
      return -1.0;
    }
<<<<<<< Updated upstream
    public boolean rlNext(){
=======

    public boolean rlNext(Creature creature){
>>>>>>> Stashed changes
        if(j < i){
            temp.setX(creature.getPos().x()+i);temp.setY(creature.getPos().y()+j); // Right Above
            arr.add(sensorMethod.invoke(temp));  
            temp.setX(creature.getPos().x()+i);temp.setY(creature.getPos().y()-j); // Right Below
            arr.add(sensorMethod.invoke(temp));  
            temp.setX(creature.getPos().x()-i);temp.setY(creature.getPos().y()+j); // Left Above
            arr.add(sensorMethod.invoke(temp));  
            temp.setX(creature.getPos().x()-i);temp.setY(creature.getPos().y()-j); // Left Below
            arr.add(sensorMethod.invoke(temp));  
            return true;
        }
        return false;
    }
    
    public boolean abNext(Creature creature){
        if(j < i+1){
          temp.setX(creature.getPos().x()+j);temp.setY(creature.getPos().y()+i); // Above right
          arr.add(sensorMethod.invoke(temp));  
          temp.setX(creature.getPos().x()-j);temp.setY(creature.getPos().y()+i); // Above Left
          arr.add(sensorMethod.invoke(temp));  
          temp.setX(creature.getPos().x()+j);temp.setY(creature.getPos().y()-i); // Below Right
          arr.add(sensorMethod.invoke(temp));  
          temp.setX(creature.getPos().x()-j);temp.setY(creature.getPos().y()-i); // Below Left
          arr.add(sensorMethod.invoke(temp));  
          return true;
        }
        return false;
    }    
}