import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class guiPanel extends JPanel {

    private JComboBox<String> highlightComboBox;
    private JCheckBox startGenerationsCheckBox;
    private JCheckBox showVisualsCheckBox;
    private JButton startGenerationButton;
    public static int currentlySelectedCreatureIndex = -1;

    public guiPanel() {
        setBackground(Database.simulationScreenColor);
    }

    public void addComponents(){

        // Combobox
        highlightComboBox = new JComboBox<String>(Screens.creatureNames);
        highlightComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Sets the neuron map panel to the neuron map of the selected subject. The string manipulation is to avoid searching for the index of the subject
            // currentlySelectedCreatureIndex = Integer.parseInt(highlightComboBox.getSelectedItem().toString().substring(highlightComboBox.getSelectedItem().toString().indexOf(" ")+1));
            currentlySelectedCreatureIndex = highlightComboBox.getSelectedIndex()-1;
            repaint();
        }});

        // Checkbox
        startGenerationsCheckBox = new JCheckBox("Automatically Start Generations",Database.autoStartGeneration);
        startGenerationsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Database.autoStartGeneration = !Database.autoStartGeneration;
            repaint();
        }});

        // Checkbox
        showVisualsCheckBox = new JCheckBox("Show Visuals",Database.doVisuals);
        showVisualsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Database.doVisuals = !Database.doVisuals;
            repaint();
        }});

        // Button
        startGenerationButton = new JButton("Start Next Generation");
        startGenerationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(Database.generationFinished == true){
                Database.generationFinished = false;
                Database.startNextGeneration = true;
            }
            repaint();
        }});
        
        // Add the components (order matters)
        Screens.guiPanel.add(highlightComboBox);
        Screens.guiPanel.add(startGenerationButton);
        Screens.guiPanel.add(showVisualsCheckBox);
        Screens.guiPanel.add(startGenerationsCheckBox);

        Screens.guiPanel.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }

    

}
