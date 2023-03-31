public class Sensor extends Neuron{

    private Subject subject;
    private double output;

    public Sensor(Subject subject) {
        this.subject = subject;
    }

    public double getOutput() {
        return output;
    }

    
    public void find() {

        Coor tempPos = new Coor();

        for (int i = subject.getPos().x() - 1; i <= subject.getPos().x() + 1; i++) {
            for (int j = subject.getPos().y() - 1; j <= subject.getPos().y() + 1; j++) {
                tempPos.setX(i);
                tempPos.setY(j);
                detectFood(tempPos);
            }
        }
    }

    // int[] center = subject.getPos().matrix();
    // private static int[] centerCoord = {12,3}; // Format (x,y)
    // private static int i;
    // private static int j;
    // public void search() {
    //     // Search logic
    // for(i=1;i<10;i++){
    //     System.out.println("\nChecks this pass:");
        
    //     printCoord(centerCoord[0],centerCoord[1]+i); // Above
    //     printCoord(centerCoord[0],centerCoord[1]-i); // Below
    //     printCoord(centerCoord[0]+i,centerCoord[1]); // Right
    //     printCoord(centerCoord[0]-i,centerCoord[1]); // Left
        
    //     j = 1;
    //     while(abNext() || rlNext()){
    //       j++;
    //     }
    //   }
    // }
    // public static boolean rlNext(){
    //     if(j < i){
    //       printCoord(centerCoord[0]+i,centerCoord[1]+j); // Right Above
    //       printCoord(centerCoord[0]+i,centerCoord[1]-j); // Right Below
    //       printCoord(centerCoord[0]-i,centerCoord[1]+j); // Left Above
    //       printCoord(centerCoord[0]-i,centerCoord[1]-j); // Left Below
    //       return true;
    //     }
    //     return false;
    // }
    
    // public static boolean abNext(){
    //     if(j < i+1){
    //       printCoord(centerCoord[0]+j,centerCoord[1]+i); // Above right
    //       printCoord(centerCoord[0]-j,centerCoord[1]+i); // Above Left
    //       printCoord(centerCoord[0]+j,centerCoord[1]-i); // Below Right
    //       printCoord(centerCoord[0]-j,centerCoord[1]-i); // Below Left
    //       return true;
    //     }
    //     return false;
    // }
    
    // public static void printCoord(int x, int y){
    //     System.out.print("("+x+","+y+") ");
    // }
    
    public void detectFood(Coor tempPos) {
        for (Food f: Main.foods) {
            if (f.getPos().equals(tempPos)) {
                System.out.println(distance(f));
            }
        }
    }
    public double distance(Obj obj) {
        // using Pyth theorem
        try {
            return Math.sqrt(Math.pow(obj.getPos().x() - subject.getPos().x(), 2) + Math.pow(obj.getPos().y() - subject.getPos().y(), 2));
        } catch (NullPointerException e) {
            return -1.0;
        }    
    }
    
}