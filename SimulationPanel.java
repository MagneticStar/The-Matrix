import java.awt.*;

import javax.swing.JPanel;  

public class SimulationPanel extends JPanel{

<<<<<<< Updated upstream
    public SimulationPanel() {
        setBackground(Database.simulationScreenColor);
=======
    private final static int CREATURE_SPRITE_WIDTH = 10;
    private final static int CREATURE_SPRITE_HEIGHT = 10;
    private final static int FOOD_SPRITE_WIDTH = 4;
    private final static int FOOD_SPRITE_HEIGHT = 4;
    private final static int HIGHLIGHT_CIRCLE_DIAMETER = 30;

    public SimulationPanel() {
        setBackground(Main.loaded.SIMULATION_SCREEN_COLOR);
>>>>>>> Stashed changes
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawSub(g);
        drawFood(g);
    }

<<<<<<< Updated upstream
    public void drawSub(Graphics g) {

        for(Creature s: Database.creaturesList) {
            g.setColor(s.getColor());
            g.fillRect(s.getPrintPos().x() - 5, s.getPrintPos().y() - 5, 10, 10);
=======
    public void drawCreaturesAndFood(Graphics g) {
        int CURRENT_TICK = Main.loaded.observedGenerationTick;
        for(int x=0; x<Main.loaded.worldSize; x++){
            for(int y=0; y<Main.loaded.worldSize; y++){
                Coor position = new Coor(x, y);
                Color printColor = Main.loaded.worldObjects.getPrintColor(CURRENT_TICK,position);
                // No creature or food at position
                if(printColor == null){
                    continue;
                }

                Coor printPosition = ScreenObject.getPrintPos(x,y);
                g.setColor(printColor);

                if(Main.loaded.worldObjects.isFoodAtTick(CURRENT_TICK, position)){
                    g.fillRect(printPosition.x() - FOOD_SPRITE_WIDTH/2, printPosition.y() - FOOD_SPRITE_HEIGHT/2, FOOD_SPRITE_WIDTH, FOOD_SPRITE_HEIGHT);
                }
                else{
                    g.fillRect(printPosition.x() - CREATURE_SPRITE_WIDTH/2, printPosition.y() - CREATURE_SPRITE_HEIGHT/2, CREATURE_SPRITE_WIDTH, CREATURE_SPRITE_HEIGHT);
                }   
            }
>>>>>>> Stashed changes
        }
    }
    public void drawFood(Graphics g) {

<<<<<<< Updated upstream
        for(int i=0; i<Database.foodsList.size(); i++){
            g.setColor(Database.foodsList.get(i).getColor());
            g.fillRect(Database.foodsList.get(i).getPrintPos().x() - 2, Database.foodsList.get(i).getPrintPos().y() - 2, 4, 4);
        }

        // This errored for some reason, could have something to do with modification
        // for(Food f : Database.foodsList) {
        //     g.setColor(f.getColor());
        //     g.fillRect(f.getPrintPos().x() - 2, f.getPrintPos().y() - 2, 4, 4);
        // }
=======
    public void highlightSubject(Graphics g){
        int creatureIndex = GUIPanel.currentlySelectedCreatureIndex;
        Creature creature = Main.loaded.worldObjects.getCreature(creatureIndex);

        int NONE_SELECTED = -1;
        if(creatureIndex == NONE_SELECTED || creature == null){
            return;
        }
        
        g.setColor(Color.red);
        Coor printPosition = ScreenObject.getPrintPos(creature.getPosX(), creature.getPosY());
        g.fillOval(printPosition.x()-HIGHLIGHT_CIRCLE_DIAMETER/2, printPosition.y()-HIGHLIGHT_CIRCLE_DIAMETER/2, HIGHLIGHT_CIRCLE_DIAMETER, HIGHLIGHT_CIRCLE_DIAMETER);
>>>>>>> Stashed changes
    }
}