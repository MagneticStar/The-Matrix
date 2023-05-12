import java.awt.*;
import java.awt.event.*;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class guiPanel extends JPanel {

    private JComboBox<String> searchDropDown;
    public static int currentlySelectedCreatureIndex = 0;

    public guiPanel() {
        setBackground(Database.simulationScreenColor);
    }

    public void updateLabel(){
        
    }

    public void addComponents(){
        searchDropDown = new JComboBox<String>(Screens.creatureNames);
        searchDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Sets the neuron map panel to the neuron map of the selected subject. The string manipulation is to avoid searching for the index of the subject
            currentlySelectedCreatureIndex = Integer.parseInt(searchDropDown.getSelectedItem().toString().substring(searchDropDown.getSelectedItem().toString().indexOf(" ")+1));
            
            repaint();
            }
        });
        Screens.guiPanel.add(searchDropDown);
        Screens.guiPanel.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
    }

    

}
