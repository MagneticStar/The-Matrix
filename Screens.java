import java.awt.FlowLayout;
import javax.swing.JSplitPane;

// class that controls all screens
public class Screens {

    public static DataFrame dataFrame = new DataFrame();
    public static BrainPanel brainPanel;
    public static GUIPanel guiPanel;
    public static animationPanel animationPanel;
    public static SimulationPanel simulationPanel;  
    public static JSplitPane simulationSplitPane;
    public static Translation brainWorldToScreen;
    public static SimulationFrame simulationFrame = new SimulationFrame();
    public static Translation SimulationWorldToScreen;
    public static String[] creatureNames;
    
    // creates initial screens
    public static void createScreens() {

        // create translation objects
        brainWorldToScreen = new Translation(Main.loaded.brainScreenSizeX, Main.loaded.brainScreenSizeY);
        SimulationWorldToScreen = new Translation(Main.loaded.worldSize, Main.loaded.worldSize);
        
        // create a list of all creature names for identification
        creatureNames = new String[Main.loaded.creaturesList.length+1];
        creatureNames[0] = "None Selected";
        for(int i=0; i<Main.loaded.creaturesList.length; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }

        // initialize all panels
        brainPanel = new BrainPanel();
            brainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        guiPanel = new GUIPanel();
        animationPanel = new animationPanel();
        simulationPanel = new SimulationPanel();

        
        // add brainPanel to Dataframe and make it visible
        dataFrame.add(brainPanel);
        dataFrame.setVisible(Main.loaded.showDataFrame);
        

        // create simulationFrame and add panels
        simulationSplitPane = new JSplitPane();
        simulationSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        simulationSplitPane.setDividerLocation(1000);
        simulationSplitPane.setDividerSize(0);
        simulationSplitPane.setLeftComponent(simulationPanel);
        simulationSplitPane.setRightComponent(guiPanel);

        simulationFrame.add(simulationSplitPane);      
        simulationFrame.setVisible(true);

        Main.loaded.visualPanel = simulationPanel;
    }
}