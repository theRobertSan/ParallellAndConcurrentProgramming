package Exercise2;

import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

public class ForkJoinTrapezoidRule extends RecursiveTask<Double> {

    private double lower;
    private double upper;
    private double res;
    private Function<Double, Double> f;

    public ForkJoinTrapezoidRule(double lower, double upper, double res, Function<Double, Double> f) {
        this.lower = lower;
        this.upper = upper;
        this.res = res;
        this.f = f;
    }

    @Override
    protected Double compute() {
        double area = (f.apply(lower) + f.apply(upper))/2 * (upper - lower);

        double halfSplit = lower + (upper-lower)/2;
        double half1Area = (f.apply(lower) + f.apply(halfSplit))/2 * (halfSplit - lower);
        double half2Area = (f.apply(halfSplit) + f.apply(upper))/2 * (upper - halfSplit);

        double difference = Math.abs(area - (half2Area + half1Area));
        if (difference < res) {
            return half1Area + half2Area;
        }

        ForkJoinTrapezoidRule half1 = new ForkJoinTrapezoidRule(lower, halfSplit, res, f);

        ForkJoinTrapezoidRule half2 = new ForkJoinTrapezoidRule(halfSplit, upper, res, f);
        half2.fork();

        return half1.compute() + half2.join();
    }
}
