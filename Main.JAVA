import java.awt.*;
import javax.swing.*;

public class Main{
    public static void main(String[] args) {
        // Create a Dimension Object to find the Screen Dimensions
        Dimension screenDimension =  Toolkit.getDefaultToolkit().getScreenSize();
        // Create Canvas
        canvas m=new canvas(); 
        // Create a gui for our program 
        JFrame f=new JFrame();  
        // Add Canvas to gui
        f.add(m);  
        // Set size of gui
        f.setSize((int)screenDimension.getWidth(),(int)screenDimension.getHeight());  
        //f.setLayout(null);  
        f.setVisible(true);
    }
}