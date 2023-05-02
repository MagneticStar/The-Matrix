import java.awt.*;
import javax.swing.JPanel;

public class SimulationPanel extends JPanel{

    public SimulationPanel() {
        setBackground(Database.simulationScreenColor);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawSub(g);
        drawFood(g);
    }

    public void drawSub(Graphics g) {

        for(Creature s: Database.creaturesList) {
            g.setColor(s.getColor());
            g.fillRect(s.getPrintPos().x() - 5, s.getPrintPos().y() - 5, 10, 10);
        }
    }
    public void drawFood(Graphics g) {

        for(Food f : Database.foodsList) {
            g.setColor(f.getColor());
            g.fillRect(f.getPrintPos().x() - 2, f.getPrintPos().y() - 2, 4, 4);
        }
    }
}