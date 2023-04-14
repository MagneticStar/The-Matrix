import java.awt.*;
import javax.swing.JPanel;  

public class SimulationPanel extends JPanel{

    public SimulationPanel() {
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

        for(Creature s: Database.creaturesList) {
            g.setColor(s.getColor());
            g.fillRect((int)s.getPrintPos().x() - 20, (int)s.getPrintPos().y() - 20, 40, 40);
        }
    }
    public void drawFood(Graphics g) {

        for(Food f : Database.foodsList) {
            g.setColor(f.getColor());
            g.fillRect(f.getPrintPos().x() - 5, f.getPrintPos().y() - 5, 10, 10);
        }
    }
    public void drawWater(Graphics g) {

        for(Water w : Database.watersList) {
            g.setColor(w.getColor());
            g.fillRect(w.getPrintPos().x() - 5, w.getPrintPos().y() - 5, 10, 10);
        }
    }
}

