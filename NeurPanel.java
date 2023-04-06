import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

public class NeurPanel extends JPanel implements ActionListener{
    
    
    private static TextField searchBar;
    private static Button searchButton;
    private static Label searchReply;

    public static void main(String[] args){
        searchButton = new Button("Find");
        searchReply = new Label("");
        
        NeurPanel nP = new NeurPanel();
        // Adds an action listener to the button
        searchButton.addActionListener(nP);
        // creates a TextField object with 16 columns
        searchBar = new TextField(16);
        // creates a font object and sets the TextField font to the newly defined font object
        Font searchBarFont = new Font("Serif",Font.BOLD,20);
        searchBar.setFont(searchBarFont);

        NeurPanel neuronPanel = Frame.neuronPanel;

        neuronPanel.add(searchBar);
        neuronPanel.add(searchButton);
        neuronPanel.add(searchReply);

    }

    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        if (s.equals("Find")){
            searchReply.setText(searchBar.getText());

            searchBar.setText("");
        }
    }

    public NeurPanel() {
        setBackground(Color.BLACK);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawNeuron(g);
    }
    public void drawNeuron(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(50, Frame.neuronMap.HEIGHT, 40, 40);
    }
}
