import java.awt.FlowLayout;

import javax.swing.JSplitPane;
public class Screens {

    public static BrainFrame brainFrame = new BrainFrame();
    public static BrainPanel brainPanel = new BrainPanel();
    public static GUIPanel guiPanel = new GUIPanel();
    public static Translation brainWorldToScreen = new Translation(Simulation.simulation.brainScreenSizeX, Simulation.simulation.brainScreenSizeY);
    public static SimulationFrame simulationFrame = new SimulationFrame();
    public static SimulationPanel simulationPanel = new SimulationPanel();
    public static Translation SimulationWorldToScreen = new Translation(Simulation.simulation.worldSize, Simulation.simulation.worldSize);
    public static JSplitPane simulationSplitPane = new JSplitPane();
    public static String[] creatureNames = new String[Simulation.simulation.generationSize];

    
    public static void createScreens() {

        brainWorldToScreen = new Translation(Simulation.simulation.brainScreenSizeX, Simulation.simulation.brainScreenSizeY);
        SimulationWorldToScreen = new Translation(Simulation.simulation.worldSize, Simulation.simulation.worldSize);
        creatureNames = new String[Simulation.simulation.generationSize+1];

        creatureNames[0] = "None Selected";
        for(int i=1; i<creatureNames.length; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }

        // Brain Frame
        brainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        for(int i=0; i<Simulation.simulation.generationSize; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }

        // Simulation Frame
        simulationSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        simulationSplitPane.setDividerLocation(1000);
        simulationSplitPane.setDividerSize(0);
        simulationSplitPane.setLeftComponent(simulationPanel);
        simulationSplitPane.setRightComponent(guiPanel);

        
        brainFrame.add(brainPanel);
        brainFrame.setVisible(true);
        
        simulationFrame.add(simulationPanel);         
        simulationFrame.setVisible(true);
        
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        brainPanel.setLayout(flowLayout);
        for(int i=0; i<Simulation.simulation.generationSize; i++){
            creatureNames[i] = String.format("Creature %04d",i);
        }
    }
}