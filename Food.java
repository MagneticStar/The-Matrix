public class Food extends ScreenObject{
    static public Coor getPrintPos(int x, int y) {
        Screens.SimulationWorldToScreen.setWorld(Screens.simulationPanel.getWidth(), Screens.simulationPanel.getHeight());
        int[] ans = Screens.SimulationWorldToScreen.translate(new Coor(x, y).matrix());
        return new Coor(ans[0], ans[1]);
    }
}