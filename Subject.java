import java.awt.*;

public class Subject extends screenObject{
    private Genome genome;
    
    public Subject() {
        super();
        this.genome = new Genome(this);
    }
    
    public Subject(Color color,Coor position){
        super(color, position);
        this.genome = new Genome(this);
    }

    // setters
    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    // getters
    public Genome getGenome() {
        return genome;
    }
    
}