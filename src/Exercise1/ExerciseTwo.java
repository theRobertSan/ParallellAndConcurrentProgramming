package Exercise1;

import java.util.function.Function;

public class ExerciseTwo {

    public static final int NUMBER_OF_THREADS = 3;
    public static double estimateIntegralSequential(double lower, double upper, double h, Function<Double, Double> f) {
        double n = (upper - lower) / h;

        double result = f.apply(lower) + f.apply(upper);

        for (int i = 1; i < n; i++) {
            result += 2 * f.apply(lower + i * h);
        }
        return Math.abs(result * (h / 2));
    }

    public static float estimateIntegralParallel(float a, float b, int n, Function<Float, Float> f) {

        Thread[] threads = new Thread[NUMBER_OF_THREADS];

        float h = (b - a) / n;

        float result = f.apply(a) + f.apply(b);

        float[] calculations = new float[n];

        for (int ti = 0; ti < NUMBER_OF_THREADS; ti++) {
            final int tid = ti;

            // 0 -> 10
            // n = 5
            // threads = 3
            // it  0 1 2 3 4 5
            // val 0 2 4 6 8 10
            // thread 1 -> 0 to 1 * n / 3 = 2
            // thread 2 -> 2 to 2 * n / 3 = 3
            // thread 3 -> 3 to last 5
            threads[tid] = new Thread( () -> {
                int start_i = tid * n / NUMBER_OF_THREADS;
                int end_i = (tid + 1) * n / NUMBER_OF_THREADS;

                if (tid == NUMBER_OF_THREADS - 1) {
                    end_i = n;
                }

                System.out.println("Thread looping from " + start_i + " to " + end_i);

                calculations[tid] = 0;
                for (int i = start_i; i < end_i; i++) {
                    calculations[tid] += 2 * f.apply((float) a + i * h);
                }
            });
            threads[tid].start();
        }

        // Wait until all threads have finished
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Thread " + t + " was interrupted!");
            }
        }

        // Sum everything up
        for (float value: calculations) {
            result += value;
        }

        return result * (h / 2);
    }

}