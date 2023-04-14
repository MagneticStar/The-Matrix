
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

public class Main {
   

    // simFrame.add(simPanel);
    //     simFrame.setVisible(true);

    //     FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
    //     neuronMapPanel.setLayout(flowLayout);
    //     neuronMap.add(neuronMapPanel);
    //     neuronMap.setVisible(true);
    
    public static void main(String[] args) {

        
        Database.subs.add(new Subject(Color.yellow, new Coor(5, 5)));
        // subs.add(new Subject(Color.yellow, new Coor(0, 10)));
        // subs.add(new Subject(Color.yellow, new Coor(10, 0)));
        // subs.add(new Subject(Color.yellow, new Coor(10, 10)));
        // foods.add(new Food(new Coor(1, 3)));
        foods.add(new Food(new Coor(7, 7)));
        // waters.add(new Water(new Coor(6, 8)));

        // while(subs.size() < 10){
        //     subs.add(new Subject(Color.yellow, new Coor(100,100)));
        // }
        String[] subNames = new String[Main.subs.size()];
        for(int i=0; i<Main.subs.size(); i++){
            subNames[i] = String.format("Subject %04d",i);
        }
        
        Frame.main(args);
        NeurPanel.main(args);

        // how many ticks
        for (int i = 0; i < 1; i++) {
            tick(Frame.simPanel, i);
        }
    }

    public static void tick(Panel panel, int i) {

        for (Subject subject : subs) {
            for (Neuron neuron: subject.getGenome().getNeurons()) {
                System.out.println("Sensor");
                if (neuron instanceof Sensor) {
                    for (Map.Entry<Neuron, Integer> sink : neuron.getSinks().entrySet()) {
                        sink.getKey().setValue((((Sensor)neuron).search()));
                        System.out.println(((Sensor)neuron).search());
                    }
                }
            }
            for (Neuron neuron: subject.getGenome().getNeurons()) {
                System.out.println("Internal");
                if (neuron instanceof Internal) {
                    for (Map.Entry<Neuron, Integer> sink : neuron.getSinks().entrySet()) {
                        // the getValue needs to change so that internal neurons can affect things
                        sink.getKey().setValue((((Internal)neuron).getValue()));
                        System.out.println(((Internal)neuron).getValue());
                    }   
                }
            }
            for (Neuron neuron: subject.getGenome().getNeurons()) {
                System.out.println("Motor");
                if (neuron instanceof Motor) {
                    ((Motor)neuron).motorMethod.invoke(subject, neuron.getValue());
                    System.out.println(neuron.getValue());
                }
            }
        }
        System.out.println(subs.get(0).getPosX() + ", " + subs.get(0).getPosY());
        panel.repaint();

        // Make tick wait
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}