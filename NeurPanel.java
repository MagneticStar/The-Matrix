import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;

public class NeurPanel extends JPanel implements ActionListener{
    
    
    private static TextField searchBar;
    private static Button searchButton;
    private static Label searchReply;

    public static void main(String[] args){
        searchButton = new Button("Find");
        searchReply = new Label("");
        
        // Adds an action listener to the button
        searchButton.addActionListener(Frame.neuronPanel);
        searchBar = new TextField(16);
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
        g.drawOval(50, NeurFrame.HEIGHT, 40, 40);
    }
}
