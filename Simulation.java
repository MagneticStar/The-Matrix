import java.awt.Color;
import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

public class Simulation implements Cloneable, Serializable {
    //////////////////////////////////////////////////////////
    // Simulation // Simulation // Simulation // Simulation //
    //////////////////////////////////////////////////////////

    public static Simulation simulation;

    /**
     * How many creatures should there be at the start of a new generation
     */
    public int generationSize = 100;
    /**
     * How much food should be initially created
     */
    public int startingFoodCount = 20;
    /**
     * The minimum number of food a creature must eat to reproduce at the end of a generation
     */
    public int minimumFoodEaten = 1; 
    /**
     * How many ticks each generation is
     */
    public int generationLength = 100;
    /**
     * How many generations there should be
     */
    public int simulationLength = 100;
    /**
     * The size of the square world
     */
    public int worldSize = 128;
    /**
     * The amount of world spaces that the sensor searchs for food or creatures as a radius
     */
    public int searchDepth = 10;
    /**
     * The amount of creatures a creature makes when reproducing
     */
    public int repoductionPerCreature = 1;
    /**
     * The chance of mutation, must be between 0 and 1 (inclusive)
     */
    public double mutationChance = 0.05;
    /**
     * The average chance of mutation
     */
    public double bitMutationAverage = (1.08665/Math.pow(mutationChance,0.531384)-0.0435476);
    /**
     * The average number of neurons in a creature
     */
    public int genomeLength = 32;

    //////////////////////////////////////////////////
    // Trackers // Trackers // Trackers // Trackers //
    //////////////////////////////////////////////////

    /**
     * How many ticks have passed simulation generation
     */
    public int currentGenerationTick;
    /**
     * Which tick, <= currentGenerationTick, is being shown in the simulation frame
     */
    public int observedGenerationTick;
    /**
     * How many generations have passed simulation simulation
     */
    public int currentGeneration; 
    /**
     * Stores positions and objects for everything in the simulation world
     */
    public WorldObjectArray worldObjects = new WorldObjectArray(generationSize, startingFoodCount,worldSize,generationLength);
    /**
     * The number of creatures that reproduced at the end of a given generation
     */
    public ArrayList<Integer> reproducedLastGeneration = new ArrayList<Integer>(Arrays.asList(0));

    //////////////////////////////////////////////////////////////////////
    // User Settings // User Settings // User Settings // User Settings //
    //////////////////////////////////////////////////////////////////////

    /**
     * If the simulation should be displayed
     */
    public boolean doVisuals = true;
    /**
     * If the start generation button has been pressed
     */
    public boolean startNextGeneration = false;
    /**
     * If the start step button has been pressed
     */
    public boolean startNextStep = false;
    /**
     * If the next generation should automatically start when the calculations for the previous one has finished
     */
    public boolean autoStartGeneration = true;
    /**
     * If the next step should automatically start when the calculations for the previous one has finished
     */
    public boolean autoStartStep = true;
    /**
     * If all calculations for a generation have finished (then the next generation can start)
     */
    public boolean generationFinished = false;
    /**
     * If all calculations for a step have finished (then the next step can start)
     */
    public boolean stepFinished = false;
    /**
     * If the save and exit button has been pressed
     */
    public boolean saveAndExit = false;
    /**
     * If a step, other than the current one, has been selected in the step selector combo box
     */
    public boolean selectedTick = false;

    //////////////////////////////////////////////////////////////////
    // Brain Screen // Brain Screen // Brain Screen // Brain Screen //
    //////////////////////////////////////////////////////////////////

    /**
     * How length of the brain screen
     */
    public int brainScreenSizeX = 30;
    /**
     * How height of the brain screen
     */
    public int brainScreenSizeY = genomeLength*2;

    //////////////////////////////////////////////
    // Visuals // Visuals // Visuals // Visuals //
    //////////////////////////////////////////////
    
