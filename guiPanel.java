import java.awt.*;
import java.awt.event.*;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class guiPanel extends JPanel {

    private JComboBox<String> searchDropDown;
    private JCheckBox automaticallyRunGenerations;
    public static int currentlySelectedSubjectIndex = 0;

    public guiPanel() {
        setBackground(Database.simulationScreenColor);
    }

    public void addComponents(){
        // Combobox
        searchDropDown = new JComboBox<String>(Screens.subNames);
        searchDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Sets the neuron map panel to the neuron map of the selected subject. The string manipulation is to avoid searching for the index of the subject
            currentlySelectedSubjectIndex = Integer.parseInt(searchDropDown.getSelectedItem().toString().substring(searchDropDown.getSelectedItem().toString().indexOf(" ")+1));
            // repaint();
            }
        });
        Screens.guiPanel.add(searchDropDown);

        // Checkbox
        automaticallyRunGenerations = new JCheckBox("Automatically Run Generations",false);
        automaticallyRunGenerations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Database.runNextGeneration = !Database.runNextGeneration;
            // repaint();
            }
        });

        // Button
        Screens.guiPanel.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }

    

}
