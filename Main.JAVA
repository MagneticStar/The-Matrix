
import java.awt.Color;
import java.util.HashMap;
import javax.swing.*;

public class Main extends JFrame{
    // HashMap for all food
    public static HashMap<Coor, Food> foods = new HashMap<Coor, Food>();
    // HashMap for all subjects
    public static HashMap<Coor, Subject> subs = new HashMap<Coor, Subject>();
    // Translation obj used to find scalers
    public static Translation matCalc = new Translation();
    // window
    public static Frame frame = new Frame();
    public static void main(String[] args) {

        subs.put(new Coor(0.0, 0.0), new Subject(Color.yellow, new Coor(0.0, 0.0)));
        subs.put(new Coor(0.0, 900.0), new Subject(Color.yellow, new Coor(0.0, 900.0)));
        subs.put(new Coor(900.0, 0.0), new Subject(Color.yellow, new Coor(900.0, 0.0)));
        subs.put(new Coor(900.0, 900.0), new Subject(Color.yellow, new Coor(900.0, 900.0)));
        foods.put(new Coor(500.0, 500.0), new Food(new Coor(500.0, 500.0)));
        // Create a gui for our program 
        Panel panel = new Panel();
        frame.add(panel);
        frame.setVisible(true);
        
        
        // how many ticks
        for (int i = 0; i < 200; i++) {
            tick(panel, subs, i);
        }
    }

    public static void tick(Panel panel, HashMap<Coor, Subject> subjects, int i) {
       
        // do all gamestate changes before repaint() is called
        
        panel.repaint();

        // Make tick wait
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}