import java.awt.*;
import java.awt.event.*;
import java.awt.image.DataBuffer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class guiPanel extends JPanel {

    private JComboBox<String> highlightComboBox;
    private JCheckBox startGenerationsCheckBox;
    private JCheckBox showVisualsCheckBox;
    private JCheckBox saveSimulationCheckbox;
    private JButton startGenerationButton;
    private JLabel settingsLabel;
    private String settingsText;
    private String trackersText;
    private String settingsLabelText;
    public static int currentlySelectedCreatureIndex = -1;

    public guiPanel() {
        setBackground(Database.simulationScreenColor);

        addComponents();
    }

    public void updateLabel(){
        trackersText = "<br/><br/>Current Step: "+(Database.currentGenerationTick+1);
        trackersText += "<br/>Current Generation: "+(Database.currentGeneration+1);
        trackersText += "<br/>Reproduced Last Generation: "+Database.reproducedLastGeneration;
        trackersText += "<br/>Food Count: "+Database.foodsList.size()+"</html>";

        settingsLabelText = settingsText+trackersText;
        
        settingsLabel.setText(settingsLabelText);
        repaint();
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

            // Debug
            // System.out.println(Database.creaturesList.get(currentlySelectedCreatureIndex).getPos().toString());
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

            if(Database.doVisuals){
                Database.visualPanel = Screens.simulationPanel;
            }
            else{
                Database.visualPanel = Screens.animationPanel;
            }
            Screens.splitPane.setLeftComponent(Database.visualPanel);
            repaint();
        }});

        // Checkbox
        saveSimulationCheckbox = new JCheckBox("Save and Exit",Database.saveAndExit);
        saveSimulationCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Database.saveAndExit = !Database.saveAndExit;
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

        // Label
        settingsText = "<html>Generation Size: "+Database.generationSize+"<br/>Generation Length: "+Database.generationLength+"<br/>World Size: "+Database.worldSize+"<br/>Mutation Chance: "+Database.mutationChance+"<br/>Genome Size: "+Database.genomeLength;
        settingsLabel = new JLabel();
        updateLabel();
        settingsLabel.setForeground(Color.WHITE);
        
        // Add the components (order matters)
        this.add(highlightComboBox);
        this.add(startGenerationButton);
        this.add(showVisualsCheckBox);
        this.add(startGenerationsCheckBox);
        this.add(saveSimulationCheckbox);
        this.add(settingsLabel);
        
        this.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }

    

}
