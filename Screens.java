public class Screens {
    public static SimulationFrame simulationFrame = new SimulationFrame();
    public static SimulationPanel simulationPanel = new SimulationPanel();
    public static Translation SimulationWorldToScreen = new Translation(10);
    
    public static BrainFrame brainFrame = new BrainFrame();
    public static BrainPanel brainPanel = new BrainPanel();
    public static Translation brainWorldToScreen = new Translation(20);

    public static void main(String[] args) {
        simulationFrame.add(simulationPanel);         
        simulationFrame.setVisible(true);

        brainFrame.add(brainPanel);
        brainFrame.setVisible(true);
    }
}
