package Week1;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
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

        double lower = 0;
        double upper = 30;
        double res = 1E-7;

        Function<Double, Double> f = x -> x * (x - 1);

        // Sequential
        // (1485ms - input 1, 30, 1E-7)
//        System.out.println(ExerciseTwo.estimateIntegralSequential(lower, upper, res, f));

        // Parallel
        // (311ms - input 1, 30, 1E-7)
//        System.out.println(ExerciseTwo.estimateIntegralParallel(lower, upper, res, f));

        // Parallel using library
        // (325ms - input 1, 30, 1E-7)
//        System.out.println(exerciseTwoUsingLibrary(lower, upper, res, f));

        // Exercise 3

        int darts = 2000000000;

        // Sequential
        // (43564ms - input 2000000000)
//        double pi = ExerciseThree.findPiSequential(darts);

        // Parallel
        // (5650ms - input 2000000000)
//        double pi = ExerciseThree.findPiParallel(darts);

        // Parallel using library
        // (5650ms - input 2000000000)
//        double pi = exerciseThreeUsingLibrary(darts);

        long finish = System.currentTimeMillis();
        System.out.println("Finished in " + (finish - start) + "ms.");
    }

    private static double exerciseTwoUsingLibrary(double lower, double upper, double res, Function<Double, Double> f) {
        int n = (int) ((upper - lower) / res);

        double result = f.apply(lower) + f.apply(upper);

        BiFunction<Integer, Integer, Double> estimatePartialIntegral = (start_i, end_i) -> {
            double acc = 0;

            for (int i = start_i; i < end_i; i++) {
                acc += 2 * f.apply(lower + i * res);
            }
            return acc;
        };

        result += ParallelFramework.parallelize(estimatePartialIntegral, n)
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return result * (res / 2);
    }

    private static double exerciseThreeUsingLibrary(int darts) {

        int radius = 1;
        double centerX = 0;
        double centerY = 0;

        BiFunction<Integer, Integer, Integer> countDartsInCircle = (start_i, end_i) -> {
            int counter = 0;
            for (int i = start_i; i < end_i; i++) {
                double x = (ThreadLocalRandom.current().nextFloat() * 2) - 1;
                double y = (ThreadLocalRandom.current().nextFloat() * 2) - 1;
                if (Math.sqrt(Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2)) <= radius) {
                    counter++;
                }
            }
            return counter;
        };

        int result = ParallelFramework.parallelize(countDartsInCircle, darts)
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        return 4 * (result / (double) darts);
    }

}