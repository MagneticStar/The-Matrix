import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GUIPanel extends JPanel {

    private JComboBox<String> highlightComboBox;
    private JCheckBox startGenerationsCheckBox;
    private JCheckBox showVisualsCheckBox;
    private JButton saveSimulationButton;
    private JButton startGenerationButton;
    private JLabel settingsLabel;
    private String settingsText;
    private String trackersText;
    private String settingsLabelText;
    public static int currentlySelectedCreatureIndex = -1;

    public GUIPanel() {
        this.setLayout(new GridBagLayout());
        setBackground(Color.black);
        addComponents();
    }

    public void updateLabel(){
        trackersText = "<br/><br/>Current Step: "+(Main.loaded.currentGenerationTick+1);
        trackersText += "<br/>Current Generation: "+(Main.loaded.currentGeneration+1);
        trackersText += "<br/>Reproduced Last Generation: "+Main.loaded.reproducedLastGeneration.get(Main.loaded.currentGeneration);
        trackersText += "<br/>Food Count: "+Main.loaded.currentFoodCount+"</html>";

        settingsLabelText = settingsText+trackersText;
        settingsLabel.setText(settingsLabelText);
    }

    public void addComponents(){

        // Step Selector Combobox
        // highlightComboBox = new JComboBox<String>();
        // highlightComboBox.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //     //
            
        //     repaint();
        // }});


        // Highlight Combobox
        highlightComboBox = new JComboBox<String>(Screens.creatureNames);
        highlightComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Sets the neuron map panel to the neuron map of the selected subject.
            currentlySelectedCreatureIndex = highlightComboBox.getSelectedIndex()-1;
            repaint();
        }});

        // Auto Start Generation Checkbox
        startGenerationsCheckBox = new JCheckBox("Automatically Start Generations",Main.loaded.autoStartGeneration);
        startGenerationsCheckBox.setForeground(Color.white);
        startGenerationsCheckBox.setBackground(Color.black);
        startGenerationsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.loaded.autoStartGeneration = !Main.loaded.autoStartGeneration;
            repaint();
        }});

        // Show Visuals Checkbox
        showVisualsCheckBox = new JCheckBox("Show Visuals",Main.loaded.doVisuals);
        showVisualsCheckBox.setForeground(Color.white);
        showVisualsCheckBox.setBackground(Color.black);
        showVisualsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.loaded.doVisuals = !Main.loaded.doVisuals;
            Screens.simulationSplitPane.setLeftComponent(Main.loaded.visualPanel);
            repaint();
        }});

        // Save and Exit Button
        saveSimulationButton = new JButton("Save and Exit");
        saveSimulationButton.setForeground(Color.white);
        saveSimulationButton.setBackground(Color.black);
        saveSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.loaded.saveAndExit = !Main.loaded.saveAndExit;
            repaint();
        }});

        // Start Generation Button
        startGenerationButton = new JButton("Start Next Generation");
        startGenerationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(Main.loaded.generationFinished == true){
                Main.loaded.generationFinished = false;
                Main.loaded.startNextGeneration = true;
            }
            repaint();
        }});

        // Simulation Data Label
        settingsText = "<html>Generation Size: "+Main.loaded.generationSize+"<br/>Generation Length: "+Main.loaded.generationLength+"<br/>World Size: "+Main.loaded.worldSize+"<br/>Mutation Chance: "+Main.loaded.mutationChance+"<br/>Genome Size: "+Main.loaded.genomeLength;
        settingsLabel = new JLabel();
        updateLabel();
        settingsLabel.setForeground(Color.WHITE);
        
        // Add the components (order matters)
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        this.add(highlightComboBox,c);
        c.gridx = 0;
        c.gridy = 1;
        this.add(startGenerationButton,c);
        c.gridx = 1;
        c.gridy = 0;
        this.add(showVisualsCheckBox,c);    
        c.gridx = 1;
        c.gridy = 1;
        this.add(startGenerationsCheckBox,c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        this.add(settingsLabel,c);
        c.gridx = 0;
        c.gridy = 3;
        this.add(saveSimulationButton,c);
        
        this.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }
}
