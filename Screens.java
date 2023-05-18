import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Screens {

    public static DataFrame dataFrame = new DataFrame();
    public static BrainPanel brainPanel;
    public static GUIPanel guiPanel;
    public static animationPanel animationPanel;
    public static SimulationPanel simulationPanel;
    public static SaveLoadPanel savePanel;
    public static SaveLoadPanel loadPanel;
    public static CardLayout mainPanelManager = new CardLayout();
    public static JPanel mainPanel = new JPanel(mainPanelManager);
    public static JSplitPane splitPane;
    public static Translation brainWorldToScreen;
    public static SimulationFrame simulationFrame = new SimulationFrame();
    public static Translation SimulationWorldToScreen;
    public static String[] creatureNames;
    
    public static void createScreens() {

        brainWorldToScreen = new Translation(Main.loaded.brainScreenSizeX, Main.loaded.brainScreenSizeY);
        SimulationWorldToScreen = new Translation(Main.loaded.worldSize, Main.loaded.worldSize);
        creatureNames = new String[Main.loaded.creaturesList.length+1];

        creatureNames[0] = "None Selected";
        for(int i=1; i<creatureNames.length; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }
        brainPanel = new BrainPanel();
        guiPanel = new GUIPanel();
        animationPanel = new animationPanel();
        simulationPanel = new SimulationPanel();
        savePanel = new SaveLoadPanel(true);
        loadPanel = new SaveLoadPanel(false);

        // Brain Frame
        brainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        dataFrame.add(brainPanel);
        dataFrame.setVisible(true);
        for(int i=0; i<Main.loaded.creaturesList.length; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }

        // Simulation Frame
        splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(1000);
        splitPane.setDividerSize(0);
        splitPane.setLeftComponent(simulationPanel);
        splitPane.setRightComponent(guiPanel);

        mainPanel.add(splitPane, "simulation");
        mainPanel.add(savePanel, "save");
        mainPanel.add(loadPanel, "load");
        mainPanelManager.show(mainPanel, "load");

        simulationFrame.setContentPane(mainPanel);      
        simulationFrame.setVisible(true);

        Main.loaded.visualPanel = simulationPanel;
    }
}