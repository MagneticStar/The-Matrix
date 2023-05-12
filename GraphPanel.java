import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {

    public GraphPanel() {
        setBackground(Color.BLACK);
        addComponents();
    }

    private void addComponents(){
        
    }

    public void updateGraph(Graphics g) {
        ArrayList<Integer> points = Database.reproducedLastGeneration;
        for(int i=0; i<points.size()-1; i++){
            g.drawLine(i, points.get(i), i+1, points.get(i+1));
        }
        g.setColor(Color.WHITE);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        updateGraph(g);
    }

    

    
}
