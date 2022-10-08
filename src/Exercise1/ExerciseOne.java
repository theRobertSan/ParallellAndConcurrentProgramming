package Exercise1;

import java.util.Random;

public class ExerciseOne {
    public static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    public static int[][] multiplySequential(int[][] m1, int[][] m2) {

        int[][] result = new int[m1.length][m2[0].length];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                int acc = 0;
                for (int k = 0; k < m1[0].length; k++) {
                    acc += m1[i][k] * m2[k][j];
                }
                result[i][j] = acc;
            }
        }

        return result;
    }

    public static int[][] multiplyParallel(int[][] m1, int[][] m2) {

        Thread[] threads = new Thread[NUMBER_OF_THREADS];

        int rows = m1.length;

        int[][] result = new int[m1.length][m2[0].length];

        for (int ti = 0; ti < NUMBER_OF_THREADS; ti++) {

            final int tid = ti;

            threads[tid] = new Thread(() -> {
                int start_i = tid * rows / NUMBER_OF_THREADS;
                int end_i = (tid + 1) * rows / NUMBER_OF_THREADS;

                if (tid == NUMBER_OF_THREADS - 1) {
                    end_i = rows;
                }

                for (int i = start_i; i < end_i; i++) {
                    for (int j = 0; j < result[0].length; j++) {
                        int acc = 0;
                        for (int k = 0; k < m1[0].length; k++) {
                            acc += m1[i][k] * m2[k][j];
                        }
                        result[i][j] = acc;
                    }
                }

            });
            threads[ti].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Thread " + t + " was interrupted!");
            }
        }

        return result;
    }

    public static void print(int[][] m) {
        for (int[] row : m) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }

    public static int[][] generateMatrix(int rows, int columns, int bound) {
        Random r = new Random();
        int[][] result = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = r.nextInt(bound);
            }
        }

        return result;
    }
}