/*
 * Laboratory Work #1: Arrays in Java
 * 7, C5=2, C7=0, C11=7
*/

import java.util.Random;

public class Main {
    
    public static void main(String[] args) {
        try {
            int studentId = 7;
            int c5 = studentId % 5;
            int c7 = studentId % 7;
            int c11 = studentId % 11;
            
            System.out.printf("Student ID: %d\nC5=%d C7=%d C11=%d\n\n", 
                            studentId, c5, c7, c11);
            
            Random rand = new Random();
            int size = rand.nextInt(6) + 2;
            
            double[][] matrixA = generateMatrix(size, rand);
            double[][] matrixB = generateMatrix(size, rand);
            
            System.out.println("Matrix A (" + size + "x" + size + "):");
            printMatrix(matrixA);
            
            System.out.println("\nMatrix B (" + size + "x" + size + "):");
            printMatrix(matrixB);
            
            double[][] matrixC = addMatrices(matrixA, matrixB);
            
            System.out.println("\nMatrix C = A + B:");
            printMatrix(matrixC);
            
            double finalResult = calculateSumOddEvenColumns(matrixC);
            
            System.out.printf("\nFinal result: %.2f\n", finalResult);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Null pointer error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
    
    static double[][] generateMatrix(int size, Random rand) {
        if (size < 2 || size > 7) {
            throw new IllegalArgumentException("Matrix size must be between 2 and 7");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Random generator cannot be null");
        }
        
        double[][] matrix = new double[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rand.nextDouble() * 100;
            }
        }
        
        return matrix;
    }
    
    static double[][] addMatrices(double[][] matrixA, double[][] matrixB) {
        if (matrixA == null || matrixB == null) {
            throw new NullPointerException("Matrices cannot be null");
        }
        if (matrixA.length == 0 || matrixB.length == 0) {
            throw new IllegalArgumentException("Matrices cannot be empty");
        }
        if (matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) {
            throw new IllegalArgumentException("Matrix dimensions must match");
        }
        
        int rows = matrixA.length;
        int cols = matrixA[0].length;
        double[][] resultMatrix = new double[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            if (matrixA[i] == null || matrixB[i] == null) {
                throw new NullPointerException("Matrix row cannot be null");
            }
            for (int j = 0; j < cols; j++) {
                resultMatrix[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        
        return resultMatrix;
    }
    
    static double calculateSumOddEvenColumns(double[][] matrix) {
        if (matrix == null) {
            throw new NullPointerException("Matrix cannot be null");
        }
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        double sum = 0;
        
        System.out.println("\nCalculation:");
        
        for (int j = 0; j < cols; j++) {
            if (matrix[0] == null) {
                throw new NullPointerException("Matrix row cannot be null");
            }
            
            if ((j + 1) % 2 == 1) {
                double max = matrix[0][j];
                
                for (int i = 1; i < rows; i++) {
                    if (matrix[i] == null) {
                        throw new NullPointerException("Matrix row cannot be null");
                    }
                    if (matrix[i][j] > max) {
                        max = matrix[i][j];
                    }
                }
                System.out.printf("Column %d (odd) max: %.2f\n", j + 1, max);
                sum += max;
            } else {
                double min = matrix[0][j];
                
                for (int i = 1; i < rows; i++) {
                    if (matrix[i] == null) {
                        throw new NullPointerException("Matrix row cannot be null");
                    }
                    if (matrix[i][j] < min) {
                        min = matrix[i][j];
                    }
                }
                System.out.printf("Column %d (even) min: %.2f\n", j + 1, min);
                sum += min;
            }
        }
        
        return sum;
    }
    
    static void printMatrix(double[][] matrix) {
        if (matrix == null) {
            throw new NullPointerException("Matrix cannot be null");
        }
        
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null) {
                throw new NullPointerException("Matrix row cannot be null");
            }
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%8.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }
}