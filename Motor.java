public class Motor extends Neuron{

    public MotorMethod motorMethod;
    private static int numberOfMotorMethods = 4; // Update this when creating new Motor methods

    public Motor(int methodID){
        super("Motor");
        switch(methodID%(numberOfMotorMethods)){
            case 0: this.motorMethod = Motor::MoveUp; break;
            case 1: this.motorMethod = Motor::MoveDown; break;
            case 2: this.motorMethod = Motor::MoveRight; break;
            case 3: this.motorMethod = Motor::MoveLeft; break;
            
        }
    }
    
    public Motor(){
        super("Motor");
        this.motorMethod = Motor::DoNothing;
    }

    public double getMaxValue(){
        double maxValue = -1000000000;
        for(double value : super.getValues()){
            if(maxValue<value){
                maxValue = value;
            }
        }
        return maxValue;
    }

    public interface MotorMethod{
        void invoke(Creature subject);
    }

    ////////////////////////////////////////////////////////
    // MOTOR METHODS // MOTOR METHODS // MOTOR METHODS //
    ////////////////////////////////////////////////////////

    private static void MoveUp(Creature creature){
        creature.setPosY(creature.getPosY() + 1);
    }
    private static void MoveDown(Creature creature){
        creature.setPosY(creature.getPosY() - 1);
    }
    private static void MoveLeft(Creature creature){
        creature.setPosX(creature.getPosX() - 1);
    }
    private static void MoveRight(Creature creature){
        creature.setPosX(creature.getPosX() + 1);
    }
    private static void DoNothing(Creature creature){
        // Default Motor Method for those who have no motors
        // NOT INCLUDED IN MOTOR METHOD NEURON COUNT
    }
}