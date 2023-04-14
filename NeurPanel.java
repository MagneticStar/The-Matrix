import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class NeurPanel extends JPanel implements ActionListener{
    private static JComboBox<String> searchDropDown;
    private static int currentlySelectedSubjectIndex = 0;

    public static void main(String[] args){
        searchDropDown = new JComboBox<String>(Main.subNames);
        searchDropDown.setSelectedIndex(0);
        
        
        // Adds an action listeners to the dropdown
        NeurPanel neuronPanel = Frame.neuronMapPanel;
        searchDropDown.addActionListener(neuronPanel);

        neuronPanel.add(searchDropDown);
        neuronPanel.revalidate();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Sets the neuron map panel to the neuron map of the selected subject. The string manipulation is to avoid searching for the index of the subject
        currentlySelectedSubjectIndex = Integer.parseInt(searchDropDown.getSelectedItem().toString().substring(searchDropDown.getSelectedItem().toString().indexOf(" ")+1));
        repaint();
    }

    public NeurPanel() {
        setBackground(Color.BLACK);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawNeuron(g,Main.subs.get(currentlySelectedSubjectIndex));
    }
    public void drawNeuron(Graphics g, Subject subject) {
    
        Neuron[] neurons = subject.getGenome().getNeurons();
        int i = 1;
        
        for (Neuron n : neurons) {
            switch(n.getClassType()) {
                // Internal
                case "Internal": internalNeuron(g, i, n);
                i++;
                break;
                // Sensor
                case "Sensor": sensorNeuron(g, i, n);
                i++;
                break;
                // Motor
                case "Motor": motorNeuron(g, i, n);
                i++;
                break;
            }
        }
        // Sinks
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
                g.drawLine(n.getPrintPos(this).x() + 10, n.getPrintPos(this).y() + 10, s.getKey().getPrintPos(this).x() + 10, s.getKey().getPrintPos(this).y() + 10);
                // System.out.println(s.getKey());
            }
        }
        //Sources
        // for (Neuron n : neurons) {
        //     for (Neuron s : n.getSources()) {
        //         switch (s.getClassType()) {
        //             case "Internal": g.setColor(Color.green);
        //             break;
        //             case "Sensor": g.setColor(Color.red);
        //             break;
        //             case "Motor": g.setColor(Color.blue);
        //             break;
        //         }
        //         g.drawLine(n.getPrintPos(this).x() + 10, n.getPrintPos(this).y() + 10, s.getPrintPos(this).x() + 10, s.getPrintPos(this).y() + 10);
        //     }
        // }
        
    }

    public void internalNeuron(Graphics g, int i, Neuron n) {
        g.setColor(Color.green);
        n.setPosX(5);
        n.setPosY(i);
        g.drawOval(n.getPrintPos(this).x(), n.getPrintPos(this).y(), 20, 20);
    }
    public void sensorNeuron(Graphics g, int i, Neuron n) {
        g.setColor(Color.red);
        n.setPosX(2);
        n.setPosY(i);
        g.drawOval(n.getPrintPos(this).x(), n.getPrintPos(this).y(), 20, 20);
    }
    public void motorNeuron(Graphics g, int i, Neuron n) {
        g.setColor(Color.blue);
        n.setPosX(8);
        n.setPosY(i);
        g.drawOval(n.getPrintPos(this).x(), n.getPrintPos(this).y(), 20, 20);
    }
}
