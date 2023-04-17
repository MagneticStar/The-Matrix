public class Translation {
    private double[][] transMat = new double[2][2];
    private double worldSize;
    // constructors
    public Translation(double worldSize) {
        this.worldSize = worldSize;
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
        transMat[0][0] = xScaler/worldSize;
        transMat[1][1] = yScaler/worldSize;
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
