public class Motor extends Neuron{

    public MotorMethod motorMethod;
    private static int numberOfMotorMethods = 2; // Update this when creating new Motor methods
    public Motor(int methodID){
        super();
        switch(methodID%(numberOfMotorMethods)){
            case 0: this.motorMethod = Motor::upDown; break;
            case 1: this.motorMethod = Motor::leftRight; break;
        }
    }

    public interface MotorMethod{
        void invoke(Creature subject, double value);
    }

    ////////////////////////////////////////////////////////
    // MOTOR METHODS // MOTOR METHODS // MOTOR METHODS //
    ////////////////////////////////////////////////////////

    private static void upDown(Creature subject, double value){
        subject.setPosY(subject.getPosY() + (int)(value - 0.5));
    }
    private static void leftRight(Creature subject, double value){
        subject.setPosX(subject.getPosX() + (int)(value - 0.5));
    }
}