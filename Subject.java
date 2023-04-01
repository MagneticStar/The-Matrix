import java.awt.*;

public class Subject extends Obj{
    private Genome genome;
    
    public Subject() {
        super();
        this.genome = new Genome(this);
        // this.position = position; Random position from list of available positions
    }
    
    public Subject(Color color,Coor position){
        super(color, position);
        this.genome = new Genome();
    }

    // setters
    public void setGenome(Genome genome) {
        this.genome = genome;
    }

<<<<<<< Updated upstream
=======
    // getters
>>>>>>> Stashed changes
    public Genome getGenome() {
        return genome;
    }
    
}