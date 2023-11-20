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
    public static JSplitPane simulationSplitPane;
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
        savePanel = new SaveLoadPanel("save");
        loadPanel = new SaveLoadPanel("load");

        // Brain Frame
        brainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        dataFrame.add(brainPanel);
        dataFrame.setVisible(true);
        for(int i=0; i<Main.loaded.creaturesList.length; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }

        // Simulation Frame
        simulationSplitPane = new JSplitPane();
        simulationSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        simulationSplitPane.setDividerLocation(1000);
        simulationSplitPane.setDividerSize(0);
        simulationSplitPane.setLeftComponent(simulationPanel);
        simulationSplitPane.setRightComponent(guiPanel);

        

        simulationFrame.add(loadPanel);      
        simulationFrame.setVisible(true);

        Main.loaded.visualPanel = simulationPanel;
    }

    public static void setContent(String type) {
        
        simulationFrame.remove(loadPanel);
        simulationFrame.remove(savePanel);
        simulationFrame.remove(loadPanel);
        switch (type) {
            case "Simulation": simulationFrame.add(simulationSplitPane);
            break;
            case "Save": simulationFrame.add(savePanel);
            break;
            case "Load" : simulationFrame.add(loadPanel);
            break;
            default: simulationFrame.add(loadPanel);
        }
        simulationFrame.setVisible(true);
        
    }
}