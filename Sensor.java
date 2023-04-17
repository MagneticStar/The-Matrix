import java.util.ArrayList;

public class Sensor extends Neuron{

    private static Creature subject;
    public SensorMethod sensorMethod; 
    private static int numberOfSensorMethods = 2; // Update this when creating new Sensor methods
    private Coor temp = new Coor();
    private ArrayList<Double> arr= new ArrayList<Double>();
    private static int i;
    private static int j;
    
    public Sensor(Creature s, int methodID) {
        super("Sensor");
        subject = s;
        switch(methodID%(numberOfSensorMethods)){
            case 0: this.sensorMethod = Sensor::detectFood; break;
            case 1: this.sensorMethod = Sensor::nearestWater; break;
        }
    }

    public interface SensorMethod{
        double invoke(Coor coordinate);
    }

    ////////////////////////////////////////////////////////
    // SENSOR METHODS // SENSOR METHODS // SENSOR METHODS //
    ////////////////////////////////////////////////////////

    public static double detectFood(Coor coor) {
        for (Food f: Database.foodsList) {
            if (f.getPos().equals(coor)) {
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

    private static double nearestWater(Coor coor){
        for (Water w: Database.watersList) {
            if (w.getPos().equals(coor)) {
                double d = distance(w);
                if (d != -1.0) {
                    // System.out.println(d);
                    return d;
                }
            }
        } 
        return -1.0;
    }

    ////////////////////////////////////////////////////////
    // SENSOR METHOD ASSISTORS // SENSOR METHOD ASSISTORS // 
    ////////////////////////////////////////////////////////


    
    public static double distance(ScreenObject obj) {
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

    public double search() {
        // Search logic
    for(i=1;i<10;i++){

        temp.setX(subject.getPos().x());temp.setY(subject.getPos().y()+i); // Above
        arr.add(sensorMethod.invoke(temp)); 
        temp.setX(subject.getPos().x()+i);temp.setY(subject.getPos().y()); // Right
        arr.add(sensorMethod.invoke(temp));
        temp.setX(subject.getPos().x()-i);temp.setY(subject.getPos().y()); // Left
        arr.add(sensorMethod.invoke(temp));  
        temp.setX(subject.getPos().x());temp.setY(subject.getPos().y()-i); // Below
        
        j = 1;
        while(abNext() || rlNext()){
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
    public boolean rlNext(){
        if(j < i){
            temp.setX(subject.getPos().x()+i);temp.setY(subject.getPos().y()+j); // Right Above
            arr.add(sensorMethod.invoke(temp));  
            temp.setX(subject.getPos().x()+i);temp.setY(subject.getPos().y()-j); // Right Below
            arr.add(sensorMethod.invoke(temp));  
            temp.setX(subject.getPos().x()-i);temp.setY(subject.getPos().y()+j); // Left Above
            arr.add(sensorMethod.invoke(temp));  
            temp.setX(subject.getPos().x()-i);temp.setY(subject.getPos().y()-j); // Left Below
            arr.add(sensorMethod.invoke(temp));  
            return true;
        }
        return false;
    }
    
    public boolean abNext(){
        if(j < i+1){
          temp.setX(subject.getPos().x()+j);temp.setY(subject.getPos().y()+i); // Above right
          arr.add(sensorMethod.invoke(temp));  
          temp.setX(subject.getPos().x()-j);temp.setY(subject.getPos().y()+i); // Above Left
          arr.add(sensorMethod.invoke(temp));  
          temp.setX(subject.getPos().x()+j);temp.setY(subject.getPos().y()-i); // Below Right
          arr.add(sensorMethod.invoke(temp));  
          temp.setX(subject.getPos().x()-j);temp.setY(subject.getPos().y()-i); // Below Left
          arr.add(sensorMethod.invoke(temp));  
          return true;
        }
        return false;
    }    
}