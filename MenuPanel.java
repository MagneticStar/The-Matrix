import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel{
    private JButton startButton;
    private JLabel logoLabel;
    private JTextField generationSizeTextField;
    private JTextField startingFoodCountTextField;
    private JTextField minimumFoodEatenTextField;
    private JTextField generationLengthTextField;
    private JTextField simulationLengthTextField;
    private JTextField worldSizeTextField;
    private JTextField searchDepthTextField;
    private JTextField repoductionPerCreatureTextField;
    private JTextField mutationChanceTextField;
    private JTextField genomeLengthTextField;
    private JTextField tickDelayTextField;
    private JCheckBox doVisualsCheckbox;
    private JCheckBox autoStartGenerationCheckbox;
    private JCheckBox autoStartStepCheckbox;

    public MenuPanel() {
        this.setLayout(new GridBagLayout());
        setBackground(Color.black);
        addComponents();
    }
    
    public void addComponents(){
        // JButton
        startButton = new JButton("Start Simulation");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.loaded.generationSize = Integer.parseInt(generationSizeTextField.getText());
                Main.loaded.startingFoodCount = Integer.parseInt(startingFoodCountTextField.getText());
                Main.loaded.minimumFoodEaten = Integer.parseInt(minimumFoodEatenTextField.getText());
                Main.loaded.generationLength = Integer.parseInt(generationLengthTextField.getText());
                Main.loaded.simulationLength = Integer.parseInt(simulationLengthTextField.getText());
                Main.loaded.worldSize = Integer.parseInt(worldSizeTextField.getText());
                Main.loaded.searchDepth = Integer.parseInt(searchDepthTextField.getText());
                Main.loaded.repoductionPerCreature = Integer.parseInt(repoductionPerCreatureTextField.getText());
                Main.loaded.mutationChance = Double.parseDouble(mutationChanceTextField.getText());
                Main.loaded.genomeLength = Integer.parseInt(genomeLengthTextField.getText());
                Main.loaded.tickDelay = Integer.parseInt(tickDelayTextField.getText());
                Main.loaded.doVisuals = doVisualsCheckbox.isSelected();
                Main.loaded.autoStartGeneration = autoStartGenerationCheckbox.isSelected();
                Main.loaded.autoStartStep = autoStartStepCheckbox.isSelected();
                Main.startThread();
        }});

        generationSizeTextField = new JTextField(String.valueOf(Main.loaded.generationSize), 20);
        startingFoodCountTextField = new JTextField(String.valueOf(Main.loaded.startingFoodCount), 20);
        minimumFoodEatenTextField = new JTextField(String.valueOf(Main.loaded.minimumFoodEaten), 20);
        generationLengthTextField = new JTextField(String.valueOf(Main.loaded.generationLength), 20);
        simulationLengthTextField = new JTextField(String.valueOf(Main.loaded.simulationLength), 20);
        worldSizeTextField = new JTextField(String.valueOf(Main.loaded.worldSize), 20);
        searchDepthTextField = new JTextField(String.valueOf(Main.loaded.searchDepth), 20);
        repoductionPerCreatureTextField = new JTextField(String.valueOf(Main.loaded.repoductionPerCreature), 20);
        mutationChanceTextField = new JTextField(String.valueOf(Main.loaded.mutationChance), 20);
        genomeLengthTextField = new JTextField(String.valueOf(Main.loaded.genomeLength), 20);
        tickDelayTextField = new JTextField(String.valueOf(Main.loaded.tickDelay), 20);

        // JTextFields
        generationSizeTextField.setForeground(Color.black);
        generationSizeTextField.setBackground(Color.white);

        startingFoodCountTextField.setForeground(Color.black);
        startingFoodCountTextField.setBackground(Color.white);

        minimumFoodEatenTextField.setForeground(Color.black);
        minimumFoodEatenTextField.setBackground(Color.white);

        generationLengthTextField.setForeground(Color.black);
        generationLengthTextField.setBackground(Color.white);

        simulationLengthTextField.setForeground(Color.black);
        simulationLengthTextField.setBackground(Color.white);

        worldSizeTextField.setForeground(Color.black);
        worldSizeTextField.setBackground(Color.white);

        searchDepthTextField.setForeground(Color.black);
        searchDepthTextField.setBackground(Color.white);

        repoductionPerCreatureTextField.setForeground(Color.black);
        repoductionPerCreatureTextField.setBackground(Color.white);

        mutationChanceTextField.setForeground(Color.black);
        mutationChanceTextField.setBackground(Color.white);

        genomeLengthTextField.setForeground(Color.black);
        genomeLengthTextField.setBackground(Color.white);

        tickDelayTextField.setForeground(Color.black);
        tickDelayTextField.setBackground(Color.white);

        // JLabels
        JLabel generationSizeLabel = new JLabel();
        generationSizeLabel.setText("Generation Size:");
        generationSizeLabel.setForeground(Color.WHITE);

        JLabel startingFoodCountLabel = new JLabel();
        startingFoodCountLabel.setText("Starting Food Count:");
        startingFoodCountLabel.setForeground(Color.WHITE);

        JLabel minimumFoodEatenLabel = new JLabel();
        minimumFoodEatenLabel.setText("Minimum Food Eaten:");
        minimumFoodEatenLabel.setForeground(Color.WHITE);

        JLabel generationLengthLabel = new JLabel();
        generationLengthLabel.setText("Generation Length:");
        generationLengthLabel.setForeground(Color.WHITE);

        JLabel simulationLengthLabel = new JLabel();
        simulationLengthLabel.setText("Simulation Length:");
        simulationLengthLabel.setForeground(Color.WHITE);

        JLabel worldSizeLabel = new JLabel();
        worldSizeLabel.setText("World Size:");
        worldSizeLabel.setForeground(Color.WHITE);

        JLabel searchDepthLabel = new JLabel();
        searchDepthLabel.setText("Search Depth:");
        searchDepthLabel.setForeground(Color.WHITE);

        JLabel repoductionPerCreatureLabel = new JLabel();
        repoductionPerCreatureLabel.setText("Reproduction per Creature:");
        repoductionPerCreatureLabel.setForeground(Color.WHITE);

        JLabel mutationChanceLabel = new JLabel();
        mutationChanceLabel.setText("Mutation Chance:");
        mutationChanceLabel.setForeground(Color.WHITE);

        JLabel genomeLengthLabel = new JLabel();
        genomeLengthLabel.setText("Genome Length:");
        genomeLengthLabel.setForeground(Color.WHITE);

        JLabel tickDelayLabel = new JLabel();
        tickDelayLabel.setText("Tick Delay:");
        tickDelayLabel.setForeground(Color.WHITE);

        // JCheckBox
        doVisualsCheckbox = new JCheckBox();
        doVisualsCheckbox.setText("Do Visuals");
        doVisualsCheckbox.setSelected(Main.loaded.doVisuals);
        doVisualsCheckbox.setBackground(Color.BLACK);
        doVisualsCheckbox.setForeground(Color.WHITE);

        autoStartGenerationCheckbox = new JCheckBox();
        autoStartGenerationCheckbox.setText("Auto Start Generation");
        autoStartGenerationCheckbox.setSelected(Main.loaded.autoStartGeneration);
        autoStartGenerationCheckbox.setBackground(Color.BLACK);
        autoStartGenerationCheckbox.setForeground(Color.WHITE);

        autoStartStepCheckbox = new JCheckBox();
        autoStartStepCheckbox.setText("Auto Start Step");
        autoStartStepCheckbox.setSelected(Main.loaded.autoStartStep);
        autoStartStepCheckbox.setBackground(Color.BLACK);
        autoStartStepCheckbox.setForeground(Color.WHITE);

        try {         
          ImageIcon logoIcon = (new ImageIcon(ImageIO.read(new File("Logo.png"))));
          logoLabel = new JLabel(logoIcon);
        }catch (IOException e) {}
        
        
        // Add the components (order matters)
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        // Grid of components
        c.gridx = 0; c.gridy = 1; this.add(generationSizeLabel, c);
        c.gridx = 1; c.gridy = 1; this.add(generationSizeTextField, c);

        c.gridx = 2; c.gridy = 1; this.add(startingFoodCountLabel, c);
        c.gridx = 3; c.gridy = 1; this.add(startingFoodCountTextField, c);

        c.gridx = 4; c.gridy = 1; this.add(minimumFoodEatenLabel, c);
        c.gridx = 5; c.gridy = 1; this.add(minimumFoodEatenTextField, c);

        c.gridx = 0; c.gridy = 2; this.add(generationLengthLabel, c);
        c.gridx = 1; c.gridy = 2; this.add(generationLengthTextField, c);

        c.gridx = 2; c.gridy = 2; this.add(simulationLengthLabel, c);
        c.gridx = 3; c.gridy = 2; this.add(simulationLengthTextField, c);

        c.gridx = 4; c.gridy = 2; this.add(worldSizeLabel, c);
        c.gridx = 5; c.gridy = 2; this.add(worldSizeTextField, c);

        c.gridx = 0; c.gridy = 3; this.add(searchDepthLabel, c);
        c.gridx = 1; c.gridy = 3; this.add(searchDepthTextField, c);

        c.gridx = 2; c.gridy = 3; this.add(repoductionPerCreatureLabel, c);
        c.gridx = 3; c.gridy = 3; this.add(repoductionPerCreatureTextField, c);

        c.gridx = 4; c.gridy = 3; this.add(mutationChanceLabel, c);
        c.gridx = 5; c.gridy = 3; this.add(mutationChanceTextField, c);

        c.gridx = 0; c.gridy = 4; this.add(genomeLengthLabel, c);
        c.gridx = 1; c.gridy = 4; this.add(genomeLengthTextField, c);

        c.gridx = 2; c.gridy = 4; this.add(tickDelayLabel, c);
        c.gridx = 3; c.gridy = 4; this.add(tickDelayTextField, c);

        c.gridx = 4; c.gridy = 4; this.add(doVisualsCheckbox, c);

        c.gridx = 2; c.gridy = 5; this.add(autoStartGenerationCheckbox, c);

        c.gridx = 3; c.gridy = 5; this.add(autoStartStepCheckbox, c);

        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 2; c.gridy = 6; c.gridwidth = 2; 
        this.add(startButton, c);

        // Logo
        c.gridx = 0; c.gridy = 0;
        c.gridwidth = 6;
        c.anchor = GridBagConstraints.CENTER;

        this.add(logoLabel, c);
        
        this.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }
}
