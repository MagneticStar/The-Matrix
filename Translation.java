// A class that allows the creation of functions in the model of F(input) = translationMatrix * input
// Used for scaling world-space to screen-space
public class Translation {
    private double[] translationMatrix = new double[2];
    private double worldSizeX;
    private double worldSizeY;

    // default
    public Translation(double worldSizeX, double worldSizeY) {
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
    }
    
    // setters
    public void setMatrix(double xScaler, double yScaler) {
        translationMatrix[0] = xScaler;
        translationMatrix[1] = yScaler;
    }
    public void setWorld(double xScaler, double yScaler) {
        translationMatrix[0] = xScaler/worldSizeX;
        translationMatrix[1] = yScaler/worldSizeY;
    }
    // getters
    public double[] getMatrix() {
        return translationMatrix;
    }

    // multiplies input by translationMatrix
    public int[] translate(int[] input) {

        int[] result = new int[2];

        for (int i = 0; i < result.length; i++) {
                result[i] = (int) (input[i] * translationMatrix[i]);
        }
        return result;
    }
}