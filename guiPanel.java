import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class guiPanel extends JPanel {

    private JComboBox<String> highlightComboBox;
    private JCheckBox startGenerationsCheckBox;
    private JCheckBox showVisualsCheckBox;
    private JButton startGenerationButton;
    public static int currentlySelectedSubjectIndex = 0;

    public guiPanel() {
        setBackground(Database.simulationScreenColor);
    }

    public void addComponents(){
        // Combobox
        highlightComboBox = new JComboBox<String>(Screens.subNames);
        highlightComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Sets the neuron map panel to the neuron map of the selected subject. The string manipulation is to avoid searching for the index of the subject
            currentlySelectedSubjectIndex = Integer.parseInt(highlightComboBox.getSelectedItem().toString().substring(highlightComboBox.getSelectedItem().toString().indexOf(" ")+1));
            repaint();
            }
        });
        Screens.guiPanel.add(highlightComboBox);

        // Checkbox
        startGenerationsCheckBox = new JCheckBox("Automatically Start Generations",Database.runNextGeneration);
        startGenerationsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Database.runNextGeneration = !Database.runNextGeneration;
            repaint();
            }
        });
        Screens.guiPanel.add(startGenerationsCheckBox);

        // Checkbox
        showVisualsCheckBox = new JCheckBox("Show Visuals",Database.doVisuals);
        showVisualsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Database.doVisuals = !Database.doVisuals;
            repaint();
            }
        });
        Screens.guiPanel.add(showVisualsCheckBox);

        // Button
        startGenerationButton = new JButton("Start Next Generation");
        startGenerationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(Database.generationFinished == true){
                Main.initializeNextGeneration();
            }
            Database.generationFinished = false;
            repaint();
            }
        });
        Screens.guiPanel.add(startGenerationButton);

        Screens.guiPanel.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }

    

}
