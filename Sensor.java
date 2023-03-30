public class Sensor extends Neuron{
    public SensorMethod sensorMethod; 
    private static int numberOfSensorMethods = 2; // Update this when creating new Sensor methods

    public Sensor(int methodID){
        switch(methodID%(numberOfSensorMethods+1)){
            case 0: this.sensorMethod = Sensor::nearestFood; break;
            case 1: this.sensorMethod = Sensor::nearestWater; break;
        }
    }

    public interface SensorMethod{
        double invoke(Subject subject);
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