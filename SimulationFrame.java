import javax.swing.JFrame;

public class SimulationFrame extends JFrame{

    private int height = 1000;
    private int width = 1500;

    public SimulationFrame(){
        this.setSize(width, height);
        this.setDefaultCloseOperation(SimulationFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
    }
}