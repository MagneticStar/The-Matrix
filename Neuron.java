import java.util.HashMap;

public class Neuron{
    private HashMap<Neuron,Integer> sources = new HashMap<Neuron,Integer>();
    private HashMap<Neuron,Integer> sinks = new HashMap<Neuron,Integer>();
}