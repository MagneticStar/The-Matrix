import java.util.HashMap;

public class Neuron{
    private HashMap<Neuron,Integer> sources = new HashMap<Neuron,Integer>();
    private HashMap<Neuron,Integer> sinks = new HashMap<Neuron,Integer>();

    public HashMap<Neuron,Integer> Sources(){
        return this.sources;
    }

    public HashMap<Neuron,Integer> Sinks(){
        return this.sinks;
    }

    public void addSource(Neuron neuron){
        this.sources.put(neuron,0);
    }

    public void addSink(Neuron neuron,int sinkWeight){
        this.sinks.put(neuron,sinkWeight);
    }
}