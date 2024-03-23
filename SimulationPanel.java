import java.awt.*;

import javax.swing.JPanel;  

public class SimulationPanel extends JPanel{

    private final static int CREATURE_SPRITE_WIDTH = 10;
    private final static int CREATURE_SPRITE_HEIGHT = 10;
    private final static int FOOD_SPRITE_WIDTH = 4;
    private final static int FOOD_SPRITE_HEIGHT = 4;
    private final static int HIGHLIGHT_CIRCLE_DIAMETER = 30;

    public SimulationPanel() {
        setBackground(Simulation.simulation.SIMULATION_SCREEN_COLOR);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        highlightSubject(g);
        drawCreaturesAndFood(g);
    }

    public void drawCreaturesAndFood(Graphics g) {
        int CURRENT_TICK = Simulation.simulation.observedGenerationTick;
        for(int x=0; x<Simulation.simulation.worldSize; x++){
            for(int y=0; y<Simulation.simulation.worldSize; y++){
                Coor position = new Coor(x, y);
                Color printColor = Simulation.simulation.worldObjects.getPrintColor(CURRENT_TICK,position);
                // No creature or food at position
                if(printColor == null){
                    continue;
                }

                Coor printPosition = ScreenObject.getPrintPos(x,y);
                g.setColor(printColor);

                if(Simulation.simulation.worldObjects.isFoodAtTick(CURRENT_TICK, position)){
                    g.fillRect(printPosition.x() - FOOD_SPRITE_WIDTH/2, printPosition.y() - FOOD_SPRITE_HEIGHT/2, FOOD_SPRITE_WIDTH, FOOD_SPRITE_HEIGHT);
                }
                else{
                    g.fillRect(printPosition.x() - CREATURE_SPRITE_WIDTH/2, printPosition.y() - CREATURE_SPRITE_HEIGHT/2, CREATURE_SPRITE_WIDTH, CREATURE_SPRITE_HEIGHT);
                }   
            }
        }
    }

    public void highlightSubject(Graphics g){
        int creatureIndex = GUIPanel.currentlySelectedCreatureIndex;
        Creature creature = Simulation.simulation.worldObjects.getCreature(creatureIndex);

        int NONE_SELECTED = -1;
        if(creatureIndex == NONE_SELECTED || creature == null){
            return;
        }
        
        g.setColor(Color.red);
        Coor printPosition = ScreenObject.getPrintPos(creature.getPosX(), creature.getPosY());
        g.fillOval(printPosition.x()-HIGHLIGHT_CIRCLE_DIAMETER/2, printPosition.y()-HIGHLIGHT_CIRCLE_DIAMETER/2, HIGHLIGHT_CIRCLE_DIAMETER, HIGHLIGHT_CIRCLE_DIAMETER);
    }
}