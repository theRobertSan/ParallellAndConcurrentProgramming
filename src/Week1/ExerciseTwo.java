package Week1;

import java.util.function.Function;

public class ExerciseTwo {

    public static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    public static double estimateIntegralSequential(double lower, double upper, double res, Function<Double, Double> f) {
        int n = (int) ((upper - lower) / res);

        double result = f.apply(lower) + f.apply(upper);

        for (int i = 1; i < n; i++) {
            result += 2 * f.apply(lower + i * res);
        }
        return result * (res / 2);
    }

    public static double estimateIntegralParallel(double a, double b, double res, Function<Double, Double> f) {

        Thread[] threads = new Thread[NUMBER_OF_THREADS];

        double[] partialCalculations = new double[NUMBER_OF_THREADS];

        int n = (int) ((b - a) / res);
        double result = f.apply(a) + f.apply(b);

        for (int ti = 0; ti < NUMBER_OF_THREADS; ti++) {
            final int tid = ti;

            threads[tid] = new Thread(() -> {
                int start_i = tid == 0 ? 1 : tid * n / NUMBER_OF_THREADS;
                int end_i = tid == NUMBER_OF_THREADS - 1 ? n : (tid + 1) * n / NUMBER_OF_THREADS;

                double acc = 0;
                for (int i = start_i; i < end_i; i++) {
                    acc += 2 * f.apply(a + i * res);
                }
                partialCalculations[tid] = acc;
            });
            threads[tid].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Thread " + t + " was interrupted!");
            }
        }

        // Sum everything up
        for (double value : partialCalculations) {
            result += value;
        }

        return result * (res / 2);
    }

}