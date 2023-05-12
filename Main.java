import java.util.ArrayList;
import java.util.BitSet;

public class Main {
   
    public static void main(String[] args) {
        Screens.createScreens();
        simulation();
    }

    public static void tick(SimulationPanel panel, int i) {
        // movement loop
        for(int j = 0; j < Database.creaturesList.length; j++){
            if (Database.creaturesList[j] != null) {
                determineNeuronActivation(Database.creaturesList[j]).motorMethod.invoke(Database.creaturesList[j]);
            }
        }
        // Do Visuals check
        if(Database.doVisuals){
            panel.repaint();
        }
    }

    public static void startSimulation() {
        boolean SerialInput = false;
        if (SerialInput){
            try {
                FileInputStream file = new FileInputStream("t.tmp");
                ObjectInputStream in = new ObjectInputStream(file);
                for (int i = 0; i < Database.creaturesList.length; i++) {
                    Database.creaturesList[i] = new Creature((BitSet)in.readObject());
                }
                in.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
        else{
            // Create generation 0
            for (int i = 0; i < Database.creaturesList.length; i++) {
                Database.creaturesList[i] = new Creature();
            }
            // for (Creature c : Database.creaturesList) {
            //     System.out.println(c);
            // }
        }
        // Run the simulation
        for(Database.currentGeneration = 0; Database.currentGeneration < Database.simulationLength; Database.currentGeneration++){
            Screens.brainPanel.repaint();
            // Debug
            System.out.println("Generation: "+(Database.currentGeneration+1));
            
            // Repopulate with food
            for (int i = 0; i < Database.foodsList.length; i++) {
                Database.foodsList[i] = new Food();
            }

            // Gives every instantiated creature and food a unique position
            populateSimulationSpace();
            // Gives every creature a color based on their composition of neurons
            Genome.calculateColor();

            // Simulate the Generation
            for (Database.currentGenerationTick = 0; Database.currentGenerationTick < Database.generationLength; Database.currentGenerationTick++) {
                tick(Screens.simulationPanel, Database.currentGenerationTick);
            }
            // Serialize the genes for a generation
            if (false) {
                try {
                    FileOutputStream file = new FileOutputStream("t.tmp");
                    ObjectOutputStream out = new ObjectOutputStream(file);
                    for (Creature c : Database.creaturesList) {
                        out.writeObject(c.getGenome().getDNA());
                    }
                    out.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            Database.generationFinished = true;
            // Wait till next generation should be run
            while(true){
                if(Database.autoStartGeneration || Database.startNextGeneration){
                    break;
                }
                
                Screens.simulationPanel.repaint();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Database.startNextGeneration = false;

            int reproductionCount = 0;
            Creature[] newGeneration = new Creature[Database.generationSize];
            for(int i = 0; i < Database.creaturesList.length; i++){
                if (Database.creaturesList[i] != null){
                    // Survival Criteria Check
                    if(Database.creaturesList[i].getFoodCount() >= Database.minimumFoodEaten){
                        reproductionCount++;
                        Creature[] temp = Database.creaturesList[i].reproduce();
                        for (int j = 0; j < temp.length; j++) {
                            newGeneration[j] = temp[j];
                        }
                    }
                }
            }

            // Debug
            System.out.println(newGeneration.size()+" creatures reproduced!");
            System.out.println(bestPerformance);
            // Fill the rest of the new generation with random creatures
            for(int i = 0; i< newGeneration.length; i++){
                if (newGeneration[i] == null){
                newGeneration[i] = new Creature();
                }
            }
            for (int i = 0; i < Database.creaturesList.length; i++) {
                Database.creaturesList[i] = newGeneration[i];
            }
            // for (Creature c : Database.creaturesList) {
            //     System.out.println(c);
            // }
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
        
        for(int i = 0; i < Database.creaturesList.length; i++){
            if (Database.creaturesList[i] != null) {
                Coor coor = startingPositions.remove(Database.random.nextInt(0,startingPositions.size()));
                Database.creaturesList[i].setPos(coor);
                Database.creatureLocations[Database.creaturesList[i].getPosX()][Database.creaturesList[i].getPosY()]+=1;
            }
        }
        for(int i = 0; i < Database.foodsList.length; i++){
            if (Database.foodsList[i] != null) {
                Coor coor = startingPositions.remove(Database.random.nextInt(0,startingPositions.size()));
                Database.foodsList[i].setPos(coor);
                Database.foodLocations[Database.foodsList[i].getPosX()][Database.foodsList[i].getPosY()]+=1;
            }
        }
    }
}