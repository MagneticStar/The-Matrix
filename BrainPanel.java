import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JPanel;

// Panel for displaying Creature brains 
public class BrainPanel extends JPanel {
    
    private JComboBox<String> searchDropDown;
    private int currentlySelectedCreatureIndex = 0;

    public BrainPanel() {
        setBackground(Color.BLACK);
        addComponents();
    }

    private void addComponents(){
        searchDropDown = new JComboBox<String>(Screens.creatureNames);
        searchDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Sets the neuron map panel to the neuron map of the selected subject. The string manipulation is to avoid searching for the index of the subject
            currentlySelectedCreatureIndex = searchDropDown.getSelectedIndex()-1;
            repaint();
            }
        });
        this.add(searchDropDown);
        this.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawNeuron(g);
    }
    // draws neurons to screen
    public void drawNeuron(Graphics g) {
        // optimization check
        if (Main.loadedDatabase.creaturesList.length == 0 || currentlySelectedCreatureIndex == -1 || Main.loadedDatabase.creaturesList[currentlySelectedCreatureIndex] == null) {
            return;
        }
        
        // create neurons array
        Neuron[] neurons = Main.loadedDatabase.creaturesList[currentlySelectedCreatureIndex].getGenome().getNeurons();
        // set width of screen
        Main.loadedDatabase.brainScreenSizeY = neurons.length;
        
        int i = 1;
        int internalcount = 0;
        int sensorcount = 0;
        int motorcount = 0;
        for (Neuron n : neurons) {
            switch(n.getClassType()) {
                // Internal
                case "Internal":
                internalNeuron(g,i,internalcount,n);
                internalcount++;
                break;
                // Sensor
                case "Sensor":
                sensorNeuron(g,i,sensorcount,n);
                sensorcount++;
                break;
                // Motor
                case "Motor":
                motorNeuron(g,i,motorcount,n);
                motorcount++;
                break;
            }
            i++;
        }
        
        // Sinks
        for (Neuron n : neurons) {
            for (Map.Entry<Neuron, Integer> sink : n.getSinks().entrySet()) {
                switch (sink.getKey().getClassType()) {
                    case "Internal": g.setColor(Color.green);
                    break;
                    case "Motor": g.setColor(Color.blue);
                    break;
                }
                g.drawLine(n.getPrintPos().x() + 10, n.getPrintPos().y() + 10, sink.getKey().getPrintPos().x() + 10, sink.getKey().getPrintPos().y() + 10);
            }
        }
        // Sources
        for (Neuron n : neurons) {
            for (Neuron source : n.getSources()) {
                switch (source.getClassType()) {
                    case "Internal": g.setColor(Color.magenta);
                    break;
                    case "Sensor": g.setColor(Color.red);
                    break;
                }
                g.drawLine(n.getPrintPos().x() + 10, n.getPrintPos().y() + 10, source.getPrintPos().x() + 10, source.getPrintPos().y() + 10);
            }
        }
    }
    // draws internal neurons
    public void internalNeuron(Graphics g, int i, int nc, Neuron n) {
        g.setColor(Color.green);
        n.setPosX(15+((nc+i)%4)*(int)Math.pow(-1,(nc+i)%3));
        n.setPosY(i);
        g.drawOval(n.getPrintPos().x(), n.getPrintPos().y(), 20, 20);
    }
    // draws sensor neurons
    public void sensorNeuron(Graphics g, int i, int nc, Neuron n) {
        g.setColor(Color.red);
        n.setPosX(8+((nc+i)%3)*(int)Math.pow(-1,(nc+i)%3));
        n.setPosY(i);
        g.drawOval(n.getPrintPos().x(), n.getPrintPos().y(), 20, 20);
    }
    // draws motor neurons
    public void motorNeuron(Graphics g, int i, int nc, Neuron n) {
        g.setColor(Color.blue);
        n.setPosX(22+((nc+i)%3)*(int)Math.pow(-1,(nc+i)%3));
        n.setPosY(i);
        g.drawOval(n.getPrintPos().x(), n.getPrintPos().y(), 20, 20);
    }
}