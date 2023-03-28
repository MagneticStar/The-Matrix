public class Internal extends Neuron{
    private boolean meta; // feeds itself?

    public Internal(boolean feedsItself){
        this.meta = feedsItself;
    }

    public boolean isMeta(){
        return this.meta;
    }

}