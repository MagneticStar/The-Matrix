public class Sensor extends Neuron{

    private Subject subject;
    private double output;

    public Sensor(Subject subject) {
        this.subject = subject;
    }

    public double getOutput() {
        return output;
    }

    public double distance(Obj obj) {
        // using Pyth theorem
        return Math.sqrt(Math.pow(obj.getPos().x() - subject.getPos().x(), 2) + Math.pow(obj.getPos().y() - subject.getPos().y(), 2));
    }
    
}