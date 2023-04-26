import java.util.ArrayList;


public class Main {
   

    // simFrame.add(simPanel);
    //     simFrame.setVisible(true);

    //     FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
    //     neuronMapPanel.setLayout(flowLayout);
    //     neuronMap.add(neuronMapPanel);
    //     neuronMap.setVisible(true);
    
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
        // ignore me
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // how many ticks
        for (int i = 0; i < 300; i++) {
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

        ArrayList<Motor> motors = creature.getGenome().getMotors();
        
        Motor highestValueMotor = motors.get(0);
        
        for(int i = 1; i<motors.size(); i++){
            if(highestValueMotor.getMaxValue() < motors.get(i).getMaxValue()){
                highestValueMotor = motors.get(i);
            }
        }
        // System.out.println(highestValueMotor.getMaxValue());
        
        // Try changing getMotors to getNeurons. Your behavior looks nicer but we should clear all neurons after each step, otherwise previous sensor input will interfere. 
        // The reason it appears that previous sensor input does in fact not interefere is because you are adding more and more to it which makes it more likely to activate it's sinks.
        // For example, if a sensor detecting food is close, it's continually building up over time but will get overtaken by a sensor detecting water if the creature gets close to that
        // water source because that build up is now higher. Like a competing armsrace, the numbers are continuing to stack up and up which makes it appear as if it's working
        for (Neuron neuron : creature.getGenome().getMotors()) {
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