    /**
     * The background color of the simulation space
     */
    public Color SIMULATION_SCREEN_COLOR = Color.white;
    /**
     * Which panel is being displayed
     */
    public JPanel visualPanel = null;
    /**
     * The amount of time, in miliseconds, artifically added between ticks to make it more viewable. Note that tick delay does not apply during replay.
     */
    public long tickDelay = 16; 
    /**
     * The color for all food
     */
    public Color FOOD_COLOR = Color.red;

    //////////////////////////////////////////
    // Random // Random // Random // Random //
    //////////////////////////////////////////
    
    /**
     * The random object used for all random calculations
     */
    public Random random = new Random();
    
    //////////////////////////////////////////
    // Serial // Serial // Serial // Serial //
    //////////////////////////////////////////

    /**
     * The name of the file that the simulation data is stored in 
     */
    public String fileName = "x.tmp";
    /**
     * If a file should be simulation instead of creating a new, random set of creatures
     */
    public boolean LOADFILE = true;

    public Simulation(){
        
    }
     
     @Override
     public Object clone() throws CloneNotSupportedException {
          return super.clone();  
     }
     public static void main(String[] args){
        simulation = new Simulation();
    }
    
    
    //TODO
    public void startSimulation(String[] args) {
        // Create generation 0
        for (int i = 0; i < this.generationSize; i++) {
            this.worldObjects.add(new Creature(), "Creature");
        }
        Screens.createScreens();
    }
    
