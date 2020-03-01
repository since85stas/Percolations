/* *****************************************************************************
 *  Name:   Stas Batura
 *  NetID:   since85
 *  Precept: P00
 *
 *  Description:  Percolation study case
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // limit
    private double[] percolationLimit;

    // n
    private int nGr;

    // timings
    private int tim;

    // mean
    private double mean;

    // stdv
    private double stdv;

    // low
    private double low;

    // high
    private double high;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n > 0 && trials > 0) {
            nGr = n;
            tim = trials;

            percolationLimit = new double[tim];

            for (int i = 0; i < tim; i++) {
                Percolation percolation = new Percolation(n);
                startPercolateTest(percolation, i);
            }
            mean = mean();
            stdv = stddev();
            low = confidenceLo();
            high = confidenceHi();
        } else {
            IllegalArgumentException exception = new IllegalArgumentException();
            throw exception;
        }
    }

    // starting test
    private void startPercolateTest(Percolation percolation, int testN) {
        int [] colomns = new int[nGr*nGr];
        int [] rows = new int[nGr*nGr];

        for (int i = 0; i < nGr; i++) {
            for (int j = 0; j < nGr; j++) {
                colomns[j + i*nGr]  = j+1;
                rows[j + i*nGr]  = j+1;
            }
        }

         // StdRandom random = new StdRandom();
        for (int i = 0; i < nGr*nGr-1; i++) {
            // int numC = random.nextInt(colomns.size()-1);
            // int numR = random.nextInt(rows.size()-1);
            int numC = StdRandom.uniform(colomns.length-1);
            int numR = StdRandom.uniform(rows.length-1);
            int row = rows[numR];
            int col = colomns[numC];

            percolation.open(row, col);

            colomns = removeElement(colomns, numC);
            rows    = removeElement(rows, numR);

            if (percolation.percolates()) {
                break;
            }
        }
        double perl = 1.d *percolation.numberOfOpenSites();
        percolationLimit[testN] =  perl / (nGr*nGr) ;
    }

    // removinf elment from array
    private int[] removeElement(int[] source, int index) {
        int[] result = new int[source.length - 1];
        System.arraycopy(source, 0, result, 0, index);
        if (source.length != index) {
            System.arraycopy(source, index + 1, result, index, source.length - index - 1);
        }
        return result;
    }


    // sample mean of percolation threshold
    public double mean() {
        double perlocation = StdStats.mean(percolationLimit);
        return perlocation;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationLimit);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double div = mean - 1.96*stdv/Math.sqrt(tim);
        return div;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double div = mean + 1.96*stdv/Math.sqrt(tim);
        return div;
    }

    // test client (see below)
    public static void main(String[] args) {

        PercolationStats stats;

        if (args.length > 0) {
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            stats = new PercolationStats(n, trials);
        } else {
            int n = 5;
            int trials = 1000;
             stats = new PercolationStats(n, trials);
        }
        System.out.println("mean                    = " + stats.mean);
        System.out.println("stddev                  = " + stats.stdv);
        System.out.println("95% confidence interval = [" + stats.low + ", " + stats.high + ",");

    }
}