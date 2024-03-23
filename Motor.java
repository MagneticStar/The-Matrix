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

<<<<<<< Updated upstream
=======
    private static void MoveUp(Creature creature){
        Coor currentPosition = creature.getPos();
        creature.setPosY((currentPosition.y() + 1) % Main.loaded.worldSize);
        Main.loaded.worldObjects.moveCreature(currentPosition,creature.getPos());
        creature.moved();
    }
    private static void MoveDown(Creature creature){
        Coor currentPosition = creature.getPos();
        creature.setPosY((currentPosition.y() - 1 + Main.loaded.worldSize) % Main.loaded.worldSize);
        Main.loaded.worldObjects.moveCreature(currentPosition,creature.getPos());
        creature.moved();
    }
    private static void MoveLeft(Creature creature){
        Coor currentPosition = creature.getPos();
        creature.setPosX((currentPosition.x() - 1 + Main.loaded.worldSize) % Main.loaded.worldSize);
        Main.loaded.worldObjects.moveCreature(currentPosition,creature.getPos());
        creature.moved();
    }
    private static void MoveRight(Creature creature){
        Coor currentPosition = creature.getPos();
        creature.setPosX((currentPosition.x() + 1) % Main.loaded.worldSize);
        Main.loaded.worldObjects.moveCreature(currentPosition,creature.getPos());
        creature.moved();
    }
>>>>>>> Stashed changes
    private static void DoNothing(Creature creature) {
        // Only meant as a default method as it serves no value
    }

    private static void MoveUp(Creature creature){
        Database.creatureCoordinates.remove(creature.getPos());
        creature.setPosY(creature.getPosY() + 1);
        Database.creatureCoordinates.add(creature.getPos());
    }
    private static void MoveDown(Creature creature){
        Database.creatureCoordinates.remove(creature.getPos());
        creature.setPosY(creature.getPosY() - 1);
        Database.creatureCoordinates.add(creature.getPos());
    }
    private static void MoveLeft(Creature creature){
        Database.creatureCoordinates.remove(creature.getPos());
        creature.setPosX(creature.getPosX() - 1);
        Database.creatureCoordinates.add(creature.getPos());
    }
    private static void MoveRight(Creature creature){
        Database.creatureCoordinates.remove(creature.getPos());
        creature.setPosX(creature.getPosX() + 1);
        Database.creatureCoordinates.add(creature.getPos());
    }
    private static void Eat(Creature creature) {
<<<<<<< Updated upstream
        for (Food food : Database.foodsList) {
            if (creature.getPos().equals(food.getPos())) {
                // Debug
                // System.out.println("Ate Food");

                creature.ateFood();
                Database.foodsList.remove(food);
                Database.foodCoordinates.remove(food.getPos());
                break;
            }
=======
        Coor location = creature.getPos();
        if (Main.loaded.worldObjects.isFoodAtLocation(location)) {
            creature.ateFood();
            Main.loaded.worldObjects.removeFood(location);
>>>>>>> Stashed changes
        }
    }
}