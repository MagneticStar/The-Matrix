import java.util.HashMap;
import java.awt.Color;
import java.util.ArrayList;

public class Neuron extends screenObject{
    private ArrayList<Neuron> sources = new ArrayList<Neuron>();
    private HashMap<Neuron,Integer> sinks = new HashMap<Neuron,Integer>();
    private int type;
    public Neuron(int type) {
        
        super(Color.white, new Coor(0, 0));
        if (this instanceof Sensor) {
            this.setPosX(50);
            this.type = type;
        }
        if (this instanceof Internal) {
            this.setPosX(100);
        }
        
        if (this instanceof Motor) {
            this.setPosX(150);
        }
    }
    public ArrayList<Neuron> getSources(){
        return this.sources;
    }

    public HashMap<Neuron,Integer> getSinks(){
        return this.sinks;
    }
    public int getClassType() {
        return type;
    }

    public void addSource(Neuron neuron){
        this.sources.add(neuron);
    }

    public void addSink(Neuron neuron,int sinkWeight){
        this.sinks.put(neuron,sinkWeight);
    }
}