public class Translation {
    private double[][] transMat;

    public Translation() {

    }

    public int[][] translate(double[][] input) {
        int[][] result = new int[input.length][transMat[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplycell(input, row, col);
            }
        }
        return result;
    }
    public int multiplycell(double[][] input, int row, int col) {
        double cell = 0;
        for (int i = 0; i < transMat.length; i++) {
            cell += input[row][i] * transMat[i][col];
        }
        // this typecasting could cause some issues.
        // The cell should already be a whole number but...
        return (int)cell;
    }
}
