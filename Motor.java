public class Motor extends Neuron{
    public MotorMethod motorMethod;
    private static int numberOfMotorMethods = 2; // Update this when creating new Motor methods

    public Motor(int methodID){
        switch(methodID%(numberOfMotorMethods+1)){
            case 0: this.motorMethod = Motor::move; break;
            case 1: this.motorMethod = Motor::turn; break;
        }
    }

    public interface MotorMethod{
        void invoke(Subject subject);
    }

    ////////////////////////////////////////////////////////
    // MOTOR METHODS // MOTOR METHODS // MOTOR METHODS //
    ////////////////////////////////////////////////////////

    private static void move(Subject subject){
        // Needs implementation
    }

    private static void turn(Subject subject){
        // Needs implementation
    }
}