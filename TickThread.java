public class TickThread extends BaseThread {
    public TickThread(int ID){
        super(ID);
    }

    @Override
    public void run(){
        try{
            int creatureIndexRange = Database.creaturesList.size()/Database.tickThreads.size();
            int START_INDEX = creatureIndexRange*super.getIntID();
            int END_INDEX = creatureIndexRange*(super.getIntID()+1);

            if(super.getIntID() == Database.tickThreads.size()-1){
                END_INDEX = Database.creaturesList.size();
            }

            for (int CURRENT_INDEX = START_INDEX; CURRENT_INDEX < END_INDEX; CURRENT_INDEX++){
                Creature creature = Database.creaturesList.get(CURRENT_INDEX);
                Main.determineNeuronActivation(creature).motorMethod.invoke(creature);
            }
            // // temporary killing mechanism for hunger
            // for (int j = 0; j < Database.creaturesList.size(); j++) {
            //     Database.creaturesList.get(j).decrementHunger();
            //     if (Database.creaturesList.get(j).getHunger() < 0) {
            //         Database.creaturesList.remove(j);
            //         j--;
            //     }
            // }
        }
        catch(Exception e){
            System.out.println("Thread Error: "+e+"\n"+e.getStackTrace());
        }
    }
}
