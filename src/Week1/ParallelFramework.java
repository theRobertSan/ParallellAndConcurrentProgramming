package Week1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;

public class ParallelFramework {

    public static <T> ArrayList<T> parallelize(BiFunction<Integer, Integer, T> iterate, int numIterations) {
        int defaultNThreads = Runtime.getRuntime().availableProcessors();
        return parallelize(iterate, numIterations, defaultNThreads);
    }

    public static <T> ArrayList<T> parallelize(BiFunction<Integer, Integer, T> iterate, int numIterations, int numThreads) {

        Thread[] threads = new Thread[numThreads];

        ArrayList<T> partialResults = new ArrayList<>(Collections.nCopies(numThreads, null));

        for (int ti = 0; ti < numThreads; ti++) {

            final int tid = ti;

            threads[ti] = new Thread(() -> {

                int start_i = tid * (numIterations / numThreads);
                int end_i = tid == (numThreads - 1) ? numIterations : (tid + 1) * (numIterations / numThreads);

                partialResults.set(tid, iterate.apply(start_i, end_i));

            });
            threads[ti].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.print("Thread " + t + " was interrupted");
            }
        }

        return partialResults;

    }

}
