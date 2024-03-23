import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPanel extends JPanel {

    private JLabel highlightLabel, settingsLabel, stepSelectorLabel;
    private JComboBox<String> highlightComboBox;
    public JComboBox<String> stepSelectorComboBox;
    private JCheckBox startGenerationsCheckBox, startStepsCheckBox, showVisualsCheckBox;
    private JButton saveSimulationButton, startGenerationButton, startStepButton;
    private String settingsText, trackersText, settingsLabelText;
    public static int currentlySelectedCreatureIndex = -1;

    public GUIPanel() {
        this.setLayout(new GridBagLayout());
        setBackground(Color.black);
        addComponents();
    }

    public void updateLabel(){
        trackersText = "<br/><br/>Current Step: "+(Simulation.simulation.observedGenerationTick);
        trackersText += "<br/>Current Generation: "+(Simulation.simulation.currentGeneration+1);
        trackersText += "<br/>Reproduced Last Generation: "+Simulation.simulation.reproducedLastGeneration.get(Simulation.simulation.currentGeneration);
        trackersText += "<br/>Food Count: "+Simulation.simulation.worldObjects.getFoodCountAtTick(Simulation.simulation.observedGenerationTick)+"</html>";

        settingsLabelText = settingsText+trackersText;
        settingsLabel.setText(settingsLabelText);
    }

    public void addComponents(){

        // Step Selector Combobox
        stepSelectorComboBox = new JComboBox<String>();
        stepSelectorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // actionPerformed gets called when the first item is added and the last is removed but those instances don't need to count
            // as the user changing the combobox
            if(stepSelectorComboBox.getSelectedIndex() != Simulation.simulation.currentGenerationTick && stepSelectorComboBox.getSelectedIndex() >= 0){
                // Changes the observed tick
                Simulation.simulation.observedGenerationTick = stepSelectorComboBox.getSelectedIndex();
                Simulation.simulation.selectedTick = true;
            }
            repaint();
        }});

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
        startGenerationsCheckBox = new JCheckBox("Automatically Start Generations",Simulation.simulation.autoStartGeneration);
        startGenerationsCheckBox.setForeground(Color.white);
        startGenerationsCheckBox.setBackground(Color.black);
        startGenerationsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulation.simulation.autoStartGeneration = !Simulation.simulation.autoStartGeneration;
            repaint();
        }});
        // Auto Start Step Checkbox
        startStepsCheckBox = new JCheckBox("Automatically Start Steps",Simulation.simulation.autoStartStep);
        startStepsCheckBox.setForeground(Color.white);
        startStepsCheckBox.setBackground(Color.black);
        startStepsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulation.simulation.autoStartStep = !Simulation.simulation.autoStartStep;
            repaint();
        }});

        // Show Visuals Checkbox
        showVisualsCheckBox = new JCheckBox("Show Visuals",Simulation.simulation.doVisuals);
        showVisualsCheckBox.setForeground(Color.white);
        showVisualsCheckBox.setBackground(Color.black);
        showVisualsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulation.simulation.doVisuals = !Simulation.simulation.doVisuals;
            Screens.simulationSplitPane.setLeftComponent(Simulation.simulation.visualPanel);
            repaint();
        }});

        // Save and Exit Button
        saveSimulationButton = new JButton("Exit");
        saveSimulationButton.setForeground(Color.white);
        saveSimulationButton.setBackground(Color.black);
        saveSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Simulation.simulation.saveAndExit = !Simulation.simulation.saveAndExit;
                System.exit(1);
            repaint();
        }});

        // Start Generation Button
        startGenerationButton = new JButton("Start Next Generation");
        startGenerationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(Simulation.simulation.generationFinished == true){
                Simulation.simulation.generationFinished = false;
                Simulation.simulation.startNextGeneration = true;
            }
            repaint();
        }});
        // Start Generation Button
        startStepButton = new JButton("Start Next Step");
        startStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(Simulation.simulation.stepFinished == true){
                Simulation.simulation.stepFinished = false;
                Simulation.simulation.startNextStep = true;
            }
            repaint();
        }});

        // Simulation Data Label
        settingsText = "<html>Generation Size: "+Simulation.simulation.generationSize+"<br/>Generation Length: "+Simulation.simulation.generationLength+"<br/>World Size: "+Simulation.simulation.worldSize+"<br/>Mutation Chance: "+Simulation.simulation.mutationChance+"<br/>Genome Size: "+Simulation.simulation.genomeLength;
        settingsLabel = new JLabel();
        updateLabel();
        settingsLabel.setForeground(Color.WHITE);

        // Highlight Label
        highlightLabel = new JLabel();
        highlightLabel.setText("Highlight Creature");
        highlightLabel.setForeground(Color.WHITE);

        // Step Selector Label
        stepSelectorLabel = new JLabel();
        stepSelectorLabel.setText("Select Step");
        stepSelectorLabel.setForeground(Color.WHITE);
        
        // Add the components (order matters)
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        this.add(highlightLabel,c);
        c.gridx = 0;
        c.gridy = 1;
        this.add(highlightComboBox,c);
        c.gridx = 1;
        c.gridy = 1;
        this.add(showVisualsCheckBox,c); 
        c.gridx = 0;
        c.gridy = 2;
        this.add(startGenerationButton,c);   
        c.gridx = 1;
        c.gridy = 2;
        this.add(startGenerationsCheckBox,c);
        c.gridx = 0;
        c.gridy = 3;
        this.add(stepSelectorLabel,c);
        c.gridx = 0;
        c.gridy = 4;
        this.add(stepSelectorComboBox,c);
        c.gridx = 0;
        c.gridy = 5;
        this.add(startStepButton,c);
        c.gridx = 1;
        c.gridy = 5;
        this.add(startStepsCheckBox,c);
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        this.add(settingsLabel,c);
        c.gridx = 0;
        c.gridy = 7;
        this.add(saveSimulationButton,c);
        
        this.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }
}
