import java.awt.FlowLayout;
public class Screens {

    public static BrainFrame brainFrame = new BrainFrame();
    public static BrainPanel brainPanel = new BrainPanel();
    public static Translation brainWorldToScreen = new Translation(Database.brainScreenSizeX, Database.brainScreenSizeY);
    public static SimulationFrame simulationFrame = new SimulationFrame();
    public static SimulationPanel simulationPanel = new SimulationPanel();
    public static Translation SimulationWorldToScreen = new Translation(Database.worldSize, Database.worldSize);
    public static String[] subNames = new String[Database.creaturesList.size()];
    
    public static void createScreens() {
<<<<<<< Updated upstream
=======

        brainWorldToScreen = new Translation(Main.loaded.brainScreenSizeX, Main.loaded.brainScreenSizeY);
        SimulationWorldToScreen = new Translation(Main.loaded.worldSize, Main.loaded.worldSize);
        creatureNames = new String[Main.loaded.generationSize+1];

        creatureNames[0] = "None Selected";
        for(int i=1; i<creatureNames.length; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }
        brainPanel = new BrainPanel();
        guiPanel = new GUIPanel();
        simulationPanel = new SimulationPanel();
        menuPanel = new MenuPanel();

        // Brain Frame
        brainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        dataFrame.add(brainPanel);
        dataFrame.setVisible(true);
        for(int i=0; i<Main.loaded.generationSize; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }

        // Simulation Frame
        simulationSplitPane = new JSplitPane();
        simulationSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        simulationSplitPane.setDividerLocation(1000);
        simulationSplitPane.setDividerSize(0);
        simulationSplitPane.setLeftComponent(simulationPanel);
        simulationSplitPane.setRightComponent(guiPanel);

>>>>>>> Stashed changes
        
        brainFrame.add(brainPanel);
        brainFrame.setVisible(true);
        
        simulationFrame.add(simulationPanel);         
        simulationFrame.setVisible(true);
        
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        brainPanel.setLayout(flowLayout);
        for(int i=0; i<Database.creaturesList.size(); i++){
            subNames[i] = String.format("Creature %04d",i);
        }
        brainPanel.selectionBox();
    }
}