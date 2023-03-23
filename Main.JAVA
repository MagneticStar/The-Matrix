import java.awt.*;
import javax.swing.*;

public class Main{
    public static void main(String[] args) {
        // Create a Dimension Object to find the Screen Dimensions
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Create a gui for our program 
        JFrame f = new JFrame();  

        // Create a panal 
        JPanel p = new JPanel();
        p.setBackground(Color.GREEN);

        // Add Canvas to gui
        f.add(p);  

        // Set size of gui
        f.setSize((int)screenDimension.getWidth(),(int)screenDimension.getHeight());

        //f.setLayout(null);  
        f.setVisible(true);
    }
}