import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
    
        Database.foodsList.add(new Food(new Coor(45, 45)));
        Database.foodsList.add(new Food(new Coor(52, 48)));
        Database.foodsList.add(new Food(new Coor(50, 40)));
        Database.foodsList.add(new Food(new Coor(70, 70)));
        Database.foodsList.add(new Food(new Coor(20, 70)));
        Database.foodsList.add(new Food(new Coor(90, 55)));
        Database.watersList.add(new Water(new Coor(90, 45)));
        Database.foodsList.add(new Food(new Coor(70, 20)));
        Database.watersList.add(new Water(new Coor(55, 59)));
        Database.watersList.add(new Water(new Coor(50, 50)));
        // Create creatureCount creatures
        for (int i = 0; i < Database.creatureCount; i++) {
        Database.creaturesList.add(new Creature());
        }
        Genome.calculateColor();
        Screens.createScreens();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // how many ticks
        for (int i = 0; i < Database.generationLength; i++) {
            tick(Screens.simulationPanel, i);
        }
    }

    public static void tick(SimulationPanel panel, int i) {
        for(Creature creature : Database.creaturesList){
            determineNeuronActivation(creature).motorMethod.invoke(creature);
        }
        panel.repaint();

        // Make tick wait
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Motor determineNeuronActivation(Creature creature){
        for(Sensor sensor : creature.getGenome().getSensors()){
            sensor.addValue(sensor.sensorMethod.invoke(creature));
            iterateThroughNeuronChain(sensor);
        }
        
        Motor highestValueMotor = new Motor();
        
        for(Motor motor : creature.getGenome().getMotors()){
            if(highestValueMotor.getMaxValue() < motor.getMaxValue()){
                highestValueMotor = motor;
            }
        }
        // System.out.println(highestValueMotor.getMaxValue());
        //loop throughneurons and clear values list
        for (Neuron neuron : creature.getGenome().getNeurons()) {
            neuron.clearValues();
        }
        return highestValueMotor;
    }

    private static void iterateThroughNeuronChain(Neuron neuron){
        for(Neuron sink : new ArrayList<Neuron>(neuron.getSinks().keySet())){
            double sum = 0;
            for(double value : neuron.getValues()){
                sum+= value;
            }

            sink.addValue(sum);

            if(sink instanceof Internal && sink.getSources().size() == sink.getValues().size()){
                iterateThroughNeuronChain(sink);
            }

            // Debug
            // System.out.println(sink.toString()+" "+sink.getValues().size()+"/"+sink.getSources().size());
        }
    }
    
}