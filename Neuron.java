import java.util.HashMap;
import java.awt.Color;
import java.util.ArrayList;

public class Neuron extends screenObject{
    private ArrayList<Neuron> sources = new ArrayList<Neuron>();
    private HashMap<Neuron,Integer> sinks = new HashMap<Neuron,Integer>();
    private String type;
    public Neuron(String type) {
        
        super(Color.white, new Coor(0, 0));
        switch(type){
            case "Sensor": this.setPosX(50);
                           this.type = type;
            break;
            case "Internal": this.setPosX(100);
                           this.type = type;
            break;
            case "Motor": this.setPosX(150);
                           this.type = type;
            break;
        }
    }
    public ArrayList<Neuron> getSources(){
        return this.sources;
    }

    public HashMap<Neuron,Integer> getSinks(){
        return this.sinks;
    }
    
    public String getClassType() {
        return type;
    }

    public void addSource(Neuron neuron){
        this.sources.add(neuron);
    }

    public void addSink(Neuron neuron,int sinkWeight){
        this.sinks.put(neuron,sinkWeight);
    }

    public void replaceSource(Neuron initial, Neuron replacement){
        this.sources.set(this.sources.indexOf(initial),replacement);
    }

    public void replaceSink(Neuron initial, Neuron replacement){
        this.sinks.put(replacement, this.sinks.get(initial));
        this.sinks.remove(initial);
    }
}