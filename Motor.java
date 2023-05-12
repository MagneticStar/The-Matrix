public class Motor extends Neuron{

    public MotorMethod motorMethod;
    private static int numberOfMotorMethods = 6; // Update this when creating new Motor methods
    public Motor(int methodID){
        super("Motor");
        switch(methodID%(numberOfMotorMethods)){
            case 0: this.motorMethod = Motor::MoveUp; break;
            case 1: this.motorMethod = Motor::MoveDown; break;
            case 2: this.motorMethod = Motor::MoveRight; break;
            case 3: this.motorMethod = Motor::MoveLeft; break;
            case 4: this.motorMethod = Motor::Eat; break;
            case 5: this.motorMethod = Motor::DoNothing; break;
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
        Database.creatureLocations[creature.getPosX()][creature.getPosY()]-=1;
        creature.setPosY((creature.getPosY() + 1) % Database.worldSize);
        Database.creatureLocations[creature.getPosX()][creature.getPosY()]+=1;
    }
    private static void MoveDown(Creature creature){
        Database.creatureLocations[creature.getPosX()][creature.getPosY()]-=1;
        creature.setPosY((creature.getPosY() - 1 + Database.worldSize) % Database.worldSize);
        Database.creatureLocations[creature.getPosX()][creature.getPosY()]+=1;
    }
    private static void MoveLeft(Creature creature){
        Database.creatureLocations[creature.getPosX()][creature.getPosY()]-=1;
        creature.setPosX((creature.getPosX() - 1 + Database.worldSize) % Database.worldSize);
        Database.creatureLocations[creature.getPosX()][creature.getPosY()]+=1;
    }
    private static void MoveRight(Creature creature){
        Database.creatureLocations[creature.getPosX()][creature.getPosY()]-=1;
        creature.setPosX((creature.getPosX() + 1) % Database.worldSize);
        Database.creatureLocations[creature.getPosX()][creature.getPosY()]+=1;
    }
    private static void DoNothing(Creature creature) {
        // Only meant as a default method as it serves no value
    }

    private static void Eat(Creature creature) {
        for (int i = 0; i < Database.foodsList.length; i++) {
            if (Database.foodsList[i] != null && (creature.getPos().equals(Database.foodsList[i].getPos()) || true)) {
                // for (int i = 0; i < 10 ; i++) {
                //     creature.incrementHunger();
                // }
                // creature.setHunger(10);
                // Database.foodsList.remove(food);
                // creature.setHunger(100);
                // Debug
                // System.out.println("Ate Food");

                creature.ateFood();
                // Database.foodLocations[Database.foodsList[i].getPosX()][Database.foodsList[i].getPosY()]-=1;
                // Database.foodsList[i] = null;
                Database.foodLocations[Database.foodsList[i].getPosX()][Database.foodsList[i].getPosY()]-=1;
                Database.foodsList[i] = null;
                break;
            }
        }
    }
}