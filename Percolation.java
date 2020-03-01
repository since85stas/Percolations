    /* *****************************************************************************
     *  Name:   Stas Batura
     *  NetID:   since85
     *  Precept: P00
     *
     *  Description:  Percolation study case
     *
     **************************************************************************** */
    import edu.princeton.cs.algs4.WeightedQuickUnionUF;

    public class Percolation {

        // grid dimension
        private int gridDimension;

        // field array
        private int[][] grid;

        // last elem
        private final int lastElementNum;

        // finder
        private WeightedQuickUnionUF find;

        // open sites
        private int openSitesNumber = 0;

        // creates n-by-n grid, with all sites initially blocked
        public Percolation(int n) {
            if (n > 0) {
                lastElementNum = n*n+1;
                gridDimension = n;
                grid = new int[n][n];
                find = new  WeightedQuickUnionUF (lastElementNum + 1);
            }
            else {
                IllegalArgumentException exception = new IllegalArgumentException();
                throw exception;
            }
        }

        // opens the site (row, col) if it is not open already
        public void open(int row, int col) {
            if (row > 0 && row <= gridDimension && col > 0 && col <= gridDimension) {
                if (!isOpen(row, col)) {

                    //
                    openedSite(row, col);

                    openSitesNumber++;
                    if (row == 1) {
                        find.union(getNodeId(row, col), 0);
                    }
                    else if (row == gridDimension) {
                        find.union(getNodeId(row, col), lastElementNum);
                    }
                    else {
                        int currId;
                        int checkId;
                        if (isOpen(row-1, col)) {
                            currId = getNodeId(row, col);
                            checkId = getNodeId(row - 1, col);
                            find.union(currId, checkId);
                        }
                        if (row != gridDimension  && isOpen(row+1, col)) {
                            currId = getNodeId(row, col);
                            checkId = getNodeId(row + 1, col);
                            find.union(currId, checkId);
                        }
                        if (col != 1 && isOpen(row,col - 1)) {
                            currId = getNodeId(row, col);
                            checkId = getNodeId(row, col - 1);
                            find.union(currId, checkId);
                        }
                        if (col != gridDimension  && isOpen(row,col + 1)) {
                            currId = getNodeId(row, col);
                            checkId = getNodeId(row, col + 1);
                            find.union(currId, checkId);
                        }
                    }
                }
            } else {
                IllegalArgumentException exception = new IllegalArgumentException();
                throw exception;
            }
        }

        // node id
        private int getNodeId(int row, int col) {
            if (row > 0 && row <= gridDimension && col > 0 && col <= gridDimension) {
                return (row-1) *gridDimension + col;
            } else {
                IllegalArgumentException exception = new IllegalArgumentException();
                throw exception;
            }
        }

        // is the site (row, col) open?
        public boolean isOpen(int row, int col) {
            if (row > 0 && row <= gridDimension && col > 0 && col <= gridDimension) {
                return grid[row-1][col-1] == 1;
            } else {
                System.out.println("row=" + row + " col=" + col);
                IllegalArgumentException exception = new IllegalArgumentException();
                throw exception;
            }
        }

        // opennig a site
        private void  openedSite(int row, int col) {
            grid[row-1][col-1] = 1;
        }

        // is the site (row, col) full?
        public boolean isFull(int row, int col) {
            if (row > 0 && row <= gridDimension && col > 0 && col <= gridDimension) {
                return isOpen(row, col) && find.connected(getNodeId(row, col),0);
            } else {
                IllegalArgumentException exception = new IllegalArgumentException();
                throw exception;
            }
        }

        // returns the number of open sites
        public int numberOfOpenSites() {
            return openSitesNumber;
        }

        // does the system percolate?
        public boolean percolates() {
            return find.connected(0, lastElementNum);
        }


    }

