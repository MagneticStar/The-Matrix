import java.awt.Color;

public class Water extends ScreenObject{
    public Water() {
        super(Color.BLUE, new Coor());
    }
    public Water(){
        super(Color.blue);
    }
    public Coor getPrintPos() {
        Screens.SimulationWorldToScreen.setWorld(Screens.simulationPanel.getWidth(), Screens.simulationPanel.getHeight());
        int[] ans = Screens.SimulationWorldToScreen.translate(this.getPos().matrix());
        return new Coor(ans[0], ans[1]);
    }
}