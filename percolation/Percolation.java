import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n, nOpen;
    private boolean[] site;
    private WeightedQuickUnionUF wquf, wqufF;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        int nS = n * n, nSP = nS + 1, noN = nSP + 1, nSPM = nSP - n;
        wquf = new WeightedQuickUnionUF(noN);
        wqufF = new WeightedQuickUnionUF(noN);
        site = new boolean[noN];
        site[0] = true;
        site[nSP] = true;
        nOpen = 0;
        for (int i = 1; i <= nS; i++) {
            site[i] = false;
            if (i <= n) {
                wquf.union(0, i);
                wqufF.union(0, i);
            }
            if (i >= nSPM)
                wquf.union(nSP, i);
        }
    }

    public void open(int row, int col) {
        if (isOpen(row, col))
            return;
        int rM = row - 1, cM = col - 1, rP = row + 1, cP = col + 1, x = rM * n + col, y = -1;
        site[x] = true;
        nOpen++;
        if (isValid(rM, col) && isOpen(rM, col)) {
            y = (rM - 1) * n + col;
            wquf.union(x, y);
            wqufF.union(x, y);
        }
        if (isValid(rP, col) && isOpen(rP, col)) {
            y = row * n + col;
            wquf.union(x, y);
            wqufF.union(x, y);
        }
        if (isValid(row, cM) && isOpen(row, cM)) {
            y = rM * n + cM;
            wquf.union(x, y);
            wqufF.union(x, y);
        }
        if (isValid(row, cP) && isOpen(row, cP)) {
            y = rM * n + cP;
            wquf.union(x, y);
            wqufF.union(x, y);
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();
        return site[(row - 1) * n + col];
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && (wqufF.find((row - 1) * n + col) == wqufF.find(0));
    }

    public int numberOfOpenSites() {
        return nOpen;
    }

    public boolean percolates() {
        boolean b = isConnected(n * n + 1, 0);
        if (n == 1)
            if (!isOpen(1, 1))
                b = false;
        return b;
    }

    private boolean isValid(int r, int c) {
        return (r >= 1 && r <= n) && (c >= 1 && c <= n);
    }

    private boolean isConnected(int p, int q) {
        return wquf.find(p) == wquf.find(q);
    }
}
