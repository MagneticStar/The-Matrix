<<<<<<< HEAD
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.BitSet;

=======
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.BitSet;
>>>>>>> main
public class Main {
    public static void main(String[] args) {
        startSimulation();
    }

    public static void tick(SimulationPanel panel, int i) {
        // movement loop
        for(int j = 0; j < Database.creaturesList.length; j++){
            if (Database.creaturesList[j] != null) {
                determineNeuronActivation(Database.creaturesList[j]).motorMethod.invoke(Database.creaturesList[j]);
            }
        }
<<<<<<< HEAD
        // Do Visuals check
=======
        // // temporary killing mechanism for hunger
        // for (int j = 0; j < Database.creaturesList.size(); j++) {
        //     Database.creaturesList.get(j).decrementHunger();
        //     if (Database.creaturesList.get(j).getHunger() < 0) {
        //         Database.creaturesList.remove(j);
        //         j--;
        //     }
        // }
        
        // Time between ticks
>>>>>>> main
        if(Database.doVisuals){
            panel.repaint();
        }
        Screens.guiPanel.updateLabel();
    }
<<<<<<< HEAD

    public static void startSimulation() {
        boolean SerialInput = false;
=======
    public static void startSimulation() {
        // ADD SERIALIZATION CHECK HERE
        boolean SerialInput = true;
>>>>>>> main
        if (SerialInput){
            try {
                FileInputStream file = new FileInputStream("t.tmp");
                ObjectInputStream in = new ObjectInputStream(file);
<<<<<<< HEAD
                for (int i = 0; i < Database.creaturesList.length; i++) {
                    Database.creaturesList[i] = new Creature((BitSet)in.readObject());
=======
                for (int i = 0; i < Database.generationSize; i++) {
                    Database.creaturesList.add(new Creature((BitSet)in.readObject()));
>>>>>>> main
                }
                in.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
        else{
            // Create generation 0
<<<<<<< HEAD
            for (int i = 0; i < Database.creaturesList.length; i++) {
                Database.creaturesList[i] = new Creature();
            }
            // for (Creature c : Database.creaturesList) {
            //     System.out.println(c);
            // }
=======
            for (int i = 0; i < Database.generationSize; i++) {
                Database.creaturesList.add(new Creature());
            }
>>>>>>> main
        }

        Screens.createScreens();
        
        for(Database.currentGeneration = 0; Database.currentGeneration < Database.simulationLength; Database.currentGeneration++){
            Screens.brainPanel.repaint();
            // Debug
            System.out.println("Generation: "+(Database.currentGeneration+1));
                
            // Repopulate with food
<<<<<<< HEAD
            for (int i = 0; i < Database.foodsList.length; i++) {
                Database.foodsList[i] = new Food();
=======
            while(Database.foodsList.size()<Database.amountOfFood){
                Database.foodsList.add(new Food());
>>>>>>> main
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
<<<<<<< HEAD
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
=======
>>>>>>> main
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
            ArrayList<Creature> newGeneration = new ArrayList<Creature>();
            for(Creature creature : Database.creaturesList){
                // Survival Criteria Check
                if(creature.getFoodCount() >= Database.minimumFoodEaten){
                    reproductionCount++;
                    newGeneration.addAll(creature.reproduce());
                }
            }
            Database.reproducedLastGeneration = reproductionCount;

            // Debug
<<<<<<< HEAD
            System.out.println(newGeneration.length+" creatures reproduced!");
=======
            System.out.println(reproductionCount+" creatures reproduced!");

>>>>>>> main
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
<<<<<<< HEAD
        
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
=======

        Database.foodLocations = new int[Database.worldSize][Database.worldSize];
        Database.creatureLocations = new int[Database.worldSize][Database.worldSize];
        
        for(Creature creature : Database.creaturesList){
            Coor coor = startingPositions.remove(Database.random.nextInt(0,startingPositions.size()));
            creature.setPos(coor);
            Database.creatureLocations[creature.getPosX()][creature.getPosY()]+=1;
        }
        for(Food food : Database.foodsList){
            Coor coor = startingPositions.remove(Database.random.nextInt(0,startingPositions.size()));
            food.setPos(coor);
            Database.foodLocations[food.getPosX()][food.getPosY()]+=1;
>>>>>>> main
        }
    }
}