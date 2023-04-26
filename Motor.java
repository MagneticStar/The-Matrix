public class Motor extends Neuron{

    public MotorMethod motorMethod;
    private static int numberOfMotorMethods = 5; // Update this when creating new Motor methods
    public Motor(int methodID){
        super("Motor");
        switch(methodID%(numberOfMotorMethods)){
            case 0: this.motorMethod = Motor::MoveUp; break;
            case 1: this.motorMethod = Motor::MoveDown; break;
            case 2: this.motorMethod = Motor::MoveRight; break;
            case 3: this.motorMethod = Motor::MoveLeft; break;
            case 4: this.motorMethod = Motor::Eat; break;
            case 5: this.motorMethod = Motor::Drink; break;
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
    private static void DoNothing(Creature creature) {
        // Only meant as a default method as it serves no value
    }
    private static void Eat(Creature creature) {
        for (Food food : Database.foodsList) {
            if (creature.getPos().equals(food.getPos())) {
                creature.incrementHunger();
                Database.foodsList.remove(food);
                break;
            }
        }
    }
    private static void Drink(Creature creature){
        for (Water water : Database.watersList) {
            if (creature.getPos().equals(water.getPos())) {
                // place thirst stuff here

                Database.watersList.remove(water);
                break;
            }
        }
    }
}