import javax.swing.JPanel;
import java.awt.*;
public class NeurPanel extends JPanel{
    public NeurPanel() {
        setBackground(Color.BLACK);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawNeuron(g);
    }
    public void drawNeuron(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(50, NeurFrame.HEIGHT, 40, 40);
    }
}
