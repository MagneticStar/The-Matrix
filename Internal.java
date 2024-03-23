public class Internal extends Neuron{
<<<<<<< Updated upstream
    private boolean meta; // does the internal neuron feed itself

    public Internal(int internalID){
=======
    public Internal(){
>>>>>>> Stashed changes
        super("Internal");
        switch(internalID%2){
            case 0: this.meta = true; break;
            case 1: this.meta = false; break;
        }
    }

    public boolean isMeta(){
        return this.meta;
    }
}