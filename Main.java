import java.util.ArrayList;

public class Main {
   
    public static void main(String[] args) {
        // Add Food
        for (int i = 0; i < Database.amountOfFood; i++) {
            Database.foodsList.add(new Food());
        }
        // Create creatureCount creatures
        for (int i = 0; i < Database.generationSize; i++) {
        Database.creaturesList.add(new Creature());
        }
    
        Screens.createScreens();

        // Run the simulation
        for(Database.currentGeneration = 0; Database.currentGeneration < Database.simulationLength; Database.currentGeneration++){
            // Debug
            System.out.println("Generation: "+(Database.currentGeneration+1));
            
            // Repopulate with food
            while(Database.foodsList.size()<Database.startingFoodCount){
                Database.foodsList.add(new Food());
            }

            // Gives every instantiated creature, food, and water a unique position
            populateSimulationSpace();
            // Gives every creature a color based on their composition of neurons
            Genome.calculateColor();

            // Simulate the Generation
            for (Database.currentGenerationTick = 0; Database.currentGenerationTick < Database.generationLength; Database.currentGenerationTick++) {
                tick(Screens.simulationPanel, Database.currentGenerationTick);
            }

            int avhung = 0;
            for (Creature creature : Database.creaturesList) {
                // avhung+=creature.getHunger();
            }
            avhung/=Database.creaturesList.size();

            // Survival Criteria Check (Needs to be changed to hunger or something)
            ArrayList<Creature> newGeneration = new ArrayList<Creature>();
            
            for(Creature creature : Database.creaturesList){
                // Check whether they get to reproduce or not
                // if(creature.getHunger() > avhung+1){
                //     newGeneration.add(creature.reproduce());
                // if(creature.getFoodCount()>=Database.minimumFoodEaten){
                //     newGeneration.addAll(creature.reproduce());
                // }
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
        // // temporary killing mechanism for hunger
        // for (int j = 0; j < Database.creaturesList.size(); j++) {
        //     Database.creaturesList.get(j).decrementHunger();
        //     if (Database.creaturesList.get(j).getHunger() < 0) {
        //         Database.creaturesList.remove(j);
        //         j--;
        //     }
        // }
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
    }
}