import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    boolean[][] grid;
    int openSites; //open sites have the state of true, blocked sites have the state of false
    int size;
    WeightedQuickUnionUF uf;
    WeightedQuickUnionUF ufperc;
    int virtualTop, virtualBottom;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        grid = new boolean[N][N];
        openSites = 0;
        this.size = N;
        uf = new WeightedQuickUnionUF(N * N + 1);
        ufperc = new WeightedQuickUnionUF(N * N + 2);
        virtualTop = N * N;
        virtualBottom = N * N + 1;
    }

    public void open(int row, int col) {
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSites++;
        } else {
            return;
        }
        int gridIndex = xyTo1D(row, col);

        if (row == 0) {
            uf.union(gridIndex, virtualTop);
            ufperc.union(gridIndex, virtualTop);
        }
        if (row == size - 1) {
            ufperc.union(gridIndex, virtualBottom);
        }

        connect(row, col, row - 1, col);
        connect(row, col, row + 1, col);
        connect(row, col, row, col - 1);
        connect(row, col, row, col + 1);
    }

    private int xyTo1D(int row, int col) {
        return (row * size) + (col);
    }

    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        return uf.find(xyTo1D(row, col)) == uf.find(virtualTop);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return ufperc.find(virtualTop) == ufperc.find(virtualBottom);
    }

    public void connect(int row, int col, int rowToConnect, int colToConnect) {
        if (rowToConnect >= 0 && rowToConnect < size
                && colToConnect >= 0 && colToConnect < size
                && isOpen(rowToConnect, colToConnect)) {
            uf.union(xyTo1D(row, col), xyTo1D(rowToConnect, colToConnect));
            ufperc.union(xyTo1D(row, col), xyTo1D(rowToConnect, colToConnect));
        }
    }
}
