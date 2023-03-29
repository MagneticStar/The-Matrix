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
        drawSub(g, Main.subs);
        drawFood(g, Main.foods);
    }

    public void drawSub(Graphics g, HashMap<Coor, Subject> sub) {

        // prints all Subjects in subject array
        for(Subject value : sub.values()) {
            g.setColor(value.getColor());
            g.fillRect((int)value.getPrintPos().x(), (int)value.getPrintPos().y(), 30, 30);
        }
            // warning!! typecast could cause bugs if getPrintPos fails to output a whole number
    }
    public void drawFood(Graphics g, HashMap<Coor, Food> food) {

        // prints all Subjects in subject array
        for(Food value : food.values()) {
            g.setColor(value.getColor());
            g.fillRect((int)value.getPrintPos().x(), (int)value.getPrintPos().y(), 20, 20);
        }
            // warning!! typecast could cause bugs if getPrintPos fails to output a whole number
    }
}

