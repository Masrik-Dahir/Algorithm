package print;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Print {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    // Print double Arraylist
    public static void printArrayList(ArrayList<double[]> matrix){
        for (double[] i: matrix){
            System.out.println(Arrays.toString(i));
        }
    }

    // Print double[][]
    public static void printMatrix(double[][] matrix) {
        int matrixLength =  matrix.length;
        for(int i = 0; i < matrixLength; i++) {
            for(int j = 0; j < matrixLength; j++) {
                System.out.printf(df.format(matrix[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    // Print ArrayList of integer array
    public static void printArraylistIntegerArray(ArrayList<Integer[]> old, int last){
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < old.size(); i++){
            Integer[] array = old.get(i);
            for (int j = 0; j <= array.length-last; j++){
                if (j == 0){
                    newStr.append("[");
                }
                newStr.append(array[j]).append(", ");
                if (j == array.length-last){
                    newStr = new StringBuilder(newStr.substring(0, newStr.length() - 2));
                    newStr.append("], ");
                }
            }
            if (i == old.size()-1){
                newStr = new StringBuilder(newStr.substring(0, newStr.length() - 2));
            }
        }
        System.out.println(newStr);
    }

}
