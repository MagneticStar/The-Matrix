import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.BitSet;

public class Main {
    public static Database initial = new Database();
    public static Database loaded = initial;
    public static void main(String[] args) {
        boolean SerialInput = false;
        if (SerialInput){
            try {
                FileInputStream file = new FileInputStream(initial.fileName);
                ObjectInputStream in = new ObjectInputStream(file);
                
                loaded = (Database)((Database)in.readObject()).clone();
                for (int i = 0; i < loaded.creaturesList.length; i++) {
                    loaded.creaturesList[i] = new Creature((BitSet)in.readObject());
                }
                in.close();
            } catch (Exception e) {
                System.out.println(e);
                // Create generation 0
                for (int i = 0; i < loaded.creaturesList.length; i++) {
                    loaded.creaturesList[i] = new Creature();
                }
            }
        }
        else{
            // Create generation 0
            for (int i = 0; i < loaded.creaturesList.length; i++) {
                loaded.creaturesList[i] = new Creature();
            }
        }

        Screens.createScreens();
    }

    public static void tick(JPanel panel, int i) {
        // movement loop
        for(int j = 0; j < Main.loaded.creaturesList.length; j++){
            if (Main.loaded.creaturesList[j] != null) {
                determineNeuronActivation(Main.loaded.creaturesList[j]).motorMethod.invoke(Main.loaded.creaturesList[j]);
            }
        }
        panel.repaint();
        Screens.guiPanel.updateLabel();
        Screens.splitPane.setDividerLocation(1000);
        // try {
        //     Thread.sleep(20);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }

    public static void startSimulation() {
        Screens.mainPanelManager.show(Screens.mainPanel, "simulation");
        
        for(loaded.currentGeneration = 0; loaded.currentGeneration < loaded.simulationLength; loaded.currentGeneration++){
            Screens.brainPanel.repaint();
            // Debug
            // System.out.println("Generation: "+(Database.currentGeneration+1));

            // Gives every instantiated creature and food a unique position
            populateSimulationSpace();
            // Gives every creature a color based on their composition of neurons
            Genome.calculateColor();

            // Simulate the Generation
            for (loaded.currentGenerationTick = 0; loaded.currentGenerationTick < loaded.generationLength; loaded.currentGenerationTick++) {
                tick(loaded.visualPanel, loaded.currentGenerationTick);
            }

            if (loaded.saveAndExit) {
                Screens.mainPanelManager.show(Screens.mainPanel, "save");
                try {
                    FileOutputStream file = new FileOutputStream(loaded.fileName);
                    ObjectOutputStream out = new ObjectOutputStream(file);
                    out.writeObject(new Database());
                    for (Creature c : loaded.creaturesList) {
                        out.writeObject(c.getGenome().getDNA());
                    }
                    out.close();
                    System.exit(0);
                } catch (Exception e) {
                    System.out.println("Save failed: "+e);
                }
            }

            loaded.generationFinished = true;
            // Wait till next generation should be run
            while(true){
                if(loaded.autoStartGeneration || loaded.startNextGeneration){
                    break;
                }
                Screens.simulationPanel.repaint();
            }
            loaded.startNextGeneration = false;

            int reproductionCount = 0;
            ArrayList<Creature> newGeneration = new ArrayList<Creature>();
            for(int i = 0; i < loaded.creaturesList.length; i++){
                if (loaded.creaturesList[i] != null){
                    // Survival Criteria Check
                    Creature survivalCheckCreature = loaded.creaturesList[i];
                    boolean survives = survivalCheckCreature.getFoodCount() > loaded.minimumFoodEaten && 
                    survivalCheckCreature.getMoveCount() > 10 &&
                    survivalCheckCreature.getMoveCount() < 100;
                    

                    if(survives){
                        reproductionCount++;
                        Creature[] temp = loaded.creaturesList[i].reproduce();
                        for (int j = 0; j < temp.length; j++) {
                            if (temp[j] != null)
                            newGeneration.add(temp[j]);
                        }
                    }
                }
            }
            loaded.reproducedLastGeneration.add(reproductionCount);

            // Debug
            // System.out.println(reproductionCount+" creatures reproduced!");
            
            for (int i = 0; i < loaded.creaturesList.length; i++) {
                if (newGeneration.size() != 0) {
                    loaded.creaturesList[i] = (Creature)newGeneration.get((i + newGeneration.size()) % newGeneration.size()).clone();
                }
                else {
                    loaded.creaturesList[i] = new Creature(); 
                }
            }
        }
    }

    // extra funcs

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
        for(int x = 0; x<loaded.worldSize; x++){
            for(int y = 0; y<loaded.worldSize; y++){
                startingPositions.add(new Coor(x, y));
            }
        }
        
        loaded.creatureLocations = new int[loaded.worldSize][loaded.worldSize];
        loaded.foodLocations = new int[loaded.worldSize][loaded.worldSize];
        loaded.currentFoodCount = loaded.startingFoodCount;

        for(int i = 0; i < loaded.creaturesList.length; i++){
            if (loaded.creaturesList[i] != null) {
                Coor coor = startingPositions.remove(loaded.random.nextInt(0,startingPositions.size()));
                loaded.creaturesList[i].setPos(coor);
                loaded.creatureLocations[loaded.creaturesList[i].getPosX()][loaded.creaturesList[i].getPosY()]+=1;
            }
        }

        for(int i = 0; i < loaded.startingFoodCount; i++){
            Coor coor = startingPositions.remove(loaded.random.nextInt(0,startingPositions.size()));
            loaded.foodLocations[coor.x()][coor.y()]+=1;
        }
    }
}