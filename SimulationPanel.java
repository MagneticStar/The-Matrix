import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;  

// Panel where the simulation is rendered
public class SimulationPanel extends JPanel{

    private final static int CREATURE_SPRITE_WIDTH = 10;
    private final static int CREATURE_SPRITE_HEIGHT = 10;
    private final static int FOOD_SPRITE_WIDTH = 4;
    private final static int FOOD_SPRITE_HEIGHT = 4;
    private final static Color FOOD_COLOR = Color.RED;
    private final static int HIGHLIGHT_CIRCLE_DIAMETER = 30;

    // constructor
    public SimulationPanel() {
        setBackground(Main.loadedDatabase.simulationScreenColor);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        highlightSubject(g);
        drawCreaturesAndFood(g);
    }

    // draws Creatures and Foods to the screen
    public void drawCreaturesAndFood(Graphics g) {
        int[][] foodLocations = Main.loadedDatabase.foodLocationsForAllTicks[Main.loadedDatabase.currentGenerationTick];
        Color[][] creatureLocations = Main.loadedDatabase.creatureColorsForAllTicks[Main.loadedDatabase.currentGenerationTick];

        for(int x=0; x<foodLocations.length; x++){
            for(int y=0; y<foodLocations[x].length; y++){
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
    
    // highlights a Creature on the screen with a red Circle for identification
    public void highlightSubject(Graphics g){
        int creatureIndex = GUIPanel.currentlySelectedCreatureIndex;

        int NONE_SELECTED = -1;
        if(creatureIndex == NONE_SELECTED || Main.loadedDatabase.creaturesList[creatureIndex] == null){
            return;
        }
        
        Creature creature = Main.loadedDatabase.creaturesList[creatureIndex];
        g.setColor(Color.red);
        Coor printPosition = ScreenObject.getPrintPos(creature.getPosX(), creature.getPosY());
        g.fillOval(printPosition.x()-HIGHLIGHT_CIRCLE_DIAMETER/2, printPosition.y()-HIGHLIGHT_CIRCLE_DIAMETER/2, HIGHLIGHT_CIRCLE_DIAMETER, HIGHLIGHT_CIRCLE_DIAMETER);
    }
}