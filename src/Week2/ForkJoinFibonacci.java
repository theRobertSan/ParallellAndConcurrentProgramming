package Week2;

import java.util.concurrent.RecursiveTask;

public class ForkJoinFibonacci extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 1L;
    private int n;
    private int depth;

    public ForkJoinFibonacci(int n, int depth) {
        this.n = n;
        this.depth = depth;
    }

    @Override
    protected Integer compute() {
        if (n == 0 || n == 1) return 1;

        // Specific for fibonacci
        // if (n <= 16) return seqFib(n);

        // Max level:
        //if ( depth >= 20 ) return seqFib(n);

        // Max tasks: if the total number of tasks >= T * #cores.
        //if ( this.getQueuedTaskCount() > 4 * Runtime.getRuntime().availableProcessors() ) return seqFib(n);

        // Surplus: if the current queue has more than 2 tasks than the average
        if (RecursiveTask.getSurplusQueuedTaskCount() > 2) return seqFib(n);

        ForkJoinFibonacci f1 = new ForkJoinFibonacci(n - 1, depth + 1);
        f1.fork();

        ForkJoinFibonacci f2 = new ForkJoinFibonacci(n - 2, depth + 1);
        //f2.fork();

        int b = f2.compute();
        int a = f1.join();


        return a + b;
    }

    // ------------------------------------------


    public static int seqFib(int n) {
        if (n == 0 || n == 1) return 1;
        return seqFib(n - 1) + seqFib(n - 2);
    }


    public static int parFib(int n) {
        ForkJoinFibonacci f2 = new ForkJoinFibonacci(n, 0);
        return f2.compute();
    }

    public static void main(String[] args) {
        int sf = seqFib(47);
        System.out.println(sf);
        ForkJoinFibonacci pf = new ForkJoinFibonacci(47, 0);
		
		/*
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		pool.invoke(pf);
		*/
        pf.fork();

        System.out.println(pf.join());
    }


}