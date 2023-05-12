import java.awt.*;
import javax.swing.JPanel;  

public class SimulationPanel extends JPanel{

    private static int CREATURE_SPRITE_WIDTH = 10;
    private static int CREATURE_SPRITE_HEIGHT = 10;
    private static int FOOD_SPRITE_WIDTH = 4;
    private static int FOOD_SPRITE_HEIGHT = 4;
    private static int HIGHLIGHT_CIRCLE_DIAMETER = 30;

    public SimulationPanel() {
        setBackground(Database.simulationScreenColor);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        highlightSubject(g);
        drawCreature(g);
        drawFood(g);
    }

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
        }
    }

    public void highlightSubject(Graphics g){
        int creatureIndex = guiPanel.currentlySelectedCreatureIndex;

        int NONE_SELECTED = -1;
        if(creatureIndex == NONE_SELECTED || Database.creaturesList[creatureIndex] == null){
            return;
        }
        
        Creature creature = Database.creaturesList[creatureIndex];
        g.setColor(Color.red);
        g.fillOval(creature.getPrintPos().x()-HIGHLIGHT_CIRCLE_DIAMETER/2, creature.getPrintPos().y()-HIGHLIGHT_CIRCLE_DIAMETER/2, HIGHLIGHT_CIRCLE_DIAMETER, HIGHLIGHT_CIRCLE_DIAMETER);
    }
}