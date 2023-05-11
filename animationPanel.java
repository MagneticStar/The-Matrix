import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class animationPanel extends JPanel {

    private int periods = 1;
    private int currentTime = 0;
    private int TimerLength = 1000;
    private JLabel animationLabel;

    public animationPanel() {
        setBackground(Database.simulationScreenColor);

        animationLabel = new JLabel("Calculating.");
        animationLabel.setForeground(Color.WHITE);
        animationLabel.setSize(40, 80);
        this.add(animationLabel);
        this.setBorder(BorderFactory.createLineBorder(getBackground(), 200, false));
        this.revalidate();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        updateLabel();
    }
    
    private void updateLabel(){
        currentTime = (currentTime+1)%TimerLength;
        if(currentTime == TimerLength-1){
            periods = (periods%3)+1;
        }
        animationLabel.setText("Calculating"+".".repeat(periods));
        repaint();
    }

}
