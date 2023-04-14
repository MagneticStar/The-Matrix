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
    public Subject(Genome genome){
        super();
        this.genome = genome;
    }

    // setters
    public void setGenome(Genome genome) {
        this.genome = genome;
        this.genome.subject = this;
    }

    // getters
    public Genome getGenome() {
        return genome;
    }
    
}