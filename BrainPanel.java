import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class BrainPanel extends JPanel {
    
    private JComboBox<String> searchDropDown;
    private int currentlySelectedSubjectIndex = 0;

    public void selectionBox(){
        searchDropDown = new JComboBox<String>(Screens.subNames);
        searchDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Sets the neuron map panel to the neuron map of the selected subject. The string manipulation is to avoid searching for the index of the subject
            currentlySelectedSubjectIndex = Integer.parseInt(searchDropDown.getSelectedItem().toString().substring(searchDropDown.getSelectedItem().toString().indexOf(" ")+1));
            Database.brainScreenSizeY = Database.creaturesList.get(currentlySelectedSubjectIndex).getGenome().getNeurons().length;
            repaint();
            }
        });
        Screens.brainPanel.add(searchDropDown);
        Screens.brainPanel.revalidate();
    }

    public BrainPanel() {
        setBackground(Color.BLACK);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawNeuron(g);
    }
    public void drawNeuron(Graphics g) {
        if (Database.creaturesList.length == 0 || currentlySelectedCreatureIndex == -1 || Database.creaturesList[currentlySelectedCreatureIndex] == null) {
            return;
        }
        Creature subject = Database.creaturesList.get(currentlySelectedSubjectIndex);
        
        Neuron[] neurons = Database.creaturesList[currentlySelectedCreatureIndex].getGenome().getNeurons();
        Database.brainScreenSizeY = neurons.length;
        
        int i = 1;
        int ic = 0;
        int sc = 0;
        int mc = 0;
        for (Neuron n : neurons) {
            switch(n.getClassType()) {
                // Internal
                case "Internal":
                internalNeuron(g,i,ic,n);
                ic++;
                break;
                // Sensor
                case "Sensor":
                sensorNeuron(g,i,sc,n);
                sc++;
                break;
                // Motor
                case "Motor":
                motorNeuron(g,i,mc,n);
                mc++;
                break;
            }
            i++;
        }
        
        // Sinks
        for (Neuron n : neurons) {
            for (Map.Entry<Neuron, Integer> s : n.getSinks().entrySet()) {
                switch (s.getKey().getClassType()) {
                    case "Internal": g.setColor(Color.green);
                    break;
                    case "Motor": g.setColor(Color.blue);
                    break;
                }
                g.drawLine(n.getPrintPos().x() + 10, n.getPrintPos().y() + 10, s.getKey().getPrintPos().x() + 10, s.getKey().getPrintPos().y() + 10);
                // System.out.println(s.getKey());
            }
        }
        // Sources
        for (Neuron n : neurons) {
            for (Neuron s : n.getSources()) {
                switch (s.getClassType()) {
                    case "Internal": g.setColor(Color.magenta);
                    break;
                    case "Sensor": g.setColor(Color.red);
                    break;
                }
                g.drawLine(n.getPrintPos().x() + 10, n.getPrintPos().y() + 10, s.getPrintPos().x() + 10, s.getPrintPos().y() + 10);
            }
        }
    }

    public void internalNeuron(Graphics g, int i, int nc, Neuron n) {
        g.setColor(Color.green);
        n.setPosX(15+((nc+i)%4)*(int)Math.pow(-1,(nc+i)%3));
        n.setPosY(i);
        g.drawOval(n.getPrintPos().x(), n.getPrintPos().y(), 20, 20);
    }
    public void sensorNeuron(Graphics g, int i, int nc, Neuron n) {
        g.setColor(Color.red);
        n.setPosX(8+((nc+i)%3)*(int)Math.pow(-1,(nc+i)%3));
        n.setPosY(i);
        g.drawOval(n.getPrintPos().x(), n.getPrintPos().y(), 20, 20);
    }
    public void motorNeuron(Graphics g, int i, int nc, Neuron n) {
        g.setColor(Color.blue);
        n.setPosX(22+((nc+i)%3)*(int)Math.pow(-1,(nc+i)%3));
        n.setPosY(i);
        g.drawOval(n.getPrintPos().x(), n.getPrintPos().y(), 20, 20);
    }
}