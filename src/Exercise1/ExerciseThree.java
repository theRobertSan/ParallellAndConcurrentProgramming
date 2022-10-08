package Exercise1;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ExerciseThree {

    public static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    public static float findPiSequential(int darts) {

        Random r = new Random();
        int insideCircle = 0;
        int radius = 1;
        float centerX = 0;
        float centerY = 0;

        for (int i = 0; i < darts; i++) {
            float x = (r.nextFloat() * 2) - 1;
            float y = (r.nextFloat() * 2) - 1;

            if (Math.sqrt(Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2)) <= radius) {
                insideCircle++;
            }
        }
        return 4 * (insideCircle / (float) darts);
    }

    public static float findPiParallel(int darts) {

        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        int[] partialResults = new int[NUMBER_OF_THREADS];

        int insideCircle = 0;
        int radius = 1;
        float centerX = 0;
        float centerY = 0;

        for (int ti = 0; ti < threads.length; ti++) {

            final int tid = ti;

            threads[ti] = new Thread(() -> {
                int start_i = tid * (darts / NUMBER_OF_THREADS);
                int end_i = (tid+1) * (darts / NUMBER_OF_THREADS);
                // System.out.println(start_i + " to " + end_i);
                if (tid == NUMBER_OF_THREADS - 1) {
                    end_i = darts;
                }

                for (int i = start_i; i < end_i; i++) {
                    float x = (ThreadLocalRandom.current().nextFloat() * 2) - 1;
                    float y = (ThreadLocalRandom.current().nextFloat() * 2) - 1;
                    if (Math.sqrt(Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2)) <= radius) {
                        partialResults[tid]++;
                    }
                }
            });
            threads[ti].start();

        }

        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (int partialResult: partialResults) {
            insideCircle += partialResult;
        }

        return 4 * (insideCircle / (float) darts);
    }

}
