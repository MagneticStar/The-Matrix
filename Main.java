import java.util.ArrayList;

public class Main {
   

    // simFrame.add(simPanel);
    //     simFrame.setVisible(true);

    //     FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
    //     neuronMapPanel.setLayout(flowLayout);
    //     neuronMap.add(neuronMapPanel);
    //     neuronMap.setVisible(true);
    
    public static void main(String[] args) {
        // Add Food and Water
        Database.foodsList.add(new Food(new Coor(45, 45)));
        Database.foodsList.add(new Food(new Coor(50, 40)));
        Database.foodsList.add(new Food(new Coor(50, 40)));
        Database.foodsList.add(new Food(new Coor(70, 70)));
        Database.foodsList.add(new Food(new Coor(20, 70)));
        Database.foodsList.add(new Food(new Coor(90, 55)));
        Database.watersList.add(new Water(new Coor(90, 45)));
        Database.foodsList.add(new Food(new Coor(70, 20)));
        Database.watersList.add(new Water(new Coor(55, 59)));
        Database.watersList.add(new Water(new Coor(50, 50)));

        // Create creatureCount creatures
        for (int i = 0; i < Database.generationSize; i++) {
        Database.creaturesList.add(new Creature());
        }
    
        // Technical stuff Joey put here that I don't understand
        Screens.createScreens();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Run the simulation
        for(Database.currentGeneration = 0; Database.currentGeneration < Database.simulationLength; Database.currentGeneration++){
            // Gives every instantiated creature, food, and water a unique position
            populateSimulationSpace();
            // Gives every creature a color based on their composition of neurons
            Genome.calculateColor();

            // Simulate the Generation
            for (Database.currentGenerationTick = 0; Database.currentGenerationTick < Database.generationLength; Database.currentGenerationTick++) {
                tick(Screens.simulationPanel, Database.currentGenerationTick);
            }

            // Survival Criteria Check (Needs to be changed to hunger or something)
            ArrayList<Creature> newGeneration = new ArrayList<Creature>();
            for(Creature creature : Database.creaturesList){
                // Check whether they get to reproduce or not
                if(Database.random.nextDouble()>0.5){
                    newGeneration.add(creature.reproduce());
                }
            }
            // Fill the rest of the new generation with random creatures
            for(int i = newGeneration.size(); i<Database.generationSize; i++){
                newGeneration.add(new Creature());
            }
            Database.creaturesList = newGeneration;
        }
        
    }

    public static void tick(SimulationPanel panel, int i) {
        // movement loop
        for(Creature creature : Database.creaturesList){
            determineNeuronActivation(creature).motorMethod.invoke(creature);
        }
        // temporary killing mechanism for hunger
        for (int j = 0; j < Database.creaturesList.size(); j++) {
            Database.creaturesList.get(j).decrementHunger();
            if (Database.creaturesList.get(j).getHunger() < 0) {
                Database.creaturesList.remove(j);
                j--;
            }
        }
        panel.repaint();

        // Make tick wait
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // extra funcs

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
        
        // Try changing getMotors to getNeurons. Your behavior looks nicer but we should clear all neurons after each step, otherwise previous sensor input will interfere. 
        // The reason it appears that previous sensor input does in fact not interefere is because you are adding more and more to it which makes it more likely to activate it's sinks.
        // For example, if a sensor detecting food is close, it's continually building up over time but will get overtaken by a sensor detecting water if the creature gets close to that
        // water source because that build up is now higher. Like a competing armsrace, the numbers are continuing to stack up and up which makes it appear as if it's working
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

    private static void populateSimulationSpace(){
        // Generate all the possible starting positions
        ArrayList<Coor> startingPositions = new ArrayList<Coor>();
        for(int i = 0; i<Database.worldSize; i++){
            for(int j = 0; j<Database.worldSize; j++){
                startingPositions.add(new Coor(i, j));
            }
        }

        for(Creature creature : Database.creaturesList){
            creature.setPos(startingPositions.remove(Database.random.nextInt(0,startingPositions.size())));
        }
        for(Food food : Database.foodsList){
            food.setPos(startingPositions.remove(Database.random.nextInt(0,startingPositions.size())));
        }
        for(Water water : Database.watersList){
            water.setPos(startingPositions.remove(Database.random.nextInt(0,startingPositions.size())));
        }
    }
}