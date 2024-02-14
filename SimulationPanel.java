import java.awt.*;
import javax.swing.JPanel;  

public class SimulationPanel extends JPanel{

    private final static int CREATURE_SPRITE_WIDTH = 10;
    private final static int CREATURE_SPRITE_HEIGHT = 10;
    private final static int FOOD_SPRITE_WIDTH = 4;
    private final static int FOOD_SPRITE_HEIGHT = 4;
    private final static Color FOOD_COLOR = Color.RED;
    private final static int HIGHLIGHT_CIRCLE_DIAMETER = 30;

    public SimulationPanel() {
        setBackground(Main.loaded.simulationScreenColor);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        highlightSubject(g);
        drawCreaturesAndFood(g);
    }

    public void drawCreaturesAndFood(Graphics g) {
        int[][] foodLocations = Main.loaded.foodLocationsForAllTicks[Math.min(Main.loaded.currentGenerationTick,Main.loaded.generationLength-1)];
        Color[][] creatureLocations = Main.loaded.creatureColorsForAllTicks[Math.min(Main.loaded.currentGenerationTick,Main.loaded.generationLength-1)];
        for(int x=0; x<Main.loaded.worldSize; x++){
            for(int y=0; y<Main.loaded.worldSize; y++){
                if(foodLocations[x][y] >= 1){
                    Coor printPostion = ScreenObject.getPrintPos(x,y);
                    g.setColor(FOOD_COLOR);
                    g.fillRect(printPostion.x() - FOOD_SPRITE_WIDTH/2, printPostion.y() - FOOD_SPRITE_HEIGHT/2, FOOD_SPRITE_WIDTH, FOOD_SPRITE_HEIGHT);
                }
                if(creatureLocations[x][y] != null){
                    Coor printPosition = ScreenObject.getPrintPos(x,y);
                    g.setColor(creatureLocations[x][y]);
                    g.fillRect(printPosition.x() - CREATURE_SPRITE_WIDTH/2, printPosition.y() - CREATURE_SPRITE_HEIGHT/2, CREATURE_SPRITE_WIDTH, CREATURE_SPRITE_HEIGHT);
                }   
            }
        }
    }

    public void highlightSubject(Graphics g){
        int creatureIndex = GUIPanel.currentlySelectedCreatureIndex;

        int NONE_SELECTED = -1;
        if(creatureIndex == NONE_SELECTED || Main.loaded.creaturesList[creatureIndex] == null){
            return;
        }
        
        Creature creature = Main.loaded.creaturesList[creatureIndex];
        g.setColor(Color.red);
        Coor printPosition = ScreenObject.getPrintPos(creature.getPosX(), creature.getPosY());
        g.fillOval(printPosition.x()-HIGHLIGHT_CIRCLE_DIAMETER/2, printPosition.y()-HIGHLIGHT_CIRCLE_DIAMETER/2, HIGHLIGHT_CIRCLE_DIAMETER, HIGHLIGHT_CIRCLE_DIAMETER);
    }
}