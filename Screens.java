import java.awt.FlowLayout;
import javax.swing.JSplitPane;

public class Screens {

    public static BrainFrame brainFrame = new BrainFrame();
    public static BrainPanel brainPanel = new BrainPanel();
    public static guiPanel guiPanel = new guiPanel();
    private static JSplitPane splitPane;
    public static Translation brainWorldToScreen = new Translation(Database.brainScreenSizeX, Database.brainScreenSizeY);
    public static SimulationFrame simulationFrame = new SimulationFrame();
    public static SimulationPanel simulationPanel = new SimulationPanel();
    public static Translation SimulationWorldToScreen = new Translation(Database.worldSize, Database.worldSize);
<<<<<<< Updated upstream
    public static String[] subNames = new String[Database.creaturesList.size()];
=======
    public static String[] creatureNames = new String[Database.creaturesList.length+1];
>>>>>>> Stashed changes
    
    public static void createScreens() {
        // Brain Frame
        brainFrame.add(brainPanel);
        brainFrame.setVisible(true);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        brainPanel.setLayout(flowLayout);
        for(int i=0; i<Database.creaturesList.size(); i++){
            subNames[i] = String.format("Creature %04d",i);
        }
        brainPanel.selectionBox();

        // Simulation Frame
        splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600);
        splitPane.setLeftComponent(simulationPanel);
        splitPane.setRightComponent(guiPanel);
        simulationFrame.add(splitPane);      
        simulationFrame.setVisible(true);
        guiPanel.addComponents();
        
        
    }
}