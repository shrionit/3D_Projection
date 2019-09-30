package pkg3dprojection;

import javafx.geometry.Point3D;

public class Matrix {
    private static double[][] vecToMatrix(Point3D v) {
        double[][] m = new double[3][1];
        m[0][0] = v.getX();
        m[1][0] = v.getY();
        m[2][0] = v.getZ();
        return m;
    }

    private static Point3D matrixToVec(double[][] m) {
        Point3D v = Point3D.ZERO;
        v = new Point3D(m[0][0], m[1][0], 0);
        if (m.length > 2) {
            v = new Point3D(m[0][0], m[1][0], m[2][0]);
        }
        return v;
    }

    public static void logMatrix(double[][] m) {
        int cols = m[0].length;
        int rows = m.length;
        System.out.println(rows + "x" + cols);
        System.out.println("----------------");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static Point3D matmul(double[][] a, Point3D b) {
        double[][] m = vecToMatrix(b);
        return matrixToVec(matmul(a, m));
    }

    public static double[][] matmul(double[][] a, double[][] b) {
        int colsA = a[0].length;
        int rowsA = a.length;
        int colsB = b[0].length;
        int rowsB = b.length;

        if (colsA != rowsB) {
            System.out.println("Columns of A must match rows of B");
            return null;
        }

        double result[][] = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                double sum = 0;
                for (int k = 0; k < colsA; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }
    
    public static double[][] matmul(double[][] a, Vec4 b) {
        int colsA = a[0].length;
        int rowsA = a.length;
        int colsB = 1;
        int rowsB = 4;
        double[][] mat = b.toMat4();

        if (colsA != rowsB) {
            System.out.println("Columns of A must match rows of B");
            return null;
        }

        double result[][] = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                double sum = 0;
                for (int k = 0; k < colsA; k++) {
                    sum += a[i][k] * mat[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }
    
}
