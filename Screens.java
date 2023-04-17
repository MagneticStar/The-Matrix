import java.awt.FlowLayout;
public class Screens {
    public static SimulationFrame simulationFrame = new SimulationFrame();
    public static SimulationPanel simulationPanel = new SimulationPanel();
    public static Translation SimulationWorldToScreen = new Translation(10);
    
    public static BrainFrame brainFrame = new BrainFrame();
    public static BrainPanel brainPanel = new BrainPanel();
    public static Translation brainWorldToScreen = new Translation(20);

    public static void createScreens() {
        simulationFrame.add(simulationPanel);         
        simulationFrame.setVisible(true);

        brainFrame.add(brainPanel);
        brainFrame.setVisible(true);
        
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        brainPanel.setLayout(flowLayout);

        String[] subNames = new String[Database.creaturesList.size()];
        for(int i=0; i<Database.creaturesList.size(); i++){
            subNames[i] = String.format("Subject %04d",i);
        }
    }
}
