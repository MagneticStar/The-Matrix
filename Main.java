import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
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
            // Debug
            System.out.println("Generation: "+(Database.currentGeneration+1));
            
            // Repopulate with food and water
            while(Database.foodsList.size()<Database.startingFoodCount){
                Database.foodsList.add(new Food());
            }
            while(Database.watersList.size()<Database.startingWaterCount){
                Database.watersList.add(new Water());
            }

            // Gives every instantiated creature, food, and water a unique position
            populateSimulationSpace();
            // Gives every creature a color based on their composition of neurons
            Genome.calculateColor();

            // Simulate the Generation
            for (Database.currentGenerationTick = 0; Database.currentGenerationTick < Database.generationLength; Database.currentGenerationTick++) {
                tick(Screens.simulationPanel, Database.currentGenerationTick);
            }

            // Survival Criteria Check
            ArrayList<Creature> newGeneration = new ArrayList<Creature>();
            for(Creature creature : Database.creaturesList){
                // Check whether they get to reproduce or not
                if(creature.getFoodCount()>=Database.minimumFoodEaten){
                    newGeneration.addAll(creature.reproduce());
                }
            }
            // Debug
            System.out.println(newGeneration.size()+" creatures reproduced!");

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
        panel.repaint();

        // Time between ticks
        if(Database.currentGeneration > 90){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // extra funcs

    private static Motor determineNeuronActivation(Creature creature){
        Sensor[] checkedSensors = new Sensor[Sensor.numberOfSensorMethods];
        for(Sensor sensor : creature.getGenome().getSensors()){
            if(checkedSensors[sensor.methodID] == null){
                // This sensor type hasnt been run yet for this creature
                sensor.addValue(sensor.sensorMethod.invoke(creature));
                checkedSensors[sensor.methodID] = sensor;
            }
            else{
                // This sensor type has been run for this creature
                sensor.addValue(checkedSensors[sensor.methodID].getValues().get(0));
            }

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

        // Debug
        // System.out.println(highestValueMotor.getMaxValue());

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

        Database.creatureCoordinates = new ArrayList<Coor>();
        Database.waterCoordinates = new ArrayList<Coor>();
        Database.foodCoordinates = new ArrayList<Coor>();
        
        for(Creature creature : Database.creaturesList){
            Coor coor = startingPositions.remove(Database.random.nextInt(0,startingPositions.size()));
            creature.setPos(coor);
            Database.creatureCoordinates.add(coor);
        }
        for(Food food : Database.foodsList){
            Coor coor = startingPositions.remove(Database.random.nextInt(0,startingPositions.size()));
            food.setPos(coor);
            Database.foodCoordinates.add(coor);
        }
        for(Water water : Database.watersList){
            Coor coor = startingPositions.remove(Database.random.nextInt(0,startingPositions.size()));
            water.setPos(coor);
            Database.waterCoordinates.add(coor);
        }
    }
}