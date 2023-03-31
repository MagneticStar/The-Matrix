import java.util.HashMap;
import java.util.ArrayList;

public class Neuron{
    private ArrayList<Neuron> sources = new ArrayList<Neuron>();
    private HashMap<Neuron,Integer> sinks = new HashMap<Neuron,Integer>();

    public ArrayList<Neuron> getSources(){
        return this.sources;
    }

    public HashMap<Neuron,Integer> getSinks(){
        return this.sinks;
    }

    public void addSource(Neuron neuron){
        this.sources.add(neuron);
    }

    public void addSink(Neuron neuron,int sinkWeight){
        this.sinks.put(neuron,sinkWeight);
    }
}