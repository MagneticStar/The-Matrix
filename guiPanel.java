import java.awt.*;
import java.awt.event.*;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class guiPanel extends JPanel {

    private JComboBox<String> searchDropDown;
    public static int currentlySelectedSubjectIndex = 0;

    public guiPanel() {
        setBackground(Database.simulationScreenColor);
    }

<<<<<<< Updated upstream
=======
    public void updateLabel(){
        trackersText = "<br/><br/>Current Step: "+(Database.currentGenerationTick+1);
        trackersText += "<br/>Current Generation: "+(Database.currentGeneration+1);
        trackersText += "<br/>Reproduced Last Generation: "+Database.reproducedLastGeneration;
        trackersText += "<br/>Food Count: "+Database.foodsList.length+"</html>";

        settingsLabelText = settingsText+trackersText;
        
        settingsLabel.setText(settingsLabelText);
        repaint();
    }

>>>>>>> Stashed changes
    public void addComponents(){
        searchDropDown = new JComboBox<String>(Screens.subNames);
        searchDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Sets the neuron map panel to the neuron map of the selected subject. The string manipulation is to avoid searching for the index of the subject
            currentlySelectedSubjectIndex = Integer.parseInt(searchDropDown.getSelectedItem().toString().substring(searchDropDown.getSelectedItem().toString().indexOf(" ")+1));
            
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
