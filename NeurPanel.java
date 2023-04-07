import java.awt.*;
import java.awt.event.*;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class NeurPanel extends JPanel implements ActionListener{
    private static JComboBox<String> searchDropDown;
    private static Button searchButton;

    public static void main(String[] args){
        searchDropDown = new JComboBox<String>(Main.subNames);
        searchDropDown.setSelectedIndex(0);
        searchButton = new Button("Find");
        
        
        // Adds an action listeners to the button and dropdown
        NeurPanel neuronPanel = Frame.neuronMapPanel;
        searchDropDown.addActionListener(neuronPanel);
        searchButton.addActionListener(neuronPanel);

        neuronPanel.add(searchDropDown);
        neuronPanel.add(searchButton);
        neuronPanel.repaint();

    }

    public void actionPerformed(ActionEvent e){
        // JComboBox cb = (JComboBox)e.getSource();
        // String petName = (String)cb.getSelectedItem();
        // updateLabel(petName);
    }

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
