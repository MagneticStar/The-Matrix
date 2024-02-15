import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;
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

    @SuppressWarnings("rawtypes")
    public static void startThread() {
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected String doInBackground() throws Exception {
                startSimulation();
                return "a";
            }
            @Override protected void done() {

            }
        };
        swingWorker.execute();
    }

    public static void tick() {
        // This tick has never run before and needs to be calculated
        if(loaded.currentGenerationTick == loaded.observedGenerationTick){
            for(int j = 0; j < Main.loaded.creaturesList.length; j++){
                if(Main.loaded.creaturesList[j] == null){
                    continue;
                }
                Creature creature = loaded.creaturesList[j];
                // Do the action
                determineNeuronActivation(creature).motorMethod.invoke(creature);
                // Save Tick Data for viewing
                loaded.creatureColorsForAllTicks[loaded.currentGenerationTick][creature.getPosX()][creature.getPosY()] = creature.getColor();
            }
            // Save Tick Data for viewing
            loaded.foodLocationsForAllTicks[loaded.currentGenerationTick] = loaded.foodLocations;
        }

        loaded.visualPanel.repaint();
        Screens.guiPanel.updateLabel();
        Screens.simulationSplitPane.setDividerLocation(1000);
    }

    public static void startSimulation() {
        Screens.setContent("Simulation");

        for(loaded.currentGeneration = 0; loaded.currentGeneration < loaded.simulationLength; loaded.currentGeneration++){
            Screens.brainPanel.repaint();

            // Gives every instantiated creature and food a unique position
            populateSimulationSpace();
            // Gives every creature a color based on their composition of neurons
            Genome.calculateColor();
            // Reset Creature and Food Location Records
            loaded.creatureColorsForAllTicks = new Color[loaded.generationLength+1][loaded.worldSize][loaded.worldSize];
            loaded.foodLocationsForAllTicks = new int[loaded.generationLength+1][loaded.worldSize][loaded.worldSize];
            
            // Simulate the Generation
            loaded.observedGenerationTick = 0;
            runSimulationLoop(true);

            if (loaded.saveAndExit) {
                Screens.setContent("Save");
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
                if(loaded.currentGenerationTick != loaded.observedGenerationTick){
                    runSimulationLoop(false);
                }
                Main.loaded.visualPanel.repaint();
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

            Screens.guiPanel.stepSelectorComboBox.removeAllItems();
        }   
    }

    public static void runSimulationLoop(boolean newTicks){
        // Calculating New Ticks
        if(newTicks){
            for (loaded.currentGenerationTick = loaded.observedGenerationTick; loaded.currentGenerationTick < loaded.generationLength; loaded.currentGenerationTick++) {
                // A different tick has been selected
                if(loaded.selectedTick){
                    loaded.selectedTick = false;
                    runSimulationLoop(false);
                    return;
                }

                loaded.observedGenerationTick = loaded.currentGenerationTick;
                Screens.guiPanel.stepSelectorComboBox.addItem(String.valueOf(loaded.currentGenerationTick));
                Screens.guiPanel.stepSelectorComboBox.setSelectedIndex(loaded.currentGenerationTick);
                tick();
                loaded.stepFinished = true;
            
                // Wait till next step should be run
                while(true){
                    if(loaded.autoStartStep || loaded.startNextStep){
                        break;
                    }
                    Main.loaded.visualPanel.repaint();
                }
                loaded.startNextStep = false;
    
                // Tick Delay
                try{
                    TimeUnit.MILLISECONDS.sleep(loaded.tickDelay);
                }
                catch(Exception e){}
            }
        }
        // Replaying old ticks
        else{
            for (loaded.observedGenerationTick = loaded.observedGenerationTick; loaded.observedGenerationTick < loaded.currentGenerationTick; loaded.observedGenerationTick++) {
                tick();
                loaded.stepFinished = true;

                Screens.guiPanel.stepSelectorComboBox.setSelectedIndex(loaded.observedGenerationTick);
            
                // Wait till next step should be run
                while(true){
                    if(loaded.autoStartStep || loaded.startNextStep){
                        break;
                    }
                    Main.loaded.visualPanel.repaint();
                }
                loaded.startNextStep = false;
    
                // Tick Delay
                try{
                    TimeUnit.MILLISECONDS.sleep(loaded.tickDelay);
                }
                catch(Exception e){}
            }
            runSimulationLoop(true);
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
            loaded.foodLocations[coor.x()][coor.y()]++;
        }
    }
}