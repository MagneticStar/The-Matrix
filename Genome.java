import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

public class Genome{
    private final int genomeLength = 8;
    private final int geneLength = 32;
    private String DNA;
    private Subject subject;
    private Neuron[] neurons;
    private Color color;

    public Genome(Subject subject){
        this.subject = subject;
        generateDNA(); // sets this.DNA to a random binary String
        calculateColor(); // sets this.color to RGB values based on the content of this.DNA
        interpretDNA();
    }

    public Color getColor(){
        return this.color;
    }
    public Neuron[] getNeurons() {
        return neurons;
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

        Random rand = new Random();
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

        ArrayList<Neuron> emptyNeurons = new ArrayList<Neuron>(); // Contains neurons created as a byproduct that have yet to be initialized with values
        ArrayList<Neuron> neurons = new ArrayList<Neuron>();

        for(int i=0; i<genomeLength; i++){
            Neuron neuron;
            String DNASlice = DNA.substring(i*geneLength,(i+1)*geneLength);

            // parses the DNA by splicing it using the format described above
            int neuronType = Integer.parseInt(DNASlice.substring(0,2),2);
            int neuronID = Integer.parseInt(DNASlice.substring(2,6),2);
            int sourceType = Integer.parseInt(DNASlice.substring(6,7),2);
            int sourceID = Integer.parseInt(DNASlice.substring(7,11),2);
            int sinkType = Integer.parseInt(DNASlice.substring(11,12),2);
            int sinkID = Integer.parseInt(DNASlice.substring(12,16),2);
            int sinkWeight = Integer.parseInt(DNASlice.substring(16,geneLength),2);


            if(neuronType == 0 || neuronType == 2){
                // Neuron is an internal neuron
                neuron = new Internal(neuronID%2);
                emptyNeurons.add(createSource(sourceType, sourceID, neuron));
                emptyNeurons.add(createSink(sinkType, sinkID, sinkWeight, neuron));
            }
            else if(neuronType == 1){
                // Neuron is a sensor neuron
                neuron = new Sensor(subject,neuronID);
                emptyNeurons.add(createSink(sinkType, sinkID, sinkWeight, neuron));
            }
            else{
                // Neuron is a motor neuron
                neuron = new Motor(neuronID);
                emptyNeurons.add(createSource(sourceType, sourceID, neuron));
            }

            if(emptyNeurons.size()>0){
                for(int j=0; j<emptyNeurons.size();j++){
                    // Checks if an empty neuron is the same type of this new neuron
                    if(neuron.getClassType() == (emptyNeurons.get(j).getClassType())){
                        // Sets the prexisting empty neuron to this new neuron
                        for(Neuron sink : emptyNeurons.get(j).getSinks().keySet()){
                            neuron.addSink(sink, emptyNeurons.get(j).getSinks().get(sink));
                            sink.replaceSink(sink, neuron);
                        }
                        for(Neuron source : emptyNeurons.get(j).getSources()){
                            neuron.addSource(source);
                            source.replaceSource(source, neuron);
                        }
                        emptyNeurons.remove(j);
                    }
                }
                
            }
            
            
            neurons.add(neuron);
        }
        // Converts the arraylist to an array
        this.neurons = new Neuron[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            this.neurons[i] = neurons.get(i);
        }
    }

    private Neuron createSource(int sourceType, int sourceID, Neuron neuron){
        Neuron source;
        
        if(sourceType == 0){
            // Source is an internal neuron
            source = new Internal(sourceID);
        }
        else{
            // Source is a sensor neuron
            source = new Sensor(subject,sourceID);
        }

        neuron.addSource(source);
        source.addSink(neuron,0);
        return source;
    }

    private Neuron createSink(int sinkType, int sinkID, int sinkWeight, Neuron neuron){
        Neuron sink;
        if(sinkType == 0){
            // Sink is an internal neuron
            sink = new Internal(sinkID);
        }
        else{
            // Sink is a motor neuron
            sink = new Motor(sinkID);
        }
        neuron.addSink(sink,sinkWeight);
        sink.addSource(neuron);
        
        return sink;
    }
}