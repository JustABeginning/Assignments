import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStatsT {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] data;

    public PercolationStatsT(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        data = new double[trials];
        double nS = n * n;
        int row = 0, col = 0, nP = n + 1, nOS = 0;
        for (int i = 0; i < trials; i++) {
            System.out.println("\nTrial : " + (i + 1));
            PercolationT obj = new PercolationT(n);
            do {
                System.out.println("\ncount() = " + obj.getCount());
                row = StdRandom.uniformInt(1, nP);
                col = StdRandom.uniformInt(1, nP);
                System.out.println("(" + row + "," + col + ")");
                obj.open(row, col);
            } while (!obj.percolates());
            nOS = obj.numberOfOpenSites();
            System.out.println("\nOpen Sites = " + nOS + ", count() = " + obj.getCount());
            data[i] = nOS / nS;
        }
    }

    public double mean() {
        return StdStats.mean(data);
    }

    public double stddev() {
        return StdStats.stddev(data);
    }

    public double confidenceLo() {
        int t = data.length;
        double cl = mean() - PercolationStatsT.CONFIDENCE_95 * stddev() / Math.sqrt(t);
        return cl;
    }

    public double confidenceHi() {
        int t = data.length;
        double ch = mean() + PercolationStatsT.CONFIDENCE_95 * stddev() / Math.sqrt(t);
        return ch;
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            PercolationStatsT obst = new PercolationStatsT(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println("\nmean = " + obst.mean());
            System.out.println("stddev = " + obst.stddev());
            System.out.println("95% confidence interval = [" + obst.confidenceLo() + ", " + obst.confidenceHi() + "]");
            System.out.println();
        }
    }
}
