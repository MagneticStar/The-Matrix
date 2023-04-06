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
        
        Subject subject = Main.subs.get(0);
        Neuron[] neurons = subject.getGenome().getNeurons();

        for (Neuron n : neurons) {
            switch(n.getClassType()) {
                // Internal
                case 0: internalNeuron(g);
                // Sensor
                case 1: sensorNeuron(g);
                // Motor
                case 2: motorNeuron(g);
            }
        }
        
    }
    public void internalNeuron(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(250, 250, 30, 30);
    }
    public void sensorNeuron(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(100, 250, 30, 30);
    }
    public void motorNeuron(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(400, 250, 30, 30);
    }
}
