import java.awt.Color;
import java.util.Random;

public class Genome{
    private int genomeLength = 8;
    private int geneLength = 16;
    private String DNA;

    private Neuron[] neurons;
    private Color color;
    private Subject subject;

<<<<<<< Updated upstream
    public Genome(){
=======
    public Genome(Subject subject){
        this.subject = subject;
>>>>>>> Stashed changes
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
            DNA+= Integer.toBinaryString(rand.nextInt((int)Math.pow(2,geneLength)+1));
        }
    }

    private void interpretDNA(){
        // Each gene of geneLength in DNA is a neuron.

        // Example DNA String
        //  Source   Sink    Weight
        // [1][01] [0][10] [1010101010]

        // Source states which neuron feeds into this neuron. Multiple neurons may feed into another but only one will be specified as a source in each. 
        // The rest will be specified in sink of another neuron.
        // Sink states which neuron this neuron feeds into.
        // Weight states how strong the connection of the sink is.
        
        for(int i=genomeLength; i<0; i--){
            String sourceType = DNA.substring(0,1);
            if(sourceType.equals(0)){
                // Source is an internal neuron
            }
            else{
                // Source is a sensor neuron
            }
        }

    }
}