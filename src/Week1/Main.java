package Week1;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

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
//        double test1 = 2.1;
//
//        Function<Double, Double> f = x -> x * (x - 1);
//        System.out.println(ExerciseTwo.estimateIntegralSequential(5, 10, 0.5, f));

        // Exercise 3
        // (5650ms - input 2000000000)
//        double pi = ExerciseThree.findPiParallel(2000000000);

        // (43564ms - input 2000000000)
//        double pi = ExerciseThree.findPiSequential(2000000000);
//        System.out.println("PI = " + pi);

        // Exercise 3 using library
        // (5650ms - input 2000000000)
//        System.out.println("PI = " + exerciseThreeUsingLibrary());

        long finish = System.currentTimeMillis();
        System.out.println("Finished in " + (finish - start) + "ms.");
    }

    private static double exerciseThreeUsingLibrary() {

        int darts = 2000000000;
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