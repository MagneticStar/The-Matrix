import java.util.HashMap;
import java.awt.Color;
import java.util.ArrayList;

public abstract class Neuron extends ScreenObject{
    private ArrayList<Neuron> sources = new ArrayList<Neuron>();
    private HashMap<Neuron,Integer> sinks = new HashMap<Neuron,Integer>();
    private String type;
    private ArrayList<Double> values = new ArrayList<Double>();

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

    public ArrayList<Double> getValues() {
        return values;
    }
    public void addValue(Double value) {
        this.values.add(value);
    }
    public void clearValues() {
            values.clear();
    }

    public void addSource(Neuron neuron){
        this.sources.add(neuron);
    }

    public void addSink(Neuron neuron,int sinkWeight){
        this.sinks.put(neuron,sinkWeight);
    }

    public void removeSource(Neuron neuron){
        this.sources.remove(neuron);
    }

    public void removeSink(Neuron neuron){
        this.sinks.remove(neuron);
    }

    public void replaceSource(Neuron initial, Neuron replacement){
        this.sources.set(this.sources.indexOf(initial),replacement);
    }

    public void replaceSink(Neuron initial, Neuron replacement){
        this.sinks.put(replacement, this.sinks.get(initial));
        this.sinks.remove(initial);
    }

    public Coor getPrintPos() {
        Screens.brainWorldToScreen.setWorld(Screens.brainPanel.getWidth(), Screens.brainPanel.getHeight());
        int[] ans = Screens.brainWorldToScreen.translate(this.getPos().matrix());
        return new Coor(ans[0], ans[1]);
    }
}