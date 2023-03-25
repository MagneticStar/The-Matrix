
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class Main extends JFrame{

    // ArrayList for all subjects
    public static ArrayList<Subject> subs = new ArrayList<Subject>();
    public static void main(String[] args) {

        subs.add(new Subject(Color.yellow, new Coor(0, 0)));
        subs.add(new Subject(Color.yellow, new Coor(0, 1000)));
        subs.add(new Subject(Color.yellow, new Coor(1000, 0)));
        subs.add(new Subject(Color.yellow, new Coor(1000, 1000)));
        
        // Create a gui for our program 
        Frame frame = new Frame();
        Panel panel = new Panel(subs);
        frame.add(panel);
        frame.setVisible(true);
        
        
        // how many ticks
        for (int i = 0; i < 200; i++) {
            tick(panel, subs, i);
        }
        
        
    }

    public static void tick(Panel panel, ArrayList<Subject> subjects, int i) {
       
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