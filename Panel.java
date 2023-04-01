import java.awt.*;
import javax.swing.JPanel;  

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
        drawWater(g);
    }

    public void drawSub(Graphics g) {

        // prints all Subjects in subject array
        for(Subject s: Main.subs) {
            g.setColor(s.getColor());
            g.fillRect((int)s.getPrintPos().x(), (int)s.getPrintPos().y(), 30, 30);
        }
    }
    public void drawFood(Graphics g) {

        // prints all Foods in Food array
        for(Food f : Main.foods) {
            g.setColor(f.getColor());
            g.fillRect(f.getPrintPos().x(), f.getPrintPos().y(), 20, 20);
        }
    }
    public void drawWater(Graphics g) {

        // prints all Waters in Water array
        for(Water w : Main.waters) {
            g.setColor(w.getColor());
            g.fillRect(w.getPrintPos().x(), w.getPrintPos().y(), 20, 20);
        }
    }
}

