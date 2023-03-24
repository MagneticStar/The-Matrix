import java.awt.*;
import javax.swing.JPanel;  
import java.util.ArrayList;

public class Panel extends JPanel{
    // Array of Subjects to draw
    private ArrayList<Subject> subjects;

    public Panel(ArrayList<Subject> subjects) {
        setBackground(Color.BLACK);
        this.subjects = subjects;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Where all graphics are rendered
        drawSub(g, subjects);
    }

    public void drawSub(Graphics g, ArrayList<Subject> sub) {
        // prints all Subjects in subject array
        for (int i = 0; i < sub.size(); i++) {
            g.setColor(sub.get(i).getColor());
            // cannot use Coor for drawing!! Coor are doubles and define worldspace, not screenspace
            // currently I am typecasting becasue the matrix math for translating between the two is not done and I need to debug 
            g.fillRect((int)sub.get(i).getPosX(), (int)sub.get(i).getPosY(), 30, 30);
        }
    }
}
