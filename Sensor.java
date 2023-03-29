public class Sensor extends Neuron{
    public sensorMethod invoke; 

    public Sensor(int methodID){
        switch(methodID){
            case 0: this.invoke = Sensor::nearestFood; break;
            case 1: this.invoke = Sensor::nearestWater; break;
        }
        
    }

    public interface sensorMethod{
        double SensorMethod(Subject subject);
    }

    ////////////////////////////////////////////////////////
    // SENSOR METHODS // SENSOR METHODS // SENSOR METHODS //
    ////////////////////////////////////////////////////////

    private static double nearestFood(Subject subject){
        // Needs implementation
        return 0; // Delete before implementing
    }

    private static double nearestWater(Subject subject){
        // Needs implementation
        return 0; // Delete before implementing
    }
}