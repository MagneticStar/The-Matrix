import java.awt.*;
import javax.swing.JPanel;  

public class Panel extends JPanel{

    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(50, 50, 100, 100);
    }
}
