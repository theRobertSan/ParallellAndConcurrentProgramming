package Week2;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        double upper = 5;
        double lower = 10;
        double res = 0.5;
        Function<Double, Double> f = x -> x * (x - 1);
        ForkJoinTrapezoidRule thing = new ForkJoinTrapezoidRule(lower, upper, res, f);
        thing.fork();

        System.out.println(thing.join());
    }
}