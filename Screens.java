import java.awt.FlowLayout;
import javax.swing.JSplitPane;

public class Screens {

    public static BrainFrame brainFrame = new BrainFrame();
    public static BrainPanel brainPanel;
    public static guiPanel guiPanel;
    public static animationPanel animationPanel;
    public static SimulationPanel simulationPanel;
    public static JSplitPane splitPane;
    public static Translation brainWorldToScreen = new Translation(Database.brainScreenSizeX, Database.brainScreenSizeY);
    public static SimulationFrame simulationFrame = new SimulationFrame();
    public static Translation SimulationWorldToScreen = new Translation(Database.worldSize, Database.worldSize);
    public static String[] creatureNames = new String[Database.creaturesList.size()+1];
    
    public static void createScreens() {
        creatureNames[0] = "None Selected";
        for(int i=1; i<creatureNames.length; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }
        brainPanel = new BrainPanel();
        guiPanel = new guiPanel();
        animationPanel = new animationPanel();
        simulationPanel = new SimulationPanel();

        // Brain Frame
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        brainPanel.setLayout(flowLayout);
        brainFrame.add(brainPanel);
        brainFrame.setVisible(true);

        // Simulation Frame
        splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(650);
        splitPane.setDividerSize(0);
        splitPane.setLeftComponent(simulationPanel);
        splitPane.setRightComponent(guiPanel);
        simulationFrame.add(splitPane);      
        simulationFrame.setVisible(true);
        Database.visualPanel = simulationPanel;
        
    }
}