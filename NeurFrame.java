import javax.swing.JFrame;

public class NeurFrame extends JFrame{
    private int height = 500;
    private int width = 1000;

    public NeurFrame() {
        this.setSize(width, height);
        this.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
    }
}
