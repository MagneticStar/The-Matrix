import java.awt.Color;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Map;

public class Genome{
    public static final int genomeLength = Database.genomeLength;
    private static final int geneLength = 32;
    private int oscillatorPeriod = Database.random.nextInt(1,Database.generationLength+1);
    private BitSet DNA = new BitSet(geneLength * geneLength);
    public Creature creature;
    private Color color;
    private Neuron[] neurons;
    private ArrayList<Sensor> sensors = new ArrayList<Sensor>();
    private ArrayList<Motor> motors = new ArrayList<Motor>();
    private ArrayList<Internal> internals = new ArrayList<Internal>();

    private int BASESINKWEIGHT = 32;
    // Debug Vars

    public Genome(Creature creature){
        this.creature = creature;
        generateDNA(); 
        interpretDNA();
    }

    // Creates a new neuron map based on the inputed DNA
    public Genome(Creature creature, BitSet DNA){
        this.creature = creature;
        this.DNA = DNA;
        interpretDNA();
    }

    // Copies a Genome
    public Genome(Genome genome){
        this.creature = genome.creature;
        this.color = genome.getColor();
        this.oscillatorPeriod = genome.getOscillatorPeriod();
        this.sensors = genome.getSensors();
        this.internals = genome.getInternals();
        this.motors = genome.getMotors();
        this.neurons = genome.getNeurons();
        this.DNA = genome.getDNA();
    }

