import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.BitSet;

public class Main {
    public static void main(String[] args) {
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
        }

        Screens.createScreens();
        Screens.switchSimulationScreen();
    }

    public static void tick(JPanel panel, int i) {
        // movement loop
        for(int j = 0; j < Database.creaturesList.length; j++){
            if (Database.creaturesList[j] != null) {
                determineNeuronActivation(Database.creaturesList[j]).motorMethod.invoke(Database.creaturesList[j]);
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
        
        for(Database.currentGeneration = 0; Database.currentGeneration < Database.simulationLength; Database.currentGeneration++){
            Screens.brainPanel.repaint();
            // Debug
            // System.out.println("Generation: "+(Database.currentGeneration+1));

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
                    survivalCheckCreature.getMoveCount() >= 10;
                    

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
            // System.out.println(reproductionCount+" creatures reproduced!");
            
            for (int i = 0; i < Database.creaturesList.length; i++) {
                if (newGeneration.size() != 0) {
                    Database.creaturesList[i] = (Creature)newGeneration.get((i + newGeneration.size()) % newGeneration.size()).clone();
                }
                else {
                    Database.creaturesList[i] = new Creature(); 
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
        for(int x = 0; x<Database.worldSize; x++){
            for(int y = 0; y<Database.worldSize; y++){
                startingPositions.add(new Coor(x, y));
            }
        }
        
        Database.creatureLocations = new int[Database.worldSize][Database.worldSize];
        Database.foodLocations = new int[Database.worldSize][Database.worldSize];
        Database.currentFoodCount = Database.startingFoodCount;

        for(int i = 0; i < Database.creaturesList.length; i++){
            if (Database.creaturesList[i] != null) {
                Coor coor = startingPositions.remove(Database.random.nextInt(0,startingPositions.size()));
                Database.creaturesList[i].setPos(coor);
                Database.creatureLocations[Database.creaturesList[i].getPosX()][Database.creaturesList[i].getPosY()]+=1;
            }
        }

        for(int i = 0; i < Database.startingFoodCount; i++){
            Coor coor = startingPositions.remove(Database.random.nextInt(0,startingPositions.size()));
            Database.foodLocations[coor.x()][coor.y()]+=1;
        }
    }
}