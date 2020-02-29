/* *****************************************************************************
 *  Name:   Stas Batura
 *  NetID:   since85
 *  Precept: P00
 *
 *  Description:  Percolation study case
 *
 **************************************************************************** */

public class Percolation {

    // grid dimension
    private int gridDimension;

    private int[][] grid;

    private int lastElementNum;

    private QuickUnionUFWeightedM find;

    private int openSitesNumber = 0;

    public static void main(String[] args) {
        Percolation perc = new Percolation (5);
    }



    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n > 0) {
            lastElementNum = n*n+1;
            gridDimension = n;
            grid = new int[n][n];
            find = new  QuickUnionUFWeightedM (lastElementNum + 1);
            initGrid();
            open(2,1);
            open(3,1);
            open(4,1);
            boolean percolates = percolates();
            System.out.println("endInit");
        }
        else {
            IllegalArgumentException exception = new IllegalArgumentException();
            throw exception;
        }
    }

    // creating virtual nodes
    private void initGrid() {
        for (int i = 0; i < gridDimension; i++) {
            for (int j = 0; j < gridDimension; j++) {
                grid[i][j] = 0;
            }
        }
        for (int i = 1; i <= gridDimension; i++) {
            open(1, i);
        }
        for (int i = 1; i <= gridDimension; i++) {
            open(gridDimension, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row1, int col1) {
        int row = row1 -1;
        int col = col1 -1;
         if (grid[row][col] != 1) {
             grid[row][col] = 1;
             openSitesNumber ++;
             if (row == 0 ) {
                 find.union(getNodeId(row, col),0);
             } else if (row == gridDimension-1) {
                 find.union(getNodeId(row, col), lastElementNum);
             } else {
                 int currId;
                 int checkId;
                  if ( grid[row-1][col] == 1 ){
                      currId = getNodeId(row, col);
                      checkId = getNodeId(row-1, col);
                      find.union(currId, checkId);
                  } else if ( grid[row+1][col] == 1 ){
                      currId = getNodeId(row, col);
                      checkId = getNodeId(row+1, col);
                      find.union(currId, checkId);
                  } else if (col != 0 && grid[row][col-1] == 1) {
                      currId = getNodeId(row, col);
                      checkId = getNodeId(row, col-1);
                      find.union(currId, checkId);
                  } else if (col != gridDimension-1 && grid[row][col+1] == 1) {
                      currId = getNodeId(row, col);
                      checkId = getNodeId(row, col+1);
                      find.union(currId, checkId);
                  }
             }
         }
    }

    private int getNodeId (int row, int col) {
        return row *gridDimension + col +1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row-1][col-1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesNumber;
    }

    // does the system percolate?
    public boolean percolates() {
        return find.connected(0,lastElementNum);
    }


    public class QuickUnionUFWeightedM
    {
        public int[] id;
        public int[] sz;
        public QuickUnionUFWeightedM(int N)
        {
            id = new int[N];
            sz = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
            }
        }
        private int root(int i)
        {
            while (i != id[i]) {
                id[i] = id[id[i]];
                i = id[i];
            }
            return i;
        }

        public boolean connected(int p, int q)
        {
            return root(p) == root(q);
        }

        public void union(int p, int q)
        {
            int i = root(p);
            int j = root(q);
            if (i == j) return;
            if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
            else { id[j] = i; sz[i] += sz[j]; }

        }
    }


}



