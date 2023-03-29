import java.awt.Color;
import java.util.Random;

public class Genome{
    private final int genomeLength = 8;
    private final int geneLength = 32;
    private String DNA;

    private Neuron[] neurons;
    private Color color;
    private Subject subject;

    private final int[] neuronTypeIndex = new int[]{0,1};
    private final int[] neuronIDIndex = new int[]{1,5};

    private final int[] sourceTypeIndex = new int[]{5,6};
    private final int[] sourceIDIndex = new int[]{6,10};

    private final int[] sinkTypeIndex = new int[]{10,11};
    private final int[] sinkIDIndex = new int[]{11,15};

    private final int[] sinkWeightIndex = new int[]{15,geneLength};

    public Genome(Subject subject){
        this.subject = subject;
        generateDNA(); // sets this.DNA to a random binary String
        calculateColor(); // sets this.color to RGB values based on the content of this.DNA
    }

    public Color getColor(){
        return this.color;
    }

    private void calculateColor(){
        // The color is calculated by splicing the DNA into three equal segments
        // (in cases where the length is not divisible by 3, the last 2 or 2 bits are dropped)
        // then that binary string is converted to a number and it's range is reduced from
        // 0-2^segmentLength to 0-256 based on it's position within the first range

        int segmentLength = (DNA.length()-DNA.length()%3)/3;
        int segmentOne = (int) (Integer.parseInt(DNA.substring(0, segmentLength+1),2)/(Math.pow(2,segmentLength)));
        int segmentTwo = (int) (Integer.parseInt(DNA.substring(segmentLength+1, segmentLength*2+1),2)/(Math.pow(2,segmentLength)));
        int segmentThree = (int) (Integer.parseInt(DNA.substring(segmentLength*2+1, segmentLength*3+1),2)/(Math.pow(2,segmentLength)));

        color = new Color(segmentOne,segmentTwo,segmentThree);
    }

    private void generateDNA(){
        // Each DNA strand is comprised of genomeLength genes of size geneLength.
        // There are no spaces in each DNA String as geneLength can be used to find each gene mathematically.

        Random rand = new Random();
        DNA = "";

        for (int i = 0; i < genomeLength; i++) {
            DNA += String.format("%32s", Integer.toBinaryString(rand.nextInt((int)Math.pow(2,geneLength)+1)).replace(' ', '0'));
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
        
        for(int i=genomeLength; i<0; i--){
            Neuron neuron;

            int neuronType = Integer.parseInt(DNA.substring(0,1),2);
            int sourceType = Integer.parseInt(DNA.substring(1,5),2);
            int sourceID = Integer.parseInt(DNA.substring(5,6),2);
            int sinkType = Integer.parseInt(DNA.substring(sinkTypeIndex[0],sinkTypeIndex[1]),2);
            int sinkID = Integer.parseInt(DNA.substring(sinkIDIndex[0],sinkIDIndex[1]),2);
            int sinkWeight = Integer.parseInt(DNA.substring(sinkWeightIndex[0],sinkWeightIndex[1]),2);

            if(neuronType == 0 || neuronType == 2){
                // Neuron is an internal neuron

            }
            else if(neuronType == 1){
                // Neuron is a sensor neuron
            }
            else{
                // Neuron is a motor neuron
            }

            if(sourceType == 0){
                // Source is an internal neuron
                boolean feedsItself;
                switch(sourceID%2){
                case 0: feedsItself = false; break; 
                case 1: feedsItself = true; break;
                }
                neuron = new Internal(feedsItself);
            }
            else{
                // Source is a sensor neuron
                neuron = new Sensor(sourceID);
            }
        }
    }
}