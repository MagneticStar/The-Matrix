import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SaveLoadPanel extends JPanel{
    private JButton newFileButton;
    private JButton oldFileButton;
    private JTextField newFileTextField;

    public SaveLoadPanel(String panelType) {
        this.setLayout(new GridBagLayout());
        setBackground(Color.black);

        if(panelType.equals("save")){
            createSaveComponents();
        }
        else{
            createLoadComponents();
        }
        addComponents();
    }

    public void createSaveComponents(){
        // Button
        oldFileButton = new JButton("Save to Old File");
        oldFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save to old file
        }});
        // Button
        newFileButton = new JButton("Create New File");
        newFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = newFileTextField.getText();
                // Create New File
        }});
    }
    public void createLoadComponents(){
        // Button
        oldFileButton = new JButton("Load Old File");
        oldFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Load Old File
                Main.startSimulation();
        }});

        // Button
        newFileButton = new JButton("Create New File");
        newFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = newFileTextField.getText();
                // Create New File
                Main.startSimulation();
        }});
    }
    public void addComponents(){
        // Textfield
        newFileTextField = new JTextField(20);
        newFileTextField.setForeground(Color.black);
        newFileTextField.setBackground(Color.white);
        
        // Add the components (order matters)
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, 10);
        c.gridx = 2;
        c.gridy = 1;
        this.add(newFileTextField,c);
        c.insets = new Insets(0, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 1;
        this.add(oldFileButton,c);
        c.gridx = 1;
        c.gridy = 1;
        this.add(newFileButton,c);
        
        this.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }
}
