import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class Genome{
    private final int genomeLength = 8;
    private final int geneLength = 32;
    private final Random rand = new Random();
    private String DNA;
    public Subject subject;
    private Color color;
    private Neuron[] neurons;
    private ArrayList<Neuron> sensors = new ArrayList<Neuron>();
    // Debug Vars
    // public ArrayList<Integer> neuronChainLengths = new ArrayList<Integer>();
    

    public Genome(Subject subject){
        this.subject = subject;
        generateDNA(); // sets this.DNA to a random binary String
        calculateColor(); // sets this.color to RGB values based on the content of this.DNA
        interpretDNA();
    }

    public Genome(Subject subject, String DNA, ArrayList<Neuron> neurons){
        this.subject = subject;
        this.neurons = new Neuron[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            this.neurons[i] = neurons.get(i);
            if(neurons.get(i).getClassType().equals("Sensor")){
                this.sensors.add(neurons.get(i));
            }
        }
        this.DNA = DNA;
        calculateColor();
    }

    public Color getColor(){
        return this.color;
    }
    public Neuron[] getNeurons() {
        return neurons;
    }
    public ArrayList<Neuron> getSensors(){
        return sensors;
    }

    private void calculateColor(){
        // The color is calculated by splicing the DNA into three equal segments
        // (in cases where the length is not divisible by 3, the last 1 or 2 bits are dropped)
        // then that binary string is converted to a number and it's range is reduced from
        // 0-2^segmentLength to 0-256 based on it's position within the first range

        // int segmentLength = (DNA.length()-DNA.length()%3)/3;
        // int segmentOne = (int) (Integer.parseInt(DNA.substring(0, segmentLength+1),2)/(Math.pow(2,segmentLength)));
        // int segmentTwo = (int) (Integer.parseInt(DNA.substring(segmentLength+1, segmentLength*2+1),2)/(Math.pow(2,segmentLength)));
        // int segmentThree = (int) (Integer.parseInt(DNA.substring(segmentLength*2+1, segmentLength*3+1),2)/(Math.pow(2,segmentLength)));

        // color = new Color(segmentOne,segmentTwo,segmentThree);
        color = Color.yellow;
    }

    private void generateDNA(){
        // Each DNA strand is comprised of genomeLength genes of size geneLength.
        // There are no spaces in each DNA String as geneLength can be used to find each gene mathematically.

        DNA = "";

        for (int i = 0; i < genomeLength; i++) {
            for(int j=0; j<geneLength; j++){
                DNA+=rand.nextInt(0,2);
            }
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
            // Main.subs.add(new Subject(new Genome(this.subject,this.DNA,neurons)));

            Neuron neuron;
            
            // parses the DNA by splicing it using the format described above
            String gene = DNA.substring(i*geneLength,(i+1)*geneLength);
            int neuronType = Integer.parseInt(gene.substring(0,2),2);
            int neuronID = Integer.parseInt(gene.substring(2,6),2);
            int sourceType = Integer.parseInt(gene.substring(6,7),2);
            int sourceID = Integer.parseInt(gene.substring(7,11),2);
            int sinkType = Integer.parseInt(gene.substring(11,12),2);
            int sinkID = Integer.parseInt(gene.substring(12,16),2);
            int sinkWeight = Integer.parseInt(gene.substring(16,geneLength),2);

            // Debug
            // System.out.println("");
            
            if(neuronType <= 1){
                // Neuron is an internal neuron
                neuron = new Internal(neuronID%2);
                replaceEmptyNeuron(neuron, emptyNeurons);
                emptyNeurons.add(createSource(sourceType, sourceID, neuron));
                emptyNeurons.add(createSink(sinkType, sinkID, sinkWeight, neuron));
            }
            else if(neuronType == 2){
                // Neuron is a sensor neuron
                neuron = new Sensor(subject,neuronID);
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
            // Debug
            // System.out.println(i+1 +" "+neurons.toString()+" "+emptyNeurons.toString());
        }

        // Debug
        // Main.subs.add(new Subject(new Genome(this.subject,this.DNA,neurons)));
        
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
                    Neuron randomNeuron = neurons.get(rand.nextInt(neurons.size()));
                    while(randomNeuron instanceof Motor){
                        randomNeuron = neurons.get(rand.nextInt(neurons.size()));
                    }
                    // Complete the incomplete internal neuron by giving it a valid source
                    emptyNeuron.addSource(randomNeuron);
                    randomNeuron.addSink(emptyNeuron, rand.nextInt(0,(int)Math.pow(2, 16)));
                }
                else{
                    // Get a random neuron that isnt a sensor
                    Neuron randomNeuron = neurons.get(rand.nextInt(neurons.size()));
                    while(randomNeuron instanceof Sensor){
                        randomNeuron = neurons.get(rand.nextInt(neurons.size()));
                    }
                    // Complete the incomplete internal neuron by giving it a valid sink (with a random sinkweight)
                    emptyNeuron.addSink(randomNeuron, rand.nextInt(0,(int)Math.pow(2, 16)));
                    randomNeuron.addSource(emptyNeuron);
                }

                // Debug
                // System.out.println("Complete "+emptyNeuron.toString());
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
            }
            else if(emptyNeuron.getClassType().equals("Sensor")){
                sensors.add(emptyNeuron);
            }
            neurons.add(emptyNeuron);
        }
        // Debug
        // Main.subs.add(new Subject(new Genome(this.subject,this.DNA,neurons)));
        for(Neuron neuron : neurons){
            String toPrint = "";
            Boolean print = false;
            toPrint+="Subject "+Main.subs.size()+":\n";
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
            if(inNeuronChain.get(i).getClassType().equals("Sensor")){
                this.sensors.add(inNeuronChain.get(i));
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
            source = new Sensor(subject,sourceID);
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
                    newNeuron.addSink(sink, rand.nextInt(0,(int)Math.pow(2, geneLength-16)));
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