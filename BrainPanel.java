import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import javax.swing.JPanel;

public class BrainPanel extends JPanel implements ActionListener{
    
    
    private static TextField searchBar;
    private static Button searchButton;
    private static Label searchReply;

    public static void main(String[] args){
        searchButton = new Button("Find");
        searchReply = new Label("");
        
        BrainPanel nP = new BrainPanel();
        // Adds an action listener to the button
        searchButton.addActionListener(nP);
        // creates a TextField object with 16 columns
        searchBar = new TextField(16);
        // creates a font object and sets the TextField font to the newly defined font object
        Font searchBarFont = new Font("Serif",Font.BOLD,20);
        searchBar.setFont(searchBarFont);
    }

    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        if (s.equals("Find")){
            searchReply.setText(searchBar.getText());

            searchBar.setText("");
        }
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
        
        Creature subject = Database.creaturesList.get(0);
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
        //         g.drawLine(n.getPrintPos().x() + 15, n.getPrintPos().y() + 15, s.getPrintPos().x() + 15, s.getPrintPos().y() + 15);
        //     }
        // }
        
    }

    public void internalNeuron(Graphics g, int i, Neuron n) {
        g.setColor(Color.green);
        n.setPosX(5);
        n.setPosY(i);
        g.drawOval(n.getPrintPos().x(), n.getPrintPos().y(), 30, 30);
    }
    public void sensorNeuron(Graphics g, int i, Neuron n) {
        g.setColor(Color.red);
        n.setPosX(2);
        n.setPosY(i);
        g.drawOval(n.getPrintPos().x(), n.getPrintPos().y(), 30, 30);
    }
    public void motorNeuron(Graphics g, int i, Neuron n) {
        g.setColor(Color.blue);
        n.setPosX(8);
        n.setPosY(i);
        g.drawOval(n.getPrintPos().x(), n.getPrintPos().y(), 30, 30);
    }
}
