import java.awt.*;
import javax.swing.JPanel;  
import java.util.HashMap;

public class Panel extends JPanel{

    public Panel() {
        setBackground(Color.BLACK);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawSub(g);
        drawFood(g);
    }

    public void drawSub(Graphics g) {

        // prints all Subjects in subject array
        for(Subject s: Main.subs) {
            g.setColor(s.getColor());
            g.fillRect((int)s.getPrintPos().x(), (int)s.getPrintPos().y(), 30, 30);
        }
            // warning!! typecast could cause bugs if getPrintPos fails to output a whole number
    }
    public void drawFood(Graphics g) {

        // prints all Subjects in subject array
        for(Food f : Main.foods) {
            g.setColor(f.getColor());
            g.fillRect(f.getPrintPos().x(), f.getPrintPos().y(), 20, 20);
        }
            // warning!! typecast could cause bugs if getPrintPos fails to output a whole number
    }
}

