import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JPanel;

import java.util.BitSet;

public class Main {
    public static Database initial = new Database();
    public static Database loadedDatabase = initial;
    public static void main(String[] args) {
        if (loadedDatabase.LOADFILE){
            try {
                FileInputStream file = new FileInputStream(initial.fileName);
                ObjectInputStream in = new ObjectInputStream(file);
                
                loadedDatabase = (Database)in.readObject();
                for (int i = 0; i < loadedDatabase.creaturesList.length; i++) {
                    loadedDatabase.creaturesList[i] = new Creature((BitSet)in.readObject());
                }
                in.close();
            } catch (Exception e) {
                System.out.println(e);
                // Create generation 0
                for (int i = 0; i < loadedDatabase.creaturesList.length; i++) {
                    loadedDatabase.creaturesList[i] = new Creature();
                }
            }
        }
        else{
            // Create generation 0
            for (int i = 0; i < loadedDatabase.creaturesList.length; i++) {
                loadedDatabase.creaturesList[i] = new Creature();
            }
        }

        Screens.createScreens();

        startSimulation();
    }


    private static void tick(JPanel panel, int i) {
        // movement loop
        for(int j = 0; j < Main.loadedDatabase.creaturesList.length; j++){
            if (Main.loadedDatabase.creaturesList[j] != null) {
                Creature creature = loadedDatabase.creaturesList[j];
                // Do the action
                determineNeuronActivation(creature).motorMethod.invoke(creature);
                // Save Tick Data for viewing
                loadedDatabase.creatureColorsForAllTicks[loadedDatabase.currentGenerationTick][creature.getPosX()][creature.getPosY()] = creature.getColor();
            }
        }
        // Save Tick Data for viewing
        loadedDatabase.foodLocationsForAllTicks[loadedDatabase.currentGenerationTick] = loadedDatabase.foodLocations;
        panel.repaint();
        Screens.guiPanel.updateLabel();
        Screens.simulationSplitPane.setDividerLocation(1000);
    }

    private static void startSimulation() {

        for(loadedDatabase.currentGeneration = 0; loadedDatabase.currentGeneration < loadedDatabase.simulationLength; loadedDatabase.currentGeneration++){
            Screens.brainPanel.repaint();

            // Gives every instantiated creature and food a unique position
            populateSimulationSpace();
            // Gives every creature a color based on their composition of neurons
            Genome.calculateColor();
            // Reset Creature and Food Location Records
            loadedDatabase.creatureColorsForAllTicks = new Color[loadedDatabase.generationLength+1][loadedDatabase.worldSize][loadedDatabase.worldSize];
            loadedDatabase.foodLocationsForAllTicks = new int[loadedDatabase.generationLength+1][loadedDatabase.worldSize][loadedDatabase.worldSize];
            
            // Simulate the Generation
            for (loadedDatabase.currentGenerationTick = 0; loadedDatabase.currentGenerationTick < loadedDatabase.generationLength; loadedDatabase.currentGenerationTick++) {
                tick(loadedDatabase.visualPanel, loadedDatabase.currentGenerationTick);
                // slow down for viewing
                try {
                    Thread.sleep(20);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            if (loadedDatabase.saveAndExit) {
                try {
                    FileOutputStream file = new FileOutputStream(loadedDatabase.fileName);
                    ObjectOutputStream out = new ObjectOutputStream(file);
                    out.writeObject(new Database());
                    for (Creature c : loadedDatabase.creaturesList) {
                        out.writeObject(c.getGenome().getDNA());
                    }
                    out.close();
                    System.exit(0);
                } catch (Exception e) {
                    System.out.println("Save failed: "+e);
                }
            }
            
            loadedDatabase.generationFinished = true;
            // Wait till next generation should be run
            while(true){
                if(loadedDatabase.autoStartGeneration || loadedDatabase.startNextGeneration){
                    break;
                }
                Screens.simulationPanel.repaint();
            }
            loadedDatabase.startNextGeneration = false;

            int reproductionCount = 0;
            ArrayList<Creature> newGeneration = new ArrayList<Creature>();
            for(int i = 0; i < loadedDatabase.creaturesList.length; i++){
                if (loadedDatabase.creaturesList[i] != null){
                    // Survival Criteria Check
                    Creature survivalCheckCreature = loadedDatabase.creaturesList[i];
                    boolean survives = survivalCheckCreature.getFoodCount() > loadedDatabase.minimumFoodEaten && 
                    survivalCheckCreature.getMoveCount() < Main.loadedDatabase.maximumDistanceMoved;
                    

                    if(survives){
                        reproductionCount++;
                        Creature[] temp = loadedDatabase.creaturesList[i].reproduce();
                        for (int j = 0; j < temp.length; j++) {
                            if (temp[j] != null)
                            newGeneration.add(temp[j]);
                        }
                    }
                }
            }

            loadedDatabase.reproducedLastGeneration = reproductionCount;
            loadedDatabase.foodEatenLastGeneration = loadedDatabase.currentFoodCount;

            // Debug
            // System.out.println(reproductionCount+" creatures reproduced!");
            
            for (int i = 0; i < loadedDatabase.creaturesList.length; i++) {
                if (newGeneration.size() != 0) {
                    loadedDatabase.creaturesList[i] = (Creature)newGeneration.get((i + newGeneration.size()) % newGeneration.size()).clone();
                }
                else {
                    loadedDatabase.creaturesList[i] = new Creature(); 
                }
            }
        }
    }

    
    // calculates the signal values of a creatures neuron map and returns a motor neuron to activate
    private static Motor determineNeuronActivation(Creature creature){
        Sensor[] checkedSensors = new Sensor[Sensor.numberOfSensorMethods];
        for(Sensor sensor : creature.getGenome().getSensors()){
            if(checkedSensors[sensor.methodID] == null){
                // This sensor type hasnt been run yet for this creature
                try {
                    sensor.addValue(sensor.sensorMethod.invoke(creature));
                } catch (Exception e) {
                    // will throw exceptions if an object is not found or if there is a NullPointerException
                    sensor.addValue(0.0);
                }
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
        }
    }

    private static void populateSimulationSpace(){
        // Generate all the possible starting positions
        ArrayList<Coor> startingPositions = new ArrayList<Coor>();
        for(int x = 0; x<loadedDatabase.worldSize; x++){
            for(int y = 0; y<loadedDatabase.worldSize; y++){
                startingPositions.add(new Coor(x, y));
            }
        }
        
        loadedDatabase.creatureLocations = new int[loadedDatabase.worldSize][loadedDatabase.worldSize];
        loadedDatabase.foodLocations = new int[loadedDatabase.worldSize][loadedDatabase.worldSize];
        loadedDatabase.currentFoodCount = loadedDatabase.startingFoodCount;

        for(int i = 0; i < loadedDatabase.creaturesList.length; i++){
            if (loadedDatabase.creaturesList[i] != null) {
                Coor coor = startingPositions.remove(loadedDatabase.random.nextInt(0,startingPositions.size()));
                loadedDatabase.creaturesList[i].setPos(coor);
                loadedDatabase.creatureLocations[loadedDatabase.creaturesList[i].getPosX()][loadedDatabase.creaturesList[i].getPosY()]+=1;
            }
        }

        for(int i = 0; i < loadedDatabase.startingFoodCount; i++){
            Coor coor = startingPositions.remove(loadedDatabase.random.nextInt(0,startingPositions.size()));
            loadedDatabase.foodLocations[coor.x()][coor.y()]++;
        }
    }
}