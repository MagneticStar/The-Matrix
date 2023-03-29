import java.awt.*;

public class Subject extends Obj{
    private Genome genome;
    private Color color;

    public Subject() {
        super();
        this.genome = new Genome();
        this.color = genome.getColor();
        // this.position = position; Random position from list of available positions
    }
    
    public Subject(Color color,Coor position){
        super(position);
        this.genome = new Genome();
        this.color = color;
        
    }

    // setters
    public void setPosX(int x) {
        super.getPos().setX(x);
    }
    public void setPosY(int y) {
        super.getPos().setY(y);
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    // getters
    public double getPosX() {
        return super.getPos().x();
    }
    public double getPosY() {
        return super.getPos().y();
    }
    public Coor getPos() {
        return super.getPos();
    }

    public Coor getPrintPos() {
        
        Translation t = new Translation();
        t.setMat(Main.frame.getWidth()/1000.0, Main.frame.getHeight()/1000.0);
        double[] ans = t.translate(super.getPos().matrix());
        return new Coor(ans[0], ans[1]);
    }
    
    public Color getColor() {
        return color;
    }
    public Genome getGenome() {
        return genome;
    }
    
}