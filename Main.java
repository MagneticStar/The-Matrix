
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

public class Main extends JFrame{
    // ArrayList for all food
    public static  ArrayList<Food> foods = new ArrayList<Food>();
    // ArrayList for all water
    public static  ArrayList<Water> waters = new ArrayList<Water>();
    // ArrayList for all subjects
    public static ArrayList<Subject> subs = new ArrayList<Subject>();
    // Translation obj used to find scalers
    public static Translation matCalc = new Translation();
    public static void main(String[] args) {

        
        subs.add(new Subject(Color.yellow, new Coor(5, 5)));
        // subs.add(new Subject(Color.yellow, new Coor(0, 10)));
        // subs.add(new Subject(Color.yellow, new Coor(10, 0)));
        // subs.add(new Subject(Color.yellow, new Coor(10, 10)));
        // foods.add(new Food(new Coor(1, 3)));
        foods.add(new Food(new Coor(7, 7)));
        // waters.add(new Water(new Coor(6, 8)));

<<<<<<< Updated upstream
=======
        // while(subs.size() < 10){
        //     subs.add(new Subject(Color.yellow, new Coor(100,100)));
        // }
        subNames = new String[Main.subs.size()];
        for(int i=0; i<Main.subs.size(); i++){
            subNames[i] = String.format("Subject %04d",i);
        }
        
>>>>>>> Stashed changes
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