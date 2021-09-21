import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    private final int size;
    private final int gridLen;
    private boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private int openGrids;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        size = n * n;
        openGrids = 0;
        gridLen = n;
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(size + 2); // +2 for to and buttom id[]

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > gridLen || col <= 0 || col > gridLen) {
            throw new IllegalArgumentException();
        }
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openGrids++;
        }

        if (row == 1) {
            // connect to top row
            uf.union(getGridIdx(row, col), 0);
        }

        if (row == gridLen) {
            // connect to bottom
            uf.union(getGridIdx(row, col), size + 1);
        }
        // if open connect to right cell
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(getGridIdx(row, col), getGridIdx(row - 1, col));
        }
        // if open connect to left cell
        if (row < gridLen && isOpen(row + 1, col)) {
            uf.union(getGridIdx(row, col), getGridIdx(row + 1, col));
        }
        // if open connect to top neighbor cell
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(getGridIdx(row, col), getGridIdx(row, col - 1));
        }
        // if open connect to bottom neighbor cell
        if (col < gridLen && isOpen(row, col + 1)) {
            uf.union(getGridIdx(row, col), getGridIdx(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (row <= 0 || row > gridLen || col <= 0 || col > gridLen) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // check if connected to the top
        if (row <= 0 || row > gridLen || col <= 0 || col > gridLen) {
            throw new IllegalArgumentException();
        }

        return uf.find(getGridIdx(row, col)) == uf.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openGrids;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(size + 1) == uf.find(0);
    }

    private int getGridIdx(int row, int col) {
        return (gridLen * (row - 1)) + col;
    }

    // test client (optional)
    // public static void main(String[] args) {
    //    Percolation p = new Percolation(4);
    //}
}
