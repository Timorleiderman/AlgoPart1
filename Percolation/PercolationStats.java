import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int numOfExp;
    private final double[] fract;
    private final double confidence96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        confidence96 = 1.96;

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        numOfExp = trials;
        fract = new double[trials];
        int row;
        int col;
        int expOpenGrids;
        for (int t = 0; t < numOfExp; t++) {
            expOpenGrids = 0;
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    expOpenGrids++;
                }

            }
            fract[t] = (double) expOpenGrids / (n * n);
        }


    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fract);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fract);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (confidence96 * stddev() / Math.sqrt(numOfExp));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (confidence96 * stddev() / Math.sqrt(numOfExp));
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length <= 1) {
            throw new IllegalArgumentException();
        }
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);

        PercolationStats perStats = new PercolationStats(n, trails);
        System.out.println("mean                     = " + perStats.mean());
        System.out.println("stddev                   = " + perStats.stddev());
        System.out.println(
                "95% confidence interval  = [" + perStats.confidenceLo() + ", " + perStats
                        .confidenceHi()
                        + "]");


    }

}
