public class Internal extends Neuron{
    private boolean meta; // does the internal neuron feed itself

    public Internal(int internalID){
        switch(internalID%2){
            case 0: this.meta = true; break;
            case 1: this.meta = false; break;
        }
    }

    public boolean isMeta(){
        return this.meta;
    }

}