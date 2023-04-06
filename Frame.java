import javax.swing.JFrame;
public class Frame extends JFrame{

    public static Frame simFrame = new Frame("simulation");
    public static Panel simPanel = new Panel();

    public static Frame neuronMap = new Frame("neuronMap");
    public static NeurPanel neuronMapPanel = new NeurPanel();

    public static void main(String[] args){
        simFrame.add(simPanel);
        simFrame.setVisible(true);

        neuronMap.add(neuronMapPanel);
        neuronMap.setVisible(true);
    }
    
    private int height = 500;
    private int width = 1000;

    public Frame(String frameID){
        switch(frameID){
            case "simulation": this.setSize(width, height);
                               this.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
                               this.setResizable(true);
                               this.setLocationRelativeTo(null);
                               this.setUndecorated(false);
            break;

            case "neuronMap": this.setSize(width, height);
                              this.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
                              this.setResizable(true);
                              this.setLocationRelativeTo(null);
                              this.setUndecorated(false);
            break;
            }
        
    }
}