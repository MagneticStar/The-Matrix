import java.util.HashMap;
import java.awt.Color;
import java.util.ArrayList;

public class Neuron extends screenObject{
    private ArrayList<Neuron> sources = new ArrayList<Neuron>();
    private HashMap<Neuron,Integer> sinks = new HashMap<Neuron,Integer>();
    private String type;
    private double value;


    public Neuron() {
        
        super(Color.white, new Coor(0, 0));
        if (this instanceof Sensor) {
            this.type = "Sensor";
        }
        if (this instanceof Internal) {
            this.type = "Internal";
        }
        
        if (this instanceof Motor) {
            this.type = "Motor";
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
    public double getValue() {
        return value;
    }

    public void addSource(Neuron neuron){
        this.sources.add(neuron);
    }

    public void addSink(Neuron neuron,int sinkWeight){
        this.sinks.put(neuron,sinkWeight);
    }
<<<<<<< Updated upstream
=======

    public void replaceSource(Neuron initial, Neuron replacement){
        this.sources.set(this.sources.indexOf(initial),replacement);
    }

    public void replaceSink(Neuron initial, Neuron replacement){
        this.sinks.put(replacement, this.sinks.get(initial));
        this.sinks.remove(initial);
    }
    public void setValue(double value) {
        this.value = value;
    }
>>>>>>> Stashed changes
}