import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel{
    private JButton newFileButton;
    private JButton oldFileButton;
    private JLabel logo;
    private JTextField newFileTextField;

    public MenuPanel() {
        this.setLayout(new GridBagLayout());
        setBackground(Color.black);
        createLoadComponents();
        addComponents();
    }
    
    public void createLoadComponents(){
        // Button
        oldFileButton = new JButton("Load Old File");
        oldFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Load Old File
                Main.startThread();
        }});

        // Button
        newFileButton = new JButton("Create New File");
        newFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = newFileTextField.getText();
                // Create New File
                Main.startThread();
        }});
    }
    public void addComponents(){
        // Textfield
        newFileTextField = new JTextField(20);
        newFileTextField.setForeground(Color.black);
        newFileTextField.setBackground(Color.white);
        try {         
          ImageIcon logoIcon = (new ImageIcon(ImageIO.read(new File("Logo.png"))));
          logo = new JLabel(logoIcon);
        }catch (IOException ex) {}
        
        
        // Add the components (order matters)
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 0, -350);
        c.gridx = 0;
        c.gridy = 1;
        this.add(newFileTextField,c);
        c.insets = new Insets(0, 0, 0, 300);
        c.gridx = 0;
        c.gridy = 1;
        this.add(oldFileButton,c);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 1;
        this.add(newFileButton,c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        this.add(logo,c);
        
        this.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }
}
