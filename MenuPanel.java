import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.BitSet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel{
    private JButton newFileButton;
    private JButton oldFileButton;
    private JTextField newFileTextField;
    private BufferedImage logo;

    public MenuPanel() {
        this.setLayout(new GridBagLayout());
        setBackground(Color.black);
        createLoadComponents();
        addComponents();
        
        try {                
          logo = ImageIO.read(new File("Logo.png"));
       } catch (IOException ex) {
       }
    }
    
    public void createLoadComponents(){
        // Button
        oldFileButton = new JButton("Load Old File");
        oldFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Load Old File
                try {
                FileInputStream file = new FileInputStream(newFileTextField.getText());
                ObjectInputStream in = new ObjectInputStream(file);
                
                Main.loaded = (Database)((Database)in.readObject()).clone();
                for (int i = 0; i < Main.loaded.creaturesList.length; i++) {
                    Main.loaded.creaturesList[i] = new Creature((BitSet)in.readObject());
                }
                in.close();
            } catch (Exception exception) {
                System.out.println(exception);
                // Create generation 0
                for (int i = 0; i < Main.loaded.creaturesList.length; i++) {
                    Main.loaded.creaturesList[i] = new Creature();
                }
            }
                Main.loaded.visualPanel = Screens.simulationPanel;
                Main.startThread();
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

        c.insets = new Insets(0, 0, 0, -350);
        c.gridx = 0;
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
        g.drawImage(logo, 0, 0, this);
    }
}

