import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class NeurPanel extends JPanel implements ItemListener{
    private static JComboBox<String> searchDropDown;

    public static void main(String[] args){
        searchDropDown = new JComboBox<String>(Main.subNames);
        searchDropDown.setSelectedIndex(0);
        
        
        // Adds an action listeners to the dropdown
        NeurPanel neuronPanel = Frame.neuronMapPanel;
        searchDropDown.addItemListener(neuronPanel);

        neuronPanel.add(searchDropDown);
        // neuronPanel.repaint();

    }

    public void itemStateChanged(ItemEvent e)
    {
        // if the combobox is changed
        // drawNeuron(getGraphics(), Main.subs.get(Integer.parseInt(searchDropDown.getSelectedItem().substring((searchDropDown.getSelectedItem().indexOf(" "))))));
        System.out.println(searchDropDown.getSelectedItem());
    }

    public NeurPanel() {
        setBackground(Color.BLACK);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawNeuron(g,Main.subs.get(0));
    }
    public void drawNeuron(Graphics g, Subject subject) {
    
        Neuron[] neurons = subject.getGenome().getNeurons();
        for (Neuron n : neurons) {
            for (Map.Entry<Neuron, Integer> s : n.getSinks().entrySet()) {
                switch (s.getKey().getClassType()) {
                    case "Internal": g.setColor(Color.green);
                    break;
                    case "Sensor": g.setColor(Color.red);
                    break;
                    case "Motor": g.setColor(Color.blue);
                    break;
                }
                g.drawLine(n.getPrintPos().x() + 15, n.getPrintPos().y() + 15, s.getKey().getPrintPos().x() + 15, s.getKey().getPrintPos().y() + 15);
                // System.out.println(s.getKey());
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
