/* *****************************************************************************
 *  Name:   Stas Batura
 *  NetID:   since85
 *  Precept: P00
 *
 *  Description:  Percolation study case
 *
 **************************************************************************** */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PercolationStats {

    private List<Double> percolationLimit;

    private List<Integer> colomns;

    private List<Integer> rows;


    private int N;

    private int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        N = n;
        T = trials;

        percolationLimit = new ArrayList<>();

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            startPercolateTest(percolation);
        }
        double per = mean();
        System.out.println("T");
    }

    private void startPercolateTest( Percolation percolation ) {
        colomns = new ArrayList<>();
        rows = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                colomns.add(j);
                rows.add(j);
            }
        }

        Random random = new Random();
        for (int i = 0; i < N*N-1; i++) {
            int numC = random.nextInt(colomns.size()-1);
            int numR = random.nextInt(rows.size()-1);
            int row = rows.get(numR);
            int col = colomns.get(numC);

            percolation.open(row,col);

            colomns.remove(numC);
            rows.remove(numR);
            if (percolation.percolates()) {
                break;
            }
        }
        double perl = 1.d *percolation.numberOfOpenSites();
        percolationLimit.add( perl / (N*N) );
    }


    // sample mean of percolation threshold
    public double mean() {
        double perlocation = percolationLimit.stream().mapToDouble(Double::doubleValue).sum();
        return perlocation/T;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0.d;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0.d;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0.d;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 200;
        int trials = 50;

        PercolationStats stats = new PercolationStats(n,trials);
    }
}
