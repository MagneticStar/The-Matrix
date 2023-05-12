import java.awt.*;
import javax.swing.JPanel;  

public class SimulationPanel extends JPanel{

    public SimulationPanel() {
        setBackground(Database.simulationScreenColor);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        highlightSubject(g);
        drawSub(g);
        drawFood(g);
    }

<<<<<<< Updated upstream
    public void drawSub(Graphics g) {

        for(Creature s: Database.creaturesList) {
            g.setColor(s.getColor());
            g.fillRect(s.getPrintPos().x() - 5, s.getPrintPos().y() - 5, 10, 10);
        }
    }
    public void drawFood(Graphics g) {

        for(int i=0; i<Database.foodsList.size(); i++){
            g.setColor(Database.foodsList.get(i).getColor());
            g.fillRect(Database.foodsList.get(i).getPrintPos().x() - 2, Database.foodsList.get(i).getPrintPos().y() - 2, 4, 4);
=======
    public void drawCreature(Graphics g) {
        for(int i = 0; i < Database.creaturesList.length; i++) {
            if (Database.creaturesList[i] != null) {
                g.setColor(Database.creaturesList[i].getColor());
                g.fillRect(Database.creaturesList[i].getPrintPos().x() - CREATURE_SPRITE_WIDTH/2, Database.creaturesList[i].getPrintPos().y() - CREATURE_SPRITE_HEIGHT/2, CREATURE_SPRITE_WIDTH, CREATURE_SPRITE_HEIGHT);
            }
        }
    }
    public void drawFood(Graphics g) {
        for(int i=0; i<Database.foodsList.length; i++){
            if (Database.foodsList[i] != null) {
            g.setColor(Database.foodsList[i].getColor());
            g.fillRect(Database.foodsList[i].getPrintPos().x() - FOOD_SPRITE_WIDTH/2, Database.foodsList[i].getPrintPos().y() - FOOD_SPRITE_HEIGHT/2, FOOD_SPRITE_WIDTH, FOOD_SPRITE_HEIGHT);
            }
>>>>>>> Stashed changes
        }
    }

    public void highlightSubject(Graphics g){
<<<<<<< Updated upstream
        Creature creature = Database.creaturesList.get(guiPanel.currentlySelectedSubjectIndex);
=======
        int creatureIndex = guiPanel.currentlySelectedCreatureIndex;

        int NONE_SELECTED = -1;
        if(creatureIndex == NONE_SELECTED || Database.creaturesList[creatureIndex] == null){
            return;
        }
        
        Creature creature = Database.creaturesList[creatureIndex];
>>>>>>> Stashed changes
        g.setColor(Color.red);
        g.fillOval(creature.getPrintPos().x()-15, creature.getPrintPos().y()-15, 30, 30);
        
        
        
    }
}