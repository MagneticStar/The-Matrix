import javax.swing.JFrame;

public class DataFrame extends JFrame{
    private int height = 500;
    private int width = 1000;

    public DataFrame() {
        this.setSize(width, height);
        this.setDefaultCloseOperation(DataFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
    }
}