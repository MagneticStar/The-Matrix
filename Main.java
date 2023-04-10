
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class Main extends JFrame{
    // ArrayList for all food
    public static  ArrayList<Food> foods = new ArrayList<Food>();
    // ArrayList for all water
    public static  ArrayList<Water> waters = new ArrayList<Water>();
    // ArrayList for all subjects
    public static ArrayList<Subject> subs = new ArrayList<Subject>();
    // An array containing a seralized name for each subject
    public static String[] subNames = new String[1];
    // Translation obj used to find scalers
    public static Translation matCalc = new Translation();
    public static void main(String[] args) {

        
        subs.add(new Subject(Color.yellow, new Coor(0, 0)));
        subs.add(new Subject(Color.yellow, new Coor(0, 10)));
        subs.add(new Subject(Color.yellow, new Coor(10, 0)));
        subs.add(new Subject(Color.yellow, new Coor(10, 10)));
        foods.add(new Food(new Coor(1, 3)));
        foods.add(new Food(new Coor(7, 3)));
        waters.add(new Water(new Coor(6, 8)));

        while(subs.size() < 10){
            subs.add(new Subject(Color.yellow, new Coor(100,100)));
        }
        subNames = new String[Main.subs.size()];
        for(int i=0; i<Main.subs.size(); i++){
            subNames[i] = String.format("Subject %04d",i);
        }
        
        Frame.main(args);
        NeurPanel.main(args);
        
        // how many ticks
        for (int i = 0; i < 5; i++) {
            tick(Frame.simPanel, i);
        }
    }

    public static void tick(Panel panel, int i) {
       
        // do all gamestate changes before repaint() is called
        subs.get(0).setPosX(subs.get(0).getPosX() + 1);
        Sensor s = new Sensor(subs.get(0), 0);
        Sensor s2 = new Sensor(subs.get(0), 1);
        // System.out.println(s.search());
        // System.out.println(s2.search());
        panel.repaint();

        // Make tick wait
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}