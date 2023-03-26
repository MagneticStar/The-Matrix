import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Image;
public class Frame extends JFrame{
    
    private int height = 500;
    private int width = 1000;

    public Frame() {
        this.setSize(width, height);
        this.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
    }
}
