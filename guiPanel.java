import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Panel that displays information about the simulation 
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

    // update information
    public void updateLabel(){
        trackersText = "<br/><br/>Step: "+(Main.loadedDatabase.currentGenerationTick+1)+"/"+Main.loadedDatabase.generationLength;
        trackersText += "<br/>Generation: "+(Main.loadedDatabase.currentGeneration+1)+"/"+Main.loadedDatabase.simulationLength;
        trackersText += "<br/>Food: "+Main.loadedDatabase.currentFoodCount+"/"+Main.loadedDatabase.startingFoodCount;
        trackersText += "<br/>Reproduced Last Generation: "+Main.loadedDatabase.reproducedLastGeneration+"/"+Main.loadedDatabase.generationSize;
        trackersText += "<br/>Food Eaten Last Generation: "+Main.loadedDatabase.foodEatenLastGeneration+"/"+Main.loadedDatabase.startingFoodCount+"</html>";
        settingsLabelText = settingsText+trackersText;
        settingsLabel.setText(settingsLabelText);
    }

    private void addComponents(){
        highlightComboBox = new JComboBox<String>(Screens.creatureNames);
        highlightComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Sets the neuron map panel to the neuron map of the selected subject. The string manipulation is to avoid searching for the index of the subject
            currentlySelectedCreatureIndex = highlightComboBox.getSelectedIndex()-1;
            repaint();
        }});

        // Checkbox
        startGenerationsCheckBox = new JCheckBox("Automatically Start Generations",Main.loadedDatabase.autoStartGeneration);
        startGenerationsCheckBox.setForeground(Color.white);
        startGenerationsCheckBox.setBackground(Color.black);
        startGenerationsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.loadedDatabase.autoStartGeneration = !Main.loadedDatabase.autoStartGeneration;
            repaint();
        }});

        // Checkbox
        showVisualsCheckBox = new JCheckBox("Show Visuals",Main.loadedDatabase.doVisuals);
        showVisualsCheckBox.setForeground(Color.white);
        showVisualsCheckBox.setBackground(Color.black);
        showVisualsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.loadedDatabase.doVisuals = !Main.loadedDatabase.doVisuals;

            if(Main.loadedDatabase.doVisuals){
                Main.loadedDatabase.visualPanel = Screens.simulationPanel;
            }
            else{
                Main.loadedDatabase.visualPanel = Screens.animationPanel;
            }
            Screens.simulationSplitPane.setLeftComponent(Main.loadedDatabase.visualPanel);
            repaint();
        }});

        // Save and Exit Button
        saveSimulationButton = new JButton("Save and Exit");
        saveSimulationButton.setForeground(Color.white);
        saveSimulationButton.setBackground(Color.black);
        saveSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.loadedDatabase.saveAndExit = !Main.loadedDatabase.saveAndExit;
            repaint();
        }});

        // Start Next Generation Button
        startGenerationButton = new JButton("Start Next Generation");
        startGenerationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(Main.loadedDatabase.generationFinished == true){
                Main.loadedDatabase.generationFinished = false;
                Main.loadedDatabase.startNextGeneration = true;
            }
            repaint();
        }});

        // Information Label
        settingsText = "<html>Generation Size: "+Main.loadedDatabase.generationSize+"<br/>Generation Length: "+Main.loadedDatabase.generationLength+"<br/>World Size: "+Main.loadedDatabase.worldSize+"<br/>Mutation Chance: "+Main.loadedDatabase.mutationChance+"<br/>Genome Size: "+Main.loadedDatabase.genomeLength;
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
