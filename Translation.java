public class Translation {
    private double[][] transMat = new double[2][2];
    
    // constructors
    public Translation() {

    }
    public Translation(double[][] m) {
        transMat = m;
    }
    
    // setters
    public void setMat(double xScaler, double yScaler) {
        transMat[0][0] = xScaler;
        transMat[1][1] = yScaler;
    }
    
    // getters
    public double[][] getMat() {
        return transMat;
    }
  
    public int[] translate(double[] input) {

        int[] result = new int[2];

        for (int i = 0; i < result.length; i++) {
                result[i] = multiplycell(input, i);
        }
        return result;
    }
    public int multiplycell(double[] input, int col) {
        double cell = 0;
        for (int i = 0; i < transMat.length; i++) {
            cell += input[i] * transMat[i][col];
        }
        // this typecasting could cause some issues.
        // The cell should already be a whole number but...
        return (int)cell;
    }
}