    public Color getColor(){
        return this.color;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public Neuron[] getNeurons() {
        return this.neurons;
    }
    public ArrayList<Sensor> getSensors(){
        return this.sensors;
    }
    public ArrayList<Motor> getMotors(){
        return this.motors;
    }
    public ArrayList<Internal> getInternals(){
        return this.internals;
    }
    public int getOscillatorPeriod(){
        return this.oscillatorPeriod;
    }
    public BitSet getDNA(){
        return this.DNA;
    }

    public static void calculateColor(){
        int[][] neuronCoordinates = new int[Database.generationSize][3];
        int[] averageNeuronCoordinate = {0,0,0};
        int[] coordinateDistanceMax = new int[3]; 
        int[] lowestNeuronCoordinate = {genomeLength,genomeLength,genomeLength};
        int[] highestNeuronCoordinate = {genomeLength,genomeLength,genomeLength};
        
        for(int i=0; i<Database.generationSize; i++){
            if (Database.creaturesList[i] != null) {
                int[] neuronListLengths = {Database.creaturesList[i].getGenome().getSensors().size(),Database.creaturesList[i].getGenome().getInternals().size(),Database.creaturesList[i].getGenome().getMotors().size()};
                for(int j = 0; j<3; j++){
                    neuronCoordinates[i][j] = neuronListLengths[j];
                    averageNeuronCoordinate[j]+=neuronCoordinates[i][j];
                    
                    if(neuronCoordinates[i][j] > highestNeuronCoordinate[j]){
                        highestNeuronCoordinate[j] = neuronCoordinates[i][j];
                    }
                    else if(neuronCoordinates[i][j] < lowestNeuronCoordinate[j]){
                        lowestNeuronCoordinate[j] = neuronCoordinates[i][j];
                    }
                }
            }
        }

        for(int i=0; i<3; i++){
            averageNeuronCoordinate[i] = averageNeuronCoordinate[i]/Database.generationSize; 
            coordinateDistanceMax[i] = ((highestNeuronCoordinate[i]-averageNeuronCoordinate[i])+(averageNeuronCoordinate[i]-lowestNeuronCoordinate[i]))/2; //JACKSON
        }

        for(int i=0; i<Database.creaturesList.length; i++){
            if (Database.creaturesList[i] != null) {
                int[] rgb = new int[3];
            for(int j =0; j<3; j++){
                double distance = (Math.abs(averageNeuronCoordinate[j]-neuronCoordinates[i][j])/(double)coordinateDistanceMax[j]);
                // double multiplier = 255 / (Math.abs(averageNeuronCoordinate[j]-neuronCoordinates[i][j])/(double)coordinateDistanceMax[j]); //JACKSON
                rgb[j] = (int) (255*(distance*3)); //JACKSON
                
                // Debug
                // System.out.println(10*(averageNeuronCoordinate[j]-neuronCoordinates[i][j])/(double)coordinateDistanceRange[j] * 10);
            }
            
            // Debug
            // System.out.println(rgb[0]+ "," + rgb[1] + "," + rgb[2]);

            Database.creaturesList[i].getGenome().setColor(new Color(Math.min(rgb[0],255), Math.min(rgb[1],255),Math.min(rgb[2],255)));
            Database.creaturesList[i].setColor(Database.creaturesList[i].getGenome().getColor());
            }
        }
    }

    private void generateDNA(){
        // Each DNA strand is comprised of genomeLength genes of size geneLength.
        // There are no spaces in each DNA String as geneLength can be used to find each gene mathematically.
        for (int i = 0; i < geneLength * genomeLength; i++) {
            DNA.set(i, Database.random.nextBoolean());
        }
    }

    private void interpretDNA(){

        // Each gene of geneLength in DNA is a neuron.

        // Example DNA String
        // Neuron      Source   Sink      Weight
        // [10][1010] [1][0101] [0][1010] [1010101010101010]

        // Source states which neuron feeds into this neuron. Multiple neurons may feed into another but only one will be specified as a source in each. 
        // The rest will be specified in sink of another neuron.
        // Sink states which neuron this neuron feeds into.
        // Weight states how strong the connection of the sink is.

        /////////////////////////////////////////
        // INTERPRET // INTERPRET // INTERPRET //
        /////////////////////////////////////////

        ArrayList<Neuron> emptyNeurons = new ArrayList<Neuron>(); // Contains neurons created as a byproduct that have yet to be initialized with values
        ArrayList<Neuron> neurons = new ArrayList<Neuron>();
        ArrayList<Neuron> sensors = new ArrayList<Neuron>();

        for(int i=0; i<genomeLength; i++){
            // Debug
            // Database.creaturesList.add(new Creature(new Genome(this.subject,this.DNA,neurons)));

            Neuron neuron;
            
            // parses the DNA by splicing it using the format described above
            BitSet gene = DNA.get(i, i+geneLength);
            
            int neuronType = 0;
            for (int j = 0; j < 2; j++) {
                if (gene.get(j)) {
                    neuronType += 1 * Math.pow(2, j);
                }
            }
            
            int neuronID = 0;
            for (int j = 2; j < 6; j++) {
                if (gene.get(j)) {
                    neuronID += 1 * Math.pow(2, j-2);
                }
            }
            int sourceType = 0;
            for (int j = 6; j < 7; j++) {
                if (gene.get(j)) {
                    sourceType += 1 * Math.pow(2, j-6);
                }
            }
            int sourceID = 0;
            for (int j = 7; j < 11; j++) {
                if (gene.get(j)) {
                    sourceID += 1 * Math.pow(2, j-7);
                }
            }
            int sinkType = 0;
            for (int j = 11; j < 12; j++) {
                if (gene.get(j)) {
                    sinkType += 1 * Math.pow(2, j-11);
                }
            }
            int sinkID = 0;
            for (int j = 12; j < 16; j++) {
                if (gene.get(j)) {
                    sinkID += 1 * Math.pow(2, j-12);
                }
            }
            int sinkWeight = 0;
            for (int j = 16; j < geneLength; j++) {
                if (gene.get(j)) {
                    sinkWeight+= 1 * Math.pow(2, j-16);
                }
            }
            
            if(neuronType <= 1){
                // Neuron is an internal neuron
                neuron = new Internal(neuronID%2);
                replaceEmptyNeuron(neuron, emptyNeurons);
                emptyNeurons.add(createSource(sourceType, sourceID, neuron));
                emptyNeurons.add(createSink(sinkType, sinkID, sinkWeight, neuron));
            }
            else if(neuronType == 2){
                // Neuron is a sensor neuron
                neuron = new Sensor(creature,neuronID);
                sensors.add(neuron);
                replaceEmptyNeuron(neuron, emptyNeurons);
                emptyNeurons.add(createSink(sinkType, sinkID, sinkWeight, neuron));
            }
            else{
                // Neuron is a motor neuron
                neuron = new Motor(neuronID);
                replaceEmptyNeuron(neuron, emptyNeurons);
                emptyNeurons.add(createSource(sourceType, sourceID, neuron));
            }

            neurons.add(neuron);
        }

        ////////////////////////////////////////////
        // FILL EMPTY // FILL EMPTY // FILL EMPTY //
        ////////////////////////////////////////////
        
        for(Neuron emptyNeuron:emptyNeurons){
            // Empty sensors and motors are valid and simply need to be added to the list, only internals need extra
            if(emptyNeuron instanceof Internal){
                // Debug
                // System.out.println("Empty "+emptyNeuron.toString());
                // System.out.print("Sources:");
                // for(Neuron source : emptyNeuron.getSources()){
                //     System.out.print(" ["+source.toString()+","+new ArrayList<Neuron>(source.getSinks().keySet()).toString()+"]");
                // }
                // System.out.println();
                // System.out.print("Sinks:");
                // for(Neuron sink : new ArrayList<>(emptyNeuron.getSinks().keySet())){
                //     System.out.print(" ["+sink.toString()+","+sink.getSources().toString()+"]");
                // }
                // System.out.println();

                if(emptyNeuron.getSources().size() == 0){
                    // Get a random neuron that isnt a motor
                    Neuron replacementNeuron = null;
                    for (Neuron n : neurons) {
                        if (!(n instanceof Motor)) {
                            replacementNeuron = n;
                        }
                    }
                    if (replacementNeuron == null) {
                        replacementNeuron = new Motor();
                    }
                    // Complete the incomplete internal neuron by giving it a valid source
                    emptyNeuron.addSource(replacementNeuron);
                    replacementNeuron.addSink(emptyNeuron, BASESINKWEIGHT);
                }
                else{
                    // Get a random neuron that isnt a sensor
                    Neuron replacementNeuron = null;
                    for (Neuron n : neurons) {
                        if (!(n instanceof Sensor)) {
                            replacementNeuron = n;
                        }
                    }
                    if (replacementNeuron == null) {
                        replacementNeuron = new Sensor(creature, BASESINKWEIGHT);
                    }
                    // Complete the incomplete internal neuron by giving it a valid sink (with a random sinkweight)
                    emptyNeuron.addSink(replacementNeuron, BASESINKWEIGHT);
                    replacementNeuron.addSource(emptyNeuron);
                }
            }
            else if(emptyNeuron.getClassType().equals("Sensor")){
                sensors.add(emptyNeuron);
            }
            neurons.add(emptyNeuron);
        }
        // Debug
        // Database.creaturesList.add(new Creature(new Genome(this.subject,this.DNA,neurons)));
        // for(Neuron neuron : neurons){
        //     if(findSourceSinkError(neuron,false,0)){
        //         System.out.println("Uh Ohh");
        //     }
        // }
        for(Neuron neuron : neurons){
            String toPrint = "";
            Boolean print = false;
            toPrint+="Subject "+Database.creaturesList.length+":\n";
            toPrint+="Neuron "+neuron.toString()+":\n";
            toPrint+="Sources:\n";
            for(Neuron source : neuron.getSources()){
                toPrint+=(" ["+source.toString()+","+new ArrayList<Neuron>(source.getSinks().keySet()).toString()+"]");
                print = !new ArrayList<Neuron>(source.getSinks().keySet()).contains(neuron);
            }
            toPrint+=("\n");
            toPrint+=("Sinks:\n");
            for(Neuron sink : new ArrayList<Neuron>(neuron.getSinks().keySet())){
                toPrint+=(" ["+sink.toString()+","+sink.getSources().toString()+"]");
                print = !sink.getSources().contains(neuron);
            }
            toPrint+=("\n");
            if(print){
                System.out.println(toPrint);
            }
        }

        /////////////////////////////////////////////////////
        // PRUNE USELESS // PRUNE USELESS // PRUNE USELESS //
        /////////////////////////////////////////////////////

        ArrayList<Neuron> inNeuronChain = new ArrayList<Neuron>();
        // Follow each neuron chain to find every chain that leads to a motor neuron. Any neurons not in those chains can then be pruned. We pass in 1 as the chain length because the sensor is included in the length of the chain.
        for(Neuron sensor : sensors){
            // Debug
            // System.out.println("New Sensor: "+sensor.toString());
            
            inNeuronChain.addAll(findNeuronChain(sensor,new ArrayList<Neuron>()));

            // Debug
            // System.out.println(inNeuronChain.toString());
        }
        // We pass the created arraylist through a hashset to remove duplicates
        HashSet<Neuron> duplicateFilter = new HashSet<Neuron>(inNeuronChain);
        inNeuronChain.clear();
        inNeuronChain.addAll(duplicateFilter);
        // Once all useful neurons are determined, prune the rest
        for(Neuron neuron:neurons){
            if(!inNeuronChain.contains(neuron)){
                for(Neuron source:neuron.getSources()){
                    source.removeSink(neuron);
                }
                for(Map.Entry<Neuron, Integer> sink:neuron.getSinks().entrySet()){
                    sink.getKey().removeSource(neuron);
                }
            }
        }

        // Converts the arraylist to an array
        this.neurons = new Neuron[inNeuronChain.size()];
        for (int i = 0; i < inNeuronChain.size(); i++) {
            this.neurons[i] = inNeuronChain.get(i);
            if(inNeuronChain.get(i) instanceof Sensor){
                this.sensors.add((Sensor) inNeuronChain.get(i));
            }
            else if(inNeuronChain.get(i) instanceof Motor){
                this.motors.add((Motor) inNeuronChain.get(i));
            }
            else{
                this.internals.add((Internal) inNeuronChain.get(i));
            }
        }
    }
    // Note: Both elses are redundant but I think it adds clarity
    private ArrayList<Neuron> findNeuronChain(Neuron neuron, ArrayList<Neuron> thisChain){
        ArrayList<Neuron> completeChain = new ArrayList<Neuron>();

        // Base Case 1
        if(thisChain.contains(neuron)){
            // neuron chain is a loop (returns empty loop)
            return completeChain;
        }
        else{
            thisChain.add(neuron);
        }

        for(Neuron sink : new ArrayList<Neuron>(neuron.getSinks().keySet())){
            completeChain.addAll(findNeuronChain(sink, thisChain));
        }
        // Debug
        // System.out.println(thisChain.toString()+" "+completeChain.toString());

        // Base Case 2 (above loop won't run if basecase 2 is true)
        if(neuron instanceof Motor){
            return thisChain;
        }
        else{
            
            return completeChain;
        }  
    }

    private Neuron createSource(int sourceType, int sourceID, Neuron neuron){
        Neuron source;
        // System.out.print("Creating source: ");
        if(sourceType == 0){
            
            // Source is an internal neuron
            source = new Internal(sourceID);
            // System.out.println("Internal");
        }
        else{
            // Source is a sensor neuron
            source = new Sensor(creature,sourceID);
            // System.out.println("Sensor");
        }

        neuron.addSource(source);
        source.addSink(neuron,0);
        return source;
    }

    private Neuron createSink(int sinkType, int sinkID, int sinkWeight, Neuron neuron){
        Neuron sink;
        // System.out.print("Creating sink: ");
        if(sinkType == 0){
            // Sink is an internal neuron
            sink = new Internal(sinkID);
            // System.out.println("Internal");
        }
        else{
            // Sink is a motor neuron
            sink = new Motor(sinkID);
            // System.out.println("Motor");
        }
        neuron.addSink(sink,sinkWeight);
        sink.addSource(neuron);
        
        return sink;
    }

    private ArrayList<Neuron> replaceEmptyNeuron(Neuron newNeuron, ArrayList<Neuron> emptyNeurons){
        for(int j=0; j<emptyNeurons.size();j++){
            // Checks if an empty neuron is the same type of this new neuron
            if(newNeuron.getClassType().equals(emptyNeurons.get(j).getClassType())){
                // Debug
                // System.out.println("\nOld Sinks: "+new ArrayList<Neuron>(emptyNeurons.get(j).getSinks().keySet()).toString()+"\nOld Sources: "+emptyNeurons.get(j).getSources().toString());
                
                // Deletes the prexisting empty neuron and adds its sources and sinks to this new neuron
                for(Neuron sink : new ArrayList<Neuron>(emptyNeurons.get(j).getSinks().keySet())){
                    newNeuron.addSink(sink, BASESINKWEIGHT);
                    sink.replaceSource(emptyNeurons.get(j), newNeuron);
                }
                for(Neuron source : emptyNeurons.get(j).getSources()){
                    newNeuron.addSource(source);
                    source.replaceSink(emptyNeurons.get(j), newNeuron);
                }
                emptyNeurons.remove(j);

                // Debug
                // System.out.println("\nNew Sinks: "+new ArrayList<Neuron>(newNeuron.getSinks().keySet()).toString()+"\nNew Sources: "+newNeuron.getSources().toString());
                
                break;
            }
        }
        return emptyNeurons;
    }
}