    @SuppressWarnings("rawtypes")
    public void startThread() {
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
    
    public void tick() {
        // simulation tick has never run before and needs to be calculated
        // TODO: Check if it recalculates the last tick every time it replays
        if(this.currentGenerationTick == this.observedGenerationTick){
            for(int i = 0; i < this.generationSize; i++){
                if(this.worldObjects.getCreature(i) == null){
                    System.out.println("C");
                    continue;
                }
                Creature creature = this.worldObjects.getCreature(i);
                // Do the action
                determineNeuronActivation(creature).motorMethod.invoke(creature);
            }
            // Save all food and creature tick data for replay
            this.worldObjects.save();
        }
    
        this.visualPanel.repaint();
        Screens.guiPanel.updateLabel();
        Screens.simulationSplitPane.setDividerLocation(1000);
    }
    
    public void startSimulation() {
        Screens.setContent("Simulation");
    
        for(this.currentGeneration = 0; this.currentGeneration < this.simulationLength; this.currentGeneration++){
            Screens.brainPanel.repaint();
    
            // Gives every instantiated creature and food a unique position
            populateSimulationSpace();
            // Gives every creature a color based on their composition of neurons
            Genome.calculateColor();
            
            // Simulate the Generation
            this.observedGenerationTick = 0;
            runSimulationLoop(true);
    
            if (this.saveAndExit) {
                System.exit(0);
            }
            
            this.generationFinished = true;
            // Wait till next generation should be run
            while(true){
                if(this.autoStartGeneration || this.startNextGeneration){
                    break;
                }
                if(this.selectedTick){
                    this.selectedTick = false;
                    runSimulationLoop(false);
                }
                this.visualPanel.repaint();
            }
            this.startNextGeneration = false;
    
            int reproductionCount = 0;
            ArrayList<Creature> newGeneration = new ArrayList<Creature>();
            for(int i = 0; i < this.generationSize; i++){
                Creature creature = this.worldObjects.getCreature(i);
                if(creature == null){
                    continue;
                }
                
                // Survival Criteria Check
                boolean survives = creature.getFoodCount() > this.minimumFoodEaten && creature.getMoveCount() > 10 && creature.getMoveCount() < 100;
                
                if(survives){
                    reproductionCount++;
                    for(Creature c : creature.reproduce()){
                        if(c == null){
                            continue;
                        }
                        newGeneration.add(c);
                    }
                }
            }
            this.reproducedLastGeneration.add(reproductionCount);
    
            // Debug
            // System.out.println(reproductionCount+" creatures reproduced!");
    
            // Reset Creatures and Food and their records
            this.worldObjects.removeAll();
            
            for (int i = 0; i < this.generationSize; i++) {
                if (newGeneration.size() != 0) {
                    this.worldObjects.add((Creature)newGeneration.get((i + newGeneration.size()) % newGeneration.size()).clone(),"creature");
                }
                else {
                    this.worldObjects.add(new Creature(),"creature"); 
                }
            }
    
            Screens.guiPanel.stepSelectorComboBox.removeAllItems();
        }   
    }
    
    public void runSimulationLoop(boolean newTicks){
        // Calculating New Ticks
        if(newTicks){
            for (this.currentGenerationTick = this.observedGenerationTick; this.currentGenerationTick < this.generationLength; this.currentGenerationTick++) {
                // A different tick has been selected
                if(this.selectedTick){
                    this.selectedTick = false;
                    runSimulationLoop(false);
                    return;
                }
    
                this.observedGenerationTick = this.currentGenerationTick;
                Screens.guiPanel.stepSelectorComboBox.addItem(String.valueOf(this.currentGenerationTick));
                tick();
                this.stepFinished = true;
            
                // Wait till next step should be run
                while(true){
                    if(this.autoStartStep || this.startNextStep){
                        break;
                    }
                    this.visualPanel.repaint();
                }
                this.startNextStep = false;
    
                // Tick Delay
                try{
                    TimeUnit.MILLISECONDS.sleep(this.tickDelay);
                }
                catch(Exception e){}
            }
        }
        // Replaying old ticks
        else{
            for (; this.observedGenerationTick < this.currentGenerationTick; this.observedGenerationTick++) {
                System.out.println("Old ticks");
                tick();
                this.stepFinished = true;
            
                // Wait till next step should be run
                while(true){
                    if(this.autoStartStep || this.startNextStep){
                        break;
                    }
                    this.visualPanel.repaint();
                }
                this.startNextStep = false;
    
                // Tick Delay
                try{
                    TimeUnit.MILLISECONDS.sleep(this.tickDelay);
                }
                catch(Exception e){}
            }
            runSimulationLoop(true);
        }
        
    }
    
    // extra funcs
    
    private Motor determineNeuronActivation(Creature creature){
        Sensor[] checkedSensors = new Sensor[Sensor.numberOfSensorMethods];
        for(Sensor sensor : creature.getGenome().getSensors()){
            if(checkedSensors[sensor.methodID] == null){
                // simulation sensor type hasnt been run yet for simulation creature
                try {
                    sensor.addValue(sensor.sensorMethod.invoke(creature));
                } catch (Exception e) {
                    // will throw exceptions if an object is not found or if there is a NullPointerException
                    sensor.addValue(0.0);
                }
                checkedSensors[sensor.methodID] = sensor;
            }
            else{
                // simulation sensor type has been run for simulation creature
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
    
    private void iterateThroughNeuronChain(Neuron neuron){
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
    
    private void populateSimulationSpace(){
        // Generate all the possible starting positions
        ArrayList<Coor> startingPositions = new ArrayList<Coor>();
        for(int x = 0; x<this.worldSize; x++){
            for(int y = 0; y<this.worldSize; y++){
                startingPositions.add(new Coor(x, y));
            }
        }
    
        for(int i = 0; i < this.generationSize; i++){
            Creature creature = this.worldObjects.getCreature(i);
            if(creature == null){
                continue;
            }
            
            Coor coor = startingPositions.remove(this.random.nextInt(0,startingPositions.size()));
            creature.setPos(coor);
        }
    
        for(int i = 0; i < this.startingFoodCount; i++){
            Coor position = startingPositions.remove(this.random.nextInt(0,startingPositions.size()));
            this.worldObjects.add(new ScreenObject(position),"Food");
        }
    }
}


