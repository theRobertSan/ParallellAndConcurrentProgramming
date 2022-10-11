package Week2;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        double upper = 0;
        double lower = 30;
        double res = 1E-7;

        Function<Double, Double> f = x -> x * (x - 1);

        ForkJoinTrapezoidRule fjSolver = new ForkJoinTrapezoidRule(lower, upper, res, f);
        fjSolver.fork();

        System.out.println(fjSolver.join());

        long finish = System.currentTimeMillis();
        System.out.println("Finished in " + (finish - start) + "ms.");
    }
}