import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {

    // Boolean value for blocked(false) and open(true)
    private boolean[] openArray;
    // weighted Quick union to check if a node is connected to an open (full) top site.
    private WeightedQuickUnionUF full;
    // all sites joined together, to quickly check if grid percolates
    private WeightedQuickUnionUF perc;
    // store value of n for bounds checking
    private int n;
    private int numOpenSites;

    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        // store n to use later for boundary checks
        this.n = n;
        // node for each site on n * n gird
        this.full = new WeightedQuickUnionUF(n * n);
        this.perc = new WeightedQuickUnionUF(n * n);

        // all sites are blocked to begin.
        this.openArray = new boolean[n * n];
        Arrays.fill(this.openArray, false);

        numOpenSites = 0;

        // virtual sites connected
        for (int i = 1; i < n; i++) {
            // connect all sites in top row
            perc.union(i, 0);
            full.union(i, 0);
            // connect all sites in bottom row
            int lastNode = (n * n) - 1;
            perc.union(lastNode, lastNode - i);

            /* System.out.println("connected: node " + i + " and node 0 // " +
                                       "node " + lastNode + " and node " + (lastNode - i)); */
        }


    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // user should enter site relative to n
        // e.g. 0,0 on the openArray should be input as 1,1
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("Out of bounds");
        }

        // if site has already been opened then return without updating count of open sites.
        if (isOpen(row, col)) {
            // System.out.println(row + "," + col + " Opened already");
            return;
        }

        // offset user input by 1 to get the id of row/col
        int id = (((row - 1) * n) + (col - 1));

        // System.out.println("Opening site: " + id);
        openArray[id] = true;
        numOpenSites++;

        // above site
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                // System.out.println("Connect above");
                perc.union(id, id - n);
                full.union(id, id - n);
            }
        }
        // below site
        if (row < n) {
            if (isOpen(row + 1, col)) {
                // System.out.println("Connect below");
                perc.union(id, id + n);
                full.union(id, id + n);
            }
        }

        // left site
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                // System.out.println("Connect left");
                perc.union(id, id - 1);
                full.union(id, id - 1);
            }
        }

        // right site
        if (col < n) {
            if (isOpen(row, col + 1)) {
                // System.out.println("Connect right");
                perc.union(id, id + 1);
                full.union(id, id + 1);
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("Out of bounds");
        }
        else {
            int id = (((row - 1) * n) + (col - 1));
            // System.out.println("Checking id: " + id);
            return openArray[id];
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("Out of bounds");
        }

        int id = (n * (row - 1)) + (col - 1);
        return full.connected(id, 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // System.out.println("ids: " + 0 + " " + ((n * n) - 1));
        return perc.connected(0, ((n * n) - 1));
    }

    // test client (optional)
    public static void main(String[] args) {


    }
}
