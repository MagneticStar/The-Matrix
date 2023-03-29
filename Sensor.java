import java.util.HashMap;

public class Sensor extends Neuron{

    private Subject subject;
    private double output;

    public Sensor(Subject subject) {
        this.subject = subject;
    }

    public double getOutput() {
        return output;
    }

    public void detectFood() {

    }
    public void findObjs(HashMap<Coor, Obj> map, Coor pos) {

        Coor Opos = new Coor();

        for (double i = pos.x() - 1.0; i < pos.x() + 1.0; i++) {
            for (double j = pos.y() + 1.0; j < pos.y() - 1.0; j++) {
                Opos.setX(i);
                Opos.setY(j);
                distance(map.get(Opos));
            }
        }
        
    }


    public double distance(Obj obj) {
        // using Pyth theorem
        return Math.sqrt(Math.pow(obj.getPos().x() - subject.getPos().x(), 2) + Math.pow(obj.getPos().y() - subject.getPos().y(), 2));
    }
    
}