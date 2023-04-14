
import java.util.Map;

public class Main {
   

    // simFrame.add(simPanel);
    //     simFrame.setVisible(true);

    //     FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
    //     neuronMapPanel.setLayout(flowLayout);
    //     neuronMap.add(neuronMapPanel);
    //     neuronMap.setVisible(true);
    
    public static void main(String[] args) {
        Database.creaturesList.add(new Creature());
        String[] subNames = new String[Database.creaturesList.size()];
        for(int i=0; i<Database.creaturesList.size(); i++){
            subNames[i] = String.format("Subject %04d",i);
        }
        Screens.main(args);

        // how many ticks
        for (int i = 0; i < 1; i++) {
            tick(Screens.simulationPanel, i);
        }
    }

    public static void tick(SimulationPanel panel, int i) {

        for (Creature creature : Database.creaturesList) {
            for (Neuron neuron: creature.getGenome().getNeurons()) {
                System.out.println("Sensor");
                if (neuron instanceof Sensor) {
                    for (Map.Entry<Neuron, Integer> sink : neuron.getSinks().entrySet()) {
                        sink.getKey().setValue((((Sensor)neuron).search()));
                        System.out.println(((Sensor)neuron).search());
                    }
                }
            }
            for (Neuron neuron: creature.getGenome().getNeurons()) {
                System.out.println("Internal");
                if (neuron instanceof Internal) {
                    for (Map.Entry<Neuron, Integer> sink : neuron.getSinks().entrySet()) {
                        // the getValue needs to change so that internal neurons can affect things
                        sink.getKey().setValue((((Internal)neuron).getValue()));
                        System.out.println(((Internal)neuron).getValue());
                    }   
                }
            }
            for (Neuron neuron: creature.getGenome().getNeurons()) {
                System.out.println("Motor");
                if (neuron instanceof Motor) {
                    ((Motor)neuron).motorMethod.invoke(creature, neuron.getValue());
                    System.out.println(neuron.getValue());
                }
            }
        }
        panel.repaint();

        // Make tick wait
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}