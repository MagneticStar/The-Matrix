import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JPanel;
import java.util.BitSet;

public class Main {
    public static void main(String[] args) {

        startSimulation();
    }

    public static void tick(JPanel panel, int i) {
        // movement loop
        for(int j = 0; j < Database.creaturesList.length; j++){
            if (Database.creaturesList[j] != null) {
                determineNeuronActivation(Database.creaturesList[j]).motorMethod.invoke(Database.creaturesList[j]);
            }
        }
        
        // Time between ticks
        panel.repaint();
        Screens.splitPane.setDividerLocation(650);
        // try {
        //     Thread.sleep(20);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        Screens.guiPanel.updateLabel();
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

        Screens.createScreens();
        
        for(Database.currentGeneration = 0; Database.currentGeneration < Database.simulationLength; Database.currentGeneration++){
            Screens.brainPanel.repaint();
            // Debug
            System.out.println("Generation: "+(Database.currentGeneration+1));

            Screens.graphPanel.repaint();
                
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
                tick(Database.visualPanel, Database.currentGenerationTick);
            }
            if (Database.saveAndExit) {
                try {
                    FileOutputStream file = new FileOutputStream("t.tmp");
                    ObjectOutputStream out = new ObjectOutputStream(file);
                    for (Creature c : Database.creaturesList) {
                        out.writeObject(c.getGenome().getDNA());
                    }
                    out.close();

                    System.exit(0);
                } catch (Exception e) {
                    System.out.println("Save failed: "+e);
                }
            }

            Database.generationFinished = true;
            // Wait till next generation should be run
            while(true){
                if(Database.autoStartGeneration || Database.startNextGeneration){
                    break;
                }
                
                Screens.simulationPanel.repaint();
            }
            Database.startNextGeneration = false;

            int reproductionCount = 0;
            ArrayList<Creature> newGeneration = new ArrayList<Creature>();
            for(int i = 0; i < Database.creaturesList.length; i++){
                if (Database.creaturesList[i] != null){
                    // Survival Criteria Check
                    Creature survivalCheckCreature = Database.creaturesList[i];
                    boolean survives = survivalCheckCreature.getFoodCount() >= Database.minimumFoodEaten && 
                    survivalCheckCreature.getMoveCount() > 10;
                    
                    if(survives){
                        reproductionCount++;
                        Creature[] temp = Database.creaturesList[i].reproduce();
                        for (int j = 0; j < temp.length; j++) {
                            if (temp[j] != null)
                            newGeneration.add(temp[j]);
                        }
                    }

                }
            }
            Database.reproducedLastGeneration.add(reproductionCount);

            // Debug
            System.out.println(reproductionCount+" creatures reproduced!");
            
            for (int i = 0; i < Database.creaturesList.length; i++) {
                if (newGeneration.size() != 0) {
                    Database.creaturesList[i] = (Creature)newGeneration.get((i + newGeneration.size()) % newGeneration.size()).clone();
                }
                else {
                    Database.creaturesList[i] = new Creature(); 
                }
            }
            int amount = 0;
            for (Creature c : Database.creaturesList) {
                if (c != null) {amount++;}
            }
            System.out.println(amount);
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