
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class Main extends JFrame{
    // HashMap for all food
    public static  ArrayList<Food> foods = new ArrayList<Food>();
    // HashMap for all subjects
    public static ArrayList<Subject> subs = new ArrayList<Subject>();
    // Translation obj used to find scalers
    public static Translation matCalc = new Translation();
    // window
    public static Frame frame = new Frame();
    public static Panel panel = new Panel();
    public static void main(String[] args) {

        subs.add(new Subject(Color.yellow, new Coor(0, 0)));
        subs.add(new Subject(Color.yellow, new Coor(0, 900)));
        subs.add(new Subject(Color.yellow, new Coor(900, 0)));
        subs.add(new Subject(Color.yellow, new Coor(900, 900)));
        foods.add(new Food(new Coor(0, 0)));

        // Create a gui for our program 
        frame.add(panel);
        frame.setVisible(true);
        
        Sensor s = new Sensor(subs.get(0));
        s.find();
        
        // how many ticks
        for (int i = 0; i < 10; i++) {
            tick(panel, i);
        }
    }

    public static void tick(Panel panel, int i) {
       
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