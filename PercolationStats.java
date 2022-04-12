import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double mean;
    private double stddev;
    private double lo;
    private double hi;
    private double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }
        // Array for storing results of each trial
        results = new double[trials];

        // Loop for number of trials
        for (int i = 0; i < trials; i++) {
            Percolation trial = new Percolation(n);
            // Open a random site until grid percolates
            while (!trial.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                // n + 1 because StdRandom.uniform  2nd parameter is exclusive
                trial.open(row, col);
                // System.out.println(row + " " + col + " opened");
            }
            /* System.out.println("Opened sites = " + trial.numberOfOpenSites()
                                       + " / " + (n * n)); */
            results[i] = (double) trial.numberOfOpenSites() / (n * n);
            // System.out.println("num open sites " + trial.numberOfOpenSites());
        }

        // calculate stat variables
        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);
        double s = 1.96 * stddev / Math.sqrt(trials);
        this.lo = mean - s;
        this.hi = mean + s;
    }


    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.hi;
    }

    // test client (see below)
    public static void main(String[] args) {


        PercolationStats ps = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1])
        );
        System.out.println("Mean                    = " + ps.mean());
        System.out.println("StdDev                  = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");


    }


}
