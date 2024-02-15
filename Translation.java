public class Translation {
    private double[][] transMat = new double[2][2];
    private double worldSizeX;
    private double worldSizeY;
    // constructors
    public Translation(double worldSizeX, double worldSizeY) {
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
    }
    public Translation(double[][] m) {
        transMat = m;
    }
    
    // setters
    public void setMat(double xScaler, double yScaler) {
        transMat[0][0] = xScaler;
        transMat[1][1] = yScaler;
    }
    public void setWorld(double xScaler, double yScaler) {
        transMat[0][0] = xScaler/worldSizeX;
        transMat[1][1] = yScaler/worldSizeY;
    }
    // getters
    public double[][] getMat() {
        return transMat;
    }

    // multiplies two matrices for translating
    public int[] translate(int[] input) {

        int[] result = new int[2];

        for (int i = 0; i < result.length; i++) {
                result[i] = multiplycell(input, i);
        }
        return result;
    }
    public int multiplycell(int[] input, int col) {
        double cell = 0.0;
        for (int i = 0; i < transMat.length; i++) {
            cell += input[i] * transMat[i][col];
        }
        return (int)cell;
    }
}