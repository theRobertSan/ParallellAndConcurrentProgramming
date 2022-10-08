package Exercise1;

import java.math.BigInteger;
import java.util.Random;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        // Exercise 1
//
//        int bound = 10;
//        int M = 1500;
//        int N = 1500;
//        int O = 1500;
//
//        int[][] m1 = ExerciseOne.generateMatrix(M, N, bound);
//        int[][] m2 = ExerciseOne.generateMatrix(N, O, bound);
//
//        int[][] result = ExerciseOne.multiplyParallel(m1, m2);
//        ExerciseOne.print(result);
//

        // Exercise 2
        double test1 = 2.1;

        Function<Double, Double> f = x -> x * (x - 1);
        System.out.println(ExerciseTwo.estimateIntegralSequential(5, 10, 0.5, f));

        // Exercise 3
//        float pi = ExerciseThree.findPiParallel(1000000000);
//        float pi = ExerciseThree.findPiSequential(1000000000);
//        System.out.println("PI = " + pi);

        long finish = System.currentTimeMillis();
        System.out.println("Finished in " + (finish - start) + "ms.");
    }

}