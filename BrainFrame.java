import javax.swing.JFrame;

public class BrainFrame extends JFrame{
    private int height = 500;
    private int width = 1000;

    public BrainFrame() {
        this.setSize(width, height);
        this.setDefaultCloseOperation(BrainFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
    }
